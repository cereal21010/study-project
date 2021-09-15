<template>
    <div id="demo">
        <div class="comment-form">
            <textarea v-model="commentDetail.content" type="text"
                      placeholder="Comment is here: with markdown"></textarea>
            <label>
                <input v-model="commentDetail.writer" type="text" placeholder="Author name here:">
            </label>
            <button @click="save">Add Comment</button>
        </div>
        <div class="comments-box"
             v-for="(comment, index) in comments"
             :key="index">
            <p class="author"> {{ comment.writer }}</p>
            <p v-if="!comment.updateMode" class="content-comment">{{ comment.content }}</p>
            <div v-else>
                <textarea
                    v-model="comment.content"
                    type="text"
                    placeholder="Comment is here: with markdown"
                ></textarea>
                <b-button @click="updateComment(comment)">저장</b-button>
            </div>
            <p class="delete" @click="removeComment(comment.seq, index)">Delete</p>
            <p class="update" @click="isUpdateMode(index)">Update</p>
        </div>
    </div>
</template>

<script>
export default {
    name: "Comment",

    inject: ['commentService'],

    props: {
        boardSeq: Number,
        comments: []
    },

    data() {
        return {
            commentDetail: {
                content: '',
                writer: '',
                boardSeq: Number,
            },
        }
    },

    mounted() {
    },

    computed: {},

    methods: {
        save: function () {
            this.commentDetail['boardSeq'] = this.boardSeq;
            this.commentService.insertComment(this.commentDetail);
            // this.comments.unshift(this.commentDetail);
            this.$router.go();
            // this.$emit('insert-comment', this.commentDetail);
        },

        removeComment: function (seq, commentsIndex) {
            if (confirm('해당 댓글을 삭제 하시겠습니까?')) {
                this.commentService.deleteComment(seq);
                this.comments.splice(commentsIndex, 1);
            }
        },

        isUpdateMode(index) {
            this.comments[index].updateMode = !this.comments[index].updateMode;
        },

        async updateComment(comment){
            const response = await this.commentService.updateComment(comment);
            console.log(response);
            if(response === 200){
                this.$router.go();
            }else {
                alert('에러!');
                this.$router.go();
            }
        },

    },

}
</script>

<style scoped>

* {
    padding: 0;
    margin: 0;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 16px;
}

#demo {
    margin: 20px 0 0 0;
}

.comment-form {
    display: block;
    width: 80%;
    margin: auto;
}

textarea {
    width: 100%;
    border: 2px solid #ccc;
    border-radius: 7px;
    height: 70px;
    font-family: Verdana, Helvetica, sans-serif;
    padding: 5px;
}

input {
    border: 2px solid #ccc;
    border-radius: 5px;
    padding: 5px;
}

button {
    background: #333;
    color: #ccc;
    border: 0;
    padding: 5px;
    cursor: pointer;
}

/*Comment Box*/

.comments-box {
    width: 90%;
    margin: auto;
    padding: 20px 0;
    border-bottom: 1px solid #000;
}

.delete {
    background: red;
    color: #fff;
    font-size: 12px;
    cursor: pointer;
    display: inline;
    padding: 3px;
    line-height: 10px;
}

.update {
    background: #0048ff;
    color: #fff;
    font-size: 12px;
    cursor: pointer;
    display: inline;
    padding: 3px;
    margin-left: 50px;
}

.author {
    margin: 10px 0;
    font-weight: bold;
}

</style>