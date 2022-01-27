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
          <v-list-item><button>프로필 보기</button></v-list-item>
          <v-list-item v-if="tab == 2" @click="addFriend"><button>친구추가</button></v-list-item>
          <v-list-item v-if="tab === 0" @click="deleteFriend"><button>친구삭제</button></v-list-item>
          <v-list-item v-if="tab === 0"><button>차단</button></v-list-item>
        </v-list>
      </v-menu>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
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
  methods: {
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
    banFriend: function () {
      //친구차단
    },
    agreeFriend: function () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'POST',
        url: `${process.env.VUE_APP_API_URL}/friend/agree`,
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
    rejectFriend: function () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'DELETE',
        url: `${process.env.VUE_APP_API_URL}/friend/reject`,
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
    prevent: function (e) {
      e.preventDefault()
    }
  }

}
</script>

<style>

</style>
