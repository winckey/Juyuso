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
        </div>
        <div class="main-board">
          <img src="@/assets/account_board.png">
        </div>
          <div class="main-button">
            <div class="d-flex flex-column">

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
export default {
  name: 'Main',
  methods: {
    goToLogin: function () {
      this.$router.push({ name: 'Login' })
    },
    goToSignup: function () {
      this.$router.push({ name: 'Signup' })
    },
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
    left: 47%;
    z-index: 1;
  }

  .main-board {
    position: absolute;
    left: 60%;
    bottom: 0%;
  }
  
  .main-enter {
    position: absolute;
    top: 15%;
    left: 44%;
    opacity: 0.75;
  }

  .main-enter:hover {
    position: absolute;
    top: 15%;
    left: 44%;
    opacity: 1;
  }

  .main-bottom {
    position: absolute;
    left: 10%;
    bottom: 7%
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

</style>
