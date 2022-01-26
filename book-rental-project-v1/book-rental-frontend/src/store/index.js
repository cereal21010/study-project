import Vue from "vue";
import Vuex from "vuex"
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({

    plugins: [
        createPersistedState()
    ],

    state: {
        accessToken: null,
        loginId: null,
        role: null,
    },

    getters: {
        getLoginId: state => state.loginId,
        getAccessToken: state => state.accessToken,
        getRole: state => state.role,
    },

    mutations: {
        LOGIN(state, payload) {
            state.accessToken = payload.accessToken
            state.loginId = payload.loginId
            state.role = payload.role
        },
        LOGOUT(state) {
            state.accessToken = null
            state.loginId = null
            state.role = null
        },
    },

    actions: {
        async LOGIN({commit}, {response}) {
            const responseId = response.loginInfo.id
            const accessToken = response.accessToken
            const role = response.role
            commit("LOGIN", {loginId: responseId, accessToken: accessToken, role: role})
        },
        LOGOUT({commit}) {
            commit("LOGOUT")
        },
    },

})