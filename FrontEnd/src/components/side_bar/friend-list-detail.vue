<template>
  <div v-if="userInfo"
      oncontextmenu="return false"
      @mousedown.right.stop="showMenu=!showMenu">
      <div v-if="tab == -1">
        <v-list-item
          oncontextmenu="return false">
            <v-list-item-avatar size=40>
              <img src="@/assets/logo.png" style="object-fit: cover">
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ userInfo.nickname }}</v-list-item-title>
            </v-list-item-content>
            <v-btn class="mx-1" color="primary" @click="agreeFriend">수락</v-btn>
            <v-btn class="mx-1" color="error" @click="rejectFriend">거절</v-btn>
          </v-list-item>
      </div>
      <div v-else>
        <div v-if="user.id != userInfo.id">
          <v-menu
          v-model="showMenu"
          offset-y
          :position-x="x"
          :position-y="y"
          @contextmenu.prevent
        >
          <template v-slot:activator="{ on, attrs }">
            <v-list-item
            v-bind="attrs"
            v-on="on"
            oncontextmenu="return false">
              <v-list-item-avatar size=40>
                <img src="@/assets/logo.png" style="object-fit: cover">
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title>{{ userInfo.nickname }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
          <v-list @contextmenu.prevent>
            <v-list-item><button>방에 초대</button></v-list-item>
            <v-list-item @click="goFriendProfile"><button>프로필 보기</button></v-list-item>
            <span v-if="userInfo">
              <v-list-item v-if="tab == 2" @click="addFriend">
                  <button>친구추가</button></v-list-item>
            </span>
            <v-list-item v-if="tab === 0" @click="deleteFriend"><button>친구삭제</button></v-list-item>
            <v-list-item v-if="tab === 0" @click="banFriend"><button>차단</button></v-list-item>
          </v-list>
        </v-menu>
        </div>

      </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'FriendListDetail',
  props: {
    userInfo: Object,
    tab: Number,
  },
  data: function () {
    return {
      x:0,
      y:0,
      showMenu: false
    }
  },
  computed: {
    ...mapState('accounts', [
      'user',
    ]),
    ...mapState('friends',[
      'friendsList',
      'banList'
    ])
  },
  methods: {
    ...mapActions('friends', ['blockFriend']),
    ...mapActions('friends',['agreeFriends']),
    ...mapActions('friends',['rejectFriends']),
    addFriend: function () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'POST',
        url: `${process.env.VUE_APP_API_URL}/friend/request`,
        headers: { Authorization: `Bearer ${token}`},
        data: {
          id: this.userInfo.id,
          nickName: this.userInfo.nickname,
        }
      })
      .then( res => {
        console.log(res)
      })
    },
    deleteFriend: function () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'DELETE',
        url: `${process.env.VUE_APP_API_URL}/friend`,
        headers: { Authorization: `Bearer ${token}`},
        data: {
          id: this.userInfo.id,
          nickName: this.userInfo.nickname,
        }
      })
      .then( res => {
        console.log(res)
      })
    },
    // 차단
    banFriend: function () {
      const userId = {
        id : this.userInfo.id,
        nickName: this.userInfo.nickname
      }
        //this.$store.friends.dispatch('banFriend',userId)
        this.blockFriend(userId)
    },
    // 수락
    agreeFriend: function(){
      const userId = {
        id : this.userInfo.id,
        nickName: this.userInfo.nickname
      }
      this.agreeFriends(userId)
    },
    // 거절
    rejectFriend: function () {
      const userId={
        id : this.userInfo.id,
        nickName: this.userInfo.nickname
      }
      this.rejectFriends(userId)
    },
    goFriendProfile: function(){
      this.$router.push({name: 'FriendPage', params: {userId: this.userInfo.id}})
    },
    prevent: function (e) {
      e.preventDefault()
    }
  }

}
</script>

<style>

</style>