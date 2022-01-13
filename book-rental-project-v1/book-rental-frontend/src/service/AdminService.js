export class AdminService {
    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    async getRentalList(customerSeq) {
        const response = await this.axiosInstance.get(`/api/admin/customer/rental/list/${customerSeq}`);
        return response.data;
    }

    async bookReturn(rentalSeq) {
        const response = await this.axiosInstance.get(`/api/admin/customer/rental/return/${rentalSeq}`);
        return response.data;
    }

}