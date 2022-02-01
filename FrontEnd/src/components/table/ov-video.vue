<template>
  <video autoplay/>
</template>

<script>

export default {
  name: 'OvVideo',

  props: {
    streamManager: Object,
  },
  
  mounted: function () {
    this.streamManager.addVideoElement(this.$el)
  },

}
</script>

<style scoped>
  video {
    -moz-border-radius:20px;
    -webkit-border-radius:20px;
    border-radius:20px;
    width: 100%
  }
</style>