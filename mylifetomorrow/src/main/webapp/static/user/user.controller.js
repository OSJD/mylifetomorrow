(function () {
    'use strict';

    angular
        .module('app')
        .controller('user', UserDetailsController);

    UserDetailsController.$inject = ['AuthenticationService','UserService', '$location', '$rootScope', 'FlashService','$cookieStore'];
    function UserDetailsController(AuthenticationService,UserService, $location, $rootScope, FlashService,$cookieStore) {
        var vm = this;

        vm.logout=logout;
        
        var userStatus=false;
        checkUserLoad();
        function loadCurrentUser(){
	    	UserService.GetUser(function (response) {
    		    vm.userDetails=response.data;
	    		userStatus=true
	    	});
        }
        
        loadCurrentUser();
        
	    function checkUserLoad(){
	    	if(!userStatus){
	    		setTimeout(checkUserLoad, 10);
	    	}
	    }

	    if($cookieStore.get('globals')==undefined){
	       $location.path('/');
	    }

        
        function logout() {
            vm.dataLoading = true;
            UserService.logout(function (response) {
                    AuthenticationService.ClearCredentials();
                    vm.dataLoading = false;
                    $location.path('/');

                });
        };
        
        
    }

})();
