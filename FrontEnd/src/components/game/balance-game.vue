<template>
	<div v-if="balanceGame">
		<div>
      <div>
        <p>타이머 되나?</p>
        <v-btn @click="gameStart()" :disabled="balanceGame.isStart">시작</v-btn>
        <div style="color: white">
          {{ balanceGame.totalTime }}
        </div>
      </div>
      <v-container fluid class="flex">
        <v-row>
          <v-col>
            <v-btn v-for="n in 2" :key="n" @click="cardCount(n - 1)"> 
              <v-hover>
                <v-card>
                  card {{ n }} {{balanceGame.cardData[n - 1]}}
                </v-card>
              </v-hover>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
		</div>
    <user-video class="col-md-4" :stream-manager="publisher"/>
    <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
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
        totalTime: 60,
        cardData: [[], []],
        curMember:0,
        members:[],
      },
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
        this.balanceGame.totalTime = 60,
        this.balanceGame.isStart = true
        this.sendGameInfo()
      }
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
        clearInterval(this.timer)
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

<style>

</style>
