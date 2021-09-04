<template>
    <div>
        <b-card>
            <div class="content-detail-content-info">
                <div class="content-detail-content-info-left">
                    <div class="content-detail-content-info-left-number">{{ boardDetail.seq }}</div>
                    <div class="content-detail-content-info-left-subject">{{ boardDetail.title }}</div>
                </div>
                <div class="content-detail-content-info-right">
                    <div class="content-detail-content-info-right-user">글쓴이: {{ boardDetail.writer }}</div>
                    <div class="content-detail-content-info-right-created">등록일: {{ boardDetail.createdDate }}</div>
                </div>
            </div>
            <div class="content-detail-fileList">
                {{ boardDetail.category }}
            </div>
            <div class="content-detail-content">{{ boardDetail.contents }}</div>

            <file-list
                v-if="files.length > 0"
                :files="files"
                @file-download="fileDownload"
            ></file-list>

            <div class="content-detail-button">
                <b-button @click="goList">목록</b-button>
                <b-button variant="primary" @click="updateBoard">수정</b-button>&nbsp;
                <b-button variant="success" @click="deleteBoard">삭제</b-button>
            </div>
        </b-card>
    </div>
</template>

<script>
import FileList from "./common/FileList";
export default {
    name: "BoardView",
    components: {FileList},
    props: {
        seq: {
            type: String,
            required: true
        },
        query: {
            type: Object,
            default: () => {
            }
        },

    },

    inject: ['boardService'],

    data() {
        return {
            boardDetail: {},
            files: [],
            fileDeleteList: []
        }
    },

    mounted() {
        /*Object.keys(src).forEach(function(key) {
          Vue.set(target, key, src[key]); // 또는
        })*/
        this.fatchBoardDetail()
    },

    methods: {
        async fatchBoardDetail() {
            let result = await this.boardService.getBoardDetail(this.seq);
            this.boardDetail = result.data.board;
            this.files = result.data.files;
        },

        async deleteBoard() {
            let result = await this.boardService.deleteBoard((this.seq));
            if (result.status === 200) {
                this.$router.push('/');
            }
        },

        updateBoard() {
            this.$router.push({
                path: '/board/update/' + this.seq,
                query: this.query
            })
        },

        goList() {
            this.$router.push({
                path: `/board/list`,
                query: this.query
            })
        },

        fileDownload(fileSeq) {
            this.boardService.downloadFile(fileSeq)
        },

        fileDeleteListAdd(fileSeq, event) {
            event.stopPropagation();
            this.fileDeleteList.push(fileSeq);
            console.log(this.fileDeleteList);
        }
    }
}
</script>

<style scoped>
.content-detail-content-info {
    border: 1px solid black;
    display: flex;
    justify-content: space-between;
}

.content-detail-content-info-left {
    width: 720px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
}

.content-detail-content-info-right {
    width: 300px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 1rem;
}

.content-detail-content {
    border: 1px solid black;
    margin-top: 1rem;
    padding-top: 1rem;
    min-height: 720px;
}

.content-detail-fileList {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}

.content-detail-button {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}

.content-detail-comment {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}
</style>