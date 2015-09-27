/*
 *   app.js is part of the "wishbox ( gateway )" project
 *   Copyright (C)  2015  author:  johnnym
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU General Public License
 *   as published by the Free Software Foundation; either version 2
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
            var protocols = {protocols_whitelist: ["websocket", "xhr-streaming", "xdr-streaming", "xhr-polling", "xdr-polling", "iframe-htmlfile", "iframe-eventsource", "iframe-xhr-polling"]};
            var opt = {debug: true, devel: true};
            socket.client = new SockJS(service.SOCKET_URL, protocols, opt);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            //socket.stomp.onclose = reconnect;
        };

        return service;
    });
// USEFULL http://codepen.io/m-e-conroy/pen/ALsdF

//CONTROLLERS

myApp.controller("mainController", ["$scope", "$http", "ngDialog", "$filter", "$interval", "$timeout", "MessageService",
    function ($scope, $http, ngDialog, $filter, $interval, $timeout, MessageService) {

        var apiEndpoint = "/gateway";
        $scope.RELOAD = 60000 * 15;
        $scope.TOKEN = "";

        var reload = $interval(function () {
            $scope.redraw()
        }, $scope.RELOAD);

        var messageHandler = function (message) {
            console.log(message)
        };

        $scope.signInCallback = function (authResult) {
            if (authResult['code']) {
                // Hide the sign-in button now that the user is authorized, for example:
                $('#signinButton').attr('style', 'display: none');

//            var notifications = angular.element($("#notifications")).scope();

                // Send the code to the server
                $http.post(apiEndpoint + '/token', authResult['code'])
                    .success(function (result) {
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
            var apiCall = function (endpoint, scope, visible) {
                $http.get(apiEndpoint + '/' + endpoint)
                    .success(function (data, status, headers, config) {
                        if (endpoint.contains('wish')) {
                            scope.wishes = data;
                            scope.showWish = true;
                        } else if (endpoint.contains('notification')) {
                            scope.notifications = data;
                            scope.showNotification = true;
                        } else if (endpoint.contains('event')) {
                            scope.events = data;
                            scope.showEvent = true;
                        }
                    })
                    .error(function (data, status, headers, config) {
                        $timeout(apiCall(endpoint), 5000);
                    })
                ;
            };
            $scope.currentdate = new Date();

            apiCall('event', $scope);
            apiCall('notification', $scope);
            apiCall('wish', $scope);

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
    console.log('logout');
    //$.ajax({
    //    type: 'DELETE',
    //    url: 'token',
    //    success: function (result) {
    //        // 200 OK  - so connected
    //        // Handle or verify the server response if necessary.
    //
    //        // Prints the list of people that the user has allowed the app to know
    //        // to the console.
    //        // console.log(result);
    //
    //        console.log(result);
    //    },
    //    processData: false,
    //});
};

function outercallback(googleUser) {
    var mainScope = angular.element(
        document.getElementById('wrapper')).scope();

    mainScope.$apply(function () {
        mainScope.signInCallback(googleUser);
    });
}

