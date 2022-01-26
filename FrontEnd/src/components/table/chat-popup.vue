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
      class="p-3 chat-box"
    >
      <!-- 채팅 목록 -->
      <div class="chat-list">
        <div
          :key="idx" 
          v-for="(message, idx) in messages">
          <div :class="JSON.parse(message.from.data).clientData == userInfo.nickname ? 'my-chat': 'other-chat'">
            <div>
              <div class="chat-name">
                <!-- {{ JSON.parse(message.from.data).clientData }} -->
                {{ '성아영' }}
              </div>
              <div class="other-chat-bubble">
                {{ message.data }}
              </div>
            </div>
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

  .chat-input {
    resize: none;
  }

  .chat-list {
    height: 85%;
  }

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