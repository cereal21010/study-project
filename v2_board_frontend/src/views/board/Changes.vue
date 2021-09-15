<template>
    <div>
        <b-card>
            <h3>현재 글</h3>
            <view-form
                :board-list="[boardDetail]"
            ></view-form>

            <h3>수정 이력</h3>
            <view-form
                :board-list="changedBoardList"
            ></view-form>

            <div class="content-detail-button">
                <b-button @click="goView">이전</b-button>
            </div>

        </b-card>
    </div>
</template>

<script>
import ViewForm from "./common/ViewForm";
export default {
    name: "Changes",
    components: {ViewForm},
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
            boardDetail: {},
            changedBoardList: [],
        }
    },

    async mounted() {
        await this.fatchList();
    },

    methods: {
        async fatchList() {
            const {boardDetail, changedBoardList} = await this.boardService.getChangedBoardList(this.seq);
            this.boardDetail = boardDetail;
            this.changedBoardList = changedBoardList;
        },

        goView() {
            this.$router.push({
                path: `/board/view/${this.seq}`,
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
</style>