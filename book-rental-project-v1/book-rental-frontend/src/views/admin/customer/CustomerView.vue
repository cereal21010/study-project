<template>
    <div>
        <v-card
            class="mx-auto my-12"
            max-width="700"
        >
            <b-card title="Customer Info">
                <div class="main-text">
                    <div>
                        <p class="customerInfo label">Name : </p>
                        <p class="customerInfo">{{customerDetail.name}}</p>
                    </div>
                    <div>
                        <p class="customerInfo label">ID : </p>
                        <p class="customerInfo">{{ customerDetail.id }}</p>
                    </div>
                    <div>
                        <p class="customerInfo label">Password : </p>
                        <p class="customerInfo">{{ customerDetail.password }}</p>
                    </div>
                    <div>
                        <p class="customerInfo label">Birthday : </p>
                        <p class="customerInfo">{{ customerDetail.birthday }}</p>
                    </div>
                    <div>
                        <p class="customerInfo label">Phone Number : </p>
                        <p class="customerInfo">{{ customerDetail.phoneNumber }}</p>
                    </div>
                    <div>
                        <p class="customerInfo label">Memo : </p>
                        <p class="customerInfo">{{ customerDetail.memo }}</p>
                    </div>
                </div>
            </b-card>
        </v-card>


        <div class="content-detail-button">

            <b-button @click="gotoList" class="m-2">목록</b-button>
            <!--                <b-button v-if="writerCheck" variant="primary" @click="updateBoard('update')">수정</b-button>-->

            <!--   dialog     -->
            <v-dialog
                v-model="editDialog"
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
                        수정
                    </v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Customer Edit</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field
                                        label="Name*"
                                        v-model="customerEditForm.name"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="ID*"
                                        v-model="customerEditForm.id"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="Password*"
                                        v-model="customerEditForm.password"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="Birthday*"
                                        v-model="customerEditForm.birthday"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-text-field
                                        label="Phone Number*"
                                        v-model="customerEditForm.phoneNumber"
                                        required
                                    ></v-text-field>
                                </v-col>
                                <v-col cols="12">
                                    <v-textarea
                                        outlined
                                        label="memo*"
                                        v-model="customerEditForm.memo"
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
                            @click="editDialog = false"
                        >
                            Close
                        </v-btn>
                        <v-btn
                            color="blue darken-1"
                            text
                            @click="onEditSave"
                        >
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <!--   dialog     -->

            <b-button
                variant="danger"
                v-b-modal.modal-prevent-closing
                @click="onDeleteBook"
            >삭제
            </b-button>

        </div>



    </div>
</template>

<script>
export default {
    name: "CustomerView",

    props: {
        query: {
            type: Object,
            default: () => {},
        },
        seq: String,
    },

    data() {
        return {
            customerDetail: {},
            customerEditForm: {},
            editDialog: false,
        }
    },

    mounted() {
        this.fetchCustomerDetail();
    },

    computed: {
    },

    methods: {
        async fetchCustomerDetail() {
            const customerDetail = await this.$customerService.getCustomerDetail(this.seq);
            this.customerDetail = customerDetail;
        },

        gotoList() {
            this.$router.push({
                path: `/admin/customer/list`,
                query: this.query,
            })
        },

        onEditModal() {
            this.customerEditForm = JSON.parse( JSON.stringify(this.customerDetail) );
        },

        async onEditSave() {
            await this.$customerService.updateCustomer(this.customerEditForm, this.seq);
            alert('고객 정보가 수정 되었습니다!');
            await this.fetchCustomerDetail();
            this.editDialog = false;
        },

        async onDeleteBook() {
            if ( confirm('고객 정보를 삭제 하시겠습니까?') ) {
                await this.$customerService.deleteCustomer(this.seq);
                alert('도서 정보가 삭제 되었습니다!');
                this.gotoList();
            }

        },

    },


}
</script>

<style scoped>
.customerInfo {
    display: inline-block;
}
.label {
    font-weight: bold;
}
</style>