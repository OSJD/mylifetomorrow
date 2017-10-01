(function () {
    'use strict';

    angular
        .module('app')
        .controller('menu', MenuController);

    MenuController.$inject = ['$scope', '$location', '$anchorScroll'];
    function MenuController($scope, $location, $anchorScroll) {

        $scope.gotoAbout = function() {
            $location.hash('about');
            $anchorScroll();
        };
        
        $scope.gotoServices = function() {
            $location.hash('services');
            $anchorScroll();
        };
        
        $scope.gotoContact = function() {
          $location.hash('contact');
          $anchorScroll();
        };
    }

})();