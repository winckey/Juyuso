<template>
  <div>
    <div id="session" v-if="session">
      <div id="session-header">
        <h2 class="session-title">{{ roomInfo.meetingTitle }}</h2>
        <h5 class="session-title">{{ gameMode }}</h5>
      </div>
      <!-- <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager"/>
      </div> -->
      <TitanicGame
        v-if="gameMode == '타이타닉'"
        :subscribers="subscribers"
        :publisher="publisher"/>
      <div v-else class="container">
        <div class="row">
          <div id="video-container">
            <user-video class="col-md-4" :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
            <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
          </div>
        </div>
      </div>
    </div>
    <div class="people-list" v-show="peopleListShow">
      <p>{{ JSON.parse(publisher.stream.connection.data).clientData }}</p>
      <p 
        v-for="sub in subscribers" 
        :key="sub.stream.connection.connectionId">
        {{ JSON.parse(sub.stream.connection.data).clientData }}
      </p>
    </div>
    <ChatPopup
      ref="ChatPopup"
      :userInfo="userInfo"/>
    <!-- 메뉴바 -->
    <v-sheet 
      class="menu-bar p-3"
      rounded="xl"
      elevation="18">
      <div class="d-flex justify-content-between align-items-center" style="height: 100%">
        <div class="d-flex align-items-center" style="height: 100%">
          <v-btn
            class="m-1"
            fab
            small
            @click="audioToggle">
            <v-icon dense>{{ publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
          </v-btn>
          <v-btn
            class="m-1"
            fab
            small
            @click="videoToggle">
            <v-icon dense>{{ publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
          </v-btn>
          <v-btn
            class="m-1"
            fab
            small
            @click="bullhorn = !bullhorn">
            <v-icon dense>mdi-bullhorn-outline</v-icon>
          </v-btn>
          <transition name="stretch" mode="out-in">
            <v-text-field
              class="align-items-center"
              v-show="bullhorn" 
              type="text" 
              dense 
              hide-details
              solo
              rounded
              @keyup.enter="sendWholeMessage" 
              v-model="messageInput"/>
          </transition>
        </div>
        <div class="d-flex">
          <v-btn
            class="chat-btn m-1"
            fab
            small
            @click="toggleChatbox"
          >
            <v-icon>mdi-chat-processing</v-icon>
          </v-btn>
          
          <v-btn
            class="m-1"
            fab
            small
            @click="peopleListShow = !peopleListShow">
            <v-icon>mdi-account-group-outline</v-icon>
          </v-btn>
          <v-speed-dial
            v-model="fab"
            direction="top"
            fab
          >
            <template v-slot:activator>
              <v-btn
                v-model="fab"
                class="m-1"
                color="blue darken-2"
                dark
                small
                fab
              >
                <v-icon dense v-if="fab">
                  mdi-close
                </v-icon>
                <v-icon dense v-else>
                  mdi-controller-classic-outline
                </v-icon>
              </v-btn>
            </template>
            <v-btn
              v-for="(game, idx) in games"
              :key="idx"
              dark
              small
              color="green"
              @click="sendGameMode(game)"
            >
              {{ game.name }}
            </v-btn>
          </v-speed-dial>
        </div>
        <div>
          <v-btn
            color="error"
            fab
            small
            @click="leaveTable">
            <v-icon dark dense>mdi-application-export</v-icon>
          </v-btn>
        </div>
      </div>
    </v-sheet>
    <v-snackbar
      v-model="snackbar"
      :timeout="2500"
    >
      <template v-slot:action="{ attrs }">
        <v-btn
          color="red"
          text
          v-bind="attrs"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
      {{ snackbarText }}
    </v-snackbar>
  </div>
</template>

<script>
import axios from 'axios'
import UserVideo from '@/components/table/user-video.vue'
import ChatPopup from '@/components/table/chat-popup.vue'
import TitanicGame from '@/components/game/titanic-game.vue'

import { mapState, mapActions } from 'vuex'

const openviduStore = 'openviduStore'
axios.defaults.headers.post['Content-Type'] = 'application/json';


export default {
  name: 'Table',

  components: {
    UserVideo,
    ChatPopup,
    TitanicGame
  },
  props: {
    roomInfo: Object,
  },
  data: function () {
    return {
      fab: false,
      snackbar: false,
      bullhorn: false,
      snackbarText: '',
      messageInput: '',
      peopleListShow: false,
      menuBar: false,
      myUserName: '성아영',
      roomId: this.$route.params.roomId,
      publishAudio: true,
      publishVideo: true,
      userInfo: null,
      games: [
        {name: '이순신'},
        {name: '타이타닉'},
        {name: '밸런스'}
      ]
    }
  },

  mounted: function () {
    window.addEventListener('beforeunload', function (event) {
      event.returnValue = "안녕"
      this.leaveSession(this.roomId)
    });
    console.log(this.publisher)
    // console.log(this.publisher)
    console.log(this.subscribers)
    this.publishAudio = this.publisher.stream.audioActive
    this.publishVideo = this.publisher.stream.videoActive
    const token = localStorage.getItem('jwt')
    axios({
      method: 'GET',
      url: `${process.env.VUE_APP_API_URL}/users/me`,
      headers: { Authorization: `Bearer ${token}`}
    })
    .then( res => {
      this.userInfo = res.data.user
    })
  },


  beforeRouteLeave (to, from, next) {
    const answer = window.confirm('정말로 방을 떠나시겠습니까?')
    if (answer) {
        this.leaveSession(this.roomId)
        window.removeEventListener('beforeunload', function () {
        this.leaveSession(this.roomId)
      });
      next()
    }
    else {
      next(false)
    }
    // 경고창 띄우기
  },

  computed: {
    ...mapState(openviduStore, [
      'Chat_OV',
      'Chat_session',
      'Chat_messages',
      'OV',
      'session',
      'mainStreamManager',
      'publisher',
      'subscribers',
      'messages',
      'gameMode',
    ])
  },
  watch: {
    subscribers: function () {
      const username = JSON.parse(this.subscribers[this.subscribers.length - 1].stream.connection.data).clientData
      this.snackbarText = `${username}님이 입장하셨습니다.`
      this.snackbar = true
    }
  },
  methods: {
    ...mapActions(openviduStore, [
      // 'joinSession',
      'leaveSession',
      'switchGameMode',
    ]),
    toggleChatbox () {
      this.$refs.ChatPopup.chatBox = !this.$refs.ChatPopup.chatBox
    },
    leaveTable () {
      this.leaveSession(this.roomId)
			window.removeEventListener('beforeunload', function () {
        this.leaveSession(this.roomId)
      });
      this.$router.push({ name: 'TableList' })
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

    audioToggle () {
      this.publisher.publishAudio(!this.publishAudio)
      this.publishAudio = !this.publishAudio
    },

    videoToggle () {
      this.publisher.publishVideo(!this.publishVideo)
      this.publishVideo = !this.publishVideo
    },
    sendWholeMessage() {
      if (this.messageInput.trim() != '') {
        this.Chat_session.signal({
          data: this.messageInput,
          to: [],
          type: 'whole-chat'
        })
      }
      this.messageInput=''
    },
    sendGameMode(gameMode) {
      // 게임 초기 세팅
      this.switchGameMode(gameMode.name)
      if (gameMode.name === '타이타닉') {
        let members = []
        this.session.streamManagers.forEach(stream => {
          members.push(stream.stream.connection.connectionId)
        })
        let gameInfo = {
          type: 'Titanic',
          members: members.sort(() => Math.random() - 0.5),
          curMember: 0,
          curAmount: 0,
          maxAmount: Math.random() * 50 + members.length * 30,
        }
        this.session.signal({
          data: JSON.stringify(gameInfo),
          to: [],
          type: 'game-info'
        })
      }
    },
    
  },

}

</script>

<style scoped>
  .session-title {
    color: white;
    text-align: center;
  }

  .menu-bar {
    position: fixed;
    background: white;
    height: 100px;
    width: 100%;
    bottom: 0;
  }
  
  .people-list {
    position: fixed;
    background: white;
    height: 300px;
    width: 200px;
    border-radius: 2em;
    padding: 1em;
    right: 2em;
    top: 6em
  }


  .stretch-enter-active {
    animation: stretch-in .3s;
  }
  .stretch-leave-active {
    animation: stretch-in .3s reverse;
  }
  @keyframes stretch-in {
    0% {
      width: 0px;
    }
    100% {
      width: 167px;
    }
  }
</style>