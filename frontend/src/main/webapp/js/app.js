var myApp = angular.module("myApp", ["ngMessages", "ngRoute"]);

////ROUTES
//myApp.config("$routeProvider", function ($routeProvider)  {
//    $routeProvider
//
//    .when("/", {
//        templateUrl: "pages/main.html",
//        controller: "mainController"
//    })
//
//    .otherwise({
//                redirectTo: '/'
//    });
//
//});

//CONTROLLERS
myApp.controller("mainController", ["$log", "$scope", "$routeParams", function($log, $scope, $routeParams) {
//    $log.info($resource);
    alert("cica");
}]);