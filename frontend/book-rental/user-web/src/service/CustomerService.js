export class CustomerService {

    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    async getCustomerList(params) {
        const response = await this.axiosInstance.get(`/api/customer/list`, {params: params});
        return response.data;
    }

    async getCustomerDetail(seq) {
        const response = await this.axiosInstance.get(`/api/customer/detail/${seq}`);
        return response.data;
    }

    async insertCustomer(customerForm) {
        const response = await this.axiosInstance.post(`/api/customer/insert`, customerForm, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        return response.data;
    }

    async updateCustomer(customeEditForm, seq) {
        const response = await this.axiosInstance.put(`/api/customer/update/${seq}`, customeEditForm, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        return response.data;
    }

    async deleteCustomer(bookSeq) {
        const response = await this.axiosInstance.delete(`/api/customer/delete/${bookSeq}`);
        return response.data;
    }

    async loginCustomer(id, password) {
        const response = await this.axiosInstance.post(`/api/customer/login`, {id: id, password: password}, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        const responseMap = {'accessToken': response.headers["authorization"], 'loginInfo': response.data.loginInfo, 'role': response.data.role };
        return responseMap;
    }


}
