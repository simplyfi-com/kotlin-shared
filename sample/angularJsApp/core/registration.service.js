import angular from 'angular';
import { com } from '@simplyfi-com/sdk-browser/browser';

const letters = 'abcdefghijklmnopqrstuvwxyz';
const digits = '0123456789';
const symbols = '!$#&';

function randomString(length, characters) {
    let result = '';
    const charactersLength = characters.length;
    let counter = 0;
    while (counter < length) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
      counter += 1;
    }
    return result;
}

angular.module('core').
    factory('Registration', [
        '$rootScope',
        function($rootScope) {
            const config = $rootScope.config;

            const now = new Date();

            const service = {
                companyName: null,
                firstName: null,
                lastName: null,
                phone: null,
                email: null,
                authority: 'DEDD',
                tln: null,
                secret: null,
                compareSecret: null,
                expiration: now.setDate(now.getDate() + 7),
                token: null
            };

            service.register = function (resolve, reject) {
                const client = new com.simplyfi.sdk.Client(
                    new com.simplyfi.sdk.ClientConfig(config.baseUrl, config.apiKey)
                )

                com.simplyfi.sdk.clients.executeAsync(
                    client.routines,
                    "GW.05.0002.00.00.0.00.00.001",
                    new com.simplyfi.sdk.models.core.routines.RoutineExecute([
                        this.companyName,
                        this.authority,
                        this.tln,
                        this.firstName,
                        this.lastName,
                        this.email,
                        this.phone,
                        this.secret,
                        this.expiration.toString()
                    ])
                ).then(function (response) {
                    service.token = response["message"]
                    resolve()
                }, reject)
            }

            service.random = function () {
                this.tln = randomString(5, digits)
                this.firstName = randomString(6, letters)
                this.lastName = randomString(6, letters)
                this.companyName = randomString(6, letters) + " " + randomString(4, letters)
                this.email = randomString(6, letters) + "@company.com"
                this.phone = "971;58" + randomString(7, digits)
                this.secret = randomString(6, letters) + randomString(2, digits) + randomString(
                    1,
                    symbols
                )
                this.compareSecret = this.secret
            }

            return service;
        }
    ]);