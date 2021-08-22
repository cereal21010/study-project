<template>
  <div>
    <b-table
        striped
        hover
        :items="boardList"
        :fields="fields"
    ></b-table>
    <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" align="center"></b-pagination>
    <b-button @click="goWrite">글쓰기</b-button>
  </div>
</template>

<script>

export default {
  name: "BoardList",

  props: {
    query: {
      type: Object,
      default: () => {

      }
    }
  },

  inject: ['boardService'],

  data() {
    return {
      fields: [
        {
          key: "seq",
          label: "번호"
        },
        {
          key: "title",
          label: "제목"
        },
        {
          key: "writer",
          label: "작성자"
        },
        {
          key: "createdDate",
          label: "작성일"
        },
        {
          key: "modifiedDate",
          label: "수정일"
        }
      ],
      boardList: []
    }
  },

  mounted() {
    this.fetchList();
  },

  methods: {
    async fetchList() {
      const result = await this.boardService.getBoardList();
      this.boardList = result;
      console.log(result);
    },
    goWrite() {
      this.$router.push({
        path: `board/write`
      })
    }
  }

}
</script>

<style scoped>

</style>