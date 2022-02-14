import axios from "axios"
import store from "../../store"

const http = axios.create({
  baseURL: `${process.env.VUE_APP_API_URL}`,
  headers: { "content-type": "application/json" },
})

http.interceptors.request.use(
  config => {
    const isLogin = store.getters['isLogin']
    if (isLogin) {
      config.headers.common['Authorization'] = `Bearer ${store.getters['getAccessToken']}`
    }
    return config
  },
  error => {
    // Do something with request error
    Promise.reject(error)
  }
)
// http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded"

export default http