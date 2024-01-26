import angular from 'angular';

angular.
    module('sample').
    config(['$routeProvider',
        function config($routeProvider) {
            $routeProvider.
                when('/registration', {
                    template: '<registration></registration>'
                }).
                when('/onboarding', {
                    template: '<onboarding></onboarding>'
                }).
                otherwise('/registration')
        }
    ]);