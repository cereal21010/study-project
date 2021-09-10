<template>
    <div>
        <br>
        <!--  검색 창  -->
        <b-input-group class="mt-3">
            <b-input-group-prepend>
                <b-form-select
                    v-model="searchParams.searchType"
                    :options="selectOptions"
                    style="height: 100%"
                ></b-form-select>
            </b-input-group-prepend>
            <b-form-input
                v-model="searchParams.keyword"
            ></b-form-input>
            <b-input-group-append>
                <b-button
                    variant="outline-success"
                    @click="doSearch"
                >Search</b-button>
            </b-input-group-append>
        </b-input-group>

        <br>
        <b-table
            id="boardList-table"
            striped
            hover
            :items="boardList"
            :fields="fields"
            @row-clicked="goView"
            @head-clicked="goSort"
        ></b-table>

        <b-col lg="11" class="pb-2">
            <b-button
                size="lg"
                @click="moreGetBoardList"

            >더 보기</b-button>
        </b-col>

        <b-form-select
            v-model="searchParams.contentNum"
            :options="contentOptions"
            size="sm"
            class="mt-3"
            @change="contentNumChange">
        </b-form-select>

        <b-pagination
            v-if="pagination.totalCount > 0"
            v-model="searchParams.pageNum"
            :total-rows="pagination.totalCount"
            :per-page="searchParams.contentNum"
            @change="gotoPage"
            align="center"
        ></b-pagination>


        <b-button @click="goWrite">글쓰기</b-button>

        <password-modal
            v-if="showModal"
            :clickedBoard="clickedBoard"
            @close-modal="closeModal"
            @password-check="passwordCheck"
        ></password-modal>
    </div>
</template>

<script>
// import Vue from "vue";

import PasswordModal from "../../components/PasswordModal";
export default {
    name: "BoardList",
    components: {PasswordModal},
    props: {
        query: {
            type: Object,
            default: () => {

            }
        }
    },

    inject: ['boardService'],

    data() {
        return {

            testObject: {},
            fields: [
                {
                    key: "seq",
                    label: "번호"
                },
                {
                    key: "title",
                    label: "제목"
                },
                {
                    key: "writer",
                    label: "작성자"
                },
                {
                    key: "createdDate",
                    label: "작성일"
                },
                {
                    key: "modifiedDate",
                    label: "수정일"
                },
            ],

            boardList: [],

            pagination: {
                totalCount: 0
            },

            searchParams: {
                totalCount: 0,
                contentNum: 10,
                pageNum: 1,
                searchType: 'title',
                keyword: '',
                sort: 'createdDate',
                order: 'desc',
                startContentNum: 0,
                endContentNum: 1,   //fetch 때 쿼리에서 사용할 limit에 들어갈 변수
                listLength: 0,
            },

            selectOptions: [
                {value: 'title', text: '제목'},
                {value: 'writer', text: '작성자'},
                {value: 'contents', text: '내용'},
            ],

            contentOptions: [
                {value: 10, text: '10'},
                {value: 20, text: '20'},
                {value: 30, text: '30'},
            ],

            showModal: false,

            clickedBoard: {},
        }
    },

    async created() {
    },

    async mounted() {
        this.initSearchParams();
        await this.fetchList();
    },

    computed: {
    },

    methods: {

        async fetchList() {
            this.searchParams.startContentNum = 0;
            this.searchParams.endContentNum = this.searchParams.listLength > 0 ? this.searchParams.listLength : 10;
            const {boardList, search} = await this.boardService.moreGetBoardList(this.searchParams);
            this.boardList.push(...boardList);
            this.searchParams = search;
            this.searchParams.listLength = this.boardList.length;
        },
        /*async fetchList() {
            const {boardList, search} = await this.boardService.getBoardList(this.searchParams);
            this.boardList.push(...boardList);
            this.pagination.totalCount = search.totalCount;
            this.searchParams.pageNum = search.pageNum;
        },*/
        /* promise로 처리했을 때
        fetchList() {
            this.boardService.getBoardList(this.searchParams).then(function ({boardList, search}){
                this.boardList.push(...boardList);
                this.searchParams.totalCount = search.totalCount;
                this.searchParams.pageNum = search.pageNum;
                return {boardList, search}
            });
        },
        */
        initSearchParams() {
            let _searchParams = this.searchParams;
            let _query = this.query;
            Object.keys(_query).forEach(function (key) {
                _searchParams[key] = _query[key];
            })
        },


        goWrite() {
            this.$router.push({
                path: `/board/write`,
                query: this.searchParams
            })
        },

        closeModal() {
            this.showModal = false;
        },

        passwordCheck(pwCheck) {
            if(pwCheck){
                this.$router.push({
                    path: `/board/view/${this.clickedBoard.seq}`,
                    query: this.searchParams
                })
            }
        },

        goView(row) {
            this.clickedBoard = row;
            if( this.clickedBoard.password !== null ){
                this.showModal = true;
            }else {
                this.$router.push({
                    path: `/board/view/${row.seq}`,
                    query: this.searchParams
                })
            }
        },

        async gotoPage(page) {
            this.searchParams.pageNum = page;
            const {boardList, search} = await this.boardService.getBoardList(this.searchParams);
            this.boardList = boardList;
            this.pagination.totalCount = search.totalCount;
            this.searchParams.pageNum = search.pageNum;
        },

        async doSearch() {
            /*this.searchParams["pageNum"] = 1;
            const {boardList, search} = await this.boardService.getBoardList(this.searchParams);*/
            this.searchParams.startContentNum = 0;
            this.searchParams.endContentNum = this.searchParams.listLength > 0 ? this.searchParams.listLength : 10;
            const {boardList, search} = await this.boardService.moreGetBoardList(this.searchParams);
            this.boardList = boardList;
            this.searchParams.totalCount = search.totalCount;
        },

        async goSort(sort) {
            if (this.searchParams["sort"] === sort && this.searchParams["order"] === 'desc') {
                this.searchParams["order"] = 'asc'
            } else {
                this.searchParams["sort"] = sort;
                this.searchParams["order"] = 'desc'
            }

            // const {boardList, search} = await this.boardService.getBoardList(this.searchParams);
            this.searchParams.startContentNum = 0;
            this.searchParams.endContentNum = this.searchParams.listLength > 0 ? this.searchParams.listLength : 10;
            const {boardList, search} = await this.boardService.moreGetBoardList(this.searchParams);
            this.boardList = boardList;
            this.searchParams.totalCount = search.totalCount;
        },

        /*async contentNumChange() {
            this.searchParams["pageNum"] = 1;
            const {boardList, search} = await this.boardService.getBoardList(this.searchParams);
            this.boardList = boardList;
            this.searchParams.totalCount = search.totalCount;
        },*/

        async contentNumChange() {
            // const {boardList} = await this.boardService.moreGetBoardList(this.searchParams);
            // this.boardList = boardList;
        },

        async moreGetBoardList(){
            this.searchParams.startContentNum = this.boardList.length;
            this.searchParams.endContentNum = this.searchParams.contentNum;
            const {boardList, search} = await  this.boardService.moreGetBoardList(this.searchParams);
            this.boardList.push(...boardList);
            this.searchParams = search;
            this.searchParams.listLength = this.boardList.length;
        },

    },


}
</script>

<style scoped>
.sr-only {
    display: none;
}
</style>