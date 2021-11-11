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
                id="memberId"
                v-model="memberId"
                type="email"
                placeholder="Enter email"
                required
            ></b-form-input>
        </b-form-group>

        <!--        <div class="alert-danger" v-if="errorState"> &lt;!&ndash; errorState가 있으면 표시한다 &ndash;&gt;
                    <p>{{ errorState }}</p>
                </div>-->

        <b-form-group id="input-group-3" label="Your password:" label-for="input-3" class="m-5">
            <b-form-input
                id="memberPassword"
                v-model="memberPassword"
                type="password"
                placeholder="Enter password"
                required
            ></b-form-input>
        </b-form-group>
        <b-button type="submit" variant="primary" class="mt-3" @click="onSubmit(memberId, memberPassword)">Login
        </b-button>
        <b-button type="cancel" variant="danger" class="mt-3" @click="cancel">Cancel</b-button>
    </div>
</template>

<script>
// import EventBus from "../../utils/EventBus";
// import {mapActions, mapGetters} from "vuex";

export default {
    name: "Login",

    inject: ['memberService'],

    props: {
        query: {
            type: Object,
            default: () => {
            }
        },
    },

    data() {
        return {
            show: true,
            memberId: "",
            memberPassword: "",
            message: "로그인해주세요",
            status: "",
            token: "",
            info: ""
        };
    },

    computed: {
        /*...mapGetters({
            errorState: 'getErrorState'
        })*/
    },

    methods: {

        /*...mapActions(['login']),
        async onSubmit() {
            try {
                let loginResult = await this.login({memberId: this.memberId, password: this.memberPassword})
                console.log(loginResult)
                if( loginResult ) {
                    // this.gotoPage();
                }

            } catch (error) {
                console.error(error)
            }
        },

        gotoPage() {
            this.$router.push({
                path: `/board/list`,
                query: this.query
            })
        },*/
        async onSubmit(memberId, memberPassword) {
            this.$store
                .dispatch("LOGIN", {memberId, memberPassword})
                .then(
                    this.$router.push({
                        path: `/board/list`,
                        query: this.query
                    })
                )
        },

        cancel() {
            this.$router.push({
                path: `/board/list`,
                query: this.query
            })
        },

    },

}
</script>

<style scoped>

</style>
