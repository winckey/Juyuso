<template>
  <div v-if="friend">
      <v-list-item >
          <v-list-item-avatar>
              <v-img :src="imgUrl"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
              <v-list-item-title>{{ friend.nickname }}</v-list-item-title>
          </v-list-item-content>
          <v-btn @click="blockCancel" v-if="isBlocked" rounded>차단해제</v-btn>
          <v-btn @click="block" v-else color="red" rounded>차단하기</v-btn>
      </v-list-item>
  </div>
</template>

<script>
import axios from 'axios'
import {mapActions} from 'vuex'

export default {
    name: 'BlockedFriendDetail',
    props: {
        friend: Object
    },
    data: function () {
        return {
            isBlocked: true
        }
    },
    computed: {
        imgUrl: function () {
            if (this.friend.imgUrl) {
                return `${process.env.VUE_APP_IMG_URL}/${this.friend.imgUrl}`
            } else {
                return require('@/assets/chat.png')
            }
        }
    },
    methods: {
        ...mapActions('friends', ['blockFriendList']),
        blockCancel: function () {
            axios({
                method: 'DELETE',
                url: `${process.env.VUE_APP_API_URL}/friends/ban`,
                headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`},
                data: {
                id: this.friend.id,
                }
            })
                .then(res => {
                    console.log(res)
                    this.isBlocked = false
                    this.blockFriendList()
                })
                // .catch(err => {
                //     console.log(err)
                // })
        },
        block: function () {
            axios({
                method: 'POST',
                url: `${process.env.VUE_APP_API_URL}/friends/ban`,
                headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`},
                data: {
                id: this.friend.id,
                }
            })
                .then(res => {
                    console.log(res)
                    this.isBlocked = true
                    this.blockFriendList()
                })
                // .catch(err => {
                //     console.log(err)
                // })
        }
    }
}
</script>

<style>

</style>