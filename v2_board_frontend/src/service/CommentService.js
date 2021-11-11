import axios from "../utils/AxiosInterceptors";

export class CommentService {
    constructor() {
        this.testUrl = 'http://localhost'
    }

    insertComment(commentDetail) {
        const formData = new FormData();
        const json = JSON.stringify(commentDetail);
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);

        return axios
            .post(this.testUrl + '/api/comment/insert', formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then(response => {
                console.log('>>== response', response);
                return response;
            })
            .catch((e) => {
                console.log(e);
            });
    }

    updateComment(commentDetail) {
        const formData = new FormData();
        const json = JSON.stringify(commentDetail);
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);

        return axios
            .put(this.testUrl + '/api/comment/update', formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then(response => {
                console.log('>>== response', response);
                return response.status;
            })
            .catch((e) => {
                console.log(e);
            });
    }

    deleteComment(commentSeq) {
        return axios
            .delete(this.testUrl + `/api/comment/delete/` + commentSeq)
            .then(response => {
                return response;
            })
            .catch((e) => {
                console.log(e);
            });
    }
}