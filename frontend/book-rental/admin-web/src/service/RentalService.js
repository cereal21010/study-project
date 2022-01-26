export class RentalService {
    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    async getRentalList(params) {
        const response = await this.axiosInstance.get(`/api/rental/list`, {params: params});
        return response.data;
    }

    async insertRental(bookDetail) {
        const response = await this.axiosInstance.post(`/api/rental/insert`, bookDetail, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        return response.data;
    }

}
