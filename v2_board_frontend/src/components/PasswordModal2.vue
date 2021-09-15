<template>
    <div>
        <b-modal
            id="modal-prevent-closing"
            ref="modal"
            title="Submit Your Name"
            @ok="passwordCheck"
        >
            <b-form-input
                id="name-input"
                v-model="inputPassword"
                required
            ></b-form-input>
        </b-modal>
    </div>
</template>

<script>
export default {
    name: "PasswordModal2",

    props:{
        clickedBoard: {
            type: Object,
            default: () => {}
        },
    },

    data() {
        return {
            inputPassword: '',

            pwCheck: true
        }
    },

    methods: {
        modalClose(){
            this.$emit('close-modal');
        },

        passwordCheck(){
            if( !(this.clickedBoard.password === this.inputPassword) ){
                this.pwCheck = false;
            }else {
                this.pwCheck = true;
            }
            this.$emit('password-check', this.pwCheck);
        }
    }
}
</script>

<style scoped>

</style>