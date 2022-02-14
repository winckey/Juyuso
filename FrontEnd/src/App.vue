<template>
  <v-app style="background: #1B1B32" >
    <transition 
      name="fade">
    <NavBar v-if="$route.name != 'Main'"/>
    </transition>
    <transition 
      name="fade"
      mode="out-in">
    <router-view :class="{'nav-margin' : $route.name != 'Main' && $route.name != 'Table'}"></router-view>
    </transition>
  </v-app>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import NavBar from '@/components/nav-bar.vue'
import axios from 'axios'
const accounts = 'accounts'

export default {
  name: 'App',
  components: {
    NavBar
  },
  mounted: function () {
    console.log(this.$route.name)
    if (!localStorage.getItem('jwt')) {
      this.$router.push({name: 'Login'})
    }
    else {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'get',
        url: `${process.env.VUE_APP_API_URL}/users/me`,
        headers: { Authorization: `Bearer ${token}`}
      })
      .then(res =>{
        this.userUpdate(res.data.user)
        this.initSession(res.data.user)
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
  data: () => ({
    //
  }),
  methods: {
    ...mapActions('accounts', [
      'userUpdate'
    ]),
    ...mapActions('openviduStore', [
      'initSession'
    ])
  },
  computed: {
    ...mapState(accounts, ['isLogin'])
  },

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