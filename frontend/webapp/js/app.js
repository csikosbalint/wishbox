var myApp = angular.module("myApp",[]);

//ROUTES
//myApp.config(function ($routeProvider)  {
//
//    $routeProvider
//    .when("", {
//        templateUrl: "pages/wrapper.html",
//        controller: "mainController"
//    });
//
//});

//CONTROLLERS
myApp.controller("mainController", ["$scope", function($scope) {
    console.log("mainController");
    $scope.chiliSpicy = function() {

                $.ajax({
                    type: 'GET',
                    url: 'gateway',
                    success: function (result) {
                        // 200 OK  - so connected
                        // Handle or verify the server response if necessary.

                        // Prints the list of people that the user has allowed the app to know
                        // to the console.
                        // console.log(result);
                        $scope.$apply(function() {
                            $scope.spice = result;
                        });
                        console.log(result);
                    },
                    processData: false,
                });
    }
}]);

myApp.controller("dropDownMenuController", ["$scope", function($scope) {
    console.log("dropDownMenuController");
}]);

myApp.controller("googleConnectController", ["$scope", function($scope) {
    console.log("googleConnectController");
}]);