<template>
  <div>
    <v-card
      class="p-3"
      v-if="user">
      <div class="card-style">
        <div>
          <v-avatar
              class="grey"
              size="80"
          ></v-avatar>
        </div>
        <div>
          <v-list-item-content>
            <v-list-item-title class="text-h5 mb-1">
              <p>{{ user.nickname }}</p>
            </v-list-item-title>
            <v-list-item-subtitle>자기소개</v-list-item-subtitle>
          </v-list-item-content>  
        </div>

        <v-card-actions class="d-flex justify-content-center">
          <v-btn @click="goMyPage"
            plain>
            마이페이지
          </v-btn>
          <v-btn class="logout-btn"
            plain
            @click="logout">
            로그아웃
          </v-btn>
        </v-card-actions>
      </div>
    </v-card>
    <v-card
      v-else
      class="card-style">
      <v-btn
        class="m-3 p-3"
        @click="goToLogin">로그인해주세요</v-btn>
    </v-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SideBarProfile',
  data(){
    return{
        user: null,
        isLogin: false,
    }
  },
  methods:{
      goMyPage: function () {
        this.$router.push({name: 'MyPage'})
        },
      goToLogin: function () {
        this.$router.push({ name: 'Login' })
        },
      logout: function(){
        console.log('isLogin')
        this.isLogin = false
        localStorage.removeItem('jwt')
          
      },
    },
    mounted: function(){
      const token = localStorage.getItem('jwt')
      axios({
        method: 'get',
        url: `${process.env.VUE_APP_API_URL}/user/info`,
        headers: { Authorization: `Bearer ${token}` }
      })
      .then(res =>{
        console.log(res.data.user)
        this.user = res.data.user
      })
      .catch(err => {
          console.log(err)
      })
      if(token){
        this.isLogin=true
      }
    },

}
</script>

<style>
.card-style{
    text-align: center;
}
.logout-btn{
    float: right;
    text-align: right;
}
</style>
