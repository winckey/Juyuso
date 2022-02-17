<template>
  <v-app style="background: #1B1B32" >
    <transition 
      name="fade">
    <NavBar v-if="$route.name != 'Main' && $route.name != 'Login' && $route.name != 'Signup'"/>
    </transition>
    <transition 
      name="fade"
      mode="out-in">
    <router-view :class="{'nav-margin' : $route.name != 'Main' && $route.name != 'Table' && $route.name != 'Login' && $route.name != 'Signup'}"></router-view>
    </transition>
  </v-app>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import NavBar from '@/components/nav-bar.vue'
import { getFcmToken } from '@/common/util'

const accounts = 'accounts'

export default {
  name: 'App',
  components: {
    NavBar
  },
  methods: {
    ...mapActions('openviduStore', ['initSession']),
    ...mapActions('accounts', ['setFcmToken'])
  },
  computed: {
    ...mapState(accounts, ['isLogin', 'user'])
  },
  mounted() {
    this.isLogin ? this.initSession(this.user) : null
    /* firebase fcm token 스토어에 저장 */
    getFcmToken().then(token => token && this.setFcmToken(token))
  }
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
    padding-top: 65px;
  }
</style>