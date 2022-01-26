<template>
    <div>

        <b-row class="main-contain">
            <b-col>

                <v-row
                    class="mb-6"
                    no-gutters
                >
                    <v-col
                        v-for="file in bookFileList"
                        :key="file.seq"
                    >
                        <v-img
                            max-height="350"
                            max-width="350"
                            :src="`http://localhost:8082/api/admin/book/image/${file.seq}`"
                        ></v-img>
                    </v-col>
                </v-row>

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

            <b-button @click="gotoList" class="m-2">목록</b-button>

            <!--   dialog     -->
            <BookRentalDialog
                :book-detail="bookDetail"
            ></BookRentalDialog>
            <!--   dialog     -->


        </div>
    </div>
</template>

<script>
import BookRentalDialog from "@/components/dialogs/BookRentalDialog";
export default {
    name: "BookView",
    components: {BookRentalDialog},
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

        async fetchBookDetail() {
            const {bookDetail, bookFileList} = await this.$bookService.getBookDetail(this.seq);
            this.bookDetail = bookDetail;
            this.bookFileList = bookFileList;

        },

        gotoList() {
            this.$router.push({
                path: `/customer/book/list`,
                query: this.query,
            })
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