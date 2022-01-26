import createAxiosInstance from "@/plugins/api-service-plugin/axios-instance";
import {BookService} from "@/service/BookService";
import {CustomerService} from "@/service/CustomerService";
import {RentalService} from "@/service/RentalService";
import {PointService} from "@/service/PointService";

export default class ApiServiceFactoryBuilder {

    constructor(props) {
        this.host = props.host;
    }

    makeBaseServices(options = {}) {
        const host = this.host || options.host;
        const axiosInstance = createAxiosInstance(host);
        const serviceMap = {};

        serviceMap.$bookService = new BookService(axiosInstance);
        serviceMap.$customerService = new CustomerService(axiosInstance);
        serviceMap.$rentalService = new RentalService(axiosInstance);
        serviceMap.$pointService = new PointService(axiosInstance);
        return serviceMap;
    }
}