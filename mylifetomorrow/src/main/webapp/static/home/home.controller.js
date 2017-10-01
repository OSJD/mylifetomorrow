(function () {
    'use strict';

    angular
        .module('app')
        .controller('home', HomeController);

    HomeController.$inject = ['UserService', '$rootScope','$scope'];
    function HomeController(UserService, $rootScope,$scope) {
        var vm = this;
        
        $scope.noWrapSlides = false;
        $scope.active = 0;
        var slides = $scope.slides = [];
        var currIndex = 0;

        $scope.addSlide = function() {
          var newWidth = 600 + slides.length + 1;
          slides.push({
            image: './static/img/slider/img-'+newWidth+'.jpg',
            text: ['Career path and growth','Wedding and Marriage alliance','Onsite Opportunities','Permanent residency and Citizenship','Health Conditions','Wealth and Finance prediction','Family Astrology and Horoscope'][slides.length % 7],
            id: currIndex++
          });
        };

        $scope.randomize = function() {
          var indexes = generateIndexesArray();
          assignNewIndexesToSlides(indexes);
        };

        for (var i = 0; i < 7; i++) {
          $scope.addSlide();
        }

        // Randomize logic below

        function assignNewIndexesToSlides(indexes) {
          for (var i = 0, l = slides.length; i < l; i++) {
            slides[i].id = indexes.pop();
          }
        }

        function generateIndexesArray() {
          var indexes = [];
          for (var i = 0; i < currIndex; ++i) {
            indexes[i] = i;
          }
          return shuffle(indexes);
        }

        // http://stackoverflow.com/questions/962802#962890
        function shuffle(array) {
          var tmp, current, top = array.length;

          if (top) {
            while (--top) {
              current = Math.floor(Math.random() * (top + 1));
              tmp = array[current];
              array[current] = array[top];
              array[top] = tmp;
            }
          }

          return array;
        }

    }

})();