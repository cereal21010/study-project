<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand to="/">Vue.js 게시판</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav v-if="!getIsLogin">
                    <b-nav-item to="/">자유게시판</b-nav-item>
                    <b-nav-item to="/login">로그인</b-nav-item>
                    <b-nav-item to="/join">회원가입</b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav v-else>
                    <b-nav-item to="/">자유게시판</b-nav-item>
                    <b-nav-item to="/user-info">{{getUserEmail}} 정보</b-nav-item>
                    <b-nav-item @click="logout">로그아웃</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
// import EventBus from "../../utils/EventBus";

export default {
    name: "Header",

    data() {
        return {
            isLogin: false,
            userEmail: '',
        }
    },

    created() {
    },

    mounted() {
        this.isLogin = this.$store.getters.getMemberId !== null ? true : false;
        this.userEmail = this.$store.getters.getMemberId;

    },

    computed: {
        getIsLogin() {
            return this.$store.getters.getMemberId !== null ? true : false
        },

        getUserEmail() {
            return this.$store.getters.getMemberId
        }


    },

    updated() {
    },

    beforeDestroy() {
    },

    methods: {
        logout() {
            /*window.sessionStorage.removeItem('jwt-auth-token');
            window.sessionStorage.removeItem('login_user');*/
            // this.isLogin = false;
            this.$store.dispatch("LOGOUT")
            this.$router.push({
                path: '/login'
            })
        }
    },
}
</script>

<style scoped>

</style>