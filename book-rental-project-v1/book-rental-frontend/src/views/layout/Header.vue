<template>
    <v-app>
        <v-card >
            <v-card-title class="blue white--text">
                <span class="text-h5">Menu</span>

                <v-spacer></v-spacer>

                <v-menu
                    bottom
                    left
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            dark
                            icon
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-icon>mdi-dots-vertical</v-icon>
                        </v-btn>
                    </template>

                    <v-list>
                        <v-list-item
                            v-for="(item, i) in customerMenu"
                            :key="i"
                            :to="item.to"
                        >
                            <v-list-item-title>{{ item.title }}</v-list-item-title>
                        </v-list-item>
                    </v-list>
                </v-menu>
            </v-card-title>
        </v-card>

        <v-main>
            <router-view/>
        </v-main>
    </v-app>
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