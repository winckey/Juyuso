<template>
  <div v-if="titanicGame" class="game-mode" @mouseover="bgsound.play()">
    <div style="text-align: center; font-size: 1.4rem; height: 3vh">타이타닉 게임</div>
    <audio class="bgaudio" src="@/assets/sound/game_background.mp3"></audio>
    <audio class="audio" src="@/assets/sound/pour_sound.mp3"></audio>
    <div class="game-box">
      <div class="beer-box">
        <div class="game-result" v-if="titanicGame.isEnd">술 넘치게 따른 💥{{ titanicGame.members[titanicGame.curMember].username }}💥 당첨</div>
        <div class="game-info" v-else>{{ gameText }}</div>
        <div class="box">
          <img
            class="img-style mx-auto img-style-drink"
            :style="imgStyle"
            src="@/assets/game_beer.png" 
            alt="">
          <img 
            src="@/assets/game_glass.png" alt=""
            class="img-style img-style-galss">
          <img 
            v-if="titanicGame.isEnd"
            src="@/assets/game_bubble.png" alt=""
            class="img-style-bubble">
        </div>
        <div class="mx-auto beer-btn">
          <v-btn
            v-if="!titanicGame.isEnd"
            class="m-2"
            :disabled="!MyTurn"
            color="white"
            rounded
            @mousedown="setStartTime"
            @mouseup="setEndTime">
            따르기
          </v-btn>
          <span v-else class="end-btn">
            <v-btn
              class="m-2"
              color="white"
              rounded>
              한판 더하기
            </v-btn>
            <v-btn
              class="m-2"
              color="white"
              rounded
              @click="switchGameMode(undefined)">
              게임 끝내기
            </v-btn>
          </span>
        
        </div>
      </div>
      <TitanicVideo 
        v-for="(sub, idx) in turnSubscribers" 
        :key="sub.stream.connection.connectionId" 
        :stream-manager="sub" 
        :style="memberPos[idx]" 
        :background="setBackground(sub)"/>
    </div>
    <!-- <v-dialog
      v-if="titanicGame.isEnd"
      v-model="titanicGame.isEnd"
      persistent
      max-width="300">
      <v-card>
        <v-card-title>당첨자 확인</v-card-title>
        <v-card-text>🎉{{ titanicGame.members[titanicGame.curMember].username }}님 당첨🎉</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="[titanicGame.isEnd = false, changeGameMode(undefined)]"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog> -->
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import TitanicVideo from '@/components/table/titanic-video.vue'

