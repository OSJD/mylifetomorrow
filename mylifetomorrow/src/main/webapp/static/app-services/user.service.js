(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};

        service.GetDepProfiles = GetDepProfiles;
        service.Create = Create;
        service.logout=logout;
        service.GetUser=GetUser;
        service.GetProfileById=GetProfileById;
        service.UpdateProfile=UpdateProfile;
        service.CreateProfile=CreateProfile;
        service.DeleteProfile=DeleteProfile;
        service.GetPendProfiles=GetPendProfiles;
        service.uploadFileToUrl=uploadFileToUrl;
        service.completeVerification=CompleteVerification;
        service.GetQuestions=GetQuestions;
        service.PostQuestion=PostQuestion;
        service.GetAllQuestions=GetAllQuestions;
        service.VerifyAddress=VerifyAddress;
        service.DeleteQuestion=DeleteQuestion;
        service.UserChatConversation=UserChatConversation;
        service.SearchProfile=SearchProfile;

        return service;
        
        function SearchProfile(searchCriteria,callback){
            return $http.get('/profile/search/'+searchCriteria).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        function DeleteQuestion(questionId,callback){
            return $http.get('/profile/deletequestion/'+questionId).then(function(response) {
        	      callback && callback(response);
        	    }, function(response) {
        	      callback && callback(response);
        	    });
          }
        
        function VerifyAddress(address,callback){
            return $http.post('/profile/verifyaddress', address).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        
        function GetAllQuestions(callback){
            return $http.get('/profile/questions').then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        function GetQuestions(profileId,callback){
            return $http.get('/profile/question/'+profileId).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        function PostQuestion(question,callback){
            return $http.post('/profile/postquestion', question).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        function UserChatConversation(questionId,callback){
            return $http.get('/profile/viewcomments/'+questionId).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
              
        function CompleteVerification(profile,callback){
            return $http.post('/admin/verification', profile).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        function uploadFileToUrl(file,profileId,callback){
            var fd = new FormData();
            fd.append('file', file);
            $http.post('/admin/fileUpload/'+profileId, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
        }

        function GetPendProfiles(callback) {
            return $http.get('/admin/verify').then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
        }

        function GetDepProfiles() {
            return $http.get('/dependents').then(handleSuccess, handleError('Error getting all users'));
        }

        function Create(user) {
            return $http.post('/registerUser', user).then(handleCreateSuccess, handleError('Error creating user'));
        }
        
        function UpdateProfile(profile,callback) {
            return $http.post('/profile/update', profile).then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
        }
        
        
        function CreateProfile(profile,callback) {
            return $http.post('/profile/create', profile).then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
        }
        
        function GetUser(callback) {
            return $http.get('/loggedInUser').then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
        }
        
        function GetProfileById(depId,callback) {
            return $http.get('/profile/'+depId).then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
        }
        
        function DeleteProfile(depId,callback){
            return $http.get('/profile/delete/'+depId).then(function(response) {
      	      callback && callback(response);
      	    }, function(response) {
      	      callback && callback(response);
      	    });
        }
        
        

        function handleCreateSuccess(res){
        	return res.data;
        }
        
        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
        
  	  function logout(callback) {
    	    $http.get('http://mylifetomorrow-haasinigroups.rhcloud.com/logoutUser').then(function(response) {
    	      callback && callback(response);
    	    }, function(response) {
    	      callback && callback(response);
    	    });
  	  };
    }

})();
