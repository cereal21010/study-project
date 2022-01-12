import Vue from "vue";
import Vuex from "vuex"

Vue.use(Vuex)

export default new Vuex.Store({

    state: {
        accessToken: null,
        customerId: null,
    },

    getters: {
        getCustomerId: state => state.customerId,
        getAccessToken: state => state.accessToken,
    },

    mutations: {
        LOGIN(state, payload) {
            state.accessToken = payload.accessToken
            state.customerId = payload.customerId
        },
        LOGOUT(state) {
            state.accessToken = null
            state.customerId = null
        },
    },

    actions: {
        async LOGIN({commit}, {response}) {
            const responseId = response.customerInfo.id
            const accessToken = response.accessToken
            commit("LOGIN", {customerId: responseId, accessToken: accessToken})
        },
        LOGOUT({commit}) {
            commit("LOGOUT")
        },
    },

})