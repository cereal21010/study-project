import axios from "axios";

const createAxiosInstance = (host) => {

    const axiosInstance = axios.create({
        baseURL: host,
    });

    axiosInstance.interceptors.request.use(
        function (config) {
            //cors
            config.headers['Access-Control-Allow-Origin'] = '*';
            config.headers['Content-Type'] = 'application/json; charset = UTF-8';

            return config;

        }, function (error) {
            return Promise.reject(error);

        }
    );

    axiosInstance.interceptors.response.use(
        function (response) {
            return response;

        }, function (error) {
            return Promise.reject(error);

        }
    );

    return axiosInstance;
}

export default createAxiosInstance;