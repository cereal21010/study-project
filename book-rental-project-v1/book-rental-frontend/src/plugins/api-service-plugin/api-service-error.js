// import _ from 'lodash'

export default class ApiServiceError {
    constructor({errorCode, errorMessage}) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    toString() {
        return `errorCode: ${this.errorCode}, errorMessage: ${this.errorMessage}`;
    }
}

class ServiceErrorEventHandlerWrapper {
    constructor(serviceErrorEventHandler, propagation = true) {
        this.handler = serviceErrorEventHandler;
        this.propagation = propagation;
    }

    handleError(error) {
        try {
            this.handler(error);
        } catch (e) {
            console.warn(e);
        }
        return this.propagation;
    }
}

/**
 * API 에러 이벤트 버스
 * */
export class ApiServiceErrorEventBus {

    constructor() {
        this.listenerGroup = {};
        this.defaultHandler = new ServiceErrorEventHandlerWrapper((apiServiceError) => {
            console.log(`Default handler ${apiServiceError.toString()}`);
        });
    }

    /**
     * 기본 에러 핸들러 변경
     */
    setDefaultErrorHandler(handler) {
        this.defaultHandler = new ServiceErrorEventHandlerWrapper(handler);
    }

    /**
     * 핸들러 등록
     */
    on(errorCode, handler, propagation) {
        const listeners = this._getListeners(errorCode);
        listeners.push(new ServiceErrorEventHandlerWrapper(handler, propagation));
    }

    _getListeners(errorCode) {
        console.log(`listenerGroup::`, this.listenerGroup);
        if (this.listenerGroup[errorCode]) {
            //TODO
        } else {
            this.listenerGroup[errorCode] = [];
            this.listenerGroup[errorCode].push(this.defaultHandler);
        }
        return this.listenerGroup[errorCode];
    }
}
