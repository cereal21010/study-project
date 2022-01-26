<template>
    <v-app>
        <v-card
            color="grey lighten-4"
            flat
        >
            <v-app-bar
                color="primary"
                dark
                dense
            >
                <v-app-bar-nav-icon @click="drawer = !drawer"/>
                <v-toolbar-title>ADMIN Page</v-toolbar-title>
                <v-spacer/>
            </v-app-bar>
        </v-card>

        <v-navigation-drawer
            v-model="drawer"
            color=""
            absolute
            bottom
            temporary
        >
            <v-list-item>
                <v-list-item-avatar>
                    <v-icon>mdi-account</v-icon>
                </v-list-item-avatar>

                <v-list-item-content>
                    <v-list-item-title>{{ loginUser }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>

            <v-divider/>

            <v-list dense>
                <v-list-item
                    v-for="item in items"
                    :key="item.title"
                    link
                    :to="item.to"
                >
                    <v-list-item-icon>
                        <fa-icon :icon="item.icon"/>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title>{{ item.title }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>

            <template v-slot:append>
                <div class="pa-2">
                    <v-btn
                        v-if="isLogin"
                        class="blue-grey"
                        dark
                        block
                        @click="onLogout"
                    >
                        Logout
                    </v-btn>

                    <v-btn
                        v-if="isLogin"
                        class="blue-grey"
                        dark
                        block
                        @click="onLogin"
                    >
                        Login
                    </v-btn>
                </div>
            </template>
        </v-navigation-drawer>
        <router-view/>
    </v-app>
</template>

<script>

export default {
    name: "Header",

    data() {
        return {
            drawer: false,

            isLogin: this.$store.getters.getLoginId == null ? false : true,

            items: [
                {
                    title: 'Book',
                    icon: 'book',
                    to: '/admin/book/list'
                },
                {
                    title: 'login',
                    icon: 'address-card',
                    to: '/'
                },
            ]
        }
    },

    created() {
    },

    mounted() {
    },

    computed: {
        loginUser: function() {
            return this.$store.getters.getLoginId;
        }

    },

    updated() {
    },


    methods: {
        onLogout() {
            this.$store.dispatch("LOGOUT")
            .then(
                this.$router.push({
                    path: `/`,
                    query: this.query,
                })
            )
        },

        onLogin() {
            this.$router.push({
                path:`/login`,
                query: this.query,
            })
        },
    },

    watch: {},
}
</script>

<style scoped>

</style>