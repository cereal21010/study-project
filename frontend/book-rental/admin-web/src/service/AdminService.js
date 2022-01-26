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

    async loginAdmin(id, password) {
        const response = await this.axiosInstance.post(`/api/admin/login`, {id: id, password: password}, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        const responseMap = {'accessToken': response.headers["authorization"], 'loginInfo': response.data.loginInfo, 'role': response.data.role};
        return responseMap;
    }

    async insertAdmin(adminForm) {
        const response = await this.axiosInstance.post(`/api/admin/insert`, adminForm, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        return response.data;
    }

}