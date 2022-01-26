import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import {BootstrapVue, BootstrapVueIcons} from "bootstrap-vue";
import ApiServices from "@/plugins/api-service-plugin";
import router from "@/router";
import store from "./store"
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.use(ApiServices, {host: 'http://localhost:8082'})

Vue.mixin({
  beforeDestroy() {
    if (this.$apiErrorHandler) {
      this.$removeApiErrorHandler(this.$apiErrorHandler);
    }
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
