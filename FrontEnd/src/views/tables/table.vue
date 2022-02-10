<template>
  <div class="background-box"
  :style="{'background-image':'url('+ require(`@/assets/theme/${roomInfo.theme}.jpg`)+')'}">
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
        :subscribers="session.streamManagers"
        :publisher="publisher"/>
      <DrawGame v-else-if="gameMode == '그림그리기'"
        :subscribers="subscribers"
        :publisher="publisher"/>
      <TypingGame v-else-if="gameMode == '타자연습'"
        :subscribers="subscribers"
        :publisher="publisher"/>
      <BalanceGame
        v-else-if="gameMode == '밸런스'"
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
          <!-- 사운드 관련 버튼 -->
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                small
                v-bind="attrs"
                v-on="on"
                fab
                @click="audioToggle">
                <v-icon dense>{{ publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
              </v-btn>
            </template>
            <span>{{ publishAudio ? '오디오 중지' : '오디오 시작' }}</span>
          </v-tooltip>

          
        <!-- 카메라 관련 버튼 -->
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                fab
                small
                v-bind="attrs"
                v-on="on"
                @click="videoToggle">
                <v-icon dense>{{ publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
              </v-btn>
            </template>
            <span>{{ publishVideo ? '비디오 중지' : '비디오 시작' }} </span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                fab
                small
                v-bind="attrs"
                v-on="on"
                @click="bullhorn = !bullhorn">
                <v-icon dense>mdi-bullhorn-outline</v-icon>
              </v-btn>
            </template>
            <span>확성기 기능</span>
          </v-tooltip>
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
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                fab
                small
                v-on="on"
                v-bind="attrs"
                @click="openFilterPopup"
              >
                <v-icon>mdi-movie-filter</v-icon>
              </v-btn>
            </template>
            <span>필터 기능</span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="chat-btn m-1"
                fab
                small
                v-on="on"
                v-bind="attrs"
                @click="toggleChatbox"
              >
                <v-icon>mdi-chat-processing</v-icon>
              </v-btn>
            </template>
            <span>채팅창 열기</span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                fab
                small
                v-on="on"
                v-bind="attrs"
                @click="peopleListShow = !peopleListShow">
                <v-icon>mdi-account-group-outline</v-icon>
              </v-btn>
            </template>
            <span>참가자 보기</span>
          </v-tooltip>
          
          
          <!-- 게임 관련 -->
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-model="game"
                class="m-1"
                @click="openGamePopup"
                color="blue darken-2"
                dark
                v-on="on"
                v-bind="attrs"
                small
                fab
              >
                <v-icon dense>
                  mdi-controller-classic-outline
                </v-icon>
              </v-btn>
            </template>
            <span>게임 기능</span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-show="gameMode"
                class="m-1"
                color="red darken-2"
                dark
                v-on="on"
                v-bind="attrs"
                small
                fab
                @click="switchGameMode(undefined)"
              >
                <v-icon dense>
                  mdi-close
                </v-icon>
              </v-btn>
            </template>
            <span>게임 취소</span>
          </v-tooltip>
        </div>
        <div>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                fab
                small
                v-on="on"
                v-bind="attrs">
                <v-icon dark dense>mdi-compare</v-icon>
              </v-btn>
            </template>
            <span>테마 변경</span>
          </v-tooltip>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="m-1"
                color="error"
                fab
                small
                v-on="on"
                v-bind="attrs"
                @click="leaveTable">
                <v-icon dark dense>mdi-application-export</v-icon>
              </v-btn>
            </template>
            <span>나가기</span>
          </v-tooltip>
        </div>
      </div>
    </v-sheet>
    <GamePopup
    ref="gamePopup"/>
    <FilterPopup
    ref="filterPopup"/>
  </div>
</template>

<script>
import axios from 'axios'
import UserVideo from '@/components/table/user-video.vue'
import ChatPopup from '@/components/table/chat-popup.vue'
import TitanicGame from '@/components/game/titanic-game.vue'
import BalanceGame from '@/components/game/balance-game.vue'

import DrawGame from '@/components/game/draw-game.vue'
import TypingGame from '@/components/game/typing-game.vue'
import GamePopup from '@/components/game/game-popup.vue'
import FilterPopup from '@/components/table/filter-popup.vue'
import { mapState, mapActions } from 'vuex'

const openviduStore = 'openviduStore'
axios.defaults.headers.post['Content-Type'] = 'application/json';


export default {
  name: 'Table',

  components: {
    UserVideo,
    ChatPopup,
    TitanicGame,
    BalanceGame,
    DrawGame,
    TypingGame,
    GamePopup,
    FilterPopup
  },
  props: {
    roomInfo: Object,
  },
  data: function () {
    return {
      audioFab: false,
      game: false,
      bullhorn: false,
      messageInput: '',
      peopleListShow: false,
      menuBar: false,
      myUserName: '성아영',
      roomId: this.$route.params.roomId,
      publishAudio: true,
      publishVideo: true,
      userInfo: null,
      titanicMembers: null,
      games: [
        {name: '타자연습'},
        {name: '타이타닉'},
        {name: '그림그리기'},
        {name: '밸런스'}
      ],
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
    ]),
    isGameMode() {
      console.log(this.gameMode)
      return this.gameMode ? true : false
    }
  },
  methods: {
    ...mapActions(openviduStore, [
      // 'joinSession',
      'leaveSession',
      'switchGameMode',
      'changeSound'
    ]),
    openFilterPopup () {
      this.$refs.filterPopup.dialog = true
    },
    openGamePopup () {
      this.$refs.gamePopup.dialog = true
    },
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

    audioToggle() {
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
    changeVoice(){
      if(this.voiceChange == false){
        const pitchs = ['0.75', '0.77', '1.5', '1.6']
        const pitch = pitchs[Math.floor(Math.random()*pitchs.length)]
        this.publisher.stream.applyFilter("GStreamerFilter", {"command": `pitch pitch=${pitch}`})
        this.voiceChange = true
      }
      else{
        this.publisher.stream.removeFilter()
        this.voiceChange = false
      }
    },
    sendGameMode(gameMode) {
      // 게임 초기 세팅
      if (gameMode.name === '타이타닉') {
        let members = this.session.streamManagers.map(stream => {
          return {
            connectionId: stream.stream.connection.connectionId,
            username: JSON.parse(stream.stream.connection.data).clientData
          }
        })
        let gameInfo = {
          type: 'Titanic',
          members: members.sort(() => Math.random() - 0.5),
          isEnd: false,
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
      else if (gameMode.name === '밸런스') {
        let members = []
        this.session.streamManagers.forEach(stream => {
          members.push({
            connectionId: stream.stream.connection.connectionId,
            username: JSON.parse(stream.stream.connection.data).clientData,
            isSelected: false,
          })
        })
        let gameInfo = {
          type: 'Balance',
          members: members.sort(() => Math.random() - 0.5),
          isStart: false,
          isEnd: false,
          curMember:0,
          totalTime: 60,
          cardData:[[], []],
        }
        console.log(gameInfo)
        this.session.signal({
          data: JSON.stringify(gameInfo),
          to: [],
          type: 'game-info'
        })
      }
      this.switchGameMode(gameMode.name)
    },
  },
  created:{

  }

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
    z-index: 3;
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
  .background-box{
    width: 100vw;
    height: 100vh;
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: cover
  }
</style>