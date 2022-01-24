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
      height="90%"
      width="300px"
      class="p-3"
    >
      <!-- 채팅 목록 -->
      <div class="chat-list">

      </div>
      <!-- 채팅 보내기 -->
      <div class="chat-input">
        <v-row>
          <v-col cols="9">
            <v-text-field
            outlined>

            </v-text-field>

          </v-col>
          <v-col cols="3">
            <v-btn>

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
  data: function () {
    return {
      chatBox: false,
    }
  },
  methods: {
    toggleChatbox () {
      this.chatBox = !this.chatBox
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'messages'
    ])
  }
}
</script>

<style scoped>
  .chat-btn {
    position: fixed;
    right: 10px;
    bottom: 10px;
  }

  .chat-list {
    height: 85%;
    background: blue;
  }


</style>