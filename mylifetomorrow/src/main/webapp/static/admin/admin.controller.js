(function () {
    'use strict';

    angular
        .module('app')
        .controller('adminuser', AdminController)
        .directive('fileModel', ['$parse', function ($parse) {
			    return {
			        restrict: 'A',
			        link: function(scope, element, attrs) {
			            var model = $parse(attrs.fileModel);
			            var modelSetter = model.assign;
			            
			            element.bind('change', function(){
			                scope.$apply(function(){
			                    modelSetter(scope, element[0].files[0]);
			                });
			            });
			        }
			    };
        	}]);

    AdminController.$inject = ['UserService', '$location', '$rootScope','$scope', 'FlashService','$anchorScroll','$window','$uibModal', '$log'];
    function AdminController(UserService, $location, $rootScope,$scope, FlashService,$anchorScroll,$window,$uibModal) {
        var vm = this;
        vm.searchCriteria;
        vm.dataLoading = true;
        vm.openProfile=openProfile;
        vm.getAllPendQuestions=getAllPendQuestions;
        vm.searchProfile=searchProfile;
        vm.newQuestionModal=newQuestionModal;
        vm.deleteQuestion=deleteQuestion;
        vm.viewProfile=viewProfile;
        vm.viewcomments=viewcomments;
        vm.searchResults=[];
		$rootScope.chatHistory=[];
		var errorhandling={};
		$rootScope.questions=[];
        
        $rootScope.profiles=[];

	    UserService.GetPendProfiles(function (response) {
	    	for(var profile in response.data.profiles){
	    		$rootScope.profiles.push(response.data.profiles[profile]);
	    	}
	    	 vm.dataLoading = false;
	    });
	    
	    function viewProfile(profileId){
	    	openProfile({
	    		depId:profileId
	    	});
	    }
	    
	    function viewcomments(question){		  

	      var profile={
	    		  currentQuestionId:question.questionId,
	    		  depId:question.profileId
	      };
	      UserService.UserChatConversation(question.questionId,function(response){
	    	  if(response.data.success){
	    		  $rootScope.chatHistory=response.data.chatConversation;
	      		  $uibModal.open({
	    		      templateUrl: 'newQuestionModalContent.html',
	    		      controller: ['$rootScope','$uibModal','$scope','$uibModalInstance','UserService','profile','FlashService',ProfileModalCtrl],
	    		      controllerAs: 'vm',
	    		      size :'lg',
	    		      resolve: {
	    			        profile: function() { return profile; }
	    			      }
	    		    });
	    	  }
	      });

	    

		  
	    }
	    
	    function deleteQuestion(questionId){

	    	if (confirm("Are you sure to delete the Question? All conversations with Guru will be deleted permanently!") == true) {
		    	UserService.DeleteQuestion(questionId,function(response){
		    		if(response.data.success){
			    		errorhandling.message="Question deleted successfully!";		    		
			    		FlashService.Success(errorhandling.message);
			    		var questions=[];
			    		for(var index in $rootScope.questions){
			    			if($rootScope.questions[index].questionId==questionId){
			    				
			    			}else{
			    				questions.push($rootScope.questions[index]);
			    			}
			    		}
			    		$rootScope.questions=questions;

		    		}else {
			    		errorhandling.message="Question failed to delete";
			    		FlashService.Error(errorhandling.message);	
		    		}
		    		
		    	});
	    	}
	    
	    }
	    
	    function searchProfile(){
	    	FlashService.clear();
	    	vm.searchResults=[];
	      	UserService.SearchProfile(vm.searchCriteria,function (response) {
	      		vm.searchCriteria=null;
	      		for(var index in response.data){
	      			vm.searchResults.push(response.data[index]);
	      		}
	    	});
	    }
	    
	    function newQuestionModal(profile) {
	    	$rootScope.chatHistory=[];
	    	profile.currentQuestionId=0;
    		  $uibModal.open({
			      templateUrl: 'newQuestionModalContent.html',
			      controller: ['$rootScope','$uibModal','$scope','$uibModalInstance','UserService','profile','FlashService',ProfileModalCtrl],
			      controllerAs: 'vm',
			      size :'lg',
			      resolve: {
					        profile: function() { 
					        	return profile; 
					        }
			      		}
			    });

		  }
	    
	    function getAllPendQuestions(){
	      	UserService.GetAllQuestions(function (response) {
	      		$rootScope.questions=response.data.pendingQuestions;
	    	});
	    }
	    
	    function openProfile(profile) {
	    	
	    	FlashService.clear();
	    	
	    	var responseLoaded=false;
	    	UserService.GetProfileById(profile.depId,function (response) {
	    		profile=response.data;
	    		responseLoaded=true;

	    	});
	    	
	    	var waitForResponse=function(){
	    		if(!responseLoaded){
	    			setTimeout(waitForResponse,10);
	    		} else {
	    			$uibModal.open({
	   			     templateUrl: 'verifyProfileModalContent.html',
	   			     controller: ['$rootScope','$uibModal','$scope','$uibModalInstance','UserService','profile','FlashService',ProfileModalCtrl],
	   			     controllerAs: 'vm',
	   			     size :'lg',
	   			     resolve: {
	   				        profile: function() { return profile; }
	   				     }
	    			});
	    		}
	    	}
	    	
	    	waitForResponse();
	
	    }
	    
		  vm.save=function(){
			  $modalInstance.close();
		  }
        
    }
    
    
	function ProfileModalCtrl($rootScope,$uibModal,$scope,$modalInstance,UserService,profile,FlashService) {
		  var vm = this;
		  vm.profile=profile;
		  vm.uploadFile=uploadFile;
		  vm.save=save;
		  vm.dataLoading=false;
		  var errorhandling={};
		  vm.addQuestion=addQuestion;

		  
		  function addQuestion(){
			  vm.dataLoading=true;
			  var question={
					  profileId:profile.depId,
					  questionId:profile.currentQuestionId,
					  question:vm.question.quesDes,
					  datePosted:new Date(),
					  replied:vm.question.replied,
					  postedBy:"Guru Admin"
			  }
			  UserService.PostQuestion(question,function(response){
				 if(response.data.success){
					  errorhandling.message="Question posted successfully!"; 
					  $rootScope.chatHistory.push(response.data.currentQuestion);
					  if($rootScope.questions==undefined){
						  $rootScope.questions=[];
					  }
					  if(profile.currentQuestionId==response.data.currentQuestion.questionId){
						  //
					  }else{
						  $rootScope.questions.push(response.data.currentQuestion);
					  }
					  vm.question.questionId=response.data.currentQuestion.questionId;
					  if(vm.question.replied==true){
						  errorhandling.message="Conversation closed successfully!"; 
						  var newquestionlist=[];
						  for(var index in $rootScope.questions){
							  if($rootScope.questions[index].questionId==profile.currentQuestionId){
								  
							  } else{
								  newquestionlist.push($rootScope.questions[index]);
							  }
						  }
						  $rootScope.questions=newquestionlist;
						  $modalInstance.close();
					  }
					  FlashService.Success(errorhandling.message);
				 } else {
					  errorhandling.message=response.data.message;
					  FlashService.Error(errorhandling.message);
				 }
			  });
			  vm.question.quesDes=null;
			  document.getElementById('message').focus();
		  }
		  
		  function uploadFile(){
		        var file = $scope.myFile;
		        vm.dataLoading=true;
		        UserService.uploadFileToUrl(file,profile.depId,function(response){
		        	vm.dataLoading=false;
		        	if(response.data.success){
		        		vm.profile.chartImgData=response.data.message;
			    		errorhandling.message="Birth chart uploaded successfully!";
			    		FlashService.Success(errorhandling.message);
		        	} else {
			    		errorhandling.message=response.data.message;
			    		FlashService.Error(errorhandling.message);
		        	}
		        });
		  }
		  
		  function save(){
			  if(!$scope.myFile){
				  if(vm.profile.chartImgData==null){
		    		errorhandling.message="Please upload Birth chart.";
		    		FlashService.Error(errorhandling.message);
		    		return;
				  }
			  }
			  if(vm.profile.chartImgData==null){
		    		errorhandling.message="Please upload Birth chart and then save profile!";
		    		FlashService.Error(errorhandling.message);
		    		return;
			  }else{
				  if(!vm.profile.verified){
			    		errorhandling.message="Profile check verification, analysis and investigation checkbox!";
			    		FlashService.Error(errorhandling.message);
			    		return;  
				  }else{
					  FlashService.clear();
					  vm.dataLoading=true;
					  UserService.completeVerification(profile,function(response){
				    		errorhandling.message="Profile verification saved successfully!";
				    		FlashService.Success(errorhandling.message);
				    		vm.dataLoading=false;
					  });
				  }
			  }
		  }

	}

})();
