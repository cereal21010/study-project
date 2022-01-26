import _ from "lodash";
import Login from "@/views/customer/Login";
import Container from "@/views/Container";
import BookList from "@/views/book/BookList";
import BookView from "@/views/book/BookView";


const bookProps = (route)  => {
    const props = {
    }
    _.merge(props, route.params);
    props.query = route.query;
    return props;
}


const customerRoutes = [
    {
        path: '/',
        component: Login
    },
    {

        path: '/customer',
        component: Container,
        props: {
            role: 'customer'
        },
        children:[
            {
                path: 'login',
                component: Login,
            },
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
        ]
    },
]

export default customerRoutes;