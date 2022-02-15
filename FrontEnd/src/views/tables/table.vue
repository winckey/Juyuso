<template>
  <div class="background-box"
  :style="{'background-image':'linear-gradient( rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5) ), url('+ require(`@/assets/theme/${roomInfo.theme}.jpg`)+')'}">
  <div class="m-3">
    <h2 class="session-title">{{ roomInfo.meetingTitle }}</h2>
  </div>
    <div class="video-container" :style="videoBackground">
      <!-- 메뉴바 -->
      <div
        class="menu-bar p-3" :style="menuBarBackground">
        <div class="d-flex flex-column justify-content-around align-center" style="height: 100%">
          <div class="d-flex flex-column align-center">
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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

            
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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
            <v-menu
              :close-on-content-click="false"
              transition="stretch"
              offset-x>
              <template v-slot:activator="{ on: menu, attrs }">
                <v-tooltip right :open-on-click="false" :open-on-focus="false">
                  <template v-slot:activator="{ on: tooltip }">
                    <v-btn
                    class="m-1"
                    fab
                    small
                    v-bind="attrs"
                    v-on="{ ...tooltip, ...menu }">
                    <v-icon dense>mdi-bullhorn-outline</v-icon>
                  </v-btn>
                  </template>
                  <span>확성기 기능</span>
                </v-tooltip>
              </template>
              <v-text-field
                class="align-items-center"
                type="text" 
                dense 
                hide-details
                solo
                rounded
                @keyup.enter="sendWholeMessage" 
                v-model="messageInput"/>
            </v-menu>            
          </div>
          <div class="d-flex flex-column align-center">
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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
            
            
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
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
          <div class="d-flex flex-column align-center">
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="m-1"
                  fab
                  small
                  v-on="on"
                  v-bind="attrs"
                  @click="openThemePopup">
                  <v-icon dark dense>mdi-compare</v-icon>
                </v-btn>
              </template>
              <span>테마 변경</span>
            </v-tooltip>
            <v-tooltip right :open-on-click="false" :open-on-focus="false">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="m-1"
                  color="error"
                  fab
                  small
                  v-on="on"
                  v-bind="attrs"
                  @click="goToTable">
                  <v-icon dark dense>mdi-application-export</v-icon>
                </v-btn>
              </template>
              <span>나가기</span>
            </v-tooltip>
          </div>
        </div>
        </div>
        <div id="session" style="width: 100%; height=80vh" v-if="session">
          <TitanicGame
            v-if="gameMode == '타이타닉'"
            :subscribers="session.streamManagers"
            :publisher="publisher"/>
          <DrawGame v-else-if="gameMode == '그림그리기'"
            :subscribers="wholeSubscribers"
            :publisher="publisher"/>
          <TypingGame v-else-if="gameMode == '타자연습'"
            :subscribers="subscribers"
            :publisher="publisher"/>
          <BalanceGame
            v-else-if="gameMode == '밸런스'"
            :subscribers="subscribers"
            :publisher="publisher"/>
          <div v-else class="video-grid">
              <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
              <user-video  v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
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
      <div class="d-flex justify-content-center align-center">
        <span style="color: white">{{ beer }}</span>
        <v-menu
        top
        :close-on-content-click="false"
        :nudge-width="55"
        offset-y>
          <template v-slot:activator="{ on: menu, attrs }">
            <v-tooltip top :open-on-click="false" :open-on-focus="false">
              <template v-slot:activator="{ on: tooltip }">
                <v-btn
                class="m-1"
                fab
                small
                v-bind="attrs"
                v-on="{ ...tooltip, ...menu }">
                <img src="@/assets/beer_icon.png" style="width: 30px">
              </v-btn>
              </template>
              <span>맥주 추가</span>
            </v-tooltip>
          </template>
          <v-card>
            <v-btn icon @click="beer > 0 ? beer -= 1: null">
              <v-icon>mdi-minus</v-icon>
            </v-btn>
            {{ beer }}
            <v-btn icon @click="beer += 1">
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </v-card>
        </v-menu>
        <v-menu
        top
        :close-on-content-click="false"
        :nudge-width="55"
        offset-y>
          <template v-slot:activator="{ on: menu, attrs }">
            <v-tooltip top :open-on-click="false" :open-on-focus="false">
              <template v-slot:activator="{ on: tooltip }">
                <v-btn
                class="m-1"
                fab
                small
                v-bind="attrs"
                v-on="{ ...tooltip, ...menu }">
                <img src="@/assets/soju_icon.png" style="width: 30px">
              </v-btn>
              </template>
              <span>소주 추가</span>
            </v-tooltip>
          </template>
          <v-card>
            <v-btn icon @click="soju > 0 ? soju -= 1: null">
              <v-icon>mdi-minus</v-icon>
            </v-btn>
            {{ soju }}
            <v-btn icon @click="soju += 1">
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </v-card>
        </v-menu>
        <span style="color: white">{{ soju }}</span>
      </div>
      <ChatPopup
        ref="ChatPopup"
        :userInfo="userInfo"/>
      <GamePopup
      ref="gamePopup"/>
      <FilterPopup
      ref="filterPopup"/>
      <ThemePopup
      ref="themePopup"
      :roomInfo="roomInfo"/>
      <LeaveRoomPopup
      ref="leaveRoomPopup"/>
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
import ThemePopup from '@/components/table/theme-popup.vue'
import LeaveRoomPopup from '@/components/table/leave-room-popup.vue'
import { mapState, mapActions, mapGetters } from 'vuex'

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
    FilterPopup,
    ThemePopup,
    LeaveRoomPopup
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
      soju: 0,
      beer: 0,
      games: [
        {name: '타자연습'},
        {name: '타이타닉'},
        {name: '그림그리기'},
        {name: '밸런스'}
      ],
    }
  },

  mounted: function () {
    window.addEventListener('beforeunload', (event) => {
      event.preventDefault()
      event.returnValue = ''
      this.leaveTable
      this.setSojuBeer
    });
    this.setTheme(this.roomInfo.theme)
    this.publishAudio = this.publisher.stream.audioActive
    this.publishVideo = this.publisher.stream.videoActive
    this.session.on('signal:theme', event => {
      let data = JSON.parse(event.data)
      this.roomInfo.theme = data.theme
      this.setTheme(data.theme)
      this.$toast.open({
        position: 'bottom',
        message: `${data.username}님이 테마를 변경하셨습니다`,
        type: 'info',
        duration: 2500,
      })
    })
  },
  beforeDestroy: function () {
    window.removeEventListener('beforeunload', (event) => {
      event.preventDefault()
      this.leaveTable
      this.setSojuBeer
    })
  },

  async beforeRouteLeave (to, from, next) {
    try {
      const answer = await this.$refs.leaveRoomPopup.open()
      if (answer) {
          this.leaveSession(this.roomId)
          this.setSojuBeer()
          window.removeEventListener('beforeunload', (event) => {
            event.preventDefault()
            this.leaveTable
            this.setSojuBeer
          })
        next()
      }
      else {
        return false
      }
    }
    catch {
      window.removeEventListener('beforeunload', (event) => {
        event.preventDefault()
        this.leaveTable
        this.setSojuBeer
      })
      next()
    }
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
      'wholeSubscribers'
    ]),
    ...mapGetters('table', [
      'getBackgroudColor',
      'getTextColor'
    ]),
    ...mapState('accounts', [
      'user'
    ]),
    ...mapState('friends', [
      'vuexDialog'
    ]),
    isGameMode() {
      return this.gameMode ? true : false
    },
    menuBarBackground() {
      return {
        background: this.getBackgroudColor
      }
    },
    videoBackground() {
      return {
        borderColor: this.getBackgroudColor,
        boxShadow: `0 0 8px ${this.getBackgroudColor}`,
      }
    }
  },
  methods: {
    ...mapActions(openviduStore, [
      // 'joinSession',
      'leaveSession',
      'switchGameMode',
      'changeSound'
    ]),
    ...mapActions('friends', [
      'changeDialog'
    ]),
    ...mapActions('table', [
      'setTheme'
    ]),
    openThemePopup () {
      this.$refs.themePopup.dialog = true
    },
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
      this.$router.push({ name: 'TableList' })
		},
    goToTable() {
      this.$router.push({ name: 'TableList'})
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
    setSojuBeer() {
      let today = new Date()
      let year = today.getFullYear();
      let month = ('0' + (today.getMonth() + 1)).slice(-2);
      let day = ('0' + today.getDate()).slice(-2);
      const item = {
        beer: this.beer,
        date: `${year}-${month}-${day}`, 
        soju: this.soju
      }
      
      axios({
        method: 'POST',
        url: `${process.env.VUE_APP_API_URL}/drinking/history`,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}` },
        data: item
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
    }
  },

}

</script>

<style scoped>
  .session-title {
    color: white;
    text-align: center;
  }

  .menu-bar {
    position: absolute;
    /* background: #1a84b2; */
    height: 84vh;
    width: 5vw;
    left: 0;
    z-index: 1;
    border-radius: 25px 0 0 25px;
  }

  .video-container {
    position: relative;
    width: 90vw;
    height: 84vh;
    padding-left: 5vw;
    background: rgba(255, 255, 255, 0.671);
    border-radius: 25px;
    display: flex;
    margin: auto;
    outline: none;
    /* border-color: #1a84b2;
    box-shadow: 0 0 8px #1a84b2; */
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
      width: 236px;
    }
  }
  .background-box{
    width: 100vw;
    height: 100%;
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: cover;
  } 

  .video-grid {
    display: grid;
    grid-template-columns: 28vw 28vw 28vw;
    grid-template-rows: 42vh 42vh;
  }
</style>