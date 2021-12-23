<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand to="/">Vue.js 게시판</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav v-if="true">
                    <b-nav-item to="/board/list">자유게시판</b-nav-item>
                    <b-nav-item to="/login">로그인</b-nav-item>
                    <b-nav-item to="/join">회원가입</b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav v-else>
                    <b-nav-item to="/board/list">자유게시판</b-nav-item>
                    <b-nav-item to="/user-info">User 정보</b-nav-item>
                    <b-nav-item @click="logout">로그아웃</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>

export default {
    name: "Header",

    data() {
        return {
        }
    },

    created() {
    },

    mounted() {

    },

    computed: {


    },

    updated() {
    },

    beforeDestroy() {
    },

    methods: {

    },
}
</script>

<style scoped>

</style>