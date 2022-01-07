<template>
    <v-row justify="center">
        <v-col
            cols="12"
            sm="10"
            md="8"
            lg="6"
        >
            <v-card ref="form">
                <v-card-text>
                    <v-text-field
                        ref="idNo"
                        v-model="bookForm.idNo"
                        label="Book IdNo"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="name"
                        v-model="bookForm.name"
                        label="Book Name"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="author"
                        v-model="bookForm.author"
                        label="author"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="price"
                        v-model="bookForm.price"
                        label="price"
                        type="number"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="rentalFee"
                        v-model="bookForm.rentalFee"
                        label="rental fee"
                        required
                    ></v-text-field>
                    <v-text-field
                        ref="rentalPeriod"
                        v-model="bookForm.rentalPeriod"
                        label="rental period"
                        required
                    ></v-text-field>
                    <v-textarea
                        outlined
                        ref="memo"
                        v-model="bookForm.memo"
                        label="memo"
                    ></v-textarea>

                    <v-file-input
                        chips
                        multiple
                        label="book image files"
                        v-model="fileList"
                    ></v-file-input>

                </v-card-text>
                <v-divider class="mt-12"></v-divider>
                <v-card-actions>
                    <v-btn
                        text
                        @click="gotoList"
                    >
                        Cancel
                    </v-btn>
                    <v-spacer></v-spacer>

                    <v-btn
                        color="primary"
                        text
                        @click="onSubmit"
                    >
                        Submit
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
export default {
    name: "BookRegister",

    props: {
        query: {
            type: Object,
            default: () => {},
        }
    },

    data() {
        return {
            bookForm: {
                idNo: null,
                name: null,
                author: null,
                price: null,
                rentalFee: null,
                rentalPeriod: null,
                memo: null,
            },
            fileList: [],
        }
    },

    methods: {
        async onSubmit() {
            await this.$bookService.insertBook(this.bookForm, this.fileList);
            alert("도서 정보가 정상적으로 등록되었습니다.");
            this.gotoList();
        },

        gotoList() {
            this.$router.push({
                path: `/book/list`,
                query: this.query,
            })
        },
    }
}
</script>

<style scoped>

</style>