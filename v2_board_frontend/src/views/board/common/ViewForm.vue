<template>
    <div>
        <b-card>
            <div v-for="(boardDetail, index) in boardList" :key="index">
                <div class="content-detail-content-info">
                    <div class="content-detail-content-info-left">
                        <div class="content-detail-content-info-left-number">{{ boardDetail.seq }}</div>
                        <div class="content-detail-content-info-left-category">카테고리: {{ boardDetail.category }}</div>
                        <div class="content-detail-content-info-left-subject">제목: {{ boardDetail.title }}</div>
                    </div>
                    <div class="content-detail-content-info-right">
                        <div class="content-detail-content-info-right-user">글쓴이: {{ boardDetail.writer }}</div>
                        <div class="content-detail-content-info-right-created"
                             v-if="boardDetail.changedDate !== undefined"
                        >수정일: {{ boardDetail.changedDate }}
                        </div>
                    </div>
                </div>

                <div class="content-detail-content">
                    {{ boardDetail.contents }}
                </div>
            </div>
            <br/>
        </b-card>
    </div>
</template>

<script>
export default {
    name: "ViewForm",

    props: {
        boardList: [],
    },

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

.content-detail-content-info2-item {
    flex: 1;
}

.content-detail-button {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}
</style>