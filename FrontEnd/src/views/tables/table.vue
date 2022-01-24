<template>
  <div>
    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ roomId }}</h1>
        <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveTable" value="Leave session">
      </div>
      <!-- <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager"/>
      </div> -->
      <div id="video-container" class="col-md-6">
        <user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
        <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/>
      </div>
    </div>
    <ChatPopup/>
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

  data: function () {
    return {
      menuBar: false,
      myUserName: '성아영',
      roomId: this.$route.params.roomId,
      publishAudio: true,
      publishVideo: true,
    }
  },

  mounted: function () {
    window.addEventListener('beforeunload', this.leaveSession(this.roomId))
    console.log(this.mainStreamManager)
    // console.log(this.publisher)
    console.log(this.subscribers)
    this.publishAudio = this.mainStreamManager.stream.audioActive
    this.publishVideo = this.mainStreamManager.stream.videoActive
  },


  // beforeRouteLeave (to, from, next) {
  //   // 경고창 띄우기
  // },

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
      this.leaveSession(this.roomId)
			window.removeEventListener('beforeunload', this.leaveSession(this.roomId));
      this.$router.push({ name: 'TableList' })
		},

		updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

    audioToggle () {
      this.mainStreamManager.publishAudio(!this.publishAudio)
      this.publishAudio = !this.publishAudio
    },

    videoToggle () {
      this.mainStreamManager.publishVideo(!this.publishVideo)
      this.publishVideo = !this.publishVideo
    },
  }
}

</script>

<style scoped>
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

</style>