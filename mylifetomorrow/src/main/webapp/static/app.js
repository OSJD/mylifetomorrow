(function () {
    'use strict';

    angular
        .module('app', ['ezfb','ngRoute', 'ngCookies','ngAnimate','ui.bootstrap'])
        .config(config)
        .run(run);

    config.$inject = ['ezfbProvider','$routeProvider', '$locationProvider'];
    function config(ezfbProvider,$routeProvider, $locationProvider) {
    	
    	ezfbProvider.setInitParams({
    		    appId: '1725359874401515',
    		    version: 'v2.6'
    	});
    	
		$routeProvider.when('/', {
	        templateUrl: '/static/home/home.view.html',
	        controller: 'home',
	        controllerAs: 'vm'
	    }).when('/login', {
			templateUrl : '/static/login/login.view.html',
			controller : 'login',
			controllerAs : 'vm'
		}).when('/register', {
	        templateUrl: '/static/register/register.view.html',
	        controller: 'register',
	        controllerAs: 'vm'
	    }).when('/userHome', {
	        templateUrl: '/static/user/user.view.html',
	        controller: 'user',
	        controllerAs: 'vm'
	    }).when('/admin', {
	        templateUrl: '/static/admin/admin.view.html',
	        controller: 'adminuser',
	        controllerAs: 'vm'
	    }).otherwise({ redirectTo: '/' });
    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
        	$http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
        	var availableURLs=['/home','/login', '/register','/userHome','/admin'];
            var restrictedPage = availableURLs.indexOf($location.path()) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage) {
                $location.path('/');
            } //else if(!loggedIn){
              //  $location.path('/login');
            //}
        });
    }

})();