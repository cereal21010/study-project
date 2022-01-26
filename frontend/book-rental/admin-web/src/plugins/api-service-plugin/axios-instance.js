import axios from "axios";
import ApiServiceError from "./api-service-error";
import store from "@/store"

const createAxiosInstance = (host)=>{

    const axiosInstance = axios.create({
        baseURL: host,
        headers: { Pragma: "no-cache" },
        timeout: 5000
    });

    axiosInstance.interceptors.request.use(
        function(config){
            config.headers["Content-Type"] = "application/json; charset=utf-8";
            config.headers["Authorization"] = store.getters.getAccessToken;
            return config;
        }, (error)=>{
            console.error(error);
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(function (response) {
        return response;
    },  (error) => {

        // console.dir(error)
        if(error.response){
            return Promise.reject(new ApiServiceError( error.response.data ));
        }else{
            return Promise.reject( new ApiServiceError( {errorCode:'NETWORK_ERROR', errorMessage:'Network Error'} ) );
        }

    });

    return axiosInstance;
}

export default createAxiosInstance;