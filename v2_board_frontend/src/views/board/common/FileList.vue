<template>
    <div class="mt-3 file-list-area">
        file list
        <b-list-group>
            <b-list-group-item
                v-for="(file, index) in files"
                :key="index"
                @click="fileDownload(file.seq)"
            >{{ file.originalName }}
                <b-button
                    v-if="updateMode"
                    variant="danger"
                    @click="fileRemoveListAdd( file.seq, index )"
                >x
                </b-button>
            </b-list-group-item>
        </b-list-group>
    </div>
</template>

<script>
export default {
    name: "FileList",
    /*
    * viewMode 처럼 리스트를 띄워줄지를 component에서 결정하는게 맞는건지,
    * component를 사용하는 부모에서 결정하는게 맞는지..
    * ㄴ> fileList component를 띄여줄지 여부는 부모에서 결정한다.
    *     fileList 내부에서는, 수정 할때만 x버튼이 나와야하기 때문에
    *     updateMode를 props로 받아서 x버튼 노출 여부를 결정한다.
    * */
    props: {
        updateMode: {
            type: Boolean,
            default: false
        },

        files: []
    },

    methods: {
        fileRemoveListAdd(fileSeq, index) {
            this.files.splice(index, 1);
            this.$emit('file-remove', fileSeq);
        },

        fileDownload(fileSeq) {
            if (!this.updateMode) {
                this.$emit('file-download', fileSeq);
            }
        }
    }
}
</script>

<style scoped>

.file-list-area {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}

</style>