<template>
	<div v-if="balanceGame">
    <audio class="bgaudio" src="@/assets/sound/game_background.mp3"></audio>
    <audio class="audio" src="@/assets/sound/balance_click.wav"></audio>
    <div>
      <user-video class="col-md-4" :stream-manager="publisher"/>
      <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
    </div>
		<div class="game-box">
      <v-card class="balance-game">
        <v-container class="game">
          <p>🍺밸런스 게임🥃</p>
          <v-btn @click="writeText" :disabled="balanceGame.isStart">시작</v-btn>
          <v-container fluid class="flex"
            v-if="dataInput">
            <v-col
              cols="12"
              sm="6">
              <v-row>
                <v-text-field
                  v-model="Acard"></v-text-field>
              </v-row>
              <v-row>
                <v-text-field
                  v-model="Bcard"></v-text-field>
              </v-row>
              <v-btn @click="inputData" :disabled="balanceGame.isStart">문제확정</v-btn>
              <v-btn @click="gameStart" :disabled="balanceGame.isStart">게임시작</v-btn>
            </v-col>
          </v-container>
          <div style="color: rgb(0, 0, 0); font-size:1.2em"
            v-if="balanceGame.isStart">
            {{ balanceGame.totalTime }}
          </div>
        </v-container>
        <v-container fluid class="flex">
          <v-row>
            <v-col
              v-for="n in 2"
              :key="n"
              cols="12"
              sm="6">
              <v-hover v-if="balanceGame.isStart">
                <v-card @click="[cardCount(n - 1),myPick(n-1)]"
                  class="question-box">
                  <p class="question-text">{{balanceGame.gameData[n-1]}}</p>
                </v-card>
              </v-hover>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
		</div>
    <v-dialog v-model="balanceGame.isEnd"
      max-width="400">
      <v-card v-if="balanceGame.cardData">
      <v-card
        class="result win"
        v-if="winCard == myPickedCard">
        you win
      </v-card>
      <v-card
        v-else
        class="result lose">
        you lose 
      </v-card>
        <!-- A 승리 -->
        <v-progress-linear
          :value="((balanceGame.cardData[0].length) / (balanceGame.cardData[0].length + balanceGame.cardData[1].length)*100)"
          height="50"
          v-if="balanceGame.cardData[0].length > balanceGame.cardData[1].length"
          color="amber">
          <v-container>
            <v-row justify="space-between">
              <v-col cols="auto">
                A : {{ balanceGame.cardData[0].length }}
              </v-col>
              <v-col cols="auto">
                B : {{ balanceGame.cardData[1].length }}
              </v-col>
            </v-row>
          </v-container>
        </v-progress-linear>

        <!-- B 승리 -->
        <v-progress-linear
          :value="((balanceGame.cardData[1].length) / (balanceGame.cardData[0].length + balanceGame.cardData[1].length)*100)"
          height="50"
          v-else-if="balanceGame.cardData[0].length < balanceGame.cardData[1].length">
          <v-container>
            <v-row justify="space-between">
              <v-col cols="auto">
                B : {{ balanceGame.cardData[1].length }}
              </v-col>
              <v-col cols="auto">
                A : {{ balanceGame.cardData[0].length }}
              </v-col>
            </v-row>
          </v-container>
        </v-progress-linear>

        <div v-for="player in balanceGame.cardData[0]" :key="player.username">
          A카드를 {{ player.username }} 님이 선택하셨습니다
        </div>
        <div v-for="player in balanceGame.cardData[1]" :key="player.username">
          B카드를 {{ player.username}} 님이 선택하셨습니다
        </div>
        <div v-if="balanceGame.cardData[0].length > balanceGame.cardData[1].length"
          class="win-messege">
          {{ winCard }} 를 선택하신
          <span class="name-highlignt"
            v-for="player in balanceGame.cardData[0]"
            :key="player.username">
            🎉{{ player.username }}
          </span>
          님이 승리하였습니다
        </div>
        <div v-if="balanceGame.cardData[0].length < balanceGame.cardData[1].length"
          class="win-messege">
          {{ winCard }} 를 선택하신
          <span class="name-highlignt"
            v-for="player in balanceGame.cardData[1]"
            :key="player.username">
            🎉{{ player.username }}
          </span>
          님이 승리하였습니다
        </div>
        <div v-if="balanceGame.cardData[0].length == balanceGame.cardData[1].length">
          무승부입니다
        </div>
      </v-card>
    </v-dialog>
	</div>
</template>

<script>
import UserVideo from '@/components/table/user-video.vue'
import { mapState, mapActions } from 'vuex'

export default {
	name:'BalanceGame',
	components:{
    UserVideo
	},
  props: {
  subscribers: Array,
  publisher: Object,
  },
  computed: {
    ...mapState('accounts', [
      'user',
    ]),
    ...mapState('openviduStore', [
      'session',
      'gameInfo',
    ])
  },
  data: function(){
    return{
      title:'Timer',
      timer: null,
      resetButton: false,
      gameStarted: false,
      selected: false,
      bgsound: null,
      balanceGame: {
        type: 'Balance',
        isStart: false,
        isEnd:false,
        totalTime: 5,
        cardData: [[], []],
        curMember:0,
        members:[],
        gameData:[[],[]],
        // randomNum : 0
      },
      winCard : '선택해주세요',
      myPickedCard:'',
      Acard:'',
      Bcard:'',
      dataInput : false,
    }
  },
  methods:{
    ...mapActions('openviduStore', [
      'changeGameMode'
    ]),
    gameStart: function() {
      // 누군가 게임을 시작한 경우
      if (this.balanceGame.isStart && !this.gameStarted) {
        console.log(this.balanceGame.isStart, this.gameStarted)
        this.gameStarted = true
        this.balanceGame.isEnd = false
        this.selected = false
        this.timer = setInterval(this.countTime,1000)
      }
      // 내가 시작버튼을 누른 경우
      else if (!this.balanceGame.isStart && !this.gameStarted) {
        console.log('클릭')
        this.balanceGame.curMember = 0
        this.balanceGame.cardData = [[], []],
        this.balanceGame.totalTime = 5,
        this.balanceGame.isStart = true
        // const random = Math.floor(Math.random()*10)
        // this.balanceGame.randomNum = random
        this.sendGameInfo()
      }


      this.balanceGame.isEnd = false
      console.log(this.balanceGame.cardData)
      this.dataInput = false

    },
    writeText:function(){
      this.dataInput = true
    },
    inputData:function(){
      this.balanceGame.gameData[0] = this.Acard
      this.balanceGame.gameData[1] = this.Bcard
    },
    cardCount: function(n){
      if (!this.selected) {
        this.balanceGame.cardData[n].push({
          connectionId: this.publisher.stream.connection.connectionId,
          username: JSON.parse(this.publisher.stream.connection.data).clientData
        })
        this.balanceGame.curMember++
        if (this.balanceGame.curMember == this.balanceGame.members.length) {
          this.balanceGame.isStart = false
        }
        for (let i = 0; i < this.balanceGame.members.length; i++) {
          if (this.publisher.stream.connection.connectionId == this.balanceGame.members[i].connectionId) {
            this.balanceGame.members[i].isSelected = true
            break
          }
        }
        this.selected = true
        this.sendGameInfo()
        this.sound.play()
      }
    },
    myPick:function(n){
      this.myPickedCard = n
      console.log('선택카드 인덱스', this.myPickedCard)
    },
    countTime:function(){
      this.balanceGame.totalTime = this.balanceGame.totalTime - 1
      if (this.balanceGame.totalTime <= 0) {

        if(this.balanceGame.cardData[0].length > this.balanceGame.cardData[1].length){
          this.winCard = 0
        }else if(this.balanceGame.cardData[0].length == this.balanceGame.cardData[1].length){
          this.winCard = '동점입니다 다시 시작을 눌러주세요'
        }else{
          this.winCard = 1
        }

        clearInterval(this.timer)
        console.log('타이머 종료')
        this.balanceGame.isStart = false
        this.gameStarted = false
        this.balanceGame.isEnd = true
      }
    },
    showResult: function () {

    },
    sendGameInfo: function () {
      this.session.signal({
        data: JSON.stringify(this.balanceGame),
        to: [],
        type: 'game-info'
      })
    }
  },
  watch: {
    gameInfo: function () {
      this.balanceGame = {...JSON.parse(this.gameInfo)}
      if (!this.gameStarted && this.balanceGame.isStart) {
        console.log(this.balanceGame)
        this.gameStart()
      }
      else if (!this.balanceGame.isStart && this.gameStarted) {
        alert('게임 끝')

      }
    },
  },
  mounted:function(){
    this.bgsound = document.querySelector('.bgaudio')
    this.sound = document.querySelector('.audio')
    this.bgsound.volume = 0.05
    this.bgsound.play()
  }
}
</script>

<style scoped>
.game-box {
    position: fixed;
    top: 10%;
    right: 40%;
}
.balance-game {
    max-width: 500px;
   display: flex;
   flex-direction: column;
   justify-content: center; 
   align-items: center;
   padding: 2rem;
}
.game{
  text-align: center;
}
.question-box{
  display: table;
  width: 300px;
  height: 300px;
  text-align: center;
}
.question-text{
  display: table-cell;
  vertical-align: middle;
  padding: 10%;
}
.progress-text{
  text-align: justify
}
.name-highlignt{
  text-decoration-color: darkcyan;
  font-weight: bold;
}
.win-messege{
  text-align: center;
  padding: 20px;
}
.result{
  text-align: center;
  font-size: 1.5em;
  font-weight: bold;
}
.win{
  color: rgb(219, 184, 27);
}
.lose{
  color: rgb(20, 20, 129);
}
</style>
