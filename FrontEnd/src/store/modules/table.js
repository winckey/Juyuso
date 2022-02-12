// import axios from 'axios'

const table = {
  namespaced: true,
  state: {
    themeNum:'1',
    themeColor: {
      1: {
        backgroundColor: '#FFFFFF',
        textColor: '#000000'
      },
      2: {
        backgroundColor: '#FFFFFF',
        textColor: '#000000'
      },
      3: {
        backgroundColor: '#FFFFFF',
        textColor: '#000000'
      },
      4: {
        backgroundColor: '#FFFFFF',
        textColor: '#000000'
      },
    }
  },

  mutations: {
    SET_THEME : function(state, data){
        state.themeNum = data
        console.log('state:',data)
    }
  },
  actions: {
    setTheme : function({state}, themeimgName){
      console.log(state)
      console.log('commit :',themeimgName)
    }
  },
  getters: {
    getBackgroudColor (state) {
      return state.themeColor[state.themeNum].backgroundColor
    },
    getTextColor (state) {
      return state.themeColor[state.themeNum].textColor
    }
  }
}

export default table