<template>
    <div>
        <b-card title="게시글 상세보기">
            <hr>
            제목:
            <br/>
            작성자:
            <br/>
            작성일:
            <br/>
            <hr>
            <b-card-text class="textBox">
                Some quick example text to build on the <em>card title</em> and make up the bulk of the card's
                content.
            </b-card-text>

            <b-card-text class="textBox" >A second paragraph of text in the card.</b-card-text>

        </b-card>
        <a href="#" class="card-link">Card link</a>
        <b-link href="#" class="card-link">Another link</b-link>
    </div>
</template>

<script>
export default {
    name: "boardView",

    props: {
        searchParams: {
            type: Object,
            default: () => {},
        },
        seq: String,
    }
}
</script>

<style scoped>

.textBox{
    text-align: left;
}

</style>