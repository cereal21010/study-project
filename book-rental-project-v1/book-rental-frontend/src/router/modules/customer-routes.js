import Container from "@/views/Container";
import _ from "lodash";
import BookList from "@/views/customer/book/BookList";
import BookView from "@/views/customer/book/BookView";

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

const customerRoutes = [
    {
        path: '/customer',
        component: Container,
        props: {
            role: 'customer'
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
                // component: BookRegister,
                props: route => bookProps(route),
            },

            {
                path: 'customer/list',
                // component: CustomerList,
                props: route => customerProps(route),
            },
            {
                path: 'customer/register',
                // component: CustomerRegister,
                props: route => customerProps(route),
            },
            {
                path: 'customer/view/:seq',
                // component: CustomerView,
                props: route => customerProps(route),
            },
        ]
    },
]

export default customerRoutes;