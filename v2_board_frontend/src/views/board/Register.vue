<template>
    <div>

        <b-form-group
            class="m-5"
            id="input-group-1"
            label="Email address:"
            label-for="input-1"
            description="We'll never share your email with anyone else."
        >
            <b-form-input
                id="input-1"
                v-model="memberForm.memberId"
                type="email"
                placeholder="Enter email"
                required
            ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="Your Name:" label-for="input-2" class="m-5">
            <b-form-input
                id="input-2"
                v-model="memberForm.memberName"
                placeholder="Enter name"
                required
            ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="Your password:" label-for="input-3" class="m-5">
            <b-form-input
                id="input-3"
                v-model="memberForm.memberPassword"
                type="password"
                placeholder="Enter password"
                required
            ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-4" label="Your phone number:" label-for="input-4" class="m-5">
            <b-form-input
                id="input-4"
                v-model="memberForm.memberPhoneNumber"
                placeholder="Enter phone number"
                required
            ></b-form-input>
        </b-form-group>

        <b-button variant="primary" class="mt-3" @click="onSubmit">Submit</b-button>
        <b-button type="cancel" variant="danger" class="mt-3" @click="cancel">Cancel</b-button>

    </div>
</template>

<script>
export default {
    name: "Register",

    inject: ["memberService"],

    props: {
        query: {
            type: Object,
            default: () => {
            }
        },
    },

    data() {
        return {
            memberForm: {
                memberId: '',
                memberName: '',
                memberPassword: '',
                memberPhoneNumber: '',
            },
            show: true,
        }
    },

    methods: {
        async onSubmit() {
            try {
                await this.memberService.insertMember(this.memberForm)
                alert('회원가입에 성공하셨습니다.')
                this.$router.push({
                    path: `/login`,
                    query: this.query
                })
            } catch (e) {
                alert(`아이디 생성에 실패했습니다`)
            }
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