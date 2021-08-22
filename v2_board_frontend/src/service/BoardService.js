import axios from "axios";

export class BoardService {
    constructor(host) {
        this.host = host;
        this.testUrl = 'http://localhost'
    }

    getBoardList(params) {
        console.log(`getBoardList`)
        return axios
            .get(this.testUrl+`/api/board/list`, {params: params})
            .then(response => {
                return response.data;
            })
            .catch((e)=>{ console.log(e) });
    }

    insertBoard(boardDetail) {
        console.log(`insertBoard`)
        return axios
            .post(this.testUrl+`/api/board/save`, {boardDetail})
            .then(response => {
                console.log('>>== response', response);
                return response.data.response;
            })
            .catch((e) => { console.log(e); });
    }
}