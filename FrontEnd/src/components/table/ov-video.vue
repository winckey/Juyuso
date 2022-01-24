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

<style>

</style>