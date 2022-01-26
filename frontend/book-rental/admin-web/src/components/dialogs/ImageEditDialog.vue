<template>
    <div>
        <v-dialog
            v-model="imageDialog"
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
                    @click="onImageEdit"
                >
                    사진 수정
                </v-btn>
            </template>
            <v-card>
                <v-card-title>
                    <span class="text-h5">Image Edit</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-file-input
                                small-chips
                                multiple
                                label="add image files"
                                v-model="addFileList"
                            ></v-file-input>

                            <v-divider></v-divider>

                            <v-simple-table>
                                <template v-slot:default>
                                    <thead>
                                    <tr>
                                        <th class="text-left">
                                            file name
                                        </th>
                                        <th class="text-left">
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr
                                        v-for="(file, index) in editFileList"
                                        :key="file.seq"
                                    >
                                        <td>{{ file.originalName }}</td>
                                        <td>
                                            <v-btn
                                                depressed
                                                color="error"
                                                @click="onDeleteFile(file, index)"
                                            >
                                                X
                                            </v-btn>
                                        </td>
                                    </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>

                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="blue darken-1"
                        text
                        @click="cancelEditFile"
                    >
                        Close
                    </v-btn>
                    <v-btn
                        color="blue darken-1"
                        text
                        @click="onFileEditSave"
                    >
                        File Save
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
export default {
    name: "ImageEditDialog",

    props: {
        fileList:[],
        bookSeq: null,
    },

    data() {
        return {
            imageDialog: false,
            editFileList: [],
            deleteFileSeqs: [],
            addFileList: [],
        }
    },

    methods: {

        onImageEdit() {
            //
            this.editFileList = JSON.parse( JSON.stringify(this.fileList) );
        }

        ,
        onDeleteFile(file, index) {
            this.deleteFileSeqs.push(file.seq);
            this.editFileList.splice(index, 1);
        },

        async onFileEditSave() {
            await this.$bookService.updateBookFile(this.bookSeq, this.addFileList, this.deleteFileSeqs);
            alert('이미지 파일 수정이 완료 되었습니다.');
            // await this.fetchBookDetail();
            this.$emit('fetchBookDetail');
            this.imageDialog = false;
        },

        cancelEditFile() {
            this.editFileList = [];
            this.addFileList = [];
            this.deleteFileSeqs = [];
            this.imageDialog = false;
        },

    },

    watch: {
        /*'fileList': function () {
            this.editFileList = JSON.parse( JSON.stringify(this.fileList) );
        },*/
    },
}
</script>

<style scoped>

</style>