<template>
    <v-row justify="center">
        <v-col
            cols="12"
            sm="10"
            lg="6"
        >
            <v-card ref="form">
                <v-card-text>
                    <v-text-field
                        ref="id"
                        v-model="adminForm.id"
                        label="ID"
                        required
                    ></v-text-field>

                    <v-text-field
                        ref="password"
                        v-model="adminForm.password"
                        label="Password"
                        required
                    ></v-text-field>
                </v-card-text>
                <v-divider class="mt-12"></v-divider>
                <v-card-actions>
                    <v-btn
                        text
                        @click="gotoList"
                    >
                        Cancel
                    </v-btn>
                    <v-spacer></v-spacer>

                    <v-btn
                        color="primary"
                        text
                        @click="onSubmit"
                    >
                        Submit
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: "AdminRegister",

    props: {
    },

    data() {
        return {
            adminForm: {
                id: null,
                password: null,
            }
        }
    },

    methods: {

        gotoList() {
            this.$router.push({
                path: `/admin/book/list`,
                query: this.query,
            })
        },

        async onSubmit() {
            await this.$adminService.insertAdmin(this.adminForm);
            alert("관리자가 등록 되었습니다.");
            this.gotoList();
        },

    },
}
</script>

<style scoped>

</style>