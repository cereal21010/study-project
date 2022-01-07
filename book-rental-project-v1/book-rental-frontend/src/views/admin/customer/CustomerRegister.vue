<template>
    <v-row justify="center">
        <v-col
            cols="12"
            sm="10"
            md="8"
            lg="6"
        >
            <v-card ref="form">
                <v-card-text>
                    <v-text-field
                        ref="id"
                        v-model="customerForm.id"
                        label="ID"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="name"
                        v-model="customerForm.name"
                        label="Name"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="password"
                        v-model="customerForm.password"
                        label="Password"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="birthday"
                        v-model="customerForm.birthday"
                        label="Birthday"
                        type="number"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="phoneNumber"
                        v-model="customerForm.phoneNumber"
                        label="Phone Number"
                        required
                    ></v-text-field>
                    <v-textarea
                        outlined
                        ref="memo"
                        v-model="customerForm.memo"
                        label="Memo"
                    ></v-textarea>

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
    name: "CustomerRegister",

    props: {
        query: {
            type: Object,
            default: () => {},
        }
    },

    data() {
        return {
            customerForm: {
                id: null,
                password: null,
                name: null,
                birthday: null,
                phoneNumber: null,
                memo: null,
            },
            fileList: [],
        }
    },

    methods: {
        async onSubmit() {
            await this.$customerService.insertCustomer(this.customerForm);
            alert("고객 정보가 정상적으로 등록되었습니다.");
            this.gotoList();
        },

        gotoList() {
            this.$router.push({
                path: `/customer/list`,
                query: this.query,
            })
        },
    }

}
</script>

<style scoped>

</style>