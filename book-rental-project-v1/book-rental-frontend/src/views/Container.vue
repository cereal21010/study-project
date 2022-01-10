<template>
    <div>
        <h3>{{role}}</h3>
        <router-view/>
    </div>
</template>

<script>
export default {
    name: "Container",
    props: {
        role: {
            type: String,
            required: false,
        }
    },

}
</script>

<style scoped>

</style>