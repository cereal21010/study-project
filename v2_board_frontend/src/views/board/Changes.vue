<template>
    <div>
        <b-card>
            <h3>현재 글</h3>
            <div class="content-detail-content-info">
                <div class="content-detail-content-info-left">
                    <div class="content-detail-content-info-left-number">{{ boardDetail.seq }}</div>
                    <div class="content-detail-content-info-left-category">카테고리: {{ boardDetail.category }}</div>
                    <div class="content-detail-content-info-left-subject">제목: {{ boardDetail.title }}</div>
                </div>
                <div class="content-detail-content-info-right">
                    <div class="content-detail-content-info-right-user">글쓴이: {{ boardDetail.writer }}</div>
                    <div class="content-detail-content-info-right-created">등록일: {{ boardDetail.changedDate }}</div>
                </div>
            </div>

            <div class="content-detail-content">
                {{ boardDetail.contents }}
            </div>

            <h3>수정 이력</h3>


            <div class="content-detail-button">
                <b-button @click="goList">목록</b-button>
                <b-button variant="primary" @click="updateBoard">수정</b-button>&nbsp;
                <b-button variant="danger" @click="deleteBoard">삭제</b-button>
            </div>

        </b-card>
    </div>
</template>

<script>
export default {
    name: "Changes",

    inject: ['boardService'],

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

    data() {
        return {

        }
    },

    async mounted() {
        await this.fatchList();
    },

    methods: {
        async fatchList() {
            const {boardDetail, changedBoardList} = await this.boardService.getChangedBoardList()
            console.log(boardDetail);
            console.log(changedBoardList);
        },

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
</style>