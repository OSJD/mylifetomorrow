(function () {
    'use strict';
    
	angular.module('app').controller('NewRequestCtrl', NewRequestCtrl);
	
	NewRequestCtrl.$inject = ['FlashService','UserService','$scope','$uibModal', '$log','$rootScope','$window'];
	function NewRequestCtrl(FlashService,UserService,$scope,$uibModal, $log,$rootScope,$window) {
		
		var vm = this;		  
	    vm.editProfileModal = editProfileModal;
	    vm.addProfileModal=addProfileModal;
	    vm.newQuestionModal=newQuestionModal;
	    vm.getProfileQuestions=getProfileQuestions;
	    vm.payment=payment;
	    $rootScope.mapobj={};	
	    $scope.profileLoaded=false;
	   
	    vm.hideProfile=true;
	    vm.profileDetail={};
	    $rootScope.depDetails=[];
	    var errorhandling={}
	    
	    /*
	     * Wait for response
	     */
	    var responseLoaded=false;
	    var waitForResponse=function(){
	    	if(!responseLoaded){
	    		setTimeout(waitForResponse,10);
	    	}
	    }

	    UserService.GetDepProfiles().then(function (response) {
	    	for(var profile in response.profiles){
	    		$rootScope.depDetails.push(response.profiles[profile]);
	    		$rootScope.mapobj[response.profiles[profile].depId]=response.profiles[profile];
	    	}
	    	responseLoaded=true;
	    });


	    vm.status = {
	    		isProfileHeaderOpen: false,
	    	    isLifeSolutionHeaderOpen: false
	    };
	    
	    vm.toggled = function(open) {
	    	FlashService.clear();
	    	if( vm.profileDetail==undefined){
	    		vm.hideProfile=true; 
	    		FlashService.clear();
	    	} else{
	    		if(vm.profileDetail.depId==undefined){
	    			vm.hideProfile=true;
	    		} else {
	    			if(vm.profileDetail.depId==0){
	    				vm.hideProfile=true;
	    			}
	    		}
	    	}
	    	waitForResponse();
	    };
	  
	    
	  
	    vm.openProfile=function(depId){
	    	if(depId==0){
	    		vm.profileDetail=undefined;
	    		vm.addProfileModal();
	    	} else {
	    		vm.dataLoading=true;
	    		vm.hideProfile=false;
	    		vm.status.isProfileHeaderOpen= false;
	    		vm.status.isLifeSolutionHeaderOpen= false;
	    		vm.profileDetail=$rootScope.mapobj[depId];

	    		responseLoaded=false;
		    	UserService.GetProfileById(depId,function (response) {
		    		responseLoaded=true;
		       		for(var profileField in response.data){
		       			vm.profileDetail[profileField]=response.data[profileField];
		       		}
		       		var place=vm.profileDetail.depPlaceOfBirth;
		       		if(place){
		       			vm.profileDetail.depPlaceOfBirth=place.replace(/,/g , ", ");
		       		}
		       		vm.dataLoading=false;
		    	});
		    	
		    	waitForResponse();
	    	}
	    }
	  
	    vm.status = {
			    isFirstOpen: true,
			    isFirstDisabled: false
	    };
	    
	    
	    function payment(profile){
	    	if(profile.depId==undefined){
	    		errorhandling.message="Please select profile and pay!";
	    		FlashService.Error(errorhandling.message);
	    	} else {
	    		$window.open('https://www.paypal.me/MyLifeTomorrow/30', 'C-Sharpcorner', 'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,top=500,left=500,width=600,height=600');
	    	}
	    }
	    

	    function getProfileQuestions(isLifeSolutionHeaderOpen,depId){
	    	if(isLifeSolutionHeaderOpen){
		    	UserService.GetQuestions(depId,function(response){
		    		if(response.data.success){
		    			$rootScope.questions=response.data.questions;
		    		} else{
		    			$rootScope.questions=response.data.questions;
		    		}
		    	 });
	    	}else{
	    		$rootScope.questions={};
	    	}
	    }
	  
	    function editProfileModal(profile) {

			$uibModal.open({
			     templateUrl: 'editProfileModalContent.html',
			     controller: ['$scope','$rootScope','$uibModalInstance','UserService','FlashService','profile', ProfileModalCtrl],
			     controllerAs: 'vm',
			     size :'lg',
			     resolve: {
			        profile: function() { return profile; }
			     }
			});
	    }
	    
	    function addProfileModal() {
	      var profile={ };
		  $uibModal.open({
		      templateUrl: 'addProfileModalContent.html',
		      controller: ['$scope','$rootScope','$uibModalInstance','UserService','FlashService','profile', ProfileModalCtrl],
		      controllerAs: 'vm',
		      size :'lg',
		      resolve: {
			        profile: function() { return profile; }
			      }
		    });
	    }
	    
	    function newQuestionModal(profile) {
	    	vm.status.isLifeSolutionHeaderOpen=false;
	    	if(profile.depId==undefined){
	    		errorhandling.message="Please select profile and chat with Guru!";
	    		FlashService.Error(errorhandling.message);
	    	} else {
	    		
	    		  $uibModal.open({
				      templateUrl: 'newQuestionModalContent.html',
				      controller: ['$scope','$rootScope','$uibModalInstance','UserService','FlashService','profile', ProfileModalCtrl],
				      controllerAs: 'vm',
				      //windowClass: 'app-qmodal-window'
				      size :'lg',
				      resolve: {
					        profile: function() { return profile; }
					      }
				    });
	    	  }
		  }
	    
	    vm.deleteProfile=function(depId){
	
	        if (confirm("Are you sure to delete the profile?") == true) {
		    	UserService.DeleteProfile(depId,function(response){
		    		var status=response.data.success;
		    		responseLoaded=true;
		    		var indexObj = $rootScope.mapobj[depId];
		    		var index = $rootScope.depDetails.indexOf(indexObj);
		    		$rootScope.depDetails.splice(index, 1);
		    		$rootScope.mapobj[depId]={};
		    		if(status){
			    		errorhandling.message="Profile("+indexObj.depFName+") deleted successfully!";		    		
			    		FlashService.Success(errorhandling.message);
			    	}else{
			    		errorhandling.message="Profile("+indexObj.depFName+") failed to delete";
			    		FlashService.Error(errorhandling.message);
			    	}
		    		
			    	vm.hideProfile=true;
		    	});
	        } 
	    }
	    
	    vm.viewcomments=function(question){
	      var profile=$rootScope.mapobj[question.profileId];
	      UserService.UserChatConversation(question.questionId,function(response){
	    	  if(response.data.success){
	    		  profile.chatConversation=response.data.chatConversation;
	    		  profile.currentQuestionId=question.questionId;
	    		  profile.currentQuestionStatus=question.replied;

	      		  $uibModal.open({
	    		      templateUrl: 'newQuestionModalContent.html',
	    		      controller: ['$scope','$rootScope','$uibModalInstance','UserService','FlashService','profile', ProfileModalCtrl],
	    		      controllerAs: 'vm',
	    		      size :'lg',
	    		      resolve: {
	    			        profile: function() { return profile; }
	    			      }
	    		    });
	    	  }
	      });

	    }
	    
	    vm.deleteQuestion=function(questionId){
	    	if (confirm("Are you sure to delete the Question? All conversations with Guru will be deleted permanently!") == true) {
		    	UserService.DeleteQuestion(questionId,function(response){
		    		if(response.data.success){
			    		errorhandling.message="Question deleted successfully!";		    		
			    		FlashService.Success(errorhandling.message);
		    		}else {
			    		errorhandling.message="Question failed to delete";
			    		FlashService.Error(errorhandling.message);	
		    		}
		    		
		    	});
	    	}
	    }
	    
	    
   

	}
	
	function ProfileModalCtrl($scope,$rootScope,$modalInstance,UserService,FlashService,profile) {
		  var vm = this;
		  vm.chatHistory=[];
		  if(profile.chatConversation!=undefined && profile.chatConversation.length>0){
			  vm.chatHistory=profile.chatConversation;
		  }
		  
		  vm.questionReplied=profile.currentQuestionStatus;
		  
		  if(profile.depDOB instanceof Date){
			  
		  }else{
			  profile.depDOB=new Date(new Date(profile.depDOB));
		  }
		  
		  vm.profile = profile;
		  vm.question={};
		  vm.dataLoading=false;
		  vm.newProfile=function(depId,depLName,depFName,relationShipCode){
			  return {
				  "depId":depId,
				  "depFName":depFName,
				  "depLName":depLName,
				  "relationShipCode":relationShipCode
				  }
		  };
		  var errorhandling={};
		  
		  vm.save=function(){
			  
			  if(!addProfileValidation(profile)) {
				  return false;
			  } else {
				  UserService.CreateProfile(profile,function(response){
					  if(response.data.success){
						  if(response.data.currentProfile.depId==undefined){
							  errorhandling.message="Failed to create Profile("+profile.depFName+")!"; 
							  FlashService.Error(errorhandling.message);
						  }else{
							  var newObj=vm.newProfile(response.data.currentProfile.depId,
									  response.data.currentProfile.depLName,
									  response.data.currentProfile.depFName,
									  response.data.currentProfile.relationShipCode);
							  $rootScope.depDetails.push(newObj);
							  $rootScope.mapobj[response.data.currentProfile.depId]=newObj;
							  errorhandling.message="Profile("+profile.depFName+") created successfully!"; 
							  FlashService.Success(errorhandling.message);
						  }
						  $modalInstance.close();	
					  } else {
						  errorhandling.message="Please enter all red(*) fields!"; 
						  FlashService.Error(errorhandling.message); 
					  }
				  });
			  }
		  }
		  
		  function addProfileValidation(profile){
			  if(profile.depDOB==undefined) {
				  vm.dobformaterror=true;
				  return false;
			  } else {
				  vm.dobformaterror=false;
				  var todaysDate = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
				  
				  if(profile.depDOB instanceof Date){
				  }else{
					  profile.depDOB=new Date(new Date(profile.depDOB).getTime() + 24 * 60 * 60 * 1000);
				  }
				  if(profile.depDOB.setHours(0,0,0,0) >= todaysDate.setHours(0,0,0,0)){
					  vm.dobdateerror=true;
					  return false;
				  } else{
					  vm.dobdateerror=false;
					  var dob=profile.depDOB;
					  profile.depDOB=dob.getFullYear()+"-"+(dob.getMonth()+1)+"-"+dob.getDate();
					  return true;
				  }
			  }
		  }
		  
		  vm.edit=function(){
			  
			  if(!addProfileValidation(profile)) {
				  return false;
			  } else {
				  FlashService.clear();
				  UserService.UpdateProfile(profile,function(response){ });
				  $modalInstance.close();
			  }
		  }
		  
		  vm.addQuestion=function(profileId){
			  if(vm.chatHistory.replied){
				  vm.question.replied=vm.chatHistory.replied;
			  }else{
				  vm.question.replied=false;
			  }
			  var currentQuestionId=vm.question.questionId!=undefined?vm.question.questionId:(profile.currentQuestionId!=undefined?profile.currentQuestionId:0);
			  vm.dataLoading=true;
			  var question={
					  profileId:profileId,
					  questionId:currentQuestionId,
					  question:vm.question.quesDes,
					  datePosted:new Date(),
					  postedBy:profile.depLName+" "+profile.depFName
			  }
			  UserService.PostQuestion(question,function(response){
				 if(response.data.success){
					  errorhandling.message="Question posted successfully! Please click on 'Life Solutions' tab below to view all your questions."; 
					  FlashService.Success(errorhandling.message);
					  vm.chatHistory.push(response.data.currentQuestion);
					  vm.question.questionId=response.data.currentQuestion.questionId;
				 } else {
					  errorhandling.message=response.data.message;
					  FlashService.Error(errorhandling.message);
				 }
			  });
			  vm.question.quesDes=null;
			  document.getElementById('message').focus();
		  }
		  
		  
		  vm.verifyAddress=function(){
			  FlashService.clear();
			  if(vm.profile.depPlaceOfBirth.length>0){
				  vm.dataLoading=true;
				  vm.verifyAddBtn=true;
				  UserService.VerifyAddress(vm.profile.depPlaceOfBirth,function(response){
					  vm.dataLoading=false;
					  vm.verifyAddBtn=false;

					 if(response.data.status=='ZERO_RESULTS'){
						  errorhandling.message="Please enter valid address to verify!";
						  FlashService.Error(errorhandling.message);
					 } else {
						 	if(response.data.hasOwnProperty('northeast')){
								  var ne_lat=response.data["northeast"]["lat"];
								  var ne_lng=response.data["northeast"]["lng"];
								  var sw_lat=response.data["southwest"]["lat"];
								  var sw_lng=response.data["southwest"]["lng"];
								  
								  vm.profile.depPlaceLongitudeNE="NE :"+parseFloat(ne_lng).toFixed( 2 );
								  vm.profile.depPlaceLatitudeSW="SW :"+parseFloat(sw_lat).toFixed( 2 );
								  vm.profile.depPlaceLongitudeSW="SW :"+parseFloat(sw_lng).toFixed( 2 );
								  vm.profile.depPlaceLatitudeNE="NE :"+parseFloat(ne_lat).toFixed( 2 );

								  errorhandling.message="Address verified successfully! Please save the changes."; 
								  FlashService.Success(errorhandling.message);
							}else{
								  errorhandling.message="Please enter valid address to verify!";
								  FlashService.Error(errorhandling.message);
							}

					 }
				  });
			  } else {
				  vm.verifyAddBtn=false;
				  errorhandling.message="Please enter valid address to verify!";
				  FlashService.Error(errorhandling.message);
			  }
		  }
		  
		    /*DATE PICKER*/
		    
    	  $scope.inlineOptions = {
    	    customClass: getDayClass,
    	    minDate: new Date(),
    	    showWeeks: true
    	  };

		  $scope.dateOptions = {
		    customClass: getDayClass,
		    minDate: new Date(),
		    showWeeks: true
		  };


    	  $scope.toggleMin = function() {
    	    $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
    	    $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    	  };

    	  $scope.toggleMin();

    	  $scope.dobCalendar = function() {
    	    $scope.popup1.opened = true;
    	  };


    	  $scope.popup1 = {
    	    opened: false
    	  };


    	  var tomorrow = new Date();
    	  tomorrow.setDate(tomorrow.getDate() + 1);
    	  var afterTomorrow = new Date();
    	  afterTomorrow.setDate(tomorrow.getDate() + 1);
    	  $scope.events = [
    	    {
    	      date: tomorrow,
    	      status: 'full'
    	    },
    	    {
    	      date: afterTomorrow,
    	      status: 'partially'
    	    }
    	  ];

    	  function getDayClass(data) {
    	    var date = data.date,
    	      mode = data.mode;
    	    if (mode === 'day') {
    	      var dayToCheck = new Date(date);

    	      for (var i = 0; i < $scope.events.length; i++) {
    	        var currentDay = new Date($scope.events[i].date);

    	        if (dayToCheck === currentDay) {
    	          return $scope.events[i].status;
    	        }
    	      }
    	    }

    	    return '';
    	  }
    	  
    	  /*TIME PICKER*/
    	  
   	  
    	  if(profile.depTime!=undefined){    		  
    		  vm.dobTime=new Date(0,0,0,profile.depTime.split(":")[0],profile.depTime.split(":")[1],0,0); 
    	  } else {
    		  vm.dobTime=null;
    	  }

    	  
    	  $scope.ismeridian = true;
    	  $scope.toggleMode = function() {
    	    $scope.ismeridian = ! $scope.ismeridian;
    	  };


    	  $scope.changed = function () {
    		if(vm.dobTime==undefined){
    			 vm.tobformaterror=true;
    		} else{
    			vm.tobformaterror=false;
	    	    var dobTime=vm.dobTime.getHours()+":"+vm.dobTime.getMinutes();
	    	    vm.profile.depTime=dobTime;
    		}
    	  };

	}

	

})();