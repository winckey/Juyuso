<template>
  <div class="wallet-container">
    <div class="wallet">
      <img src="@/assets/wallet_main.png" alt="wallet">

      <div class="profile-card">
        <img src="@/assets/profile.png" alt="profile-card">
        <div class="profile-card-info">
          <v-container>
            <v-row >
              <v-col cols="7">
                <div v-if="user">
                  <h1>주민등록증</h1>
                  <p>별명: {{ user.nickname }}</p>
                  <p>소개: {{ user.description }}</p>
                  <p>성별: {{ user.gender }}</p>
                  <p>나이: {{ user.age }}</p>
                </div>
              </v-col>
              <v-col v-if="user" cols="4">
                <div>
                  <img :src="imgUrl" alt="profile_img">
                </div>
              </v-col>
            </v-row>
          </v-container>
         
        </div>
        <div class="profile-edit-popup my-2">
          <ProfileEditPopup :user = "user" v-if="user"/>
        </div>
          
      </div>

      <div class="profile-mydata">
        <div class="profile-mydata-card">
          <img @click="goMyData" src="@/assets/Group 56.png" width="90%" alt="mydata">
        </div>

        <div class="wallet-1">
          <img src="@/assets/wallet_card_1.png" alt="wallet-card">
        </div>

        <div class="profile-calendar">
          <v-dialog>
            <template v-slot:activator="{ on, attrs }">
              <img src="@/assets/Group 57.png" alt="calendar"
              v-bind="attrs"
              v-on="on">
            </template>
            <v-card>
              <calender-popup></calender-popup>
            </v-card>
          </v-dialog>
          
        </div>
        <div class="wallet-2">
          <img src="@/assets/wallet_card_2.png" alt="wallet-card">
        </div>


        <div class="friend-block">
          <BlockedFriend/>
        </div>
        
      </div>
      
     
    </div> 
  </div>
</template>

<script>
import CalenderPopup from '../../components/accounts/calender-popup.vue'
import ProfileEditPopup from '@/components/accounts/profile-edit-popup.vue'
import BlockedFriend from '@/components/accounts/blocked-friend.vue'
import axios from 'axios'

export default {
  name: 'MyPage',
  components:{
    CalenderPopup,
    ProfileEditPopup,
    BlockedFriend,
  },
  data(){
    return{
      user: null      
    }
  },
  methods: {
    goMyData: function () {
      console.log('goMydata')
      // this.$router.push({name: 'MyData', params: {userId: this.user.id}, query: {user:this.user}})
      this.$router.push({name: 'MyData', params: {userId: this.user.id, user:this.user}})
    }
   
  },
  created: function (){
    axios({
      method: 'get',
      url: `${process.env.VUE_APP_API_URL}/user/info`,
      headers: {Authorization: `Bearer ${localStorage.getItem('jwt')}`}
    })
      .then(res => {
        console.log(res.data.user)
        const userInfo = res.data.user
        this.user = userInfo
      })
      .catch(err => {
        console.log(err)
      })
  },
  computed: {
    imgUrl: function () {
      if (this.user.img) {
        return this.user.imgUrl
      } else {
        console.log("@")
        return require('@/assets/chat.png')
      }

    }
  }
}
</script>

<style scoped>

.wallet-container {
  height: 90vh;
  display:flex;
  justify-content:center;
  align-items:center;
}

/* img {
 max-width: 100%;
 max-height: 100%;
} */

.wallet {
  position: relative;
}

.profile-card {
  width: 41%;
  position: absolute;
  top: 18%;
  left: 5%;
  transform: rotate(6deg);
}

.profile-card:hover {
  transform: scale(1.3);
}

.profile-card-info {
  width: 90%;
  position: absolute;
  top: 5%;
  left: 5%;
  font-size: 1.5rem;
}

.profile-mydata {
  width: 40%;
  position: absolute;
  top: 18%;
  right: 3%;
}


.profile-mydata-card {
  cursor: pointer;
}

.profile-mydata-card:hover {
  transform: translateY(-30%);
  cursor: pointer;
}

.profile-calendar {
  position:absolute;
  top: 50%;
  width: 95%;
  cursor: pointer;
}

.profile-calendar:hover {
  transform: translateY(-30%);
}

.wallet-1 {
  position: absolute;
  top: 35%;
  right: -2%;
  width: 110%;
  
}

.wallet-2 {
  position: absolute;
  top: 85%;
  width: 96%;
  
}

.friend-block {
  position: absolute;
  top: 140%;
  right: 10%;
}

.profile-edit-popup {
  width: 100px;
}

</style>
