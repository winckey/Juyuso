import Vue from 'vue'
import Vuex from 'vuex'
import openviduStore from './modules/openviduStore'
Vue.use(Vuex)
import accounts from '@/store/modules/accounts.js'
import friends from '@/store/modules/friends.js'
import drinking from './modules/drinking'

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accounts: accounts,
    openviduStore: openviduStore,
    friends: friends,
    drinking: drinking
  }
})
