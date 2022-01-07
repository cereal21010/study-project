import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { BootstrapVue, BootstrapVueIcons} from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import ApiServices from "@/plugins/api-service-plugin";
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.use(ApiServices, {host: 'http://localhost:8080'})

new Vue({
  render: h => h(App),
  vuetify,
  router
}).$mount('#app')
