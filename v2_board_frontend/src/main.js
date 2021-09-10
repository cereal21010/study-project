import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {BoardService} from "./service/BoardService";
import {CommentService} from "./service/CommentService";
import {RecommendService} from "./service/RecommendService";

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

const API_URL_HOST = process.env.VUE_APP_API_HOST;

new Vue({
  router,
  provide: {
    boardService: new BoardService(API_URL_HOST),
    commentService: new CommentService(),
    recommendService: new RecommendService()
  },
  render: h => h(App),
}).$mount('#app')
