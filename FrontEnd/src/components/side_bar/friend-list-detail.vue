<template>
  <div>
    <div v-if="userInfo"
      oncontextmenu="return false"
      @mousedown.right.stop="showMenu=!showMenu">
      <div v-if="tab == -1">
        <v-list-item
          oncontextmenu="return false">
            <v-list-item-avatar size=40>
              <img :src="imgUrl" style="object-fit: cover">
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ userInfo.nickname }}</v-list-item-title>
            </v-list-item-content>
            <v-btn class="mx-1" color="primary" @click="agreeFriend">수락</v-btn>
            <v-btn class="mx-1" color="error" @click="rejectFriend">거절</v-btn>
          </v-list-item>
      </div>
      <div v-else>
        <div>
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
                  <v-img :src="imgUrl" alt="profile_img"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title>
                    {{ userInfo.nickname }}
                    <img src="@/assets/my_friend.png" alt="friend"
                      class="friendImg">
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
            <v-list @contextmenu.prevent>
              <v-list-item @click="goFriendProfile"><button>프로필 보기</button></v-list-item>
              <v-list-item @click="chatFriend"><button>채팅하기</button></v-list-item>
              <v-list-item v-if="tab === 0" @click="showAlert('삭제')"><button>친구삭제</button></v-list-item>
              <v-list-item v-if="tab === 0" @click="showAlert('차단')"><button>차단</button></v-list-item>
            </v-list>
          </v-menu>
        </div>
      </div>
    </div>
  <div v-if="notFriendUserInfo"
      oncontextmenu="return false"
      @mousedown.right.stop="showMenu=!showMenu">
      <div v-if="notFriendUserInfo.id !== user.id">
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
              <v-img :src="notFriendImgUrl" alt="profile_img"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>
                {{ notFriendUserInfo.nickname }}
                <img src="@/assets/add_friend.png" alt="notfriend"
                      class="friendImg">
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
        <v-list @contextmenu.prevent>
          <!-- <v-list-item @click="goNotFriendProfile"><button>프로필 보기</button></v-list-item> -->
          <v-list-item v-if="tab == 2" @click="addFriend"><button>친구추가</button></v-list-item>
          <v-list-item v-if="tab === 0" @click="showAlert('삭제')"><button>친구삭제</button></v-list-item>
          <v-list-item v-if="tab === 0" @click="showAlert('차단')"><button>차단</button></v-list-item>
        </v-list>
      </v-menu>
      </div>
    </div>
    <v-dialog
      v-model="alert"
      persistent
      max-width="290"
    >
      <v-card>
        <v-card-title tag="span" class="d-flex justify-content-center">❗ 경고 ❗</v-card-title>
        <v-card-text class="text-center"><strong>{{ userNickName }}</strong>{{ alertMessage }}</v-card-text>
        <v-card-actions class="d-flex justify-content-center">
          <v-btn
            color="green darken-1"
            text
            @click="[alert = false]"
          >
            취소
          </v-btn>
          <v-btn
            color="red darken-1"
            text
            @click="[alert = false, doAction()]"
          >
            {{ type }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
    notFriendUserInfo: Object
  },
  data: function () {
    return {
      alert: false,
      x:0,
      y:0,
      showMenu: false,
      alertMessage: null,
      type: null,
    }
  },
  computed: {
    ...mapState('accounts', [
      'user',
    ]),
    ...mapState('friends',[
      'friendsList',
      'banList',
    ]),
    imgUrl: function () {
      
      if (this.userInfo) {
        return `${process.env.VUE_APP_IMG_URL}/${this.userInfo.imgUrl}`
      } else {
        return require('@/assets/chat.png')
      }
    },
    notFriendImgUrl:function(){
      if (this.notFriendUserInfo.imgUrl) {
        return `${process.env.VUE_APP_IMG_URL}/${this.notFriendUserInfo.imgUrl}`
      } else {
        return require('@/assets/chat.png')
      }
    },
    userNickName: function () {
      if (this.userInfo) {
        return this.userInfo.nickname
      }
      else {
        return this.notFriendUserInfo.nickname
      }
    }
  },
  methods: {
    ...mapActions('friends', [
        'setChatFriend',
        'blockFriend',
        'agreeFriends',
        'rejectFriends',
        'changeTab',
        'friendList']),
    addFriend: function () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'POST',
        url: `${process.env.VUE_APP_API_URL}/friends/request`,
        headers: { Authorization: `Bearer ${token}`},
        data: {
          id: this.notFriendUserInfo.id,
          nickName: this.notFriendUserInfo.nickname,
        }
      })
    },
    chatFriend: function () {
      this.setChatFriend(this.userInfo || this.notFriendUserInfo)
      this.changeTab(1)
    },
    showAlert: function (type) {
      if (type == '차단') {
        this.type = '차단'
        this.alertMessage = '님을 차단하겠습니까?'
      }
      else if (type == '삭제') {
        this.type = '삭제'
        this.alertMessage = '님을 삭제하겠습니까?'
      }
      this.alert = true
    },
    doAction: function () {
      if (this.type == '삭제') {
        this.deleteFriend()
      }
      else {
        this.banFriend()
      }
    },
    deleteFriend: function () {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'DELETE',
        url: `${process.env.VUE_APP_API_URL}/friends`,
        headers: { Authorization: `Bearer ${token}`},
        data: {
          id: this.userInfo.id,
          nickName: this.userInfo.nickname,
        }
      })
      .then( () => {
        const userId = {
          id : this.userInfo.id,
          nickName: this.userInfo.nickname
        }
        this.friendList(userId)
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
      this.$router.go()
    },
    // goNotFriendProfile: function(){
    //   this.$router.push({name: 'FriendPage', params: {userId: this.notFriendUserInfo.id}})
    //   this.$router.go()
    // },
    prevent: function (e) {
      e.preventDefault()
    }
  }

}
</script>

<style scoped>
.friendImg{
  width: 30px;
  float:right;
}
</style>