import axios from "../utils/AxiosInterceptors";

export class MemberService {
    constructor() {
        this.testUrl = 'http://localhost'
    }


    insertMember(memberForm) {

        return axios
            .post(this.testUrl + `/api/member/register`, JSON.stringify(memberForm), {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                console.log('>>== response', response);
                return response;
            })
            .catch((e) => {
                console.log(e);
                // 예외를 던져줘야한다... 찾아보기
                throw new Error(`아이디를 생성하지 못 했습니다.`)
            });
    }

    loginMember(memberId, memberPassword) {

        console.log(`MemberService loginMember function start!!!`)

        return axios
            .post(`${this.testUrl}/api/member/login`,
                {
                    memberId: memberId,
                    memberPassword: memberPassword,
                })
            .then(response => {
                if (response.data.status) {
                    console.log(`>>== loginMember response`, response);
                    return response;
                } else {
                    alert("로그인 실패");
                }
                alert("성공????");
            })
            .catch(e => {
                // alert(e.response.data.errorMessage);
                return (e.response);
            });
    }

    getUserInfo(loginId) {
        return axios
            .get(`${this.testUrl}/api/member/info`, {
                params: {
                    memberId: loginId
                }
            })
            .then(response => {
                return response.data.memberInfo;
            })
            .catch((e) => {
                console.log(e);
            });
    }

    updateMember(memberInfo) {

        return axios
            .put(`${this.testUrl}/api/member/update`, JSON.stringify(memberInfo), {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                console.log(response);
                return response
            })
            .catch((e) => {
                console.log(e);
            })
    }

}
