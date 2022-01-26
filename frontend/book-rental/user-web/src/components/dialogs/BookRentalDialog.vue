<template>
    <v-dialog
        v-model="rentalDialog"
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
                    @click="rentalDialog = false"
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
</template>

<script>
export default {
    name: "BookRentalDialog",

    props: {
        bookDetail: {},
    },

    data() {
        return {
            rentalDialog: false,
            isLate: false,
            remainPoint: 0,
        }
    },

    methods: {

        //TODO: 대여완료가 아닌 대여신청으로 기능 완성하고, 신청 중 취소 되면 포인트 환불되고 그에 대한 정보도 포인트 리스트에서 확인 가능하도록 한다(충전, 사용, "취소")
        async onRental() {
            await this.$rentalService.insertRental(this.bookDetail);
            alert(`${this.bookDetail.name}에 대한 대여 신청이 완료 되었습니다.`);
            this.gotoList();
        },
    }
}
</script>

<style scoped>

</style>