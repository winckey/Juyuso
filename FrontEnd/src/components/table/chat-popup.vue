<template>
  <div>
    <v-btn
      class="chat-btn"
      fab
      @click="toggleChatbox"
    >
      <v-icon>mdi-chat-processing</v-icon>
    </v-btn>
    <v-navigation-drawer
      v-model="chatBox"
      :permanent="chatBox"
      app
      overflow
      right
      height="85%"
      width="300px"
      class="p-3"
    >
      <!-- 채팅 목록 -->
      <div class="chat-list">
        <div
          :key="idx" 
          v-for="(message, idx) in messages">
          <span :class="JSON.parse(message.from.data).clientData == userInfo.nickname ? 'my-chat': 'other-chat'">
            
            {{ JSON.parse(message.from.data).clientData }}
            <!-- {{ message.data }} -->
          </span>
        </div>
      </div>
      <!-- 채팅 보내기 -->
      <div class="chat-input">
        <v-row>
          <v-col cols="8">
            <v-textarea
              v-model="chatInput"
              no-resize
              outlined
              rows="3"
              row-height="15"
              @keyup.enter.exact.prevent="sendMessage"
            ></v-textarea>

          </v-col>
          <v-col cols="4">
            <v-btn 
              @click="sendMessage">
              전송
            </v-btn>

          </v-col>
        </v-row>
      </div>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ChatPopup',
  props: {
    userInfo: Object
  },
  data: function () {
    return {
      chatBox: false,
      chatInput: '',
    }
  },
  methods: {
    toggleChatbox () {
      this.chatBox = !this.chatBox
    },
    sendMessage() {
      this.session.signal({
        data: this.chatInput,
        to: [],
        type: 'my-chat'
      })
      .then( () => {
        console.log('success')
      })
      .catch( () => {
        console.log('fail')
      })
      this.chatInput=''
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'session',
      'messages'
    ]),
  },
}
</script>

<style scoped>
  .chat-btn {
    position: fixed;
    right: 10px;
    bottom: 10px;
  }

  .chat-input {
    position: fixed;
    bottom: 0
  }

  .chat-list {
    height: 85%;
  }

  .my-chat {
    color: green

  }

  .other-chat {
    padding: 2px;
    background-color: yellow
  }


</style>