export default {
  name: 'TitanicGame',
  components: {
    TitanicVideo
  },
  props: {
    subscribers: Array,
    publisher: Object,
  },
  data: function () {
    return {
      turnSubscribers: [],
      bgsound: null,
      sound: null,
      turnPublisher: null,
      clickStart: 0,
      MyTurn: false,
      myConnectionId: null,
      timerId: null,
      gameText: null,
      members: [],
      titanicGame: {
        type: 'Titanic',
        isEnd: false,
        maxAmount: 0,
        curAmount: 0,
        curMember: 0,
        members: [],
      },
      memberPos: [
        {top: '2vh', left: '26vw', position: 'absolute', height: '25vh', width: '12vw'}, 
        {top: '2vh', left: '46vw', position: 'absolute', height: '25vh', width: '12vw'},
        {top: '28vh', left: '16vw', position: 'absolute', height: '25vh', width: '12vw'},
        {top: '28vh', left: '58vw', position: 'absolute', height: '25vh', width: '12vw'},
        {top: '54vh', left: '20vw', position: 'absolute', height: '25vh', width: '12vw'},
        {top: '54vh', left: '52vw', position: 'absolute', height: '25vh', width: '12vw'}
      ],
    }
  },
  mounted: function () {
    this.bgsound = document.querySelector('.bgaudio')
    this.bgsound.volume = 0.0
    this.sound = document.querySelector('.audio')
    this.sound.volume = 0.5
    this.titanicGame = {...JSON.parse(this.gameInfo)}
    this.myConnectionId = this.publisher.stream.connection.connectionId
    for (let i=0; i<this.titanicGame.members.length; i++) {
      for (let j=0; j<this.titanicGame.members.length; j++) {
        if (this.titanicGame.members[i].connectionId == this.subscribers[j].stream.connection.connectionId) {
          this.turnSubscribers.push(this.subscribers[j])
          break
        }
      }
    }
    this.mainPublisher()
    this.isMyTurn()
  },
  methods: {
    ...mapActions('openviduStore', [
      'switchGameMode'
    ]),
    mainPublisher: function () {
      for(let i=0; i<this.subscribers.length; i++) {
        if(this.subscribers[i].stream.connection.connectionId == this.titanicGame.members[this.titanicGame.curMember].connectionId) {
          if (this.turnPublisher != this.subscribers[i]) {
            this.turnPublisher = this.subscribers[i]
            break
          }
        }
      }
    },
    gameStart: function() {

    },
    setBackground: function (sub) {
      if (sub == this.turnPublisher) {
        return '#ffd500'
      } else {
        return null
      }
    },
    isMyTurn: function () {
      this.MyTurn = this.myConnectionId == this.titanicGame.members[this.titanicGame.curMember].connectionId
      if (this.MyTurn) {
        this.gameText = "내차례🍻 마음껏 따라봐~"
      }
      else {
        this.gameText = `${this.titanicGame.members[this.titanicGame.curMember].username} 님이 따르는중💨`
      }
    },
    setStartTime: function () {
      this.bgsound.play()
      this.titanicGame.curAmount += 1
      this.sound.play()
      this.timerId = setInterval(this.calculateTime, 100);
    },
    calculateTime: function () {
      this.titanicGame.curAmount += 1
      if (this.titanicGame.curAmount > this.titanicGame.maxAmount) {
        this.titanicGame.isEnd = true
        this.sound.pause()
        this.sound.currentTime = 0
      }
      this.sendGameInfo()

    },
    setEndTime: function () {
      clearInterval(this.timerId)
      this.sound.pause()
      this.sound.currentTime = 0
      this.titanicGame.curMember = (this.titanicGame.curMember + 1) % this.titanicGame.members.length
      this.sendGameInfo()
    },
    sendGameInfo: function () {
      this.session.signal({
        data: JSON.stringify(this.titanicGame),
        to: [],
        type: 'game-info'
      })
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'session',
      'gameInfo',
    ]),
    imgStyle: function () {
      return {
        height: `${40 / this.titanicGame.maxAmount * this.titanicGame.curAmount}vh`,
      }
    },
  },
  watch: {
    gameInfo: function () {
      this.titanicGame = {...JSON.parse(this.gameInfo)}
      if(this.titanicGame.isEnd) {
        clearInterval(this.timerId)
      }
      this.mainPublisher()
      this.isMyTurn()
    }
  }
}
</script>

<style scoped>
  hr.rounded {
    border-top: 8px solid #bbb;
    border-radius: 5px;
  }

  .game-box {
    height: 81vh;
    width: 85vw;
    position: relative;
  }

  .game-info {
    position: absolute;
    left: 50%;
    top: 5vh;
    transform: translate(-50%)
  }
  .game-result {
    position: absolute;
    left: 50%;
    top: 5vh;
    transform: translate(-50%);
    z-index: 1;
    font-size: 2rem;
    -webkit-animation: scale-in-center 0.5s 1.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
    animation: scale-in-center 0.5s 1.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  }
  .beer-box {
    position: absolute;
    left: 50%;
    height: 56vh;
    width: 100%;
    transform: translate(-50%);
    bottom: 2vh;
  }
  .beer-btn {
    position: absolute;
    right: 50%;
    bottom: 0;
    transform: translate(50%);
  }

  .end-btn {
    position: absolute;
    left: 50%;
    bottom: 20%;
    transform: translate(-50%);
    -webkit-animation: bounce-top 1s;
    animation: bounce-top 1s;
  }

  .box{
    display: inline-block;
    position:relative;
    bottom: 0;
    left: 50%;
    transform:translate(-50%);
    display: inline-block;
    width: 11vw;
    height: 41vh;
    margin:auto;
  }

  .img-style-glass {
    position: absolute;
    left: 50%;
    height: 100%;
    transform: translateX(-50%, 0);
  }

  .img-style {
    display: block;
    object-fit: cover;
    object-position: bottom;
    height: 41vh;
    position: absolute;
    bottom: 0;
    left: 50%;
    transform:translate(-50%, 10vh);
  }

  .img-style-bubble {
    width: 12vw;
    position: absolute;
    top: 7vh;
    left: 50%;
    transform: translate(-50%);
    object-fit: cover;
    object-position: top;
    height: 35vh;
    animation: bubble 1.5s;
  }

  .img-style-drink{
    width: 11vw
  }

  @keyframes bubble {
    0% {
      height: 0;
    }

    100% {
      height: 35vh;
    }
  }

