import axios from "axios";

export class  CommentService {
    constructor() {
        this.testUrl = 'http://localhost'
    }

    insertComment(commentDetail) {
        console.log(commentDetail)
        const formData = new FormData();
        const json = JSON.stringify(commentDetail);
        console.log(json)
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);
        // formData.append('writer', commentDetail.writer);
        // formData.append('content', commentDetail.content);

        return axios
            .post( this.testUrl+'/api/comment/insert', formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            } )
            .then(response => {
                console.log('>>== response', response);
                return response;
            })
            .catch((e) => { console.log(e); });
    }

    deleteComment(commentSeq) {
        return axios
            .delete( this.testUrl+`/api/comment/delete/`+commentSeq )
            .then(response => {
                return response;
            })
            .catch((e) => { console.log(e); });
    }
}