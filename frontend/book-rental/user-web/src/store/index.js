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
        async LOGIN({commit}, response) {
            const accessToken = response.accessToken
            commit("LOGIN", {accessToken: accessToken})
        },
        LOGOUT({commit}) {
            commit("LOGOUT")
        },
    },

})