import axios from "axios"
import store from "../store/index"

const axiosInterceptors = axios.create({
    baseURL: 'http://localhost',
    headers: {
        'jwt-auth-token': store.getters.getAccessToken,
    }
});

axiosInterceptors.interceptors.request.use(
    function (config) {
        return config;
    }, function (error) {
        return Promise.reject(error);
    }
);

axiosInterceptors.interceptors.response.use(
    function (response) {
        // console.log(`>>== response`, response)
        return response;
    }, function (error) {
        /*console.log('>>== error', error.response)
        const response = error.response;
        const errorCode = response.data.errorCode;
        const errorMessage = response.data.errorMessage;
        if(errorMessage !== undefined) {
            alert(errorMessage);
        }
        if ( errorCode.startsWith('S') ) {
            this.$router.push({
                path: '/login'
            })
        }*/
        return Promise.reject(error);
    }
);

export default axiosInterceptors;