import Vue from "vue";
import VueRouter from "vue-router";
import adminRoutes from "@/router/modules/admin-routes";

Vue.use(VueRouter);



const router = new VueRouter({
    mode: 'history',
    routes: [
        ...adminRoutes,
    ]
})

export default router;