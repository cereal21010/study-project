import axios from "axios";
import store from "../../store"
import ApiServiceError from "@/plugins/api-service-plugin/api-service-error";

const createAxiosInstance =  (host) => {

    const axiosInstance = axios.create({
        baseURL: host,
    });

    axiosInstance.interceptors.request.use(
        function (config) {
            //cors
            config.headers['Access-Control-Allow-Origin'] = '*';
            config.headers['Content-Type'] = 'application/json; charset = UTF-8';
            //jwt
            config.headers['Authorization'] = store.getters.getAccessToken;
            config.headers['Customer-Id'] = store.getters.getLoginId;

            return config;
        }, function (error) {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        function (response) {
            return response;
        }, function (error) {
            if (error.response) {
                const errorCode = error.response.data.errorCode;
                //TODO: 반복되는 if문이 쓰일 경우, java Enum처럼 state화 시켜서 처리는하는 방법 고려해보기
                if ( errorCode.includes('S') ) {
                    location.href="http://localhost:8092/customer/login"
                }
                return Promise.reject(new ApiServiceError(error.response.data))
            }
            // return Promise.reject(error);
        }
    );

    return axiosInstance;

}

export default createAxiosInstance;
