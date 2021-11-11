<template>
    <div>
        <b-modal
            id="modal-prevent-closing"
            ref="modal"
            title="비밀번호 입력"
            @show="resetModal"
            @hidden="resetModal"
            @ok="handleOk"
        >
            <form ref="form" @submit.stop.prevent="passwordCheck">
                <b-form-group
                    label="Password"
                    label-for="password-input"
                    :invalid-feedback="invalidMessage"
                    :state="inputStatus"
                >
                    <b-form-input
                        id="password-input"
                        v-model="inputPassword"
                        :state="inputStatus"
                        required
                    ></b-form-input>
                </b-form-group>
            </form>
        </b-modal>
    </div>
</template>

<script>
export default {
    name: "PasswordModal",

    inject: ['boardService'],

    props: {
        /*clickedBoard: {
            type: Object,
            default: () => {}
        },*/
        boardSeq: String,
        boardPassword: String,
        nextPage: String,
    },

    data() {
        return {
            inputPassword: '',

            inputStatus: null,

            invalidMessage: '',
        }
    },

    computed: {},

    methods: {
        async passwordCheck() {
            if (!this.checkFormValidity('password is required')) {
                return
            }
            const passwordCheck = await this.boardService.passwordCheck(this.boardSeq, this.inputPassword);
            if (passwordCheck) {
                this.$emit('goto-page');
            } else {
                this.checkFormValidity('password do not match', false);
            }
        },

        checkFormValidity(invalidMessage, status) {
            let valid = this.$refs.form.checkValidity()
            this.invalidMessage = invalidMessage;
            if (status !== undefined) {
                valid = status;
            }
            this.inputStatus = valid;

            return valid;
        },
        resetModal() {
            this.inputPassword = '';
            this.inputStatus = null
        },
        handleOk(bvModalEvt) {
            bvModalEvt.preventDefault();
            this.passwordCheck();
        },

    }
}
</script>

<style scoped>

</style>