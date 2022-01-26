import Vue from "vue";
import VueRouter from "vue-router";
import customerRoutes from "@/router/modules/customer-routes";

Vue.use(VueRouter);



const router = new VueRouter({
    mode: 'history',
    routes: [
        ...customerRoutes,
    ]
})

export default router;