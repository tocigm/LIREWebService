'use strict';   // See note about 'use strict'; below

var myApp=angular.module('myApp', [
 'ngRoute', 'ngFileUpload'
]);

myApp
    .config(['$routeProvider',
     function($routeProvider) {
         $routeProvider.
             when('/', {
                 templateUrl: '../partials/index.html',
                 controller: 'MainCtr',
                 // css: ''
             }).
             when('/about', {
                 templateUrl: '../partials/about.html',
             }).
             otherwise({
                 redirectTo: '/'
             });
    }])
    .run(function($rootScope, $templateCache) {
       $rootScope.$on('$viewContentLoaded', function() {
          $templateCache.removeAll();
       });
    });