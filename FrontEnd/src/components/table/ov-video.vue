<template>
  <video autoplay/>
</template>

<script>

export default {
  name: 'OvVideo',

  props: {
    streamManager: {
      type: Object,
      default() {
        return {

        }
      }
    }
  },
  
  mounted: function () {
    this.streamManager
    this.streamManager.addVideoElement(this.$el)
  },
  watch: {
    streamManager: function () {
      this.streamManager.addVideoElement(this.$el)
    }
  }
}
</script>

<style scoped>
  video {
    -moz-border-radius:20px;
    -webkit-border-radius:20px;
    border-radius:20px;
    height: 90%;
  }


</style>