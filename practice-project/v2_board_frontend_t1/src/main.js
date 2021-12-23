import Vue from 'vue'
import App from './App.vue'
import router from '@/router'
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css'
import ApiServices from "@/plugins/api-service-plugin"

Vue.config.productionTip = false

const API_URL_HOST = 'http://localhost:8080';

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

/** use service plugin **/
Vue.use( ApiServices, {host: API_URL_HOST})


new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
