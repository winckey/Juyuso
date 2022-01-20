<template>
  <div class="wallet-container">
    <div class="wallet">
      <img src="@/assets/wallet_main.png" alt="wallet">

      <div class="profile-card">
        <img src="@/assets/profile.png" alt="profile-card">
        <div class="profile-card-info">
          <p>박씨네</p>
          <p>민쯩</p>
        </div>
        <v-btn @click="goProfileEdit" color="green" >프로필 수정</v-btn>
      </div>

      <div class="profile-mydata">
        <div class="profile-mydata-card">
          <img @click="goMyData" src="@/assets/Group 56.png" width="95%" alt="mydata">
        </div>

        <div class="wallet-1">
          <img src="@/assets/wallet_card_1.png" alt="wallet-card">
        </div>
        <div class="profile-calendar">
          <img @click="goCalendar" src="@/assets/Group 57.png" alt="calendar">
        </div>

        <div class="wallet-2">
          <img src="@/assets/wallet_card_2.png" alt="wallet-card">
        </div>

        <div class="friend-block-btn">
          <v-btn @click="goBlockedFriend">차단친구 관리</v-btn>
        </div>

      </div>

     
    </div> 
  </div>
</template>

<script>
export default {
  name: 'MyPage',
  methods: {
    goProfileEdit: function (){
      this.$router.push({name: 'ProfileEdit'})
    },
    goCalendar: function () {
      this.$router.push({name:'Calendar'})
    },
    goMyData: function () {
      this.$router.push({name: 'MyData'})
    },
    goBlockedFriend: function () {
      this.$router.push({name: 'BlockedFriend'})
    }
  }
}
</script>

<style scoped>

.wallet-container {
  width: 80vw;
  margin: 150px auto;
}

img {
 max-width: 100%;
 max-height: 100%;
}

.wallet {
  position: relative;
}

.profile-card {
  width: 41%;
  position: absolute;
  top: 18%;
  left: 4.5%;
  transform: rotate(6deg);
}

.profile-card:hover {
  transform: scale(1.3);
}

.profile-card-info {
  position: absolute;
  top: 10%;
  left: 10%;
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
  /* transform: scale(1.1); */
  cursor: pointer;
}

.profile-calendar {
  position:absolute;
  top: 50%;
  width: 95%;
  cursor: pointer;
}

.profile-calendar:hover {
  transform: scale(1.1);
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

.friend-block-btn {
  position: absolute;
  top: 140%;
  right: 10%;
}

</style>
