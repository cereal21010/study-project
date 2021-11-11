<template>
    <div id="demo">
        <div class="comment-form">
            <textarea
                v-model="commentDetail.content"
                type="text"
                placeholder="Comment is here: with markdown"
            ></textarea>
            <b-row v-if="!isLogin">
                <b-col sm="2">
                    <label>Password: </label>
                </b-col>
                <b-col sm="9">
                    <b-form-input v-model="commentDetail.password" placeholder="password enter"></b-form-input>
                </b-col>
            </b-row>

            <b-button variant="success" @click="save">Add Comment</b-button>
        </div>
        <div class="comments-box"
             v-for="(comment, index) in comments"
             :key="index">
            <p class="author"> {{ comment.writer }}</p>
            <div v-if="!comment.updateMode">
                <p class="content-comment">{{ comment.content }}</p>
                <b-button variant="primary" v-if="writerCheck(comment.writer)" @click="isUpdateMode(index)">Update
                </b-button>
                <b-button variant="danger" v-if="writerCheck(comment.writer)"
                          @click="removeComment(comment.seq, index)">Delete
                </b-button>
            </div>
            <div v-else>
                <textarea
                    v-model="comment.content"
                    type="text"
                    placeholder="Comment is here: with markdown"
                ></textarea>
                <b-button variant="primary" @click="updateComment(comment)">저장</b-button>
                <b-button variant="danger" @click="isUpdateMode(index)">취소</b-button>
            </div>

        </div>
    </div>
</template>

<script>
export default {
    name: "Comment",

    inject: ['commentService'],

    props: {
        boardSeq: Number,
        comments: [],
    },

    data() {
        return {
            commentDetail: {
                content: '',
                writer: '',
                boardSeq: Number,
                password: '',
            },

            loginId: this.$store.getters.getMemberId,

            isLogin: this.loginCheck(),
        }
    },

    mounted() {
    },

    computed: {},

    methods: {
        save: function () {
            if (this.commentDetail.content === '') {
                alert("댓글 내용을 입력해주세요~");
                return;
            }

            if (this.isLogin) {
                this.commentDetail.writer = this.loginId;
            } else {
                if (this.commentDetail.password === '') {
                    alert('비밀번호를 입력하세요!');
                    return false;
                }
                this.commentDetail.writer = 'anonymous'
            }
            this.commentDetail.boardSeq = this.boardSeq;
            this.commentService.insertComment(this.commentDetail);
            this.$router.go();

        },

        removeComment: function (seq, commentsIndex) {
            if (confirm('해당 댓글을 삭제 하시겠습니까?')) {
                this.commentService.deleteComment(seq);
                this.comments.splice(commentsIndex, 1);
            }
        },

        isUpdateMode(index) {
            let updateMode = true;

            if (this.comments[index].updateMode) {
                updateMode = false;
            }

            this.comments.map((comment) => comment.updateMode = false)
            this.comments[index].updateMode = updateMode;
        },

        async updateComment(comment) {
            if (comment.content === '') {
                alert("댓글 내용을 입력해주세요~");
                return;
            }
            const response = await this.commentService.updateComment(comment);
            if (response === 200) {
                this.$router.go();
            } else {
                alert('에러!');
            }
        },

        writerCheck(writer) {
            if (this.loginId === writer || writer === 'anonymous') {
                return true;
            } else {
                return false;
            }
        },

        loginCheck() {
            const loginId = this.$store.getters.getMemberId;
            if (loginId == undefined || loginId === '') {
                return false;
            } else {
                return true;
            }
        }

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

/*button {
    background: #333;
    color: #ccc;
    border: 0;
    padding: 5px;
    cursor: pointer;
}*/

/*Comment Box*/

.comments-box {
    width: 90%;
    margin: auto;
    padding: 20px 0;
    border-bottom: 1px solid #000;
}


.author {
    margin: 10px 0;
    font-weight: bold;
}

</style>