<template>
    <div>
        <b-container fluid>
            <b-row>
                <b-col sm="3">
                    <label>idNo</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.idNo"
                    />
                </b-col>
                <b-col sm="3">
                    <label>책 이름</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.name"
                    />
                </b-col>
                <b-col sm="3">
                    <label>저자</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.author"
                    />
                </b-col>

                <b-col sm="1" class="mt-3">
                    per-page
                    <b-form-select size="sm"
                                   v-model="searchParams.prePage"
                                   :options="prePageOption"
                    />
                </b-col>

                <b-col sm="1" class="mt-4">
                    <b-button
                        @click="fetchList">
                        검색
                    </b-button>
                </b-col>
            </b-row>

        </b-container>

        <b-table striped hover id="board-table" :items="bookList" :fields="fields" @row-clicked="gotoView"></b-table>

        <v-btn
            class="bookForm mx-2"
            fab
            dark
            large
            color="cyan"
            @click="gotoRegister"
        >
            <v-icon dark>
                mdi-pencil
            </v-icon>
        </v-btn>

        <div class="mt-3" align="center">
            <b-pagination
                v-if="totalRows > 0"
                align="center"
                v-model="searchParams.currentPage"
                :total-rows="totalRows"
                :per-page="searchParams.prePage"
                @change="gotoPage"
            ></b-pagination>
        </div>


    </div>
</template>

<script>
export default {
    name: "BookList",

    props:{
        query: {
            type: Object,
            default: () => {}
        }
    },

    data() {
        return {
            fields: [
                {
                    key: 'idNo',
                    label: 'idNo'
                },
                {
                    key: 'name',
                    label: '책 이름',

                },
                {
                    key: 'author',
                    label: '저자'
                },
                {
                    key: 'rentalFee',
                    label: '대여비용',
                },
                {
                    key: 'rentalPeriod',
                    label: '대여기간(일)'
                }
            ],

            bookList: [],

            searchParams: {
                currentPage: 1,
                prePage: 10,
                name: '',
                idNo: '',
            },

            prePageOption: [
                {value: 10, text: 10},
                {value: 20, text: 20},
                {value: 30, text: 30},
            ],

            totalRows: 0,
        }
    },

    mounted() {
        this.initSearchParams();
        this.fetchList();
    },

    computed: {
    },

    methods: {
        initSearchParams() {
            let _searchParams = this.searchParams;
            let _query = this.query;
            Object.keys(_query).forEach(function (key) {
                _searchParams[key] = _query[key];
            })
        },

        async fetchList() {
            const {bookList, totalRows, searchParams} = await this.$bookService.getBookList(this.searchParams);
            this.bookList = bookList;
            this.searchParams = searchParams;
            this.totalRows = totalRows;
        },

        gotoPage(pageNum) {
            this.searchParams.currentPage = pageNum;
            this.fetchList();
        },

        gotoView(row) {
            this.$router.push({
                path: `/customer/book/view/${row.seq}`,
                query: this.searchParams,
            })
        },

        gotoRegister() {
            this.$router.push({
                path: `/customer/book/register`,
                query: this.searchParams,
            })
        },

    }

}
</script>

<style scoped>
.bookForm {
    display: inline-block;
    float: right;
}
</style>