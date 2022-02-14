// import router from "@/router/index.js"
import jwt from '@/common/jwt'
import api from '@/common/api'

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
      state.isLogin = false
      state.user = null
      jwt.destroyToken()
    }
  },
  actions: {
    login: (context, { id, password, fcmToken }) => new Promise((resolve, reject) => {
      api
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
      api
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
    logout: ({ commit }) => new Promise(resolve => {
      setTimeout(function() {
        commit('LOGOUT')
        resolve({})
      }, 500)
    })
  }
}

export default accounts