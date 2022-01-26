import _ from 'lodash'

import ApiServiceFactory from './api-service-factory';
import ApiServiceError, {ApiServiceErrorEventBus} from "@/plugins/api-service-plugin/api-service-error";

const ApiServices = {
    install(Vue, options) {

        /** event emitter **/
        const serviceErrorEventBus = new ApiServiceErrorEventBus();

        /** Default error handler **/
        //TODO: 기본 에러 핸들러 등록
        serviceErrorEventBus.setDefaultErrorHandler(  (e)=>{
            console.log( `my site default handler ${e.toString()}` )
        });

        Vue.prototype.$addApiErrorHandler = function ( errorCode, handler, propagation) {
            serviceErrorEventBus.on(errorCode, handler, propagation);
        }
        Vue.prototype.$removeApiErrorHandler = function (errorCode, handler) {
            serviceErrorEventBus.off(errorCode,handler);
        }
        Vue.prototype.$removeApiErrorHandler = function ( handler) {
            serviceErrorEventBus.off(handler);
        }
        Vue.config.errorHandler = function(err) {
            if( err instanceof ApiServiceError ){
                serviceErrorEventBus.emit( err.errorCode , err);
            }
        }

        /** inject service **/
        const apiServiceFactory = new ApiServiceFactory({host: options.host});
        _.forEach(apiServiceFactory.makeBaseServices(), function (service, serviceName) {
            Vue.prototype[serviceName] = service;
        });
    },

};

export default ApiServices;