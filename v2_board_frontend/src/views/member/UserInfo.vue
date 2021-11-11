<template>
    <div class="m-3">
        <b-form-group
            id="input-group-1"
            label="Email address:"
            label-for="input-1"
        >
            <b-form-input
                id="input-1"
                v-model="memberInfo.memberId"
                type="email"
                placeholder="Enter email"
                required
                disabled
            ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="Your Name:" label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="memberInfo.memberName"
                placeholder="Enter name"
                required
            ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="password:" label-for="input-3">
            <b-form-input
                id="input-3"
                v-model="memberInfo.memberPassword"
                placeholder="Enter password"
                required
            ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-4" label="phone number:" label-for="input-4">
            <b-form-input
                id="input-4"
                v-model="memberInfo.memberPhoneNumber"
                placeholder="Enter phone number"
                required
            ></b-form-input>
        </b-form-group>

        <b-button variant="primary" @click="updateMemberInfo">저장</b-button>
        <b-button type="reset" variant="danger" @click="cancel">취소</b-button>

    </div>
</template>

<script>
export default {
    name: "UserInfo",

    inject: ['memberService'],

    data() {
        return {
            memberInfo: {
                memberId: '',
                memberName: '',
                memberPassword: '',
                memberPhoneNumber: '',
            },
        }
    },

    mounted() {
        this.fetchMemberInfo();
    },

    methods: {
        async fetchMemberInfo() {
            const loginId = this.$store.getters.getMemberId;
            const memberInfo = await this.memberService.getUserInfo(loginId);
            this.memberInfo = memberInfo;
        },

        async updateMemberInfo() {
            await this.memberService.updateMember(this.memberInfo);
            this.$router.go(-1);
        },

        cancel() {
            this.$router.go(-1);
        }
    },

}
</script>

<style scoped>

</style>