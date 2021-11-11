import {MEMBER_ID, IS_AUTH, ERROR_STATE} from "./mutation_type"
import {MemberService} from "../service/MemberService";

let setMemberId = ({commit}, data) => {
    commit(MEMBER_ID, data)
}

let setErrorState = ({commit}, data) => {
    commit(ERROR_STATE, data)
}

let setIsAuth = ({commit}, data) => {
    commit(IS_AUTH, data)
}

const memberService = new MemberService();


//백엔드에서 반환한 결과값을 가지고 로그인 성공 실패 여부를 vuex에 넣어준다.
let processResponse = (store, loginResponse) => {
    if (loginResponse.data.status) {
        setMemberId(store, loginResponse.data.memberInfo.memberId)
        setErrorState(store, '')
        setIsAuth(store, true)
    } else {
        setErrorState(store, 'loginError')
        setIsAuth(store, false);
    }
}

export default {
    async login(store, {memberId, password}) {
        /*로그인은 백엔드를 다녀와야 하니 비동기 처리를 한다*/
        let loginResponse = await memberService.loginMember(memberId, password)
        processResponse(store, loginResponse)
        return store.getters.getIsAuth
    }
}