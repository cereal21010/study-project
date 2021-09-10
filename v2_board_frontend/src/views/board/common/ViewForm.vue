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
                        <div class="content-detail-content-info-right-created">등록일: {{ boardDetail.createdDate }}</div>
                    </div>
                </div>

                <div class="content-detail-content">
                    {{ boardDetail.contents }}
                </div>
            </div>
        </b-card>
    </div>
</template>

<script>
export default {
    name: "ViewForm",

    props: {
        boardList: []
    }
}
</script>

<style scoped>

</style>