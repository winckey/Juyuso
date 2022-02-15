<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      max-width="500px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="#4DB6AC"
          dark
          v-bind="attrs"
          v-on="on"
          rounded
          large
        >
          차단 친구 관리
        </v-btn>
      </template>

      <v-alert
      :value="isSuccess"
        dense
        type="success"
      >
        저장되었습니다
      </v-alert>


      <v-card class="p-1" >
        <v-card-title class="justify-content-center">
          <span class="h2">차단 친구 목록</span>
        </v-card-title>
        <v-spacer></v-spacer>

        <v-card-text>
          <v-container class="rounded-lg">
            <v-list>
              <blocked-friend-detail 
              v-for="friend in blockedFriends" 
              :key="friend.id"
              :friend="friend">
              </blocked-friend-detail>
            </v-list>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="[dialog = false, isSuccess=false]"
          >
            닫기
          </v-btn>
        </v-card-actions>


        </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
// import {mapState} from 'vuex'
import axios from 'axios'
import BlockedFriendDetail from '@/components/accounts/blocked-friend-detail.vue'
export default {
  name: 'BlockedFriend',
  data: function () {
    return {
      dialog: false,
      isSuccess: false,
      blockedFriends: []
    }
  },
  components: {
    BlockedFriendDetail
  },
  computed: {
    // ...mapState('friends', ['banList'])
  },
  created: function (){
    axios({
      method: 'GET',
      url: `${process.env.VUE_APP_API_URL}/friends/ban`,
      headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
    })
      .then(res => {
        this.blockedFriends = res.data.bans
      })
      // .catch(err =>{
      //   console.log(err)
      // })
  }
}
</script>

<style>

</style>