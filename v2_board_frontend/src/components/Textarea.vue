<template>
    <div class="comment-form">
        <textarea v-model="commentDetail.content" type="text" placeholder="Comment is here: with markdown"></textarea>

        <button @click="save">Add Comment</button>
    </div>
</template>

<script>
export default {
    name: "textarea"
}
</script>

<style scoped>

.comment-form{
    display: block;
    width: 80%;
    margin: auto;
}

textarea{
    width: 100%;
    border: 2px solid #ccc;
    border-radius: 7px;
    height: 70px;
    font-family: Verdana, Helvetica, sans-serif;
    padding: 5px;
}

button{
    background: #333;
    color: #ccc;
    border: 0;
    padding: 5px;
    cursor: pointer;
}

</style>