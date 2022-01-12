import Container from "@/views/Container";
import _ from "lodash";
import BookList from "@/views/customer/book/BookList";
import BookView from "@/views/customer/book/BookView";
import Login from "@/views/customer/customer/Login";
import RentalList from "@/views/customer/rental/RentalList";
import PointList from "@/views/customer/point/PointList";

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
                path: 'view/:seq',
                // component: CustomerView,
                props: route => customerProps(route),
            },
            {
                path: 'login',
                component: Login,
                props: route => customerProps(route),
            },
            {
                path: 'rental/list',
                component: RentalList,
                props: route => customerProps(route),
            },
            {
                path: 'point/list',
                component: PointList,
                props: route => customerProps(route),
            },
        ]
    },
]

export default customerRoutes;