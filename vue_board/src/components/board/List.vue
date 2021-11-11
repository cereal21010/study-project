<template>
    <div>
        <b-table
            striped
            hover
            :items="items"
            :fields="fields"
        ></b-table>
        <b-button @click="writeContent">글쓰기</b-button>
    </div>


</template>

<script>
export default {
    name: 'app',
    data() {
        return {
            fields: [
                {
                    key: "seq",
                    label: "번호"
                },
                {
                    key: "title",
                    label: "제목"
                },
                {
                    key: "writer",
                    label: "작성자"
                },
                {
                    key: "createdDate",
                    label: "작성일"
                },
                {
                    key: "modifiedDate",
                    label: "수정일"
                },
            ],
            items: []

        }
    },
    mounted() {
        this.getList();
    },

    methods: {
        getList() {
            this.$axios.get("http://localhost/api/board/list")
                .then((res) => {
                    console.log(res);
                    this.items = res.data
                })
                .then((err) => {
                    console.log(err);
                })
        },
        writeContent() {
            this.$router.push({
                path: "/board/create"
            });
        }
    }
}
</script>

<style scoped>

</style>