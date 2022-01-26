

const accounts = {
    namespaced: true,
    state: {
        user: null,
        isLogin: false
    },
    mutations: {
        USER_UPDATE: (state, userInfo) => {
            state.user = userInfo
            state.isLogin = true
        }
    },
    actions: {
        userUpdate: ({ commit }, userInfo) => {
            commit('USER_UPDATE', userInfo)
        }
    }
}

export default accounts