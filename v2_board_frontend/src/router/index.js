import Vue from 'vue'
import VueRouter from 'vue-router'
import BoardList from '../views/board/BoardList'
import BoardWrite from "../views/board/BoardWrite";

Vue.use(VueRouter)

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: '/',
            component: BoardList,
            name: BoardList
        },
        {
            path: '/board/list',
            component: BoardList,
            name: BoardList
        },
        {
            path: '/board/write',
            component: BoardWrite,
            name: BoardWrite
        }
    ]
});

export default router;