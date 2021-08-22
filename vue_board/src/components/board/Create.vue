<template>
  <div>
    <b-input v-model="title" placeholder="제목을 입력해주세요."></b-input>
    <b-form-textarea
        v-model="content"
        placeholder="내용을 입력해 주세요"
        rows="3"
        max-rows="6"
    ></b-form-textarea>
    <b-input v-model="category" placeholder="카테고리를 입력해주세요."></b-input>
    <br>
    <b-button @click="saveContent()">저장</b-button>&nbsp;
    <b-button @click="cancle">취소</b-button>
  </div>
</template>

<script>
export default {
  name: "Create",
  data() {
    return {
      title: '',
      content: '',
      createdDate: null,
      modifiedDate: null

    }
  },
  created() {
    console.log(this.$route)
  },
  methods: {
    saveContent(){

    },

    cancle() {
      this.$router.push({
        path: '/board/list'
      })
    }
  }
}
</script>

<style scoped>

</style>