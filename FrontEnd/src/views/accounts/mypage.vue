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
        <v-btn color="green" >프로필 수정</v-btn>
      </div>

      <div class="profile-mydata">
        <img src="@/assets/Group 56.png" alt="mydata">

        <div class="profile-calendar">
          <img src="@/assets/Group 57.png" alt="calendar">
        </div>
      </div>

     
    </div> 
  </div>
</template>

<script>
export default {
  name: 'MyPage'
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

.profile-calendar {
  position:absolute;
  top: 40%;
}

</style>
