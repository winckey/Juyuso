// import router from "@/router/index.js"
import jwt from '@/common/jwt'
import http from '@/common/http'

const accounts = {
  namespaced: true,
  state: {
    user: null,
    token: {
      accessToken: jwt.getToken(),
    },
    isLogin: !!jwt.getToken(),
  },
  getters: {
    getUser: (state) => state.user,
    getIsLogin: (state) => state.isLogin,
    getAccessToken: (state) => state.token.accessToken,
  },
  mutations: {
    LOGIN: (state, payload = {}) => {
      state.token.accessToken = payload.accessToken
      state.user = payload.user
      state.isLogin = true
      jwt.saveToken(payload.accessToken)
    },
    USER_UPDATE: (state, userInfo) => {
        state.isLogin = true
        state.user = userInfo
    },
    LOGOUT: (state) => {
      state.token.accessToken = ''
      state.isLogin = true
      jwt.destroyToken()
      // localStorage.removeItem('jwt')
        // state.isLogin = false
        // state.user = null
        // router.push({name:'Login'})         
    }
  },
  actions: {
    login: (context, { id, password, fcmToken }) => new Promise((resolve, reject) => {
      http
        .post('/users/login', {
          id, password, fcmToken
        })
        .then(response => {
          const { data } = response

          context.commit('LOGIN', {
            accessToken: data.accessToken,
            user: data.user,
          })

          resolve(response)
        })
        .catch(error => reject(error))
    }),
    loginKakao: (context, { id, fcmToken }) => new Promise((resolve, reject) => {
      http
        .post('/users/social/kakao/login', {
          id, fcmToken
        })
        .then(response => {
          const { data } = response

          context.commit('LOGIN', {
            accessToken: data.accessToken,
            user: data.user,
          })

          resolve(response)
        })
        .catch(error => reject(error))
    }),
    userUpdate: ({ commit }, userInfo) => {
        commit('USER_UPDATE', userInfo)
    },
    logout: ({commit}) => {
        commit('LOGOUT')
    }
  }
}

export default accounts