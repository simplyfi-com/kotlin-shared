import angular from 'angular';
import { com } from '@simplyfi-com/sdk-browser/browser'

angular.
  module('onboarding').
  component('onboarding', {
    templateUrl: 'onboarding/onboarding.template.html',
    controller: [
      '$rootScope',
      'Registration',
      function OnboardingController($rootScope, Registration) {
        const config = new com.simplyfi.sdk.view.ViewConfig(
            $rootScope.config.webUrl,
            Registration.token
        )

        com.simplyfi.sdk.view.WebViewComponent("root", config)
      }
    ]
  });