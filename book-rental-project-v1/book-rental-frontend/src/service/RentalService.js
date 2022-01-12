export class RentalService {
    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance;
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
