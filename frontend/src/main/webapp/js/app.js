var myApp = angular.module("myApp", ["ngRoute"]);

//ROUTES
myApp.config(function ($routeProvider)  {
    $routeProvider

    .when("", {
        templateUrl: "pages/wrapper.html",
        controller: "mainController"
    })

    .otherwise({
                redirectTo: '/pages/login.html'
    });

});

//CONTROLLERS
myApp.controller("mainController", ["$scope", function($scope) {
    //$log.info($routeParams);
    alert("cica");
}]);