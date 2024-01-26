import angular from 'angular';

angular.
  module('registration').
  component('registration', {
    templateUrl: 'registration/registration.template.html',
    controller: [
      '$scope',
      'Registration',
      '$location',
      function RegistrationController($scope, Registration, $location) {
        this.service = Registration

        this.register = function () {
            this.service.register(function() {
                $location.path("/onboarding").replace();
                $scope.$apply();
            }, console.error)
        }
      }
    ]
  });