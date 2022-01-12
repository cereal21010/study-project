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
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="rental in rentalList"
                    :key="rental.seq"
                >
                    <td>{{ rental.bookName }}</td>
                    <td>{{ rental.return }}</td>
                    <td>{{ rental.rentalDate }}</td>
                    <td>{{ rental.returnDate }}</td>
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
        query: {
            type: Object,
            default: () => {
            },
        }
    },

    data() {
        return {
            rentalList: {},
            searchParams: {},
        }
    },

    mounted() {
        this.fetchRentalList();
    },

    methods: {
        async fetchRentalList() {
            const {rentalList, searchParams} = await this.$rentalService.getRentalList(this.searchParams);
            this.rentalList = rentalList;
            this.searchParams = searchParams;
        },
    }
}
</script>

<style scoped>

</style>