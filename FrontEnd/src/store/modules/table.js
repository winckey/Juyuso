// import axios from 'axios'

const table = {
    namespaced: true,
    state: {
        themeNum:'beach',
    },
    mutations: {
        SET_THEME : function(state, data){
            state.themeNum = data
            console.log('state:',data)

        }
    },
    actions: {
        setTheme : function({state},themeimgName){
            
            //themeimgName.commit('SET_THEME')
            console.log(state)
            console.log('commit :',themeimgName)
        }
    }
}

export default table