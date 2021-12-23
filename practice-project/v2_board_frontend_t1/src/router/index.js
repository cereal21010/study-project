import Vue from "vue";
import VueRouter from "vue-router";

import HelloWorld from "@/components/HelloWorld";
import boardList from "@/views/board/boardList";
import boardView from "@/views/board/boardView";

Vue.use(VueRouter);

function queryInit (route) {
    const props = {query: route.query};
    Object.assign(props, route.params);
    console.log(`props::`, props);
    return props;
}

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/index',
            component: HelloWorld,
        },
        {
            path: '/board/list',
            component: boardList,
            props: route => queryInit(route),
        },
        {
            path: '/board/view/:seq',
            component: boardView,
            props: route => queryInit(route),
        },
    ]
})

export default router;