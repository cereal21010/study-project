import _ from 'lodash';

import ApiServiceFactory from './api-service-factory';

const ApiServices = {
    install(Vue, options) {
        /** inject service **/
        const apiServiceFactory = new ApiServiceFactory({host: options.host} );
        _.forEach(apiServiceFactory.makeBaseServices(), function (service, serviceName) {
            Vue.prototype[serviceName] = service;
        });
    },

};

export default ApiServices;
