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


                    <v-divider class="mt-12"></v-divider>

                    <p>이미지 등록</p>

                    <div>
                        <!-- 1. Create the button that will be clicked to select a file -->
                        <v-btn
                            color="primary"
                            rounded
                            dark
                            :loading="isSelecting"
                            @click="handleFileImport"
                        >
                            Upload File
                        </v-btn>

                        <!-- Create a File Input that will be hidden but triggered with JavaScript -->
                        <input
                            id="uploader"
                            ref="uploader"
                            class="d-none"
                            type="file"
                            @change="onFileSelected"
                        />
                    </div>

                    <v-row>
                        <!--새로 추가할 이미지-->
                        <v-col
                            v-for="(file, index) in preViewFileList"
                            :key="index"
                            cols="auto"
                            class="lg12"
                        >
                            <v-card
                                class="pa-2"
                                width="318px"
                                height="368px"
                                outlined
                                tile
                            >
                                <div>
                                    <img
                                        class="popupImageItem"
                                        :src="file"
                                        alt="image"
                                        width="300px"
                                        height="350px"
                                    >
                                    <v-btn
                                        class="mx-2 file-delete-btn"
                                        fab
                                        dark
                                        small
                                        color="error"
                                        @click="onUploadFileCancel(index)"
                                    >
                                        <v-icon dark>
                                            mdi-minus
                                        </v-icon>
                                    </v-btn>
                                </div>
                            </v-card>
                        </v-col>
                    </v-row>
<!--                    <v-file-input
                        small-chips
                        multiple
                        accept="image/*"
                        label="book image files"
                        v-model="fileList"
                    ></v-file-input>-->


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
            addFileList: [],
            preViewFileList: [],

            isSelecting: false,
        }
    },

    methods: {
        async onSubmit() {
            await this.$bookService.insertBook(this.bookForm, this.addFileList);
            alert("도서 정보가 정상적으로 등록되었습니다.");
            this.gotoList();
        },

        gotoList() {
            this.$router.push({
                path: `/admin/book/list`,
                query: this.query,
            })
        },

        handleFileImport() {
            this.isSelecting = true;

            // After obtaining the focus when closing the FilePicker, return the button state to normal
            window.addEventListener('focus', () => {
                this.isSelecting = false
            }, {once: true});

            // Trigger click on the FileInput
            document.getElementById(`uploader`).click();
        },

        onFileSelected(event) {
            // const input = document.getElementById(`uploader`);
            const input = event.target;
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    // this.uploadImageFile = e.target.result;
                    this.preViewFileList = [
                        ...this.preViewFileList,
                        e.target.result
                    ]
                }
                reader.readAsDataURL(input.files[0]);
                this.addFileList = [
                    ...this.addFileList,
                    input.files[0]
                ]
            }
        },

        onUploadFileCancel(index) {
            this.addFileList.splice(index, 1);
            this.preViewFileList.splice(index, 1);
        },

    }
}
</script>

<style scoped>
.file-delete-btn {
    position: absolute;
    right: 5px;
    top: 10px;
}
</style>