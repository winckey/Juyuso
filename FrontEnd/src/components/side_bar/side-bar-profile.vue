<template>
  <div>
    <v-card
      class="p-2"
      style="background: #E0F2F1;">
      <div class="card-style pt-7 p-3" style="background: rgba(255, 255, 255, 0.93);">
        <div>
          <v-avatar
            class="grey"
            size="80">
            <v-img :src="imgUrl" alt="profile_img"></v-img>
          </v-avatar>
        </div>
        <div>
          <v-list-item-content>
            <v-list-item-title class="mb-1 item-title">
              <p>{{ user.nickname }}</p>
            </v-list-item-title>
          </v-list-item-content>  
        </div>

        <v-card-actions class="d-flex justify-content-evenly" >
          <div class="mypage">
            <v-btn @click="goMyPage"
              plain style="font-size: 1.1rem;" >
              <v-icon
                class="m-1"
                color="red"
                left
              >
                mdi-account-box
              </v-icon>
                마이페이지
            </v-btn>
          </div>
          <div class="logout">
            <v-btn
              plain
              @click="onLogout" style="font-size: 1.1rem;">
              <v-icon
                class="m-1"
                color="red"
                left
              >
                mdi-logout
              </v-icon>
              로그아웃
            </v-btn>
          </div>
        </v-card-actions>
      </div>
    </v-card>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  data() {
    return {
      user : ''
    }
  },
  methods:{
    ...mapActions('accounts', ['logout']),
    goMyPage: function () {
        this.$router.push({name: 'MyPage', params: {userId: this.user.id}})
        },
    goToLogin: function () {
        this.$router.push({ name: 'Login' })
        },
    onLogout: function(){
        this.logout()
          .then(() => this.$router.push({ name: 'Login' }))
    },
    setToken : function(){
        const token = localStorage.getItem('jwt')
        const config = {
            Authorization: `Bearer ${token}`
        }
        return config
    },
  },
  mounted() {    
    this.user = this.getUser;
  },
  computed: {
    ...mapGetters('accounts', ['getUser']),
    imgUrl: function () {
      if (this.user.img) {
        return `${process.env.VUE_APP_IMG_URL}/${this.user.imgUrl}`
      } else {
        return require('@/assets/basic_profile.png')
      }
    }
  }
}
</script>

<style scoped>
.card-style{
    text-align: center;
}
.logout-btn{
    float: right;
    text-align: right;
}

.item-title{
  font-size: 20px;
}
</style>
