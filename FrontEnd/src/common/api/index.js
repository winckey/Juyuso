import axios from "axios"
import store from "../../store"

const api = axios.create({
  baseURL: `${process.env.VUE_APP_API_URL}`,
  headers: { "content-type": "application/json" },
})

api.interceptors.request.use(
  config => {
    const isLogin = store.getters['accounts/getIsLogin']
    if (isLogin) {
      config.headers.common['Authorization'] = `Bearer ${store.getters['accounts/getAccessToken']}`
    }
    return config
  },
  error => {
    // Do something with request error
    Promise.reject(error)
  }
)
// http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded"

export default api