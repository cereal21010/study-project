<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand to="/">방탄 Vue</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item to="/user-info">User 정보</b-nav-item>
                    <b-nav-item>로그아웃</b-nav-item>
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