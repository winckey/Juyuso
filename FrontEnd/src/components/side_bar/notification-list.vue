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

      <v-card>
        <v-list>
          <v-list-item>
            알람 리스트
          </v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            text
            @click="menu = false"
          >
            Cancel
          </v-btn>
          <v-btn
            color="primary"
            text
            @click="makeToast(test)"
          >
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-menu>
</template>

<script>
import { getMessaging, onMessage } from 'firebase/messaging'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'NotificationList',
  data: function () {
    return {
      notificationList: [],
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
    ...mapState('accounts', ['isLogin'])
  },
  mounted: function () {
    const messaging = getMessaging();
    onMessage(messaging, (payload) => {
      console.log(payload)
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
    makeToast(payload) {
      if (payload.notification.title == '친구 추가 요청') {
        this.friendList()
        this.type = 0
      }
      else {
        let data = {
          nickname: payload.data.writerName,
          id: payload.data.writerId
        }
        this.setChatFriend(data)
        this.type = 1
      }
      this.$toast.open({
        position: 'top-right',
        message: payload.notification.body,
        type: 'info',
        duration: 2500,
        onClick: this.openDraw
      })
    },
    hello() {
      console.log('hello')
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
