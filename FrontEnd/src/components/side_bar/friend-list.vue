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
      userInfo: {
        id: 1,
        userId: "ayoung0924",
        nickname: "성아영",
        description: null,
        phone: "01054732511",
        email: "ayoung0924@naver.com",
        img: false,
        imgUrl: "default img url",
        age: 24,
        birthDate: "1997-10-25",
        regDate: "2022-01-24T14:53:18.304",
        gender: "F",
        region: {
            id: 2,
            name: "부산"
          }
      }
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
}
</script>

<style>

</style>