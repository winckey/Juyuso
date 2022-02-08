<template>
  <div>
    <div class="chat-list">
      <div
        :key="idx" 
        v-for="(message, idx) in messages">
        <div> {{ message }} </div>
        <div :class="message.writer == user.id ? 'my-chat': 'other-chat'">
          <!-- <div>
            <div v-if="JSON.parse(message.from.data).clientData != userInfo.nickname" class="chat-name">
              {{ JSON.parse(message.from.data).clientData }}
            </div>
            <div :class="JSON.parse(message.from.data).clientData == userInfo.nickname ? 'my-chat-bubble': 'other-chat-bubble'">
              {{ message.data }}
            </div>
          </div> -->
        </div>
      </div>
    </div>
    <!-- 채팅 보내기 -->
    <div class="chat-input-box d-flex justify-content-around">
      <textarea
      placeholder="채팅을 입력해주세요."
        class="chat-input"
        v-model="chatInput"
        @keyup.enter.exact.prevent="sendMessage"
      ></textarea>
      <v-btn
        dark
        color="blue-grey"
        @click="sendMessage">
        전송
      </v-btn>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import axios from 'axios'

export default {
  name: 'Chatting',
  data: function () {
    return {
      messages: [],
      sock: null,
      client: null,
      roomId: null,
      chatInput: null,
    }
  },
  computed: {
    ...mapState('accounts', ['user']),
    ...mapState('friends', ['chatFriend'])
  },
  mounted: function () {
    if (this.chatFriend) {
      this.initChat()
    }
  },
  methods: {
    initChat() {
      axios({
        method: 'GET',
        url: `${process.env.VUE_APP_API_URL}/chatRoom/${this.chatFriend.id}`,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`}
      }).then( res => {
        this.roomId = res.data.roomId
      })

      this.sock = new SockJS('https://i6e101.p.ssafy.io/ws')
      this.client = Stomp.over(this.sock, {
        protocols: Stomp.VERSIONS.supportedProtocols()
      });

      let $client = this.client

      this.client.connect({}, () => {
        console.log('socket connection successful')

        $client.subscribe(`/subscribe/chat/room/${this.roomId}`, chat => {
          let content = JSON.parse(chat.body);
          this.messages.push(content)
          console.log(chat)
        })
      })
    },
    sendMessage() {
      this.client.send('/publish/chat/message', JSON.stringify({'chatRoomId': this.roomId, 'message': this.chatInput, 'writer': this.user.id}), {})
      this.chatInput = null
    }
    
  }
}
</script>

<style scoped>
  .chat-btn {
    position: fixed;
    right: 10px;
    bottom: 10px;
  }

  .chat-input-box {
    box-shadow: 0 -1px 0 #000;
    width: 90%;
    padding-top: 10px;
    margin-bottom: 10px;
    background: white;
    position: fixed;
    bottom: 0;
    /* width: 100% */
  }
  .whole-chat-list {
    background: rgb(243, 162, 108);
    border-radius: 10px;
    text-align: center;
  }

  .chat-input {
    resize: none;
  }
/* 
  .chat-list {
    height: 50%;
  } */

  .other-chat {
    padding: 5px;
  }

  .chat-name {
    font-size: 0.85em
  }

  .other-chat-bubble {
    display: inline-block;
    margin-bottom: 5px;
    color: white;
    font-size: 0.9em;
    padding: 10px 10px 10px 12px;
    border-radius: 20px;
    background: rgb(95, 159, 196);
  }

  .my-chat {
    padding: 5px;
    text-align: right;
  }

  .my-chat-bubble {
    display: inline-block;
    margin-bottom: 5px;
    color: white;
    font-size: 0.9em;
    padding: 10px 10px 10px 12px;
    border-radius: 20px;
    background: rgb(196, 136, 219);
  }

</style>
