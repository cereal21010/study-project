import Vue from 'vue'
import VueRouter from 'vue-router'
import BoardList from '../views/board/BoardList'
import BoardWrite from "../views/board/BoardWrite";
import BoardView from "../views/board/BoardView";
import Changes from "../views/board/Changes";
import Register from "../views/board/Register";
import Login from "../views/board/Login";

Vue.use(VueRouter)

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: '/',
            component: BoardList,
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
            props: route => ({ query: route.query })
        },
        {
            path: '/board/view/:seq',
            component: BoardView,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        },
        {
            path: '/board/update/:seq',
            component: BoardWrite,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        },
        {
            path: '/board/changes/:seq',
            component: Changes,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        },
        {
            path: '/join',
            component: Register,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        },
        {
            path: '/login',
            component: Login,
            props: route => ( Object.assign( {query: route.query}, route.params) )
        },
    ]
});

export default router;