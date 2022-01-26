<template>
  <div 
    v-if="streamManager"
    class="video-box">
    <div class="user-name">{{ clientData }}</div>
    <ov-video :streamManager="streamManager"></ov-video>
    <div style="text-align: center">
      <v-icon dark class="mx-2">{{ publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
      <v-icon dark class="mx-2">{{ publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
    </div>
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
  }

}
</script>

<style scoped>
  .video-box {
    display: inline-block;
  }

  .user-name {
    color: white
  }

</style>