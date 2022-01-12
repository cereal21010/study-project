export class PointService {
    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    async getPointList(customerId) {
        const response = await this.axiosInstance.get(`/api/point/list`, {customerId: customerId});
        return response.data;
    }

    async chargePoint(chargePoint, customerId) {
        await this.axiosInstance.post(`/api/point/insert/${customerId}`, chargePoint, {
            headers: {
                'Content-Type': 'application/json',
            }
        })
    }




}