<template>
    <div>
        <b-container fluid>
            <b-row>
                <b-col sm="3">
                    <label>고객 ID</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.id"
                    />
                </b-col>
                <b-col sm="3">
                    <label>고객 이름</label>
                    <b-form-input
                        type="text"
                        v-model="searchParams.name"
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

        <b-table striped hover id="board-table" :items="customerList" :fields="fields" @row-clicked="gotoView"></b-table>

        <v-btn
            class="customerForm mx-2"
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
    name: "CustomerList",

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
                    key: 'id',
                    label: 'ID'
                },
                {
                    key: 'name',
                    label: 'name',

                },
                {
                    key: 'birthday',
                    label: '생일'
                },
            ],
            customerList: [],

            searchParams: {
                currentPage: 1,
                prePage: 10,
                id: '',
                name: '',
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
            const {customerList, totalRows, searchParams} = await this.$customerService.getCustomerList(this.searchParams);
            this.customerList = customerList;
            this.totalRows = totalRows;
            this.searchParams = searchParams;
        },

        gotoPage(pageNum) {
            this.searchParams.currentPage = pageNum;
            this.fetchList();
        },

        gotoView(row) {
            this.$router.push({
                path: `/customer/view/${row.seq}`,
                query: this.searchParams,
            })
        },

        gotoRegister() {
            this.$router.push({
                path: `/customer/register`,
                query: this.searchParams,
            })
        },

    },

}
</script>

<style scoped>
.customerForm {
    display: inline-block;
    float: right;
}
</style>