<template>
  <v-dialog
      v-model="dialog"
      width="900"
    >
      <v-card class="p-5">
        <v-card-title>
          테이블 입장전
        </v-card-title>
        <div class="d-flex">
          <video v-if="videoSrc" :srcObject.prop="videoSrc" autoplay></video>
          <div class="empty-div d-flex justify-content-center align-center" v-else>
            비디오 소스가 없습니다.
          </div>
          <div class="d-flex flex-column justify-content-end p-3">
            <div>
              <v-select
                @change="changeDevice"
                append-icon="mdi-camera-outline"
                v-model="publishInfo.videoSource"
                :items="videoDevices"
                item-text="label"
                item-value="deviceId"
                solo 
                rounded
                placehoder="video"></v-select>
            </div>
            <div>
              <v-select
                @click="changeDevice"
                append-icon="mdi-volume-high"
                v-model="publishInfo.audioSource" 
                :items="audioDevices"
                item-text="label"
                item-value="deviceId"
                solo 
                rounded></v-select>
            </div>
            <div class="d-flex justify-content-around">
              <v-btn fab @click="publishInfo.publishAudio = !publishInfo.publishAudio">
                <v-icon>{{ publishInfo.publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
              </v-btn>
              <v-btn fab @click="publishInfo.publishVideo = !publishInfo.publishVideo">
                <v-icon>{{ publishInfo.publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
              </v-btn>
            </div>
          </div>
        </div>
        <v-divider></v-divider>
        <v-card-actions class="mt-5 d-flex justify-content-center">
          <v-btn
            color="#4DB6AC"
            dark
            rounded
            @click="enterRoom"
          >
            입장
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'TablePreview',
  props: {
    roomInfo: Object
  },
  data: function () {
    return {
      publishInfo: {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
        publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
        resolution: '640x480',  // The resolution of your video
        frameRate: 30,			// The frame rate of your video
        insertMode: 'REPLACE',	// How the video is inserted in the target element 'video-container'
        mirror: false       	// Whether to mirror your local video or not
      },
      dialog: false,
      videoDevices: [],
      audioDevices: [],
      videoSrc: undefined,
    }
  },
  methods: {
    ...mapActions('openviduStore', [
      'joinSession'
    ]),
    ...mapActions('table', [
      'setTheme'
    ]),
    getUserDevices: function() {
      this.Chat_OV.getDevices().then( devices => {
        devices.forEach( device => {
          if(device.kind === 'videoinput') {
            this.videoDevices.push(device)
          }
          else {
            this.audioDevices.push(device)
          }
        })
      })
    },
    changeDevice: function () {
      this.Chat_OV.getUserMedia({
        audioSource: false,
        videoSource: this.publishInfo.videoSource,
        resolution: '640x480',
        frameRate: 30
      })
      .then(mediaStream => {
        let videoTrack = mediaStream.getVideoTracks()[0];
        this.videoSrc = new MediaStream([videoTrack])
      })
      .catch(() => {

      })
      
    },
    enterRoom: function () {
      // if (!this.videoSrc) {
      //   this.$toast.open({
      //     position: 'top',
      //     message: '비디오 소스가 없으면 입장이 불가능합니다.',
      //     type: 'error',
      //     duration: 2500,
      //   });
      //   return
      // }
      let roomInfo = {
        sessionId: String(this.roomInfo.meetingId),
        isCreate: true,
        userName: this.user.nickname,
        publishInfo: this.publishInfo
      }
      console.log(roomInfo)
      this.joinSession(roomInfo)
      try {
        this.videoSrc.getTracks()[0].stop()
      }
      catch {
        roomInfo
      }
      this.setTheme(this.roomInfo.theme)
      this.$router.push({ name: 'Table', params: { roomId: this.roomInfo.meetingId, roomInfo: this.roomInfo }})
    }
  },
  computed: {
    ...mapState('accounts', [
      'user'
    ]),
    ...mapState('openviduStore', [
      'Chat_OV'
    ])
  },
  
  watch: {
    dialog: function () {
      if (this.dialog) {
        this.getUserDevices()
        this.changeDevice()
      }
      else {
        if(this.videoSrc) {
          this.videoSrc.getTracks()[0].stop()
        }
      }
    }
  }

}
</script>

<style scoped>
  video {
    width: 500px;
  }

  .empty-div {
    width: 500px;
    height: 375px;
    background: rgb(143, 143, 143);
    color: white;
    font-size: 1.5rem;
  }

</style>