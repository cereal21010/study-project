<template>
    <div>
        <v-card
            class="mx-auto my-12"
            max-width="700"
        >
            <b-card title="Login">
                <div class="main-text">
                    <v-form
                        ref="form"
                        v-model="valid"
                        lazy-validation
                        @submit="onSubmit"
                    >
                        <v-text-field
                            v-model="id"
                            :rules="idRules"
                            label="ID"
                            required
                        ></v-text-field>

                        <v-text-field
                            v-model="password"
                            :rules="passwordRules"
                            label="Pssword"
                            required
                            type="password"
                        ></v-text-field>

                        <v-btn
                            :disabled="!valid"
                            color="success"
                            class="mr-4"
                            @click="onSubmit()"
                        >
                            Login
                        </v-btn>

                    </v-form>

                </div>
            </b-card>
        </v-card>

    </div>
</template>

<script>
export default {
    name: "Login",

    props: {
        query: {
            type: Object,
            default: () => {},
        },
    },

    data() {
        return {
            valid: false,
            id: '',
            password: '',
            idRules: [
                v => !!v || 'ID is required',
            ],
            passwordRules: [
                v => !!v || 'Password is required',
            ]
        }
    },

    methods: {
        validate() {
            this.$refs.form.validate()
        },

        async onSubmit() {
            const response = await this.$customerService.loginCustomer(this.id, this.password);
            await this.$store
                        .dispatch("LOGIN", {response})
                        .then(
                            this.$router.push({
                                path: `/customer/book/list`,
                                query: this.query,
                            })
                        )
        }

    }

}
</script>

<style scoped>

</style>