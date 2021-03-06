import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {BoardService} from "./service/BoardService";
import {CommentService} from "./service/CommentService";
import {RecommendService} from "./service/RecommendService";
import {MemberService} from "./service/MemberService";
import store from './store'
import VueMoment from 'vue-moment'

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VueMoment)

const API_URL_HOST = process.env.VUE_APP_API_HOST;

new Vue({
    router,
    store,
    provide: {
        boardService: new BoardService(API_URL_HOST),
        commentService: new CommentService(),
        recommendService: new RecommendService(),
        memberService: new MemberService(),
    },
    render: h => h(App),
}).$mount('#app')
