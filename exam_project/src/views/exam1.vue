<template>
    <div>
        <div>
            <input value="inputText" v-model="inputText"/>
            <button type="submit" @click="onSubmit">add</button>
        </div>

        <div>
            <span>
                <div class="textBoxArea" >
                    <textarea ref="textBox1"></textarea>
                    <button @click="onClickSubscribe(1, isSubscribe1)" v-if="isSubscribe1">구독해지</button>
                    <button @click="onClickSubscribe(1, isSubscribe1)" v-else>구독</button>
                </div>
                <div class="textBoxArea">
                    <textarea ref="textBox2"></textarea>
                    <button @click="onClickSubscribe(2, isSubscribe2)" v-if="isSubscribe2">구독해지</button>
                    <button @click="onClickSubscribe(2, isSubscribe2)" v-else>구독</button>
                </div>
                <div class="textBoxArea">
                    <textarea ref="textBox3"></textarea>
                    <button @click="onClickSubscribe(3, isSubscribe3)" v-if="isSubscribe3">구독해지</button>
                    <button @click="onClickSubscribe(3, isSubscribe3)" v-else>구독</button>
                </div>
            </span>
        </div>

    </div>
</template>

<script>
export default {
    name: "exam1",

    data() {
        return {
            isSubscribe1 : false,
            isSubscribe2 : false,
            isSubscribe3 : false,
            inputText: '',
        }
    },

    methods: {
        onClickSubscribe(index, value) {
            const changeVal = value === false ? true : false;
            if (index === 1) {
                this.isSubscribe1 = changeVal;
            } else if (index === 2) {
                this.isSubscribe2 = changeVal;
            } else if (index === 3) {
                this.isSubscribe3 = changeVal;
            }
        },

        onSubmit() {
            console.log(this.inputText);
            if (this.isSubscribe1 === true) {
                this.$refs.textBox1.value = this.inputText;
            }

            if (this.isSubscribe2 === true) {
                this.$refs.textBox2.value = this.inputText;
            }

            if (this.isSubscribe3 === true) {
                this.$refs.textBox3.value = this.inputText;
            }
        },

    },

}
</script>

<style scoped>
.textBoxArea {
    float: left;
}
</style>