<template>
  <div>
    <div id="session" v-if="session">
      <div id="session-header">
        <h2 class="session-title">{{ roomInfo.meetingTitle }}</h2>
      </div>
      <!-- <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager"/>
      </div> -->
      <div class="container">
        <div class="row">
          <div id="video-container" class="col-md-4">
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
      :userInfo="userInfo"/>
    <!-- 메뉴바 -->
    <v-sheet 
      class="menu-bar p-5"
      rounded="xl"
      elevation="18">
      <v-btn
        fab
        @click="audioToggle">
        <!-- <span>{{ publishAudio ? '오디오 중지' : '오디오 시작'}}</span> -->
        <v-icon>{{ publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
      </v-btn>
      <v-btn
        fab
        @click="videoToggle">
        <!-- <span>{{ publishVideo ? '비디오 중지' : '비디오 시작'}}</span> -->
        <v-icon>{{ publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
      </v-btn>

      <v-btn
        color="error"
        fab
        @click="leaveTable">
        <!-- <span>{{ publishVideo ? '비디오 중지' : '비디오 시작'}}</span> -->
        <v-icon dark>mdi-application-export</v-icon>
      </v-btn>
      <v-btn
        @click="peopleListShow = !peopleListShow">
        참가자 명단 보기
      </v-btn>
    </v-sheet>
      
  </div>
</template>

<script>
import axios from 'axios'
import UserVideo from '@/components/table/user-video.vue'
import ChatPopup from '@/components/table/chat-popup.vue'
import { mapState, mapActions } from 'vuex'

const openviduStore = 'openviduStore'
axios.defaults.headers.post['Content-Type'] = 'application/json';


export default {
  name: 'Table',

  components: {
    UserVideo,
    ChatPopup
  },
  props: {
    roomInfo: Object,
  },
  data: function () {
    return {
      peopleListShow: false,
      menuBar: false,
      myUserName: '성아영',
      roomId: this.$route.params.roomId,
      publishAudio: true,
      publishVideo: true,
      userInfo: null,
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
      url: `${process.env.VUE_APP_API_URL}/user/info`,
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
      'OV',
      'session',
      'mainStreamManager',
      'publisher',
      'subscribers',
      'messages',
    ])
  },

  methods: {
    ...mapActions(openviduStore, [
      // 'joinSession',
      'leaveSession',
    ]),

    leaveTable () {
			window.removeEventListener('beforeunload', function () {
        this.leaveSession(this.roomId)
      });
      this.leaveSession(this.roomId)
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
    height: 300px;
    width: 60%;
    bottom: -250px;
    left: 50%;
    transition-property: all;
    transition-duration: .3s;
    transform: translate(-50%);
  }

  .menu-bar:hover {
    position: fixed;
    background: white;
    height: 300px;
    width: 60%;
    bottom: -100px;
    left: 50%;
    transform: translate(-50%);
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

</style>