<template>
    <div>

        <b-row class="main-contain">
            <b-col>

                <!--이미지 수정-->
                <v-row
                    v-if="fileEditMode"
                >
                    <!--이미 저장된 이미지-->
                    <v-col
                        v-for="(file, index) in bookFileList"
                        :key="file.seq"
                        cols="auto"
                        class="lg12"
                    >
                        <v-card
                            class="pa-2"
                            width="318px"
                            height="368px"
                            outlined
                            tile
                        >
                            <v-img
                                :src="`http://localhost:8081/api/admin/book/image/${file.seq}`"
                                class="cover-img"
                            ></v-img>

                            <v-btn
                                class="mx-2 file-delete-btn"
                                fab
                                dark
                                small
                                color="error"
                                @click="onDeleteFile(file.seq, index)"
                            >
                                <v-icon dark>
                                    mdi-minus
                                </v-icon>
                            </v-btn>
                        </v-card>
                    </v-col>


                    <!--새로 추가할 이미지-->
                    <v-col
                        v-for="(file, index) in preViewFileList"
                        :key="index"
                        cols="auto"
                        class="lg12"
                    >
                        <v-card
                            class="pa-2"
                            width="318px"
                            height="368px"
                            outlined
                            tile
                        >
                            <div>
                                <img
                                    class="popupImageItem"
                                    :src="file"
                                    alt="image"
                                    width="300px"
                                    height="350px"
                                >

                                <v-btn
                                    class="mx-2 file-delete-btn"
                                    fab
                                    dark
                                    small
                                    color="error"
                                    @click="onUploadFileCancel(index)"
                                >
                                    <v-icon dark>
                                        mdi-minus
                                    </v-icon>
                                </v-btn>
                            </div>
                        </v-card>
                    </v-col>

                    <!--이미지 추가 버튼 (컬럼)-->
                    <v-col
                        v-if="bookFileList.length + preViewFileList.length < imageMaxCount"
                        cols="auto"
                        class="lg12"
                    >
                        <v-card
                            class="pa-2"
                            width="318px"
                            height="368px"
                            outlined
                            tile
                        >
                            <div>
                            <!-- 1. Create the button that will be clicked to select a file -->
                                <v-btn
                                    color="primary"
                                    rounded
                                    dark
                                    :loading="isSelecting"
                                    @click="handleFileImport"
                                >
                                    Upload File
                                </v-btn>

                                <!-- Create a File Input that will be hidden but triggered with JavaScript -->
                                <input
                                    id="uploader"
                                    ref="uploader"
                                    class="d-none"
                                    type="file"
                                    @change="onFileSelected"
                                />
                            </div>
                        </v-card>
                    </v-col>

                    <div>
                        <v-btn
                            class="m-1"
                            depressed
                            color="success"
                            @click="onFileEditSave"
                        >
                            save
                        </v-btn>

                        <v-btn
                            class="m-1"
                            depressed
                            color="error"
                            @click="onFileEditInit"
                        >
                            cancel
                        </v-btn>
                    </div>
                </v-row>

                <!--일반 이미지-->
                <v-row
                    v-else
                >
                    <v-col
                        v-for="(file) in bookFileList"
                        :key="file.seq"
                        cols="auto"
                        class="lg12"
                    >
                        <v-card
                            class="pa-2"
                            width="318px"
                            height="368px"
                            outlined
                            tile
                        >
                            <v-img
                                :src="`http://localhost:8081/api/admin/book/image/${file.seq}`"
                                class="cover-img"
                            ></v-img>
                        </v-card>
                    </v-col>

                    <div>
                        <v-btn
                            class="ml-5"
                            color="primary"
                            @click="onFileEditMode"
                        >
                            이미지 수정
                        </v-btn>
                    </div>
                </v-row>

                <!--   ImageEditDialog     -->
                <!--                <ImageEditDialog
                                    :book-seq="bookDetail.seq"
                                    :file-list="bookFileList"
                                    @fetchBookDetail="fetchBookDetail"
                                ></ImageEditDialog>-->
                <!--   ImageEditDialog     -->

            </b-col>
        </b-row>

        <b-row>
            <b-col class="main-text-area">
                <b-card title="Book Info">
                    <div class="main-text">
                        <div>
                            <p class="label">idNo : </p>
                            <p class="label">{{ bookDetail.idNo }}</p>
                        </div>
                        <div>
                            <p class="label">이름 : </p>
                            <p class="label">{{ bookDetail.name }}</p>
                        </div>
                        <div>
                            <p class="label">저자 : </p>
                            <p class="label">{{ bookDetail.author }}</p>
                        </div>
                        <div>
                            <p class="label">대여 비용 : </p>
                            <p class="label">{{ bookDetail.rentalFee }}</p>
                        </div>
                        <div>
                            <p class="label">대여 기간 : </p>
                            <p class="label">{{ bookDetail.rentalPeriod }}</p>
                        </div>

                        <div>
                            <p class="label">메모 : </p>
                            <p class="label">{{ bookDetail.memo }}</p>
                        </div>

                    </div>
                </b-card>
            </b-col>
        </b-row>

        <div class="content-detail-button">
            <b-button
                @click="gotoList"
                class="m-2 content-button"
            >목록
            </b-button>

            <!--   book-edit-dialog     -->
            <book-edit-dialog
                class="content-button"
                :book-detail="bookDetail"
                @fetchBookDetail="fetchBookDetail"
            ></book-edit-dialog>
            <!--   book-edit-dialog     -->

            <b-button
                class="content-button"
                variant="danger"
                v-b-modal.modal-prevent-closing
                @click="onDeleteBook"
            >삭제
            </b-button>

        </div>

    </div>
</template>

<script>
import BookEditDialog from "@/components/dialogs/BookEditDialog";

export default {
    name: "BookView",

    components: {BookEditDialog},

    props: {
        query: {
            type: Object,
            default: () => {
            },
        },
        seq: String,
    },

    data() {
        return {
            bookDetail: {},
            bookFileList: [],

            imageMaxCount: 4,
            isSelecting: false,
            preViewFileList: [],

            addFileList: [],
            deleteFileSeqList: [],

            fileEditMode: false,
        }
    },

    created() {
    },

    mounted() {
        this.fetchBookDetail();
    },

    computed: {
        isSelects: function () {
            return new Array(this.imageMaxCount).fill(false);
        }
    },

    methods: {

        async fetchBookDetail() {
            const {bookDetail, bookFileList} = await this.$bookService.getBookDetail(this.seq);
            this.bookDetail = bookDetail;
            this.bookFileList = bookFileList;
        },

        gotoList() {
            this.$router.push({
                path: `/admin/book/list`,
                query: this.query,
            })
        },

        async onDeleteBook() {
            if (confirm('등록된 도서 정보를 삭제 하시겠습니까?')) {
                await this.$bookService.deleteBook(this.bookDetail.seq);
                alert('도서 정보가 삭제 되었습니다!');
                this.gotoList();
            }
        },

        handleFileImport() {
            this.isSelecting = true;

            // After obtaining the focus when closing the FilePicker, return the button state to normal
            window.addEventListener('focus', () => {
                this.isSelecting = false
            }, {once: true});

            // Trigger click on the FileInput
            document.getElementById(`uploader`).click();
        },

        onFileSelected(event) {
            // const input = document.getElementById(`uploader`);
            const input = event.target;
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    // this.uploadImageFile = e.target.result;
                    this.preViewFileList = [
                        ...this.preViewFileList,
                        e.target.result
                    ]
                }
                reader.readAsDataURL(input.files[0]);
                this.addFileList = [
                    ...this.addFileList,
                    input.files[0]
                ]
            }
        },

        onDeleteFile(fileSeq, index) {
            this.deleteFileSeqList.push(fileSeq);
            this.bookFileList.splice(index, 1);
        },

        onUploadFileCancel(index) {
            this.addFileList.splice(index, 1);
            this.preViewFileList.splice(index, 1);
        },

        async onFileEditSave() {
            await this.$bookService.updateBookFile(this.seq, this.addFileList, this.deleteFileSeqList);
            alert('이미지 파일 수정이 완료 되었습니다.');
            // await this.fetchBookDetail();
            await this.fetchBookDetail();
            // this.imageDialog = false;
            await this.onFileEditInit();
        },

        onFileEditMode() {
            this.fileEditMode = true;
        },

        async onFileEditInit() {
            this.preViewFileList = [];
            this.addFileList = [];
            this.deleteFileSeqList = [];
            await this.fetchBookDetail();
            this.fileEditMode = false;
        }


    }
}
</script>

<style scoped>
.content-button {
    display: inline-block;
    margin: 5px;
}

.main-text-area {
    margin-top: 10px;
}

.main-text {
    text-align: left;
}

.label {
    display: inline-block;
}

.cover-img {
    width: 300px;
    height: 350px;
    object-fit: cover;
}

.file-delete-btn {
    position: absolute;
    right: 5px;
    top: 10px;
}
</style>