(function () {
    'use strict';

    angular
        .module('app')
        .controller('register', RegisterController);

    RegisterController.$inject = ['UserService', '$location', '$rootScope', 'FlashService','$anchorScroll'];
    function RegisterController(UserService, $location, $rootScope, FlashService,$anchorScroll) {
        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                  
                    if (response.success) {
                            $location.path('/login');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                        $anchorScroll();
                    }
                });
        }
    }

})();
