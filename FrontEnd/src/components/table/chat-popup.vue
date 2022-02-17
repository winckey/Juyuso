<template>
  <div>
    <v-navigation-drawer
      v-model="chatBox"
      app
      overflow
      right
      temporary
      width="300px"
      class="p-3 chat-box"
    >
      <div class="whole-chat-list d-flex p-2" v-if="Chat_messages.length > 0">
        <div>
          <v-icon>mdi-bullhorn-outline</v-icon>
        </div>
        <div>
          <div>
            {{ JSON.parse(Chat_messages[Chat_messages.length-1].from.data).clientData }}
          </div>
          <div>
            {{ Chat_messages[Chat_messages.length-1].data }}
          </div>
        </div>
      </div>
      <!-- 채팅 목록 -->
      <div class="chat-list">
        <div
          :key="idx" 
          v-for="(message, idx) in messages">
          <div :class="JSON.parse(message.from.data).clientData == user.nickname ? 'my-chat': 'other-chat'">
            <div>
              <div v-if="JSON.parse(message.from.data).clientData != user.nickname" class="chat-name">
                {{ JSON.parse(message.from.data).clientData }}
              </div>
              <div :class="JSON.parse(message.from.data).clientData == user.nickname ? 'my-chat-bubble': 'other-chat-bubble'">
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
          color="#4DB6AC"
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
      if (this.chatInput.trim() != '') {
        this.session.signal({
          data: this.chatInput,
          to: [],
          type: 'my-chat'
        })
      }
      this.chatInput=''
    },
  },
  computed: {
    ...mapState('openviduStore', [
      'session',
      'messages',
      'Chat_messages'
    ]),
    ...mapState('accounts', [
      'user'
    ])
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
  .whole-chat-list {
    background: rgb(243, 162, 108);
    border-radius: 10px;
    text-align: center;

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
    border-radius: 15px;
    /* background: rgb(95, 159, 196); */
    background: #02AAB0;  /* fallback for old browsers */
    background: -webkit-linear-gradient(to right, #00CDAC, #02AAB0);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #00CDAC, #02AAB0); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
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
    /* background: rgb(196, 136, 219); */
    background: #DD5E89;  /* fallback for old browsers */
    background: -webkit-linear-gradient(to right, #F7BB97, #DD5E89);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #F7BB97, #DD5E89); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  }

</style>