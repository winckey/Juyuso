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
import { mapState } from 'vuex'
export default {
  name: 'NotificationList',
  data: function () {
    return {
      notificationList: [],
      menu: false,
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
      this.makeToast(payload)
    })
  },
  methods: {
    makeToast(payload) {
      console.log(payload)
      this.$bvToast.toast(payload.notification.body, {
        title: payload.notification.title,
        appendToast: true
      })
    }
  }
}
</script>

<style>

</style>
