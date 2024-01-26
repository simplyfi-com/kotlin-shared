require("./app.module.js")
require("./app.config.js")
require("./core/core.module.js")
require("./core/registration.service.js")
require("./registration/registration.module.js")
require("./registration/registration.component.js")
require("./onboarding/onboarding.module.js")
require("./onboarding/onboarding.component.js")

const initInjector = angular.injector(["ng"]);
const $http = initInjector.get("$http");

$http.get('/sample-config.json').then(function({ data }) {
    angular.element(document).ready(function () {
        angular.module("sample").run(function ($rootScope) {
            $rootScope.config = data
        });

        angular.bootstrap(document, ["sample"]);
    });
});