@-webkit-keyframes scale-in-center {
  0% {
    -webkit-transform: scale(0) translate(-50%);
            transform: scale(0) translate(-50%);
    opacity: 1;
  }
  100% {
    -webkit-transform: scale(1) translate(-50%);
            transform: scale(1) translate(-50%);
    opacity: 1;
  }
}
@keyframes scale-in-center {
  0% {
    -webkit-transform: scale(0) translate(-50%);
            transform: scale(0) translate(-50%);
    opacity: 1;
  }
  100% {
    -webkit-transform: scale(1) translate(-50%);
            transform: scale(1) translate(-50%);
    opacity: 1;
  }
}



@-webkit-keyframes bounce-top {
  0% {
    -webkit-transform: translateY(-45px) translateX(-50%);
            transform: translateY(-45px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
    opacity: 1;
  }
  24% {
    opacity: 1;
  }
  40% {
    -webkit-transform: translateY(-24px) translateX(-50%);
            transform: translateY(-24px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  65% {
    -webkit-transform: translateY(-12px) translateX(-50%);
            transform: translateY(-12px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  82% {
    -webkit-transform: translateY(-6px) translateX(-50%);
            transform: translateY(-6px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  93% {
    -webkit-transform: translateY(-4px) translateX(-50%);
            transform: translateY(-4px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  25%,
  55%,
  75%,
  87% {
    -webkit-transform: translateY(0px) translateX(-50%);
            transform: translateY(0px) translateX(-50%);
    -webkit-animation-timing-function: ease-out;
            animation-timing-function: ease-out;
  }
  100% {
    -webkit-transform: translateY(0px) translateX(-50%);
            transform: translateY(0px) translateX(-50%);
    -webkit-animation-timing-function: ease-out;
            animation-timing-function: ease-out;
    opacity: 1;
  }
}
@keyframes bounce-top {
  0% {
    -webkit-transform: translateY(-45px) translateX(-50%);
            transform: translateY(-45px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
    opacity: 1;
  }
  24% {
    opacity: 1;
  }
  40% {
    -webkit-transform: translateY(-24px) translateX(-50%);
            transform: translateY(-24px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  65% {
    -webkit-transform: translateY(-12px) translateX(-50%);
            transform: translateY(-12px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  82% {
    -webkit-transform: translateY(-6px) translateX(-50%);
            transform: translateY(-6px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  93% {
    -webkit-transform: translateY(-4px) translateX(-50%);
            transform: translateY(-4px) translateX(-50%);
    -webkit-animation-timing-function: ease-in;
            animation-timing-function: ease-in;
  }
  25%,
  55%,
  75%,
  87% {
    -webkit-transform: translateY(0px) translateX(-50%);
            transform: translateY(0px) translateX(-50%);
    -webkit-animation-timing-function: ease-out;
            animation-timing-function: ease-out;
  }
  100% {
    -webkit-transform: translateY(0px) translateX(-50%);
            transform: translateY(0px) translateX(-50%);
    -webkit-animation-timing-function: ease-out;
            animation-timing-function: ease-out;
    opacity: 1;
  }
}

</style>