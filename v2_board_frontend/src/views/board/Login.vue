<template>
    <div>
        <b-form @submit="onSubmit" class="m-5">
            <b-form-group
                class="m-5"
                id="input-group-1"
                label="Email address:"
                label-for="input-1"
                description="We'll never share your email with anyone else."
            >
                <b-form-input
                    id="input-1"
                    v-model="loginForm.memberId"
                    type="email"
                    placeholder="Enter email"
                    required
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-3" label="Your password:" label-for="input-3" class="m-5">
                <b-form-input
                    id="input-3"
                    v-model="loginForm.memberPassword"
                    type="password"
                    placeholder="Enter password"
                    required
                ></b-form-input>
            </b-form-group>

            <b-button type="submit" variant="primary" class="mt-3">Submit</b-button>
            <b-button type="cancel" variant="danger" class="mt-3" @click="cancel">Cancel</b-button>
        </b-form>
    </div>
</template>

<script>
export default {
    name: "Login",

    props: {
        query: {
            type: Object,
            default: () => {
            }
        },
    },

    data() {
        return {
            loginForm: {
                memberId: '',
                memberPassword: '',
            },
            show: true,
        }
    },

    methods: {
        onSubmit() {
            // alert(JSON.stringify(this.form))
            this.memberService.insertMember(this.loginForm);
        },
        cancel() {
            this.$router.push({
                path: `/board/list`,
                query: this.query
            })
        }
    },
}
</script>

<style scoped>

</style>