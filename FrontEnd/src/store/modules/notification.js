// import axios from 'axios'

const notification = {
  namespaced: true,
  state: {
    notificationList: []
  },

  mutations: {
    ADD_NOTIFICATION: function ({ state }, noti) {
      state.notificationList.push(noti)
    },
    DELETE_NOTIFICATION: function ({state}, noti) {
      const index = state.notificationList.indexOf(noti, 0);
      if (index >= 0) {
        state.notificationList.splice(index, 1)
      }
    }
  },
  actions: {
    addNotification: function ({commit}, noti) {
      commit('ADD_NOTIFICATION', noti)
    },
    deleteNotification: function ({commit}, noti) {
      commit('DELETE_NOTIFICATION', noti)
    }
  }, 
  getters: {
  
  }
}

export default notification