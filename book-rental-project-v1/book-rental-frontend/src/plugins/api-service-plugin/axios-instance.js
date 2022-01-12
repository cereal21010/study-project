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

            return config;
        }, function (error) {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        function (response) {
            return response;
        }, function (error) {
            console.log(`error::`, error.response);
            if (error.response) {
                return Promise.reject(new ApiServiceError(error.response.data))
            }
            return Promise.reject(error);
        }
    );

    return axiosInstance;

}

export default createAxiosInstance;
