import axios from "axios";

export class MemberService{
    constructor() {
        this.testUrl = 'http://localhost'
    }

    insertMember(memberForm) {
        const formData = new FormData();
        const json = JSON.stringify(memberForm);
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);

        return axios
            .post(this.testUrl+`/api/member/register`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        })
        .then(response => {
                console.log('>>== response', response);
                return response.status;
            })
                .catch((e) => { console.log(e); });
        }
}