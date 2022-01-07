import Vue from "vue";
import VueRouter from "vue-router";
import Main from "@/views/Main";
import BookList from "@/views/admin/book/BookList";
import BookView from "@/views/admin/book/BookView";
import _ from 'lodash'
import BookRegister from "@/views/admin/book/BookRegister";
import CustomerList from "@/views/admin/customer/CustomerList";
import CustomerRegister from "@/views/admin/customer/CustomerRegister";
import CustomerView from "@/views/admin/customer/CustomerView";

Vue.use(VueRouter);

const bookProps = (route)  => {
    const props = {
    }
    _.merge(props, route.params);
    props.query = route.query;
    return props;
}

const customerProps = (route)  => {
    const props = {
    }
    _.merge(props, route.params);
    props.query = route.query;
    return props;
}

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/main',
            component: Main
        },
        {
            path: '/book/list',
            component: BookList,
            props: route => bookProps(route),
        },
        {
            path: '/book/view/:seq',
            component: BookView,
            props: route => bookProps(route),
        },
        {
            path: '/book/register',
            component: BookRegister,
            props: route => bookProps(route),
        },

        {
            path: '/customer/list',
            component: CustomerList,
            props: route => customerProps(route),
        },
        {
            path: '/customer/register',
            component: CustomerRegister,
            props: route => customerProps(route),
        },
        {
            path: '/customer/view/:seq',
            component: CustomerView,
            props: route => customerProps(route),
        },

    ]
})

export default router;