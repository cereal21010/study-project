<template>
    <div>

        <!--   dialog     -->
        <v-dialog
            v-model="chargeDialog"
            persistent
            max-width="600px"
        >
            <template v-slot:activator="{ on, attrs }">
                <v-btn
                    tile
                    color="success"
                    v-bind="attrs"
                    v-on="on"
                >
                    Point Charge
                </v-btn>
            </template>
            <v-card>
                <v-card-title>
                    <span class="text-h5">Point Charge</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col
                                cols="12"
                                sm="6"
                            >
                                <v-text-field
                                    label="point input"
                                    type="number"
                                    v-model="chargeInput"
                                ></v-text-field>
                            </v-col>
                            <v-col
                                cols="12"
                                sm="6"
                            >
                                <v-btn
                                    depressed
                                    color="primary"
                                    @click="onChargePoint"
                                >
                                    💰💰💰
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="blue darken-1"
                        text
                        @click="chargeDialog = false"
                    >
                        Close
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <!--   dialog     -->


        <div class="remain-point">
            남은 포인트 : {{remainPoint}}
        </div>

        <v-tabs v-model="tab">
            <v-tab>Charge Point</v-tab>
            <v-tab>Use Point</v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
            <v-tab-item>
                <v-simple-table>
                    <template v-slot:default>
                        <thead>
                        <tr>
                            <th class="text-left">
                                Date
                            </th>
                            <th class="text-left">
                                state
                            </th>
                            <th class="text-left">
                                Point Amount
                            </th>
                            <th class="text-left">
                                Remain Point
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr
                            v-for="point in chargePointList"
                            :key="point.seq"
                        >
                            <td>{{ point.transactionDate }}</td>
                            <td>{{ point.state }}</td>
                            <td>{{ point.pointAmount }}</td>
                            <td>{{ point.remainPoint }}</td>
                        </tr>
                        </tbody>
                    </template>
                </v-simple-table>
            </v-tab-item>
            <v-tab-item>
                <v-simple-table>
                    <template v-slot:default>
                        <thead>
                        <tr>
                            <th class="text-left">
                                Date
                            </th>
                            <th class="text-left">
                                state
                            </th>
                            <th class="text-left">
                                Point Amount
                            </th>
                            <th class="text-left">
                                Remain Point
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr
                            v-for="point in usePointList"
                            :key="point.seq"
                        >
                            <td>{{ point.transactionDate }}</td>
                            <td>{{ point.state }}</td>
                            <td>{{ point.pointAmount }}</td>
                            <td>{{ point.remainPoint }}</td>
                        </tr>
                        </tbody>
                    </template>
                </v-simple-table>
            </v-tab-item>
        </v-tabs-items>


    </div>
</template>

<script>
export default {
    name: "PointList",

    props: {
        query: {
            type: Object,
            default: () => {
            },
        }
    },

    data() {
        return {
            pointList: [],
            remainPoint: 0,
            searchParams: {},
            chargeDialog : false,
            chargeInput: null,
            tab: null,
        }
    },

    mounted() {
        this.fetchPointList();
    },

    computed: {
        chargePointList: function() {
            return this.pointList.filter( (point) => point.state == 'charge' );
        },

        usePointList: function() {
            return this.pointList.filter( (point) => point.state == 'use' );
        },
    },

    methods: {
        async fetchPointList() {
            const pointList = await this.$pointService.getPointList();
            console.log(`pointLIst`, pointList);

            this.remainPoint = pointList.length <= 0 ? 0 : pointList[0].remainPoint;
            // const charges = pointList.filter( (point) => point.state == 'charge' );
            pointList.map(point => {
                point.transactionDate = point.transactionDate.slice(0,10);
            });
            this.pointList = pointList;
            /**
             * 이거 왜 undefined 뜨지?
            const consoleList = pointList.map(point => {
                point.transactionDate = point.transactionDate.slice(0,9);
            });
            console.log(consoleList);
            */
        },

        async onChargePoint() {
            await this.$pointService.chargePoint(this.chargeInput, 'tid01');
            alert(`${this.chargeInput} 포인트가 충전 되었습니다.`);
            this.chargeInput = null;
            this.chargeDialog = false;
            await this.fetchPointList();
        },
    }

}
</script>

<style scoped>

</style>