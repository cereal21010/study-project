<template>
    <div>

        <b-row class="main-contain">
            <b-col>

                <v-row>
                    <v-col
                        v-for="file in bookFileList"
                        :key="file.seq"
                        cols="auto"
                    >
                        <v-card
                            class="pa-2"
                            outlined
                            tile
                        >
                            <v-img
                                :src="`http://localhost:8081/api/admin/book/image/${file.seq}`"
                            ></v-img>
                        </v-card>
                    </v-col>
                </v-row>

                <!--   ImageEditDialog     -->
                <ImageEditDialog
                    :book-seq="bookDetail.seq"
                    :file-list="bookFileList"
                    @fetchBookDetail="fetchBookDetail"
                ></ImageEditDialog>
                <!--   ImageEditDialog     -->

            </b-col>
        </b-row>

        <b-row>
            <b-col class="main-text-area">
                <b-card title="Book Info">
                    <div class="main-text">
                        <div>
                            <p class="label">idNo : </p>
                            <p class="label">{{bookDetail.idNo}}</p>
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
import ImageEditDialog from "@/components/dialogs/ImageEditDialog";
import BookEditDialog from "@/components/dialogs/BookEditDialog";

export default {
    name: "BookView",

    components: {BookEditDialog, ImageEditDialog},

    props: {
        query: {
            type: Object,
            default: () => {},
        },
        seq: String,
    },

    data() {
        return {
            bookDetail: {},
            bookFileList: [],
        }
    },

    created() {
    },

    mounted() {
        this.fetchBookDetail();
    },

    computed: {
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
            if ( confirm('등록된 도서 정보를 삭제 하시겠습니까?') ) {
                await this.$bookService.deleteBook(this.bookDetail.seq);
                alert('도서 정보가 삭제 되었습니다!');
                this.gotoList();
            }
        },

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
</style>