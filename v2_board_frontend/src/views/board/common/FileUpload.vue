<template>
    <div>

        <div class="Attachments-area">
            파일 첨부
            <b-form-file
                placeholder="Choose a file or drop it here..."
                drop-placeholder="Drop file here..."
                @change="selectFileListAdd"
                multiple
            ></b-form-file>
        </div>

        <div class="select-file-area" v-if="selectFiles.length > 0">
            선택 파일들
            <b-list-group>
                <b-list-group-item v-for="(file, index) in selectFiles" :key="index">
                    {{ file.name }}
                    <b-button
                        v-if="true"
                        variant="danger"
                        @click="selectFileListRemove(index)"
                    >x
                    </b-button>
                </b-list-group-item>
            </b-list-group>
        </div>

    </div>
</template>

<script>
export default {
    name: "FileUpload",

    props: {
        selectFiles: {
            type: Array,
            default: () => []
        }
    },

    data() {
        return {}
    },

    mounted() {
    },

    methods: {
        selectFileListAdd(event) {
            this.$emit('select-files', event.target.files);
        },

        selectFileListRemove(index) {
            this.$emit('unselect-files', index)
        }

    }


}
</script>

<style scoped>

.select-file-area {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}

.Attachments-area {
    border: 1px solid black;
    margin-top: 1rem;
    padding: 2rem;
}

</style>