<template>
    <div>
        <b-row class="main-contain">
            <b-col>
                <v-carousel>
                    <v-carousel-item
                        v-for="(item,i) in imageSrcList"
                        :key="i"
                        :src="item.src"
                        reverse-transition="fade-transition"
                        transition="fade-transition"
                        style="width:auto;height:auto;"
                    ></v-carousel-item>
                </v-carousel>
            </b-col>

            <b-col class="main-text-area">
                <b-card title="Book Info">
                    <div class="main-text">
                        <div>
                            <p class="customerInfo label">idNo : </p>
                            <p class="customerInfo">{{bookDetail.idNo}}</p>
                        </div>
                        <div>
                            <p class="customerInfo label">이름 : </p>
                            <p class="customerInfo">{{ bookDetail.name }}</p>
                        </div>
                        <div>
                            <p class="customerInfo label">저자 : </p>
                            <p class="customerInfo">{{ bookDetail.author }}</p>
                        </div>
                        <div>
                            <p class="customerInfo label">대여 비용 : </p>
                            <p class="customerInfo">{{ bookDetail.rentalFee }}</p>
                        </div>
                        <div>
                            <p class="customerInfo label">대여 기간 : </p>
                            <p class="customerInfo">{{ bookDetail.rentalPeriod }}</p>
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
                        <span class="text-h5">Book Rental</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <div class="main-text">
                                <div>
                                    <p class="customerInfo label">Name : </p>
                                    <p class="customerInfo">{{bookDetail.name}}</p>
                                </div>
                                <div>
                                    <p class="customerInfo label">ID : </p>
                                    <p class="customerInfo">{{ bookDetail.author }}</p>
                                </div>
                                <div>
                                    <p class="customerInfo label">Password : </p>
                                    <p class="customerInfo">{{ bookDetail.rentalFee }}</p>
                                </div>
                                <div>
                                    <p class="customerInfo label">Birthday : </p>
                                    <p class="customerInfo">{{ bookDetail.rentalPeriod }}</p>
                                </div>
                            </div>
                            <v-spacer></v-spacer>
                            <div>
                                사용가능한 포인트 : {{ remainPoint }}
                            </div>
                            <small
                                class="late-mention"
                                v-if="isLate"
                            >
                                *미납된 도서가 존재합니다.
                            </small>
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
                            :disabled="isLate"
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
            imageSrcList: [],
            remainPoint: 0,
            isLate: true,

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
            const {bookDetail, bookFileList, remainPoint, isLate} = await this.$bookService.getBookDetail(this.seq);
            this.bookDetail = bookDetail;
            this.bookFileList = bookFileList;
            this.isLate = isLate;
            for (const bookFile of bookFileList) {
                this.imageSrcList.push( { src: `http://localhost:8080/api/book/image/${bookFile.seq}` } );
            }
            this.remainPoint = remainPoint;
        },

        onEditModal() {
            this.bookEditForm = JSON.parse( JSON.stringify(this.bookDetail) );
        },

        gotoList() {
            this.$router.push({
                path: `/customer/book/list`,
                query: this.query,
            })
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
        },

        async onRental() {
            await this.$rentalService.insertRental(this.bookDetail);
            alert(`${this.bookDetail.name}에 대한 대여 신청이 완료 되었습니다.`);
            this.gotoList();
        },

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
.customerInfo {
    display: inline-block;
}
.label {
    font-weight: bold;
}
.late-mention {
    color: red;
}
</style>