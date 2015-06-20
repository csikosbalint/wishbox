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
function signInCallback(authResult) {
        console.log(authResult)
        if (authResult['status']['signed_in']) {
            // Your user is signed in. You can use the access token to perform
            // API calls or if you get a `code`, you could send that to your
            // server to get server-side access to the APIs.
        } else if (authResult['status']['google_logged_in']) {
            // User is signed in to Google, but not your website.
            // Customize the page to encourage the Google user to sign in.
        } else {
            // User is not signed in to your app, handle any user interface
            // changes or other aspects of your design based on this condition.
        }

        if (authResult['code']) {

            // Hide the sign-in button now that the user is authorized, for example:
            $('#signinButton').attr('style', 'display: none');


            var notifications = angular.element($("#notifications")).scope();

            // Send the code to the server
            $.ajax({
                type: 'POST',
                url: 'gateway/token',
                contentType: 'application/octet-stream; charset=utf-8',
                processData: false,
                data: authResult['code'],
                success: function (result) {
                        $.ajax({
                            type:       'GET',
                            url:        '/gateway/event',
                            dataType:   'json',
                            success:    function(result) {
                                var events = angular.element($("#events")).scope();
                                events.events = []
                                $.each(result, function(key, value) {
                                  events.events.push(value)
                                });
                                events.$apply()
                            }
                        });

                }
            });
        } else if (authResult['error']) {
            // There was an error.
            // Possible error codes:
            //   "access_denied" - User denied access to your app
            //   "immediate_failed" - Could not automatially log in the user
            console.log('There was an error: ' + authResult['error']);
        }
    }

//CONTROLLERS
myApp.controller("mainController", ["$scope","$http", function($scope, $http) {
    console.log("mainController");




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