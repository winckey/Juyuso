<template>
  <div v-if="titanicGame" class="game-mode" @mouseover="bgsound.play()">
    <div style="text-align: center; font-size: 1.4rem; height: 3vh">타이타닉 게임</div>
    <audio class="bgaudio" src="@/assets/sound/game_background.mp3"></audio>
    <audio class="audio" src="@/assets/sound/pour_sound.mp3"></audio>
    <div class="game-box">
      <div class="beer-box">
        <div class="box">
          <img
            class="img-style mx-auto img-style-drink"
            :style="imgStyle"
            src="@/assets/game_beer.png" 
            alt="">
          <img 
            src="@/assets/game_glass.png" alt=""
            class="img-style img-style-galss">
        </div>
        <span class="mx-auto beer-btn">
          <v-btn
            class="m-2"
            :disabled="!MyTurn"
            color="white"
            rounded
            @mousedown="setStartTime"
            @mouseup="setEndTime">
            따르기
          </v-btn>
        </span>
      </div>
    </div>
    <!-- <div class="container-fluid">
      <div class="row" style="height: 100%">
        <div class="col-md-8">
          <div class="d-flex justify-content-center">
            <user-video class="col-md-4" :stream-manager="turnPublisher"/>
          </div>
          <hr class="rounded">
          <div>
            <div style="color:white; text-align: center">참가자</div>
            <v-slide-group
              dark
              show-arrow
            >
              <v-slide-item v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                <user-video class="col-md-3" :stream-manager="sub"/>
              </v-slide-item>
            </v-slide-group>
          </div>
        </div>
      </div>
    </div> -->
    <v-dialog
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
    </v-dialog>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
// import UserVideo from '@/components/table/user-video.vue'

export default {
  name: 'TitanicGame',
  components: {
    // UserVideo
  },
  props: {
    subscribers: Array,
    publisher: Object,
  },
  data: function () {
    return {
      bgsound: null,
      sound: null,
      turnPublisher: null,
      clickStart: 0,
      MyTurn: false,
      myConnectionId: null,
      timerId: null,
      titanicGame: {
        type: 'Titanic',
        isEnd: false,
        maxAmount: 0,
        curAmount: 0,
        curMember: 0,
        members: [],
      },
      memberPos: [
        {top: '2vh', left: '20vw'}, 
        {top: '31vh', left: '40vw'}
      ]
    }
  },
  mounted: function () {
    this.bgsound = document.querySelector('.bgaudio')
    this.bgsound.volume = 0.1
    console.log(this.bgsound)
    this.sound = document.querySelector('.audio')
    this.sound.volume = 0.5
    this.titanicGame = {...JSON.parse(this.gameInfo)}
    this.myConnectionId = this.publisher.stream.connection.connectionId
    this.mainPublisher()
    this.isMyTurn()
  },
  methods: {
    ...mapActions('openviduStore', [
      'changeGameMode'
    ]),
    mainPublisher: function () {
      for(let i=0; i<this.subscribers.length; i++) {
        if(this.subscribers[i].stream.connection.connectionId == this.titanicGame.members[this.titanicGame.curMember].connectionId) {
          if (this.turnPublisher != this.subscribers[i]) {
            this.turnPublisher = this.subscribers[i]
            console.log(this.turnPublisher)
          }
          break
        }
      }
    },
    isMyTurn: function () {
      this.MyTurn = this.myConnectionId == this.titanicGame.members[this.titanicGame.curMember].connectionId
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
    }
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

  .beer-box {
    position: absolute;
    left: 50%;
    height: 57vh;
    width: 100%;
    transform: translate(-50%);
    bottom: 2vh;
  }
  .beer-btn {
    position: absolute;
    right: 50%;
    transform: translate(50%);
    bottom: 0;
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

  .img-style-drink{
    width: 11vw
  }

</style>