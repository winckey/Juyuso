<template>
  <v-sheet
  @click="onClickSheet"
  rounded
    elevation="1"
    width="220"
    class="p-2 d-flex justify-content-between align-center">
    <span>
      <v-icon small>
        {{noti.type=="friend" ? 'mdi-account' : 'mdi-chat-processing'}}
      </v-icon>
      {{ noti.message }}
    </span>
    <v-btn @click.stop="deleteNotification(noti)" icon small>
      <v-icon small>mdi-close</v-icon>
    </v-btn>
  </v-sheet>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'Notification',
  props: {
    noti: Object
  },
  methods: {
    ...mapActions('notification', ['deleteNotification']),
    ...mapActions('friends', [
      'friendList',
      'changeDialog',
      'changeTab',
      'setChatFriend'
    ]),
    onClickSheet() {
      if (this.noti.type == 'chat') {
        console.log(this.noti)
        this.setChatFriend(this.noti.data)
        this.openDraw(1)
      }
      else {
        this.friendList()
        this.openDraw(0)
      }
      this.$emit('closeMenu')
    },
    openDraw(tab) {
      this.changeDialog(true)
      this.changeTab(tab)
    }
  }
}
</script>

<style>

</style>