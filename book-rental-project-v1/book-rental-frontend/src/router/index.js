import Vue from "vue";
import VueRouter from "vue-router";
import adminRoutes from "@/router/modules/admin-routes";
import customerRoutes from "@/router/modules/customer-routes";

Vue.use(VueRouter);



const router = new VueRouter({
    mode: 'history',
    routes: [

        ...adminRoutes,
        ...customerRoutes,
    ]
})

export default router;