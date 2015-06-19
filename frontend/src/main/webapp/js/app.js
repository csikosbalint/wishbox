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
myApp.controller("mainController", ["$scope","$http", function($scope, $http) {
    console.log("mainController");
    $http.get('/gateway/event').then(function(result){
              $scope.events = result.data;
    });
    $http.get('/gateway/notification').then(function(result){
                  $scope.notifications = result.data;
        });
}]);

myApp.controller("dropDownMenuController", ["$scope", function($scope) {
    console.log("dropDownMenuController");
}]);

myApp.controller("googleConnectController", ["$scope", function($scope) {
    console.log("googleConnectController");
}]);

myApp.logout = function() {
    $.ajax({
        type: 'DELETE',
        url: 'token',
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
};