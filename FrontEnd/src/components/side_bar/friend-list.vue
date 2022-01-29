<template>
    <div class="white">
        <v-list>
            <friend-list-detail
                v-for="friend in friends"
                :key="friend.id"
                :userInfo="friend"
                :tab="tab"></friend-list-detail>
        </v-list>
    </div>
</template>

<script>
import FriendListDetail from '@/components/side_bar/friend-list-detail.vue'
export default {
    components:{
        FriendListDetail
    },
    props:{
        tab: Number,
        friends: Array,
    },
    name: 'FriendList',
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
        this.$store.dispatch('friendList')
    }
}
</script>

<style>

</style>
