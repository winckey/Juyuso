import router from "@/router/index.js"

const accounts = {
    namespaced: true,
    state: {
        user: null,
        isLogin: false
    },
    mutations: {
        USER_UPDATE: (state, userInfo) => {
            state.isLogin = true
            state.user = userInfo
            
        },
        LOGOUT: (state) => {
            localStorage.removeItem('jwt')
            state.isLogin = false
            state.user = null
            router.push({name:'Login'})         
        }
    },
    actions: {
        userUpdate: ({ commit }, userInfo) => {
            commit('USER_UPDATE', userInfo)
        },
        logout: ({commit}) => {
            commit('LOGOUT')
        }
    }
}

export default accounts