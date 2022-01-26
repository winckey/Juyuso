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
            console.log('유저 정보 업데이트 성공')
            
        },
        LOGOUT: (state) => {
            localStorage.removeItem('jwt')
            state.isLogin = false
            state.user = null
            router.push({name:'Login'})         
            console.log('로그아웃 성공')  
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