(function () {
    'use strict';

    angular
        .module('app')
        .controller('login', LoginController);
    

    LoginController.$inject = ['$location', 'AuthenticationService', 'FlashService','$route','$anchorScroll'];
    function LoginController($location, AuthenticationService, FlashService,$route,$anchorScroll) {
        var vm = this;
        
    	vm.tab = function(route) {
    		return $route.current && route === $route.current.controller;
    	};
    	
        vm.login = login;
        vm.forgotpassword=forgotpassword;
  	  	vm.credentials = {};
  	  	

        (function initController() {
            // reset login status
            AuthenticationService.ClearCredentials();
        })();
        
        function forgotpassword(){
        	if(vm.credentials.username==undefined){
                FlashService.Error("Please enter Username/Email id to email your password.");
        	}else{
        		var user={
        				username:vm.credentials.username
        		};
        		vm.dataLoading=true
        		AuthenticationService.ForgotPassword(user,function(response){
        			if(response.data.success){
        				FlashService.Success(response.data.message);
        			} else {
        				FlashService.Error("Email id does not exist. If the error still persists, please email to haasinigroups@gmail.com");
        			}
        			vm.dataLoading=false;
        		});
        	}
        }

        function login() {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.credentials, function (response) {

                if (response.data!=null) {
                	if(response.data.success){
	                    AuthenticationService.SetCredentials(vm.credentials);
	                    if(response.data.message=='ROLE_USER'){
	                    	$location.path('/userHome');
	                    } else {
	                    	$location.path('/admin');
	                    }
                	} else {
                   		response.message="Login authentication failed!";
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                	}
                } else {
               		response.message="Login authentication failed!";
                    FlashService.Error(response.message);
                    vm.dataLoading = false;
                }
            });
        };
        
       
    }

})();
