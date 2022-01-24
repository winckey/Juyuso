<template>
  <div v-if="streamManager">
    <ov-video :streamManager="streamManager"></ov-video>
    <v-icon dark>{{ publishAudio ? 'mdi-volume-high' : 'mdi-volume-off' }}</v-icon>
    <v-icon dark>{{ publishVideo ? 'mdi-camera-outline' : 'mdi-camera-off-outline' }}</v-icon>
    <div>{{ clientData }}</div>
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

<style>

</style>