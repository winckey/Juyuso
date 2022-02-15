// import axios from 'axios'

const table = {
  namespaced: true,
  state: {
    themeNum:'1',
    themeColor: {
      1: {
        backgroundColor: '#815927',
        textColor: '#000000'
      },
      2: {
        backgroundColor: '#fabcac',
        textColor: '#000000'
      },
      3: {
        backgroundColor: '#1a84b2',
        textColor: '#000000'
      },
      4: {
        backgroundColor: '#b9a5ca',
        textColor: '#000000'
      },
    }
  },

  mutations: {
    SET_THEME : function(state, data){
        state.themeNum = data
    }
  },
  actions: {
    setTheme : function({ commit }, themeNum){
      commit('SET_THEME', themeNum)
    }
  },
  getters: {
    getBackgroudColor (state) {
      console.log(state)
      return state.themeColor[state.themeNum].backgroundColor
    },
    getTextColor (state) {
      return state.themeColor[state.themeNum].textColor
    }
  }
}

export default table