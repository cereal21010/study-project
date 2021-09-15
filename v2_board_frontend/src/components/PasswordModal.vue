<template>
    <div class="modal-mask">
        <div class="modal-wrapper">
            <div class="modal-container">

                <div class="modal-header">
                    <h3>게시글 비밀번호 입력</h3>
                </div>

                <div class="modal-body">
                    <b-input v-model="inputPassword" placeholder="비밀 번호를 입력해주세요." ></b-input>
                </div>
                <div v-if="!pwCheck">
                    <P>❌ Try again!</P>
                </div>
                <div class="modal-footer">
                    <slot name="footer">
                        <button class="modal-default-button" @click="passwordCheck">
                            ok
                        </button>
                        <button class="modal-default-button" @click="modalClose">
                            close
                        </button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "PasswordModal",

    props:{
        /*clickedBoard: {
            type: Object,
            default: () => {}
        },*/
        boardPassword: String,
        gotoPage: String,
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

            if( !(this.boardPassword === this.inputPassword) ){
                this.pwCheck = false;
                this.$emit('password-check', undefined);
            }else {
                this.pwCheck = true;
                this.$emit('password-check', this.gotoPage);
            }


        }
    }
}
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-container {
    width: 300px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
    margin-top: 0;
    color: #42b983;
}

.modal-body {
    margin: 20px 0;
}

.modal-default-button {
    float: right;
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
    opacity: 0;
}

.modal-leave-active {
    opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
</style>