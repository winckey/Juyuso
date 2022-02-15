<template>
  <div style="positon: relative">
    <div style="position: relative" class="align-center">
      <img src="@/assets/main_sky.png" alt="" class="main-sky mx-auto my-5">
      <div>
        <img src="@/assets/main_logo.png" alt="" class="main-logo blinking">
      </div>
    </div>
    <div class="main-bottom">
      <div style="position: relative" class="align-center">
        <div class="align-center" style="position: relative">
          <img src="@/assets/main_start.png" alt="">
          <div class="main-enter">
            <button>

            <img src="@/assets/room_point.png" alt="" @click="goToTableList">
            </button>
          </div>
          <div class="main-text">불빛을 클릭해주세요</div>
        </div>
        <div class="main-board">
          <img src="@/assets/account_board.png">
        </div>
          <div class="main-button">
            <div class="d-flex flex-column">
            
              <!-- <div class="d-flex flex-column" v-if="isJwt">
                <v-btn
                  class="mx-auto my-1"
                  color="#E6D8B2"
                  @click="goToLogin"
                >로그인</v-btn>
                <v-btn 
                  class="mx-auto my-1"
                  color="#E6D8B2"
                  @click="goToSignup"
                >회원가입</v-btn>
              </div> -->

              <div>
                <v-btn
                  class="mx-auto my-1"
                  color="#E6D8B2"
                  @click="onLogout"
                >로그아웃</v-btn>
              </div>

            </div>
          </div>
      </div>
      <div class="align-center">
        <img src="@/assets/main_ground.png" alt="">
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  name: 'Main',
  // data: function () {
  //   return {
  //   }
  // },
  // computed: {   
  //   isJwt: function () {
  //     if (localStorage.getItem('jwt')) {
  //       console.log(this.isLogin)
  //       return false
  //     } else {
  //       console.log("@")
  //       return true
  //     }
  //   }
  // },
  methods: {
    ...mapActions('accounts', ['logout']),
    onLogout() {
      this.logout()
        .then(() => this.$router.push({ name: 'Login' }))
    },
    // goToLogin: function () {
    //   this.$router.push({ name: 'Login' })
    // },
    // goToSignup: function () {
    //   this.$router.push({ name: 'Signup' })
    // },
    goToTableList: function () {
      this.$router.push({ name: 'TableList' })
    }
  }
}
</script>

<style scoped>
  .main-sky {
    position: relative;
    width: 80%;
    z-index: 0;
    margin: 0px, auto;
  }

  .main-logo {
    position: absolute;
    top: 20%;
    left: 50%;
    z-index: 1;
    transform: translate(-50%);
  }

  .main-board {
    position: absolute;
    left: 60%;
    bottom: 0%;
  }
  
  .main-enter {
    position: absolute;
    top: -30%;
    left: 50%;
    opacity: 0.7;
    transform: translate(-50%);
  }

  .main-enter:hover {
    position: absolute;
    top: -30%;
    left: 50%;
    opacity: 1;
    transform: translate(-50%);
  }
  
  .main-text {
    font-size: 24px;
    color: white;
    position: absolute;
    left: 50%;
    transform: translate(-50%);
    animation-name: sparkle;
    animation-duration: 1.5s;
    animation-direction: alternate;
    animation-iteration-count: infinite;
  }

  .main-bottom {
    position: absolute;
    left: 50%;
    bottom: 7%;
    transform: translate(-50%);
  }
  
  .main-button {
    position: absolute;
    left: 63%;
    bottom: 20%;
  }

  .align-center {
    text-align: center;
  }

  .blinking{ 
    -webkit-animation:blink 1.2s ease-in-out infinite alternate; 
    -moz-animation:blink 1.2s ease-in-out infinite alternate; 
    animation:blink 1.2s ease-in-out infinite alternate; 
  } 
  
  @-webkit-keyframes blink{
    0% {opacity:0.3;}
    100% {opacity:1;} 
  } 
  
  @-moz-keyframes blink{
    0% {opacity:0.3;} 
    100% {opacity:1;} 
  } 
  
  @keyframes blink{ 
    0% {opacity:0.3;} 
    100% {opacity:1;} 
  }

  @keyframes sparkle {
    from {
      color: rgb(255, 255, 255);
    }
    to {
      color: rgb(235, 226, 176);
    }
  }
</style>
