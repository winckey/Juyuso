<template>
    <div>
        <v-text-field
            class="shrink my-2"
            solo
            rounded
            dense
            @keyup.enter="friendsSearch">   
            </v-text-field>           
    </div>
</template>

<script>
export default {
    methods:{
        freiendsSearch: function(){
            this.$store.dispatch('searchUserData',)
        }
    }
}
</script>

<style>

</style>
