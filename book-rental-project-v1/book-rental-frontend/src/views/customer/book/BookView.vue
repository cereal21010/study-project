<template>
    <div>

        <b-row class="main-contain">
            <b-col>
                <b-carousel
                    id="carousel-1"
                    v-model="slide"
                    :interval="4000"
                    no-animation
                    controls
                    indicators
                    background="#ababab"
                    img-width="1024"
                    img-height="480"
                    style="text-shadow: 1px 1px 2px #333;"
                    @sliding-start="onSlideStart"
                    @sliding-end="onSlideEnd"
                >
                    <b-carousel-slide
                        caption="First slide"
                        img-src="https://picsum.photos/1024/480/?image=10"
                    ></b-carousel-slide>
                    <b-carousel-slide
                        caption="Second Slide"
                        img-src="https://picsum.photos/1024/480/?image=12"
                    ></b-carousel-slide>
                    <b-carousel-slide
                        caption="Third Slide"
                        img-src="https://picsum.photos/1024/480/?image=22"
                    ></b-carousel-slide>
                    <b-carousel-slide
                        caption="Fourth Slide"
                        img-src="https://picsum.photos/1024/480/?image=23"
                    ></b-carousel-slide>
                </b-carousel>
            </b-col>

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
                    </div>
                </b-card>
            </b-col>
        </b-row>

        <b-row class="sub-contain">
            <b-col>
                <b-card class="sub-text-area" title="MEMO">
                    <p class="sub-text">
                        {{ bookDetail.memo }}
                    </p>
                </b-card>
            </b-col>
        </b-row>

        <div class="content-detail-button">

            <b-button @click="gotoList" class="m-2">목록</b-button>
            <!--                <b-button v-if="writerCheck" variant="primary" @click="updateBoard('update')">수정</b-button>-->

            <!--   dialog     -->
            <v-dialog
                v-model="dialog"
                persistent
                max-width="600px"
                height="1000px"
            >
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        color="primary"
                        dark
                        v-bind="attrs"
                        v-on="on"
                        @click="onEditModal"
                    >
                        대여
                    </v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Book Edit</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field
                                        label="idNo*"
                                        v-model="bookEditForm.idNo"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="name*"
                                        v-model="bookEditForm.name"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="author*"
                                        v-model="bookEditForm.author"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="price"
                                        v-model="bookEditForm.price"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="rental fee*"
                                        v-model="bookEditForm.rentalFee"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="rental period*"
                                        v-model="bookEditForm.rentalPeriod"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-textarea
                                        outlined
                                        label="memo*"
                                        v-model="bookEditForm.memo"
                                    ></v-textarea>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                            color="blue darken-1"
                            text
                            @click="dialog = false"
                        >
                            Close
                        </v-btn>
                        <v-btn
                            color="blue darken-1"
                            text
                            @click="onRental"
                        >
                            Rental
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <!--   dialog     -->


        </div>

    </div>
</template>

<script>
export default {
    name: "BookView",

    props: {
        query: {
            type: Object,
            default: () => {},
        },
        seq: String,
    },

    data() {
        return {
            slide: 0,
            sliding: null,
            bookDetail: {},
            bookFileList: [],

            dialog: false,
            imageDialog: false,
            bookEditForm: {},
            deleteFileList: [],
            addFileList: [],
            deleteFileSeqs: [],

            editBookDetail: {},
        }
    },

    mounted() {
        this.fetchBookDetail();
    },

    computed: {
    },

    methods: {
        onSlideStart() {
            this.sliding = true;
        },
        onSlideEnd() {
            this.sliding = false;
        },

        async fetchBookDetail() {
            const {bookDetail, bookFileList} = await this.$bookService.getBookDetail(this.seq);
            this.bookDetail = bookDetail;
            this.bookFileList = bookFileList;
        },

        onEditModal() {
            this.bookEditForm = JSON.parse( JSON.stringify(this.bookDetail) );
        },

        async onEditSave() {
            await this.$bookService.updateBook(this.bookEditForm);
            alert('도서 정보가 수정 되었습니다!');
            await this.fetchBookDetail();
            this.dialog = false;
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

        onDeleteFile(file, index) {
            this.deleteFileList.push(file);
            this.bookFileList.splice(index, 1);
        },

        async onFileEditSave() {
            this.deleteFileSeqs = this.deleteFileList.map((file) => file.seq);
            await this.$bookService.updateBookFile(this.bookDetail.seq, this.addFileList, this.deleteFileSeqs);
            alert('이미지 파일 수정이 완료 되었습니다.');
            await this.fetchBookDetail();
            this.imageDialog = false;
        },

        cancelEditFile() {
            this.bookFileList.push(...this.deleteFileList);
            this.deleteFileList = [];
            this.deleteFileSeqs = [];
            this.addFileList = [];
            this.imageDialog = false;
        }

    }
}
</script>

<style scoped>

.sub-contain {
    margin-top: 2%;
}
.main-text-area {
    margin-top: 10px;
}
.main-text {
    text-align: left;
}
.sub-text {
    text-align: left;
}
.label {
    display: inline-block;
}
</style>