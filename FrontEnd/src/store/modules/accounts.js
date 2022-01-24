

const accounts = {
    namespaced: true,
    state: {
        user: null,
        isLogin: false
    },
    mutations: {
        LOGIN: (state, userInfo) => {
            state.user = userInfo
            state.isLogin = true
        }
    },
    actions: {
        logIn: ({ commit }, userInfo) => {
            commit('LOGIN', userInfo)
        }
    }
}

export default accounts