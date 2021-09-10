import axios from "axios";

export class RecommendService {
    constructor() {
        this.testUrl = 'http://localhost'
    }

    getRecommendCount(boardSeq) {
        return axios
            .get(this.testUrl+'/api/recommend/getCount/'+boardSeq)
            .then(response => {
                console.log('>>== response', response);
                return response.data;
            })
            .catch((e) => { console.log(e); });
    }

    insertRecommend(boardSeq, memberSeq) {

        let data = {
            boardSeq: boardSeq,
            memberSeq: memberSeq
        }

        return axios
            .post(this.testUrl+'/api/recommend/insert', JSON.stringify(data) ,{
                headers: {
                    "Content-Type": `application/json`
                },
            })
            .then(response => {
                console.log('>>== response', response);
                return response
            })
            .catch((e) => { console.log(e); })

    }

    deleteRecommend(boardSeq) {

        return axios
            .delete(this.testUrl+'/api/recommend/delete/'+boardSeq)
            .then(response => {
                console.log(`>>== response`, response);
            })
            .catch((e) => {console.log(e); })
    }
}