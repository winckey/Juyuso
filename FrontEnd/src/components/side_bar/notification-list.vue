<template>
  <v-menu
      v-if="isLogin"
      v-model="menu"
      :close-on-content-click="false"
      :nudge-width="200"
      offset-y
      bottom
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn 
          icon 
          dark
          v-bind="attrs"
          v-on="on">
          <v-icon>mdi-bell-outline</v-icon>
        </v-btn>
      </template>

      <v-card height="500" style="overflow-y: scroll">
        <v-list>
          <v-list-item class="d-flex justify-content-center">
            알람 리스트
          </v-list-item>
        </v-list>
        <v-list>
          <v-list-item v-for="(noti, idx) in notificationList" :key="idx">
            <Notification
            @closeMenu="menu=false"
            :noti="noti"/>
          </v-list-item>
        </v-list>
      </v-card>
    </v-menu>
</template>

<script>
import { getMessaging, onMessage } from 'firebase/messaging'
import { mapState, mapActions } from 'vuex'
import Notification from './notification.vue'

export default {
  name: 'NotificationList',
  components: {
    Notification
  },
  data: function () {
    return {
      menu: false,
      type: 0,
      test: {
        notification: {

          body: '테스트',
          title: '테스트',
        }
      }
    }
  },
  computed: {
    ...mapState('accounts', ['isLogin']),
    ...mapState('notification', ['notificationList']),
    ...mapState('friends', ['chatFriend'])
  },
  mounted: function () {
    const messaging = getMessaging();
    onMessage(messaging, (payload) => {
      this.makeToast(payload)
    })
  },
  methods: {
    ...mapActions('friends', [
      'friendList',
      'changeDialog',
      'changeTab',
      'setChatFriend'
    ]),
    ...mapActions('notification', [
      'addNotification'
    ]),
    makeToast(payload) {
      let noti = {}
      noti.message = payload.notification.body

      if (payload.notification.title.includes('친구')) {
        this.friendList()
        this.type = 0
        noti.type = 'friend'
        this.addNotification(noti)
        this.$toast.open({
          position: 'top-right',
          message: payload.notification.body,
          type: 'info',
          duration: 2500,
          onClick: this.openDraw
        })
      }
      else {
        let data = {
          nickname: payload.data.writerName,
          id: payload.data.writerId
        }
        if (this.chatFriend && this.chatFriend.id == data.id) {
          null
        }
        else {
          this.setChatFriend(data)
          this.type = 1
          noti.type = 'chat'
          noti.data = data
          this.addNotification(noti)
          this.$toast.open({
            position: 'top-right',
            message: payload.notification.body,
            type: 'info',
            duration: 2500,
            onClick: this.openDraw
          })
        }
      }
    },
    openDraw() {
      this.changeDialog(true)
      this.changeTab(this.type)
    }
  }
}
</script>

<style>

</style>
