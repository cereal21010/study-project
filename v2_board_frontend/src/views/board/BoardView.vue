<template>
    <div>
        <b-card>
            <div class="content-detail-content-info">
                <div class="content-detail-content-info-left">
                    <div class="content-detail-content-info-left-number">{{ boardDetail.seq }}</div>
                    <div class="content-detail-content-info-left-category">카테고리: {{ boardDetail.category }}</div>
                    <div class="content-detail-content-info-left-subject">제목: {{ boardDetail.title }}</div>
                </div>
                <div class="content-detail-content-info-right">
                    <div class="content-detail-content-info-right-user">글쓴이: {{ boardDetail.writer }}</div>
                    <div class="content-detail-content-info-right-created">등록일: {{ boardDetail.createdDate }}</div>
                </div>
            </div>
            <div class="content-detail-content-info2">
                <div class="content-detail-content-info2-item">
                    👀조회수: {{ boardDetail.viewCount }}
                </div>
                <div class="content-detail-content-info2-item">
                    👍추천수: {{ recommendCount }}
                </div>
                <div class="content-detail-content-info2-item">
                    <b-form-checkbox
                        id="checkbox-1"
                        v-model="isRecommended"
                        name="checkbox-1"
                        @change="toRecommend"
                    >
                        👍
                    </b-form-checkbox>
                </div>
            </div>

            <div class="content-detail-change-info">
                <p @click="goChanges">수정된 날짜: {{ boardDetail.modifiedDate }}</p>
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
                <b-button variant="danger" @click="deleteBoard">삭제</b-button>
            </div>

            <Comment
                :comments="comments"
                :board-seq="boardDetail.seq"
            ></Comment>

        </b-card>
    </div>
</template>

<script>
import FileList from "./common/FileList";
import Comment from "./common/Comment";
export default {
    name: "BoardView",
    components: {Comment, FileList},
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

    inject: ['boardService',
            'commentService',
            'recommendService'
    ],

    data() {
        return {
            boardDetail: {},

            files: [],

            fileDeleteList: [],

            comments: [],

            recommendCount: 0,

            isRecommended: Boolean,

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
            let {boardDetail, files, comments, recommendCount, isRecommended} = await this.boardService.getBoardDetail(this.seq);
            this.boardDetail = boardDetail;
            this.files = files;
            this.comments = comments;
            this.recommendCount = recommendCount;
            this.isRecommended = isRecommended;
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

        async toRecommend(status){
            if( status ){
                const testMemberSeq = 2;
                await this.recommendService.insertRecommend(this.seq, testMemberSeq);
                this.recommendCount = await this.recommendService.getRecommendCount(this.seq);
                // this.isRecommended = true;
            }else {
                await this.recommendService.deleteRecommend(this.seq);
                this.recommendCount = await this.recommendService.getRecommendCount(this.seq);
                // this.isRecommended = false;
            }
        },

        goChanges() {
            this.$router.push({
                path: `/board/changes/${this.seq}`,
                query: this.query
            })
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

.content-detail-content-info2 {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
    display: flex;
}

.content-detail-content-info2-item{
    flex: 1;
}

.content-detail-button {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}

.content-detail-change-info {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
    align-content: center;
}
</style>