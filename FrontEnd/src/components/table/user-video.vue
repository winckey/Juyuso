<template>
  <div 
    v-if="streamManager"
    class="video-box">
    <ov-video class="video-inner" :streamManager="streamManager"></ov-video>
    <div class="video-status" style="text-align: center">
      <v-icon dark class="mx-2">{{ publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
      <v-icon dark class="mx-2">{{ publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
    </div>
    <span class="user-name">{{ clientData }}</span>
  </div>
</template>

<script>
import OvVideo from './ov-video.vue'

export default {
  name: 'UserVideo',

  components: {
    OvVideo,
  },
  data: function () {
    return {
      videoManager: null
    }
  },
  props: {
    streamManager: Object,
  },
  computed: {
    clientData () {
      const { clientData } = this.getConnectionData()
      return clientData
    },
    publishAudio () {
      return this.streamManager.stream.audioActive
    },
    publishVideo () {
      return this.streamManager.stream.videoActive
    }
  },
  methods: {
    getConnectionData () {
      const { connection } = this.streamManager.stream
      return JSON.parse(connection.data) 
    }
  },
}
</script>

<style scoped>
  .video-box {
    display: inline-block;
    position: relative;
  }

  .video-inner {
    position: absolute;
    left: 50%;
    top: 5%;
    transform: translate(-50%);
    outline: none;
    border-color: #ffffff;
    box-shadow: 0 0 12px #ffffffb0;
  }

  .user-name {
    top: 5%;
    left: 50%;
    transform: translate(-50%);
    position: absolute;
    background: rgba(255, 255, 255, 0.8);
    padding: 3px;
    border-radius: 0 0 5px 5px;
    color: rgb(0, 0, 0)
  }

  .video-status {
    position: absolute;
    left: 50%;
    transform: translate(-50%);
    bottom: 5%
  }


</style>