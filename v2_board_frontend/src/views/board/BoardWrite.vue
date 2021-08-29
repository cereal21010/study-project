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
      <div>
        <b-form-select v-model="boardDetail.category" :options="contentOptions" class="mt-3" style="width: 100%; height: 40px"></b-form-select>
      </div>
        파일 첨부
      <b-form-file
          v-model="uploadFiles"
          :state="Boolean(uploadFiles)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
          multiple
      ></b-form-file>
      <div class="mt-3" v-if="updateMode">
        file list
        <b-list-group>
          <b-list-group-item v-for="(file, index) in files" :key="index">
            {{file.originalName}}
          </b-list-group-item>
        </b-list-group>
      </div>
      <br>
        <b-button @click="updateMode ? update() : save()">{{ updateMode ? "수정" : "저장" }}</b-button>&nbsp;
        <b-button @click="cancel">취소</b-button>
      </div>
  </div>
</template>

<script>
export default {
  name: "BoardCreate",

  inject: ['boardService'],

  props: {
    seq: {
      type: String,
      require: true
    },
    query: {
      type: Object,
      default: () => {}
    },
  },

  data() {
    return {
      boardDetail: {
        seq: this.seq,
        title: '',
        contents: '',
        category: ''
      },

      contentOptions: [
        { value: 'free', text: '자유'},
        { value: 'community', text: '커뮤니티'},
        { value: 'question', text: '질문'},
      ],

      files: [],

      uploadFiles: [],

      deleteFileList: [],

      updateMode: this.seq !== undefined ? true : false
    }
  },

  created() {
    if(this.updateMode){
      this.fatchBoardDetail();
    }
  },

  mounted() {
  },

  methods: {
    async fatchBoardDetail() {
      let result = await this.boardService.getBoardDetail(this.seq);
      console.log(result);
      this.boardDetail.title = result.data.board.title;
      this.boardDetail.contents = result.data.board.contents;
      this.boardDetail.category = result.data.board.category;
      this.files = result.data.files;
    },

    async save() {
      const response = await this.boardService.insertBoard(this.boardDetail, this.uploadFiles);
      if( response.status === 200 ){
        this.$router.push({
          path: `/board/list`
        })
      }else {
        alert('게시글 등록 실패');
      }

    },

    async update() {
      console.log(this.boardDetail)
      const status = await this.boardService.updateBoard(this.boardDetail, this.uploadFiles, this.deleteFileList);
      console.log(status);
      if(status === 200){
        this.$router.push({
          path: `/board/view/${this.seq}`,
          query: this.query
        })
      }
    },

    cancel() {
      this.$router.push({
        path: `/board/list`,
        query: this.query
      })
    }
  }
}
</script>

<style scoped>

</style>