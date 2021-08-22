<template>
  <div>
    <div>
      <b-input v-model="boardDetail.title" placeholder="제목을 입력해주세요."></b-input>
      <b-form-textarea
          v-model="boardDetail.contents"
          placeholder="내용을 입력해 주세요"
          rows="3"
          max-rows="6"
      ></b-form-textarea>
      <b-input v-model="boardDetail.category" placeholder="카테고리를 입력해주세요."></b-input>
      <br>
      <b-button @click="save">저장</b-button>&nbsp;
      <b-button @click="cancel">취소</b-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "BoardCreate",

  inject: ['boardService'],

  data() {
    return {
      boardDetail: {
        title: '',
        contents: '',
        category: ''
      }
    }
  },

  methods: {
    save() {
      this.boardService.insertBoard(this.boardDetail);
      console.log(`save success`);
      this.$router.push({
        path: `/`
      })
    },
    cancel() {
      this.$router.push({
        path: `/`
      })
    }
  }
}
</script>

<style scoped>

</style>