<template>
    <div>
        <v-card
            class="mx-auto my-12"
            max-width="700"
        >
            <b-card title="Customer Info">
                <div class="main-text">
                    <v-form
                        ref="form"
                        v-model="valid"
                        lazy-validation
                    >
                        <v-text-field
                            v-model="name"
                            :counter="10"
                            :rules="nameRules"
                            label="Name"
                            required
                        ></v-text-field>

                        <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                        ></v-text-field>

                        <v-btn
                            :disabled="!valid"
                            color="success"
                            class="mr-4"
                            @click="validate"
                        >
                            Validate
                        </v-btn>

                        <v-btn
                            color="error"
                            class="mr-4"
                            @click="reset"
                        >
                            Reset Form
                        </v-btn>

                        <v-btn
                            color="warning"
                            @click="resetValidation"
                        >
                            Reset Validation
                        </v-btn>
                    </v-form>

                </div>
            </b-card>
        </v-card>

    </div>
</template>

<script>
export default {
    name: "Login"
}
</script>

<style scoped>

</style>