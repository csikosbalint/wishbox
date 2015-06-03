var myApp = angular.module("myApp", ["ngRoute"]);

//ROUTES
myApp.config(function ($routeProvider)  {

    $routeProvider
    .when("/", {
        templateUrl: "pages/wrapper.html",
        controller: "mainController"
    });

});

//CONTROLLERS
myApp.controller("mainController", ["$scope","$routeParams", function($scope,$routeParams) {
    console.log("mainController");
}]);