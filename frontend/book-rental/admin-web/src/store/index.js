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
    },

    getters: {
        getLoginId: state => state.loginId,
        getAccessToken: state => state.accessToken,
    },

    mutations: {
        LOGIN(state, payload) {
            state.accessToken = payload.accessToken
            state.loginId = payload.loginId
        },
        LOGOUT(state) {
            state.accessToken = null
            state.loginId = null
        },
    },

    actions: {
        async LOGIN({commit}, {response}) {
            const accessToken = response.accessToken

            const base64Url = accessToken.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const payload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            const jsonPayload = JSON.parse(payload);

            const responseId = jsonPayload['UserId']
            commit("LOGIN", {loginId: responseId, accessToken: accessToken})
        },
        LOGOUT({commit}) {
            commit("LOGOUT")
        },
    },

})