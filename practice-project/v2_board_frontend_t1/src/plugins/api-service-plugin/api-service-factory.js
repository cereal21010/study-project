import createAxiosInstance from "@/plugins/api-service-plugin/axios-instance"
import {BoardService} from "@/service/boardService";

export default class ApiServiceFactoryBuilder {

    constructor(props) {
        this.host = props.host;
    }

    makeBaseServices(options = {}) {
        const host = this.host || options.host;
        const axiosInstance = createAxiosInstance(host);
        const serviceMap = {};

        serviceMap.$boardService = new BoardService(axiosInstance);
        return serviceMap;
    }

}