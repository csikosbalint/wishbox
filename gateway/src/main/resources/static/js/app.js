var myApp = angular.module("myApp", ['ui.bootstrap', 'ngDialog', 'ngWebsocket'])
    .service("MessageService", function ($q, $timeout) {

        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        }, messageIds = [];

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = "/websocket";
        service.CHAT_TOPIC = "/user/topic/UIFeed";
        service.CHAT_BROKER = "/queue/GWFeed";

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (message) {
            var id = Math.floor(Math.random() * 1000000);
            socket.stomp.send(service.CHAT_BROKER, {
                priority: 9
            }, message);
        };

        var reconnect = function () {
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getMessage = function (data) {
            return data;
        };

        var startListener = function () {
            socket.stomp.subscribe(service.CHAT_TOPIC, function (data) {
                listener.notify(getMessage(data.body));
            });
        };

        service.initialize = function () {
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            socket.stomp.onclose = reconnect;
        };

        return service;
    });
// USEFULL http://codepen.io/m-e-conroy/pen/ALsdF

//CONTROLLERS

myApp.controller("mainController", ["$scope", "$http", "ngDialog", "$filter", "$interval", "MessageService",
    function ($scope, $http, ngDialog, $filter, $interval, MessageService) {

        var apiEndpoint = "/gateway";
        var reload = $interval(function () {
            $scope.redraw()
        }, 60000 * 15);

        var messageHandler = function (message) {
            console.log(message)
        };

        $scope.signInCallback = function (authResult) {
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
                $http.post(apiEndpoint + '/token', authResult['code'])
                    .success(function () {
                        MessageService.initialize();
                        MessageService.receive().then(null, null, messageHandler);
                        $scope.redraw()
                    });
            } else if (authResult['error']) {
                // There was an error.
                // Possible error codes:
                //   "access_denied" - User denied access to your app
                //   "immediate_failed" - Could not automatially log in the user
                console.log('There was an error: ' + authResult['error']);
            }
        };

        $scope.makeWish = function (w) {
            var dialog = ngDialog.openConfirm({
                template: 'makeWish.html',
                className: 'ngdialog-theme-default',
                scope: $scope,
                closeByEscape: true,
                closeByDocument: true,
                controller: ['$scope', function ($scope) {
                    console.log($scope.newWish)
                }]
            });
        };
        $scope.makeWish.post = function (w) {
            MessageService.send("cica");

            $http.post(apiEndpoint + '/wish', w)
                .success(function (data, status, headers, config) {
                    $scope.newWish = null;
                    $scope.redraw()
                });
        };

        $scope.removeWish = function (w) {
            $http.delete(apiEndpoint + '/wish/' + w.id)
                .success(function (data, status, headers, config) {
                    $scope.redraw()
                });
        };

        $scope.removeEvent = function (e) {
            $http.delete(apiEndpoint + '/event/' + e.id)
                .success(function (data, status, headers, config) {
                    $scope.redraw()
                });
        };

        $scope.removeNotification = function (n) {
            $http.delete(apiEndpoint + '/notification/' + n.id)
                .success(function (data, status, headers, config) {
                    $scope.redraw()
                });
        };

        $scope.redraw = function () {
            $scope.currentdate = new Date();
            $http.get(apiEndpoint + '/event')
                .success(function (data, status, headers, config) {
                    $scope.events = data;
                });
            $scope.showEvent = true;

            $http.get(apiEndpoint + '/notification')
                .success(function (data, status, headers, config) {
                    $scope.notifications = data;
                });
            $scope.showNotification = true;

            $http.get(apiEndpoint + '/wish')
                .success(function (data, status, headers, config) {
                    $scope.wishes = data;
                });
            $scope.showWish = true;

            // this code reaches out of controller scope
            $('#nav-sidebar').attr('style', 'display: visible');
            $('#nav-menubar').attr('style', 'display: visible');

        };

        $scope.ago = function (d) {
            var story = "";
            var ago = $scope.currentdate - d;
            var formatter = null;
            switch (true) {
                case ( ago < 1000 ):
                    return "Now";
                    break;
                case ( ago < 2000 ):
                    formatter = 's';
                    story = ' second ago';
                    break;
                case ( ago < 10000 ):
                    formatter = 's';
                    story = ' seconds ago';
                    break;
                case ( ago < 60000 ):
                    formatter = 'ss';
                    story = ' seconds ago';
                    break;
                case ( ago < 120000 ):
                    formatter = 'm';
                    story = ' minute ago';
                    break;
                case ( ago < 1200000 ):
                    formatter = 'm';
                    story = ' minutes ago';
                    break;
                case ( ago < 3600000 ):
                    formatter = 'mm';
                    story = ' minutes ago';
                    break;
                case ( ago < 7200000 ):
                    formatter = 'h';
                    story = ' hour ago';
                    break;
                case ( ago < 72000000 ):
                    formatter = 'h';
                    story = ' hours ago';
                    break;
                case ( ago < 86400000 ):
                    formatter = 'hh';
                    story = ' hours ago';
                    break;
                case ( ago < 172800000 ):
                    return 'Yesterday';
                    break;
                case ( ago < 345600000 ):
                    formatter = 'd';
                    story = ' days ago';
                    break;
                case ( ago < 518400000 ):
                    formatter = 'yyyy-MM-dd';
                    break;
            }
            return $filter('date')(new Date(ago), formatter) + story;
        }
    }]);

myApp.controller("dropDownMenuController", ["$scope", function ($scope) {
    console.log("dropDownMenuController");
}]);

myApp.controller("googleConnectController", ["$scope", function ($scope) {
    console.log("googleConnectController");
}]);

myApp.logout = function () {

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

    mainScope.$apply(function () {
        mainScope.signInCallback(authResult);
    });
}

