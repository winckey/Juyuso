<template>
    <div class="white">
        <p>friend list</p>
        <div v-for="friend in friendsList"
        :key='friend'>
            <div>{{ friend }}</div>
        </div>
    </div>
</template>

<script>
export default {
    methods:{
        setToken : function(){
            const token = localStorage.getItem('jwt')
            const config = {
                Authorization: `Bearer ${token}`
            }
            return config
        },
    },
    created: function(){
        this.$store.dispatch('friendList',this.setToken())
    }
}
</script>

<style>

</style>
