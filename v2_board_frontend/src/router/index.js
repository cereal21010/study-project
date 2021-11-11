import Vue from 'vue'
import VueRouter from 'vue-router'
import BoardList from '../views/board/BoardList'
import BoardWrite from "../views/board/BoardWrite";
import BoardView from "../views/board/BoardView";
import Changes from "../views/board/Changes";
import Register from "../views/board/Register";
import Login from "../views/member/Login";
import UserInfo from "../views/member/UserInfo";

Vue.use(VueRouter)

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: '/',
            component: BoardList,
            props: route => ({query: route.query}),
        },
        {
            path: '/board/list',
            component: BoardList,
            name: BoardList,
            props: route => ({query: route.query})
        },
        {
            path: '/board/write',
            component: BoardWrite,
            props: route => ({query: route.query})
        },
        {
            path: '/board/view/:seq',
            component: BoardView,
            props: route => (Object.assign({query: route.query}, route.params)),
        },
        {
            path: '/board/update/:seq',
            component: BoardWrite,
            props: route => {
                const props = Object.assign({query: route.query}, route.params);
                return props;
            },
        },
        {
            path: '/board/changes/:seq',
            component: Changes,
            props: route => (Object.assign({query: route.query}, route.params))
        },
        {
            path: '/join',
            component: Register,
            props: route => (Object.assign({query: route.query}, route.params))
        },
        {
            path: '/login',
            component: Login,
            props: route => (Object.assign({query: route.query}, route.params))
        },
        {
            path: '/user-info',
            component: UserInfo,
            props: route => (Object.assign({query: route.query}, route.params))
        },
    ]
});

/*router.beforeEach(function (to, from, next) {

    const storage = window.sessionStorage.getItem("jwt-auth-token");

    if( typeof storage !== 'undefined' && storage !== '' && storage ) {
        next();
    } else if( ['/login', '/join'].includes(to.path) ) {
        next();
    } else {
        alert("로그인 후 이용 가능합니다.\n 로그인 페이지로 이동 합니다.");
        next('/login');
    }
});*/

export default router;