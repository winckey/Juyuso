import axios from 'axios'

const friends = {
    namespace:true,
    state: {
        friendsList:[],
        searchList:[],
    },
    mutations: {
        SEARCH_USER_DATA:function(state, res){
            state.searchList = res
        },
        FRIENDLIST:function(state, res){
            state.friendsList = res
        },


    },
    actions: {
        // 친구 검색
        searchUserData : function({commit},friendsInfo){
            axios({
                method:'get',
                url:`${process.env.VUE_APP_API_URL}/friend/`,
                headers: { Authorization: `Bearer ${friendsInfo}`, }
            })
                .then(res =>{
                    commit('SEARCH_USER_DATA',res.data)
                })
                .catch(err =>{
                    console.log(err)
                })
        },
        // 친구 리스트 조회
        friendList : function({commit},token){
            axios({
                method:'get',
                url:`${process.env.VUE_APP_API_URL}/friend`,
                headers: { Authorization: `Bearer ${token}`, }
            })
                .then(res => {
                    commit('FRIENDLIST',res.data)
                })
                .catch(err =>{
                    console.log(err)
                })
        },
        // 친구 정보 조회
        friendInfo : function({commit},token){
            axios({
                method:'get',
                url:`${process.env.VUE_APP_API_URL}/friend/info/`,
                headers: { Authorization: `Bearer ${token}`, }
            })
                .then(res => {
                    commit('FRIENDINFO',res.data)
                })
                .catch(err =>{
                    console.log(err)
                })
        }

    },
}

export default friends