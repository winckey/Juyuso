import axios from 'axios'

const friends = {
  namespaced: true,
  state: {
    friendsList:[],
    searchList:[],
    banList:[],
    tab: 0,
    chatFriend: null,
  },
  getters: {
    FriendsData(state){
      return state.friendsList
    }
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
    },
    BAN_FRIEND_LIST: function (state, banInfo) {
      state.banList = banInfo
    },
    // BAN_FIREND: function(state, data){
    //   let index = state.friendList.indexOf(data)
    //   state.friendList.splice(index, 1)
    // },
    AGREE_FRIEND: function(state, userId){
      state.friendsList = userId
    },
    CHANGE_TAB: function (state, tab) {
      state.tab = tab
    },
    SET_CHAT_FRIEND: function (state, friend) {
      state.chatFriend = friend
    }

  },
  actions: {
    // 친구 검색
    searchUserData : function({ commit }, query){
      const token = localStorage.getItem('jwt')
      axios({
        method:'get',
        url:`${process.env.VUE_APP_API_URL}/friends/${query}`,
        headers: { Authorization: `Bearer ${token}`},
        params: { keyword: query }
      })
      .then(res =>{
        commit('SEARCH_USER_DATA', res.data)
      })
      .catch(err =>{
        err
      })
    },
    // 친구 리스트 조회
    friendList : function({ commit }){
      const token = localStorage.getItem('jwt')
      axios({
        method:'get',
        url:`${process.env.VUE_APP_API_URL}/friends`,
        headers: { Authorization: `Bearer ${token}`, },
      })
      .then(res => {
        commit('FRIEND_LIST', res.data)
      })
      .catch(err =>{
        err
      })
    },
    // 친구 정보 조회
    friendInfo : function({ commit }, friendId){
      const token = localStorage.getItem('jwt')
      axios({
        method:'get',
        url:`${process.env.VUE_APP_API_URL}/friends/info/${friendId}`,
        headers: { Authorization: `Bearer ${token}`, }
      })
      .then(res => {
          commit('FRIEND_INFO',res.data)
      })
      .catch(err =>{
        err
      })
    },
    // 친구 추가
    addFriendList: function({ commit }, userInfo ) {
      commit('ADD_FRIEND_LIST', userInfo)
    },

    // 친구 차단 목록
    blockFriendList: function({commit}) {
      axios({
        method: 'GET',
        url: `${process.env.VUE_APP_API_URL}/friends/ban`,
        headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
      })
        .then(res => {
          commit('BAN_FRIEND_LIST', res.data.bans)
        })
        .catch(err =>{
          err
        })
    },
    // 친구 차단
    blockFriend : function({dispatch},friendId){
      const token = localStorage.getItem('jwt')
      axios({
        method:'post',
        url:`${process.env.VUE_APP_API_URL}/friends/ban`,
        headers: { Authorization: `Bearer ${token}`},
        data: friendId
      }).then(res => {
          res
          // commit('BAN_FRIEND', res.data)
          dispatch('blockFriendList')
          dispatch('friendList')

      }).catch(err =>{
        err
      })
    },
    // 친구 신청 수락
    agreeFriends: function ({commit}, userId) {
      const token = localStorage.getItem('jwt')
      axios({
        method: 'POST',
        url: `${process.env.VUE_APP_API_URL}/friends/accept`,
        headers: { Authorization: `Bearer ${token}`},
        data: userId
      })
      .then( (res) => {
        commit('AGREE_FRIEND',res);
        axios({
          method:'get',
          url:`${process.env.VUE_APP_API_URL}/friends`,
          headers: { Authorization: `Bearer ${token}`, },
        })
        .then(res => {
          commit('FRIEND_LIST', res.data)
        })
        .catch(err =>{
          err
        })
      })
    },
    // 친구 수락 거절
    rejectFriends: function({commit}, userId){
      const token = localStorage.getItem('jwt')
      axios({
        method: 'DELETE',
        url: `${process.env.VUE_APP_API_URL}/friends/reject`,
        headers: { Authorization: `Bearer ${token}`},
        data: userId
      })
      .then( res => {
        commit('FRIEND_LIST', res.data)
        axios({
          method:'get',
          url:`${process.env.VUE_APP_API_URL}/friends`,
          headers: { Authorization: `Bearer ${token}`, },
        })
        .then(res => {
          commit('FRIEND_LIST', res.data)
        })
      })
      .catch(err =>{
        err
      })
    },
    changeTab: function ({ commit }, tab) {
      commit('CHANGE_TAB', tab)
    },
    setChatFriend: function ({ commit }, friend) {
      commit('SET_CHAT_FRIEND', friend)
    }
  }
}

export default friends