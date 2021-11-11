import Vue from "vue";
import Vuex from "vuex"
import createPersistedState from 'vuex-persistedstate'
import {MemberService} from "../service/MemberService";
// import getters from "./getters";
// import actions from "./actions"
// import mutations from "./mutations";

Vue.use(Vuex);

/*const state = {
    memberId: '',
    errorState: '',
    isAuth: false,
}*/

export default new Vuex.Store({
    state: {
        accessToken: null,
        memberId: null,
    },

    getters: {
        getMemberId: state => state.memberId,
        getAccessToken: state => state.accessToken,
    },

    mutations: {
        LOGIN(state, payload) {
            state.accessToken = payload.accessToken
            state.memberId = payload.memberId
        },
        LOGOUT(state) {
            state.accessToken = null
            state.memberId = null
        },
    },

    actions: {
        async LOGIN({commit}, {memberId, memberPassword}) {
            const response = await new MemberService().loginMember(memberId, memberPassword)
            const responseMemberId = response.data.memberInfo.memberId
            const accessToken = response.headers["jwt-auth-token"]
            commit("LOGIN", {memberId: responseMemberId, accessToken: accessToken});
        },

        LOGOUT({commit}) {
            commit("LOGOUT")
        },
    },

    modules: {
        /*auth: Auth,
        cart: Cart,
        products: Products,*/
    },

    plugins: [createPersistedState({
        // 여기에 쓴 모듈만 저장됩니다.
        // paths: ["cart","auth"],
    })
    ]

});