<template>
	<div v-if="balanceGame">
    <div>
      <user-video class="col-md-4" :stream-manager="publisher"/>
      <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
    </div>
		<div class="game-box">
      <v-card class="balance-game">
        <div>
          <p>타이머</p>
          <v-btn @click="gameStart()" :disabled="balanceGame.isStart">시작</v-btn>
          <div style="color: rgb(5, 6, 114); font-size:1.2em">
            {{ balanceGame.totalTime }}
          </div>
        </div>
        <v-container fluid class="flex">
          <v-row>
            <v-col
              v-for="n in 2"
              :key="n"
              cols="12"
              sm="6">
              <v-hover>
                <v-card @click="cardCount(n - 1)">
                  {{gameData[n-1][balanceGame.randomNum]}}
                </v-card>
              </v-hover>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
		</div>
    <v-dialog v-model="balanceGame.isEnd">
      <v-card>얍</v-card>
      <v-card v-if="balanceGame.cardData">
        {{ winCard }} 를 선택하신 분이 승리하였습니다
      </v-card>
    </v-dialog>
	</div>
</template>

<script>
import UserVideo from '@/components/table/user-video.vue'
import { mapState } from 'vuex'

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
      balanceGame: {
        type: 'Balance',
        isStart: false,
        isEnd:false,
        totalTime: 3,
        cardData: [[], []],
        curMember:0,
        members:[],
        randomNum : 0
      },
      gameData:[
        ['평생 백수로 월 250','짬뽕','가','최준에게 아침마다 모닝키스 받기','치킨 퍽퍽살','하기싫은 일 10시출근 5시 퇴근','자','콜라','엄마','또'],
        ['평생 직장인 월 1000(연차없음)','짜장','나','모닝에 치이기','치킨 날개 목','재미있는 일 8시 출근 9시 퇴근','차','사이다','아빠','뭐있지']
      ],
      winCard : '선택해주세요',
    }
  },
  methods:{
    gameStart: function() {
      // 누군가 게임을 시작한 경우
      if (this.balanceGame.isStart && !this.gameStarted) {
        console.log(this.balanceGame.isStart, this.gameStarted)
        this.gameStarted = true
        this.timer = setInterval(this.countTime,1000)
      }
      // 내가 시작버튼을 누른 경우
      else if (!this.balanceGame.isStart && !this.gameStarted) {
        console.log('클릭')
        this.balanceGame.curMember = 0
        this.balanceGame.cardData = [[], []],
        this.balanceGame.totalTime = 3,
        this.balanceGame.isStart = true
        const random = Math.floor(Math.random()*10)
        this.balanceGame.randomNum = random
        this.sendGameInfo()
      }
      
      this.balanceGame.isEnd = false
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
      }
    },
    countTime:function(){
      this.balanceGame.totalTime = this.balanceGame.totalTime - 1
      if (this.balanceGame.totalTime <= 0) {
        
        console.log(this.balanceGame.cardData[0].length)
        if(this.balanceGame.cardData[0].length > this.balanceGame.cardData[1].length){
          this.winCard = 'A카드'
        }else if(this.balanceGame.cardData[0].length == this.balanceGame.cardData[1].length){
          this.winCard = '동점입니다 다시 시작을 눌러주세요'
        }else{
          this.winCard = 'B카드'
        }

        clearInterval(this.timer)
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
      console.log(this.balanceGame)
      if (!this.gameStarted && this.balanceGame.isStart) {
        console.log(this.balanceGame)
        this.gameStart()
      }
      else if (!this.balanceGame.isStart && this.gameStarted) {
        alert('게임 끝')

      }
    }
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
</style>
