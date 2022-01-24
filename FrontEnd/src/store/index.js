import Vue from 'vue'
import Vuex from 'vuex'
import openviduStore from './modules/openviduStore'
Vue.use(Vuex)
import accounts from '@/store/modules/accounts.js'

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accounts: accounts,
    openviduStore: openviduStore
  }
})
