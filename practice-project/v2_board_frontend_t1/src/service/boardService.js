

export class BoardService {

    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance
    }

    async getBoardList(params) {
        const response = await this.axiosInstance.get(`/api/board/list`, { params: params });
        return response.data;

    }
}