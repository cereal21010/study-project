import Vue from 'vue'
import VueRouter from 'vue-router'
import BoardList from '../views/board/BoardList'
import BoardWrite from "../views/board/BoardWrite";
import BoardView from "../views/board/BoardView";

Vue.use(VueRouter)

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: '/',
            component: BoardList,
            // name: BoardList,
            props: route => ({ query: route.query })
        },
        {
            path: '/board/list',
            component: BoardList,
            name: BoardList,
            props: route => ({ query: route.query })
        },
        {
            path: '/board/write',
            component: BoardWrite,
            // name: BoardWrite,
            props: route => ({ query: route.query })
        },
        {
            path: '/board/view/:seq',
            component: BoardView,
            // name: BoardView,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        },
        {
            path: '/board/update/:seq',
            component: BoardWrite,
            // name: BoardWrite,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        }
    ]
});

export default router;