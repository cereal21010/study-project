<template>
    <div>
        <v-dialog
            v-model="bookDialog"
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
                    <span class="text-h5">Book Edit</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12">
                                <v-text-field
                                    label="idNo*"
                                    v-model="bookEditForm.idNo"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field
                                    label="name*"
                                    v-model="bookEditForm.name"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field
                                    label="author*"
                                    v-model="bookEditForm.author"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field
                                    label="price"
                                    v-model="bookEditForm.price"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field
                                    label="rental fee*"
                                    v-model="bookEditForm.rentalFee"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field
                                    label="rental period*"
                                    v-model="bookEditForm.rentalPeriod"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12">
                                <v-textarea
                                    outlined
                                    label="memo*"
                                    v-model="bookEditForm.memo"
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
                        @click="bookDialog = false"
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
    </div>
</template>

<script>
export default {
    name: "BookEditDialog",

    props: {
        bookDetail: {},
    },

    data() {
        return {
            bookDialog: false,
            bookEditForm: {},
        }
    },

    methods: {

        onEditModal() {
            this.bookEditForm = JSON.parse( JSON.stringify(this.bookDetail) );
        },

        async onEditSave() {
            await this.$bookService.updateBook(this.bookEditForm);
            alert('도서 정보가 수정 되었습니다!');
            // await this.fetchBookDetail();
            this.$emit('fetchBookDetail');
            this.bookDialog = false;
        },
    },
}
</script>

<style scoped>

</style>