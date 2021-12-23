<template>
    <div>
        <b-container fluid>
            <b-row>
                <b-col sm="3">
                    <label>제목</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.title"
                    />
                </b-col>
                <b-col sm="3">
                    <label>내용</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.content"
                    />
                </b-col>
                <b-col sm="3">
                    <label>저자</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.writer"
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

        <b-table striped hover id="board-table" :items="boardList" :fields="fields" @row-clicked="gotoView"></b-table>


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
<!--        <div class="mt-3" align="center">
            <b-pagination
                v-if="totalRows > 0"
                align="center"
                v-model="searchParams.currentPage"
                :total-rows="totalRows"
                :per-page="searchParams.prePage"
                aria-controls="board-table"
                @change="gotoPage"
            ></b-pagination>
        </div>-->
    </div>
</template>

<script>
export default {
    name: "boardList",

    props: {
        query: {
            type: Object,
            default: () => {}
        }
    },

    data() {
        return {
            fields: [
                {
                    key: 'seq',
                    label: 'No'
                },
                {
                    key: 'title',
                    label: '제목',

                },
                {
                    key: 'writer',
                    label: '작성자',
                },
                {
                    key: 'createdDate',
                    label: '작성일일'
                }
            ],

            boardList: [],

            searchParams: {
                currentPage: 1,
                prePage: 10,
                title: '',
                content: '',
                writer: '',
            },

            prePageOption: [
                {value: 10, text: 10},
                {value: 20, text: 20},
                {value: 30, text: 30},
            ],

            totalRows: 0,
        }
    },


    async mounted() {
        await this.fetchList();
        this.initSearchParams();
    },

    computed: {
    },

    methods: {
        async fetchList() {
            const {boardList, boardCount, search} = await this.$boardService.getBoardList(this.searchParams);

            this.boardList = boardList;
            this.totalRows = boardCount;
            this.searchParams = search;
            console.log(`response::`, search);
        },

        initSearchParams() {
            let _searchParams = this.searchParams;
            let _query = this.query;
            Object.keys(_query).forEach(function (key) {
                _searchParams[key] = _query[key];
            });
        },

        gotoPage(pageNum){
            this.searchParams.currentPage = pageNum;
            this.fetchList()
        },

        gotoView(row) {
            this.$router.push({
                path: `/board/view/${row.seq}`,
                query: this.searchParams,
            })
        },
    },
}
</script>

<style scoped>

</style>