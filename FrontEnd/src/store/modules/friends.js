import axios from 'axios'

const friends = {
  namespaced: true,
  state: {
    friendsList:[],
    searchList:[],
  },
  mutations: {
    SEARCH_USER_DATA:function(state, data){
      state.searchList = data
    },
    FRIEND_LIST:function(state, data){
      state.friendsList = data
    },
    ADD_FRIEND_LIST: function (state, userInfo) {
      state.friendList.friendList.push(userInfo)
    },
    DELETE_FRIEND_LIST: function (state, userInfo) {
      let index = state.friendList.indexOf(userInfo)
      state.friendList.splice(index, 1)
    }


  },
  actions: {
    // 친구 검색
    searchUserData : function({ commit }, query){
      const token = localStorage.getItem('jwt')
      axios({
        method:'get',
        url:`${process.env.VUE_APP_API_URL}/friend/${query}`,
        headers: { Authorization: `Bearer ${token}`},
        params: { keyword: query }
      })
      .then(res =>{
        commit('SEARCH_USER_DATA', res.data)
      })
      .catch(err =>{
        console.log(err)
      })
    },
    // 친구 리스트 조회
    friendList : function({ commit }){
      const token = localStorage.getItem('jwt')
      axios({
        method:'get',
        url:`${process.env.VUE_APP_API_URL}/friend`,
        headers: { Authorization: `Bearer ${token}`, },
      })
      .then(res => {
        commit('FRIEND_LIST', res.data)
        console.log(res)
      })
      .catch(err =>{
        console.log(err)
      })
    },
    // 친구 정보 조회
    friendInfo : function({ commit }, friendId){
      const token = localStorage.getItem('jwt')
      axios({
        method:'get',
        url:`${process.env.VUE_APP_API_URL}/friend/info/${friendId}`,
        headers: { Authorization: `Bearer ${token}`, }
      })
      .then(res => {
          commit('FRIEND_INFO',res.data)
      })
      .catch(err =>{
          console.log(err)
      })
    },
    addFriendList: function({ commit }, userInfo ) {
      commit('ADD_FRIEND_LIST', userInfo)
    }

  },
}

export default friends