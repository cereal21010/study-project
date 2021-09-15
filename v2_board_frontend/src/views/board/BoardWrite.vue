<template>
    <div>
        <div>
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

            <div>
                <b-form-checkbox v-model="passwordMode" name="check-button" switch>
                    set password
                </b-form-checkbox>
                <b-input v-model="boardDetail.password" placeholder="비밀 번호를 입력해주세요." :disabled="!passwordMode"></b-input>
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
            <b-button @click="updateMode ? update() : save()">{{ updateMode ? "수정" : "저장" }}</b-button>&nbsp;
            <b-button @click="cancel">취소</b-button>
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
                password: ''
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

            passwordMode: false
        }
    },

    created() {
        if (this.updateMode) {
            this.fatchBoardDetail();
        }
    },

    mounted() {
    },

    methods: {
        async fatchBoardDetail() {
            let {boardDetail} = await this.boardService.getBoardDetail(this.seq);
            this.boardDetail.title = boardDetail.title;
            this.boardDetail.contents = boardDetail.contents;
            this.boardDetail.category = boardDetail.category;
            this.files = boardDetail.files;
        },

        async save() {
            const response = await this.boardService.insertBoard(this.boardDetail, this.selectFiles);
            if (response.status === 200) {
                this.$router.push({
                    path: `/board/list`
                })
            } else {
                alert('게시글 등록 실패');
            }

        },

        async update() {
            console.log(this.boardDetail)
            const status = await this.boardService.updateBoard(this.boardDetail, this.selectFiles, this.fileRemoveList);
            console.log(status);
            if (status === 200) {
                this.$router.push({
                    path: `/board/view/${this.seq}`,
                    query: this.query
                })
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
        }
    }
}
</script>

<style scoped>

</style>