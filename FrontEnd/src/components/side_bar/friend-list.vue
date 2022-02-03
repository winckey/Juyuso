<template>
  <div class="white" v-if="friends">
    <v-list>
      <friend-list-detail
        :key="friend.id"
        v-for="friend in friends"
        :userInfo="friend"
        :tab="tab"/>
    </v-list>
  </div>
</template>

<script>
import FriendListDetail from '@/components/side_bar/friend-list-detail.vue'

export default {
  name: 'FriendList',
  components: {
    FriendListDetail
  },
  props: {
    tab: Number,
    friends: Array,
  },
  data: function () {
    return {
    }
  },
  methods:{
    setToken : function(){
      const token = localStorage.getItem('jwt')
      const config = {
          Authorization: `Bearer ${token}`
      }
      return config
    },
  },
  computed: {
  }
}
</script>

<style>

</style>