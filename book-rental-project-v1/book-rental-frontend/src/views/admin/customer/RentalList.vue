<template>
    <div>
        <v-simple-table>
            <template v-slot:default>
                <thead>
                <tr>
                    <th class="text-left">
                        Book Name
                    </th>
                    <th class="text-left">
                        return
                    </th>
                    <th class="text-left">
                        Rental Date
                    </th>
                    <th class="text-left">
                        Return Date
                    </th>
                    <th class="text-left">
                        Late Fee
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="rental in rentalList"
                    :key="rental.seq"
                    :class="{'late': rental.late}"
                >
                    <td>{{ rental.bookName }}</td>
                    <td>{{ rental.return }}</td>
                    <td>{{ rental.rentalDate }}</td>
                    <td>{{ rental.returnDate }}</td>
                    <td>{{ rental.lateFee }}</td>
                    <td>
                        <v-btn
                            v-if="!rental.return"
                            @click="onBookReturn(rental.seq)"
                        >
                            반납
                        </v-btn>
                    </td>
                </tr>
                </tbody>
            </template>
        </v-simple-table>
    </div>
</template>

<script>
export default {
    name: "RentalList",

    props: {
        seq: String,
        query: {
            type: Object,
            default: () => {
            },
        }
    },

    data() {
        return {
            rentalList: {},
        }
    },

    mounted() {
        this.fetchRentalList();
    },

    methods: {
        async fetchRentalList() {
            const {rentalList} = await this.$adminService.getRentalList(this.seq);
            this.rentalList = rentalList;
        },

        async onBookReturn(rentalSeq) {
            if (this.rentalList.late) {
                if (confirm('연체료가 존재합니다. <br>도서 반납을 계속 진행 하시겠습니까?')) {
                    await this.$adminService.bookReturn(rentalSeq);
                } else {
                    return
                }
            } else {
                await this.$adminService.bookReturn(rentalSeq);
            }

            await this.fetchRentalList();
        }
    }
}
</script>

<style scoped>
.late {
    background-color: red;
}
</style>