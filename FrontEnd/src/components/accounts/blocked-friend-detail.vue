<template>
  <div v-if="friend">
      <v-list-item>
          <v-list-item-avatar>
              <v-img :src="require('@/assets/chat.png')"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
              <v-list-item-title>{{ friend.nickname }}</v-list-item-title>
          </v-list-item-content>
          <v-btn @click="blockCancel">차단해제</v-btn>
      </v-list-item>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'BlockedFriendDetail',
    props: {
        friend: Array
    },
    data: function () {
        return {

        }
    },
    methods: {
        blockCancel: function () {
            axios({
                method: 'DELETE',
                url: `${process.env.VUE_APP_API_URL}/friend/ban`,
                headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`},
                data: {
                id: this.friend.id,
                nickName: this.friend.nickname,
                }
            })
                .then(res => {
                    console.log(res)
                })
                .catch(err => {
                    console.log(err)
                })
        }
    }
}
</script>

<style>

</style>