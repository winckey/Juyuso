<template>
  <v-app style="background: #1B1B32">
    <transition 
      name="fade">
    <NavBar v-if="$route.name != 'Main'"/>
    </transition>
    <transition 
      name="fade"
      mode="out-in">
    <router-view :class="{'nav-margin' : $route.name != 'Main'}"></router-view>
    </transition>
  </v-app>
</template>

<script>
import NavBar from '@/components/nav-bar.vue' 
export default {
  name: 'App',
  components: {
    NavBar
  },
  created: function () {
    console.log(this.$route.name)
  },
  data: () => ({
    //
  }),
};
</script>
<style scoped>
  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }

  .nav-margin {
    margin-top: 65px;
  }
</style>