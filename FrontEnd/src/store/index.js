import Vue from 'vue'
import Vuex from 'vuex'
import openviduStore from './modules/openviduStore'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    openviduStore: openviduStore
  }
})
