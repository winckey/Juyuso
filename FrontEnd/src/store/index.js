import Vue from 'vue'
import Vuex from 'vuex'
import openviduStore from './modules/openviduStore'
Vue.use(Vuex)
import accounts from '@/store/modules/accounts.js'
import friends from '@/store/modules/friends.js'
import drinking from './modules/drinking'
import table from '@/store/modules/table.js'
import notification  from './modules/notification'

import createPersistedState from 'vuex-persistedstate'



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
    drinking: drinking,
    table: table,
    notification: notification,
  },
  plugins: [
    createPersistedState({
      paths: ['accounts', 'notification']
    })
  ]
})