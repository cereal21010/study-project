<template>
  <div>
    <br>
    <!--  검색 창  -->
    <b-input-group class="mt-3">
      <b-input-group-prepend>
        <b-form-select v-model="searchParams.searchType" :options="selectOptions" style="height: 100%"></b-form-select>
      </b-input-group-prepend>
      <b-form-input v-model="searchParams.keyword"></b-form-input>
      <b-input-group-append>
        <b-button variant="outline-success" @click="doSearch">Search</b-button>
      </b-input-group-append>
    </b-input-group>

    <br>
    <b-table
        id="boardList-table"
        striped
        hover
        :items="boardList"
        :fields="fields"
        @row-clicked="goView"
        @head-clicked="goSort"
    ></b-table>

    <b-form-select
        v-model="searchParams.contentNum"
        :options="contentOptions"
        size="sm"
        class="mt-3"
        @change="contentNumChange">
    </b-form-select>

    <b-pagination
        :value="searchParams.pageNum"
        :total-rows="pagination.totalCount"
        :per-page="searchParams.contentNum"
        align="center"
        @change="gotoPage"
    ></b-pagination>
    <b-button @click="goWrite">글쓰기</b-button>
  </div>
</template>

<script>
// import Vue from "vue";

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

      boardList: [],

      pagination: {
        totalCount: 0
      },

      searchParams: {
        contentNum: 10,
        pageNum: 1,
        searchType: 'title',
        keyword: '',
        sort: 'createdDate',
        order: 'desc'
      },

      selectOptions: [
        { value: 'title', text: '제목' },
        { value: 'writer', text: '작성자' },
        { value: 'contents', text: '내용' },
      ],

      contentOptions: [
        { value: 10, text: '10' },
        { value: 20, text: '20' },
        { value: 30, text: '30' },
      ],

    }
  },

  created() {
    this.initSearchParams();
  },

  async mounted() {
    await this.fetchList();
  },

  methods: {

    initSearchParams() {
      let _searchParams = this.searchParams;
      let _query = this.query;
      Object.keys(_query).forEach(function (key){
        _searchParams[key] = _query[key];
      })
    },

    async fetchList() {
      const result = await this.boardService.getBoardList(this.searchParams);
      this.boardList = result.data.boardList;
      this.pagination.totalCount = Number(result.data.pagination.totalCount);
    },

    goWrite() {
      this.$router.push({
        path: `/board/write`,
        query: this.searchParams
      })
    },

    goView(row) {
      this.$router.push({
        path: `/board/view/${row.seq}`,
        query: this.searchParams
      })
    },

    async gotoPage(page) {
      this.searchParams.pageNum = page;
      const result = await this.boardService.getBoardList(this.searchParams);
      this.boardList = result.data.boardList;
      this.pagination.totalCount = result.data.pagination.totalCount;
    },

    async doSearch() {
      this.searchParams["pageNum"] = 1;
      const result = await this.boardService.getBoardList(this.searchParams);
      this.boardList = result.data.boardList;
      this.pagination.totalCount = result.data.pagination.totalCount;
    },

    async goSort(sort) {
      if( this.searchParams["sort"] === sort && this.searchParams["order"] === 'desc'){
        this.searchParams["order"] = 'asc'
      }else {
        this.searchParams["sort"] = sort;
        this.searchParams["order"] = 'desc'
      }

      const result = await this.boardService.getBoardList(this.searchParams);
      this.boardList = result.data.boardList;
      this.pagination.totalCount = result.data.pagination.totalCount;
    },

    async contentNumChange() {
      this.searchParams["pageNum"] = 1;
      const result = await this.boardService.getBoardList(this.searchParams);
      this.boardList = result.data.boardList;
      this.pagination.totalCount = result.data.pagination.totalCount;
    },

  },


}
</script>

<style scoped>
.sr-only {display: none;}
</style>