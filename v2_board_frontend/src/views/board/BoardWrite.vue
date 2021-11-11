<template>
    <div>
        <div v-if="boardDetail.error !== true">
            <b-input v-model="boardDetail.title" placeholder="제목을 입력해주세요."></b-input>
            <b-form-textarea
                v-model="boardDetail.contents"
                placeholder="내용을 입력해 주세요"
                rows="3"
                max-rows="6"
            ></b-form-textarea>
            <div>
                <b-form-select
                    v-model="boardDetail.category"
                    :options="contentOptions"
                    class="mt-3"
                    style="width: 100%; height: 40px"
                ></b-form-select>
            </div>

            <div class="mt-3" v-if="passwordMode">
                <b-input v-model="boardDetail.password" placeholder="비밀 번호를 입력해주세요."></b-input>
            </div>

            <file-upload
                :select-files="selectFiles"
                @select-files="selectFileListAdd"
                @unselect-files="selectFileListRemove"
            ></file-upload>

            <file-list
                v-if="updateMode"
                :update-mode="updateMode"
                :files="files"
                @file-remove="fileRemoveListAdd"
            ></file-list>

            <br>
            <b-button variant="primary" @click="updateMode ? update() : save()">{{ updateMode ? "수정" : "저장" }}
            </b-button>&nbsp;
            <b-button variant="danger" @click="cancel">취소</b-button>
        </div>

        <div v-else>
            게시글을 찾을 수 없습니다.
            <b-button to="/">돌아가기</b-button>
        </div>
    </div>
</template>

<script>
import FileUpload from "./common/FileUpload";
import FileList from "./common/FileList";

export default {
    name: "BoardCreate",
    components: {FileList, FileUpload},
    inject: ['boardService'],

    props: {
        seq: {
            type: String,
            require: true
        },
        query: {
            type: Object,
            default: () => {
            }
        },
    },

    data() {
        return {
            boardDetail: {
                seq: this.seq,
                title: '',
                contents: '',
                category: 'free',
                password: '',
                writer: '',
            },

            contentOptions: [
                {value: 'free', text: '자유'},
                {value: 'community', text: '커뮤니티'},
                {value: 'question', text: '질문'},
            ],

            files: [],

            fileRemoveList: [],

            selectFiles: [],

            updateMode: this.seq !== undefined ? true : false,

            passwordMode: false,
        }
    },


    created() {
    },

    mounted() {
        if (this.updateMode) {
            this.fatchBoardDetail();
        }

        this.loginUserSetting();
    },

    methods: {
        async fatchBoardDetail() {
            let {boardDetail} = await this.boardService.getBoardDetail(this.seq);
            this.boardDetail = boardDetail;
        },

        async save() {
            if (this.passwordMode) {
                if (this.boardDetail.password === '') {
                    alert('게시글 비밀번호를 입력해 주세요');
                    return false;
                }
            }

            try {
                await this.boardService.insertBoard(this.boardDetail, this.selectFiles);
                this.$router.push({
                    path: `/board/list`
                })
            } catch (e) {
                alert('게시글 등록 실패');
            }

        },

        async update() {
            try {
                await this.boardService.updateBoard(this.boardDetail, this.selectFiles, this.fileRemoveList);
                this.$router.push({
                    path: `/board/view/${this.seq}`,
                    query: this.query
                })
            } catch (e) {
                alert('게시글 수정에 실패 했습니다.')
            }
        },

        cancel() {
            let path = '';
            if (this.updateMode) {
                path = '/board/view/' + this.seq;
            } else {
                path = '/board/list';
            }
            this.$router.push({
                path: path,
                query: this.query
            })
        },

        fileRemoveListAdd(fileSeq) {
            this.fileRemoveList.push(fileSeq);
            console.log(`=====fileRemoveListAdd=====`)
            console.log(this.fileRemoveList);
            console.log(`===========================`)
        },

        selectFileListAdd(files) {
            this.selectFiles.push(...files);
            console.log(`=====selectFileListAdd=====`)
            console.log(this.selectFiles);
            console.log(`===========================`)
        },

        selectFileListRemove(index) {
            this.selectFiles.splice(index, 1);
            console.log(`====selectFileListRemove====`)
            console.log(this.selectFiles);
            console.log(`===========================`)
        },

        loginUserSetting() {
            const loginUser = this.$store.getters.getMemberId;
            if (loginUser === null || loginUser === '') {
                this.passwordMode = true;
                this.boardDetail.writer = 'anonymous'
            } else {
                this.passwordMode = false;
                this.boardDetail.writer = loginUser;
            }
        },

    }
}
</script>

<style scoped>

</style>