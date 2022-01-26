import _ from "lodash";
import Container from "@/views/Container";
import BookList from "@/views/admin/book/BookList";
import BookView from "@/views/admin/book/BookView";
import BookRegister from "@/views/admin/book/BookRegister";
import CustomerList from "@/views/admin/customer/CustomerList";
import CustomerRegister from "@/views/admin/customer/CustomerRegister";
import CustomerView from "@/views/admin/customer/CustomerView";
import RentalList from "@/views/admin/customer/RentalList";
import Login from "@/views/admin/Login";
import AdminRegister from "@/views/admin/AdminRegister";

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

const adminRoutes = [
    {
        path: '/',
        component: Login
    },
    {

        path: '/admin',
        component: Container,
        props: {
            role: 'admin'
        },
        children:[

            {
                path: 'book/list',
                component: BookList,
                props: route => bookProps(route),
            },
            {
                path: 'book/view/:seq',
                component: BookView,
                props: route => bookProps(route),
            },
            {
                path: 'book/register',
                component: BookRegister,
                props: route => bookProps(route),
            },

            {
                path: 'customer/list',
                component: CustomerList,
                props: route => customerProps(route),
            },
            {
                path: 'customer/register',
                component: CustomerRegister,
                props: route => customerProps(route),
            },
            {
                path: 'customer/view/:seq',
                component: CustomerView,
                props: route => customerProps(route),
            },
            {
                path: 'customer/rental/list/:seq',
                component: RentalList,
                props: route => customerProps(route),
            },
            {
                path: 'login',
                component: Login,
                props: route => customerProps(route),
            },
            {
                path: 'register',
                component: AdminRegister,
                props: route => customerProps(route),
            },
        ]
    },
]

export default adminRoutes;