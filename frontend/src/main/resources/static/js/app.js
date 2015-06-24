var myApp = angular.module("myApp",[]);

//CONTROLLERS
myApp.controller("mainController", ["$scope","$http", function($scope, $http) {

    $scope.signInCallback = function(authResult) {
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

//            var notifications = angular.element($("#notifications")).scope();

            // Send the code to the server
            $http.post('gateway/token', authResult['code']).success(function() {
                $scope.redraw()
            });
        } else if (authResult['error']) {
            // There was an error.
            // Possible error codes:
            //   "access_denied" - User denied access to your app
            //   "immediate_failed" - Could not automatially log in the user
            console.log('There was an error: ' + authResult['error']);
        }
    }

    $scope.addWish = function() {
        $http.post('/gateway/wish', $scope.wish)
            .success(function(data, status, headers, config) {
                $scope.redraw()
            });
    }

    $scope.removeEvent = function(event) {
        $http.delete('/gateway/event/' + event.id)
            .success(function(data, status, headers, config) {
                $scope.redraw()
            });
    }

    $scope.redraw = function () {
        $http.get('/gateway/event')
            .success(function(data, status, headers, config) {
                $scope.events = data;
        });
        $scope.showEvent = true

        $http.get('/gateway/notification')
            .success(function(data, status, headers, config) {
                $scope.notifications = data;
        });
        $scope.showNotification = true

        $http.get('/gateway/wish')
            .success(function(data, status, headers, config) {
                $scope.wishes = data;
        });
        $scope.showWish = true

        // this code reaches out of controller scope
        $('#nav-sidebar').attr('style', 'display: visible');
        $('#nav-menubar').attr('style', 'display: visible');

    }
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

            console.log(result);
        },
        processData: false,
    });
};

function outercallback(authResult) {
    var mainScope = angular.element(
        document.getElementById('wrapper')).scope();

    mainScope.$apply( function() {
        mainScope.signInCallback(authResult);
    });
}

