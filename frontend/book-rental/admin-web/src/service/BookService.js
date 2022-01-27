
export class BookService {

    constructor(axiosInstance) {
        this.axiosInstance = axiosInstance;
    }

    async getBookList(params) {
        const response = await this.axiosInstance.get(`/api/admin/book/list`, {params: params });
        return response.data;
    }

    async getBookDetail(seq) {
        const response = await this.axiosInstance.get(`/api/admin/book/detail/${seq}`);
        return response.data;
    }

    async getBookImage(fileSeq) {
        const response = await this.axiosInstance.get(`/api/admin/book/image/${fileSeq}`);
        return response.data;
    }

    async insertBook(bookForm, fileList) {
        const formData = new FormData();
        const json = JSON.stringify(bookForm);
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);
        if (fileList.length > 0) {
            for (let index = 0; index < fileList.length; index++) {
                formData.append('files', fileList[index]);
            }
        }

        const response = await this.axiosInstance.post(`/api/admin/book/insert`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
        return response.data;

    }

    async updateBookFile(bookSeq, fileList, deleteFileSeqs) {
        console.log(`fileList::`, fileList);
        const formData = new FormData();
        if (fileList.length > 0) {
            for (let index = 0; index < fileList.length; index++) {
                formData.append('files', fileList[index]);
            }
        }

        formData.append('deleteFileSeqs', deleteFileSeqs);
        console.log(`deleteFileSeq::`, deleteFileSeqs);
        console.log(`formData`, formData);

        const response = await this.axiosInstance.put(`/api/admin/book/file/update/${bookSeq}`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
        return response.data;
    }

    async updateBook(bookEditForm) {

        const response = await this.axiosInstance.put(`/api/admin/book/update/${bookEditForm.seq}`, bookEditForm, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        return response.data;
    }

    async deleteBook(bookSeq) {
        const response = await this.axiosInstance.delete(`/api/admin/book/delete/${bookSeq}`);
        return response.data;
    }

}