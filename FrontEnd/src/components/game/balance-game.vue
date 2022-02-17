<template>
  <v-container v-if="balanceGame"
    fluid>
    <audio class="audio" src="@/assets/sound/balance_click.wav"></audio>
    <audio class="win" src="@/assets/sound/win.mp3"></audio>
    <audio class="lose" src="@/assets/sound/lose.mp3"></audio>
    <div>
      <v-row>
        <v-col>
          <user-video class="col-md-12" style="height: 25vh" :stream-manager="publisher"/>
          <div v-for="sub in subscribers.length" :key="sub"> 
            <user-video v-if="(sub-1) % 2 != 0" style="height:25vh;" class="col-md-12" :stream-manager="subscribers[sub-1]"/>
          </div>
          <!-- <user-video style="height:28vh" class="col-md-12" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/> -->
        </v-col>
        <v-col cols="4">
          <div class="balance-game">
            <div>
              <div>
                <div>
                  <v-container class="game">
                    <p>🍺밸런스 게임🥃</p>
                    <v-btn @click="[gameStart(),makeRandomNum()]" :disabled="balanceGame.isStart">시작</v-btn>
                    <v-container fluid class="flex"
                      v-if="dataInput">
                    </v-container>
                    <div style="color: rgb(0, 0, 0); font-size:1.2em"
                      v-if="balanceGame.isStart">
                      {{ balanceGame.totalTime }}
                    </div>
                  </v-container>
                  <v-container class="flex">
                    <v-row>
                      <v-col>
                        <div class="card-box" v-if="balanceGame.isStart">
                          <div
                            class="question-box a-card">
                            <div
                              @click="[cardCount(0),myPick(0)]"
                              v-if="myPickedCard == null || myPickedCard == 0">
                              <img src="@/assets/Acard.png" alt="" class="card-img">
                              <p class="question-text">{{balanceGame.gameData[0][balanceGame.randomNum]}}</p>
                            </div>
                            <div
                              v-else>
                                <img src="@/assets/nonSelectedA.png" alt="" class="card-img">
                                <p class="question-text">{{balanceGame.gameData[0][balanceGame.randomNum]}}</p>
                            </div>
                          </div>
                          <div 
                            class="question-box b-card">
                            <div
                              @click="[cardCount(1),myPick(1)]"
                              v-if="myPickedCard == null || myPickedCard == 1">
                              <img src="@/assets/Bcard.png" alt="" class="card-img">
                              <p class="question-text">{{balanceGame.gameData[1][balanceGame.randomNum]}}</p>                             
                            </div>
                            <div
                              v-else>
                              <img src="@/assets/nonSelectedB.png" alt="" class="card-img">
                              <p class="question-text">{{balanceGame.gameData[1][balanceGame.randomNum]}}</p>                             
                            </div>
                          </div>
                          <img src="@/assets/vs.png" alt="" class="vs">
                        </div>
                      </v-col>
                    </v-row>
                  </v-container>
                </div>
              </div>
              <v-dialog v-model="balanceGame.isEnd"
                max-width="400">
                <v-card v-if="balanceGame.cardData" class="dialog">
                  <div
                    class="result win"
                    v-if="winCard == myPickedCard">
                    <img src="@/assets/you_win.png" alt="">
                  </div>
                  <div
                    v-else
                    class="result lose">
                    <img src="@/assets/you_lose.png" alt="">
                  </div>
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
                    :value="(100-(balanceGame.cardData[1].length) / (balanceGame.cardData[0].length + balanceGame.cardData[1].length)*100)"
                    height="50"
                    v-else-if="balanceGame.cardData[0].length < balanceGame.cardData[1].length"
                    background-color="blue lighten-1"
                    color="blue lighten-5">
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
                  <!-- 무승부 -->
                  <v-progress-linear
                    :value="((balanceGame.cardData[1].length) / (balanceGame.cardData[0].length + balanceGame.cardData[1].length)*100)"
                    height="50"
                    v-else-if="balanceGame.cardData[0].length == balanceGame.cardData[1].length"
                    color="deep-purple darken-2">
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
                  <v-row>
                    <v-col>
                      <div class="result-box">
                        {{balanceGame.gameData[0][balanceGame.randomNum]}} 을(를) 
                        <div v-for="player in balanceGame.cardData[0]" :key="player.username">
                          {{ player.username }} 님
                        </div>
                        이 선택하셨습니다
                      </div>
                    </v-col>
                    <v-col>
                      <div class="result-box">
                        {{balanceGame.gameData[1][balanceGame.randomNum]}} 을(를)
                        <div v-for="player in balanceGame.cardData[1]" :key="player.username">
                          {{ player.username}} 님
                        </div>
                        이 선택하셨습니다
                      </div>
                    </v-col>
                  </v-row>
                  <div v-if="balanceGame.cardData[0].length > balanceGame.cardData[1].length"
                    class="win-messege">
                    {{ balanceGame.gameData[0][balanceGame.randomNum] }} 를 선택하신
                    <span class="name-highlignt"
                      v-for="player in balanceGame.cardData[0]"
                      :key="player.username">
                      🎉{{ player.username }}
                    </span>
                    님이 승리하였습니다
                  </div>
                  <div v-if="balanceGame.cardData[0].length < balanceGame.cardData[1].length"
                    class="win-messege">
                    {{ balanceGame.gameData[1][balanceGame.randomNum] }} 를 선택하신
                    <span class="name-highlignt"
                      v-for="player in balanceGame.cardData[1]"
                      :key="player.username">
                      🎉{{ player.username }}
                    </span>
                    님이 승리하였습니다
                  </div>
                  <div v-if="balanceGame.cardData[0].length == balanceGame.cardData[1].length"
                    class="win-messege">
                    무승부입니다
                  </div>
                </v-card>
              </v-dialog>
            </div>
          </div>
        </v-col>
        <v-col >
          <!-- <user-video style="height:28vh" class="col-md-12 camera" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/> -->
          <div v-for="sub in subscribers.length" :key="sub"> 
            <user-video v-if="(sub-1)%2 == 0" style="height:25vh" class="col-md-12" :stream-manager="subscribers[sub-1]"/>
          </div>
        </v-col>

      </v-row>
    </div>


  </v-container>

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
        isWrite: false,
        isStart: false,
        isEnd:false,
        totalTime: 10,
        cardData: [[], []],
        curMember:0,
        members:[],
        gameData:[
          ['평생 백수 월 250','토마토맛 토', '처음만난 이상형과 커피','최준한테 모닝키스 받기','평생동안 사생활 노출','비오는 날 젖은 양말','1년동안 핸드폰 없이 살기','팔만대장정 다 읽기','트름할 때 방구소리','대학원생 하기',
          '계속 자도 피곤하기','평생 여름','친구의 애인과 바람','진정한 우정','19살로 돌아가서 수능 다시 보기','똥 안먹었는데 똥 먹었다고 소문나기(뉴스특보로 나옴)','아무 일 없이 씻고 8천원 내기','50% 확률로 10억 받기','스윙칩만 8달 동안 먹기','내가 좋아하는 사람이 날 싫어하기',
          '혼자 뷔페 가기','참다참다 야밤에 먹는 라면','모르는게 약','버거킹','탄산 없는 콜라','물렁물렁한 복숭아','코털 긴 애인','소음 공해','내 애인 패션 투명 스키니진','환승 이별',
          '이성 친구와 1박하는 애인','머리카락 없는 애인','애인 하루 100번 연락','매번 술 먹고 데리러 오라는 애인','내 속옷에 친구 손','모르는 사람 집에 애인 속옷','제일 싫어하는 사람과 같이 1억 복권 당첨','추성훈 선수한테 맞고 이국종 교수한테 수술받기','감자튀김에 간장 찍어먹기','탄산 없는 탄산음료',
          '열 손가락도 모자라 발가락에 까지 다이아 끼워주는 나 밖에 모르는 바보 이명박','한도 없는 카드를 쥐어주며 사고 싶은 거 다 사게 해주지만 집 안에서 나체로 생활하는 김경진','평생 떡볶이만 먹기','배터리 5% 데이터 가능','차가운 피자','재미없는 비행기 1시간','예쁜 글씨체 획득','이상적인 얼굴로 살기','머리 아픔','바선생 24시간 관찰',
          '비오는날 칵테일 우산','북극에서 아아 마시기','대머리 되기'],
          ['평생 직장임 월 1000(연차없음)','토맛 토마토','10년지기 절친 생일파티','모닝에 치이기','평생동안 노출','비오는 날 새 신발','1년동안 친구 없이 살기','대장내시경 팔만번 하기','방구 뀔 때 트름소리','대학교 10년 다니기',
          '계속 먹어도 배고프기','평생 겨울','애인의 친구와 바람','진정한 사랑','그냥 이대로 살기','진짜 똥 먹었는데 아무도 모르기','사람 많은 목욕탕에서 넘어지고 8만원 받기(목욕탕 안에 모든 사람한테 주목받고 걱정 받음)','그냥 5000만원 받기','스윙스한테 800만원 주기','내가 싫어하는 사람이 날 좋아하기',
          '혼자 놀이공원 가기','등산 후 먹는 파전 + 막걸리','아는게 힘','맘스터치','치즈 없는 피자','딱딱한 복숭아','겨털 긴 애인','송해 고음','형광 핑크 망고 나시','잠수 이별',
          '전 애인과 단 둘이 술 마시는 애인','머릿속 빈 애인','한 달에 한번 연락','매번 술 먹으면 연락 두절되는 애인','친구 속옷 안에 내 손','애인집에 모르는 사람 속옷','그냥 살기','이국종 교수한테 맞고 추성훈 선수에게 수술받기','회에 케챱 찍어먹기','녹아서 액체가 된 아이스크림',
          '묶어놓고 나 때리는 조인성','사채 끌어다쓰는 도박꾼 원빈','평생 떡볶이 안 먹기','배터리 100% 데이터 와이파이 불가','따듯하고 치즈 듬뿍 들어간 고급 피자 끝부분','재미있는 비행기 10시간','엄청난 필력 획득','1억 받기','배 아픔','자는데 같은 방에서 바선생 돌아다니기',
          '모르는 사람 우산 안으로 살포시 들어가기','참숯불 황토 가마에서 뜨아 마시기','침팬치 되기']
        ],
        randomNum : 0
      },
      winCard : '선택해주세요',
      myPickedCard:null,
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
        this.gameStarted = true
        this.balanceGame.isEnd = false
        this.balanceGame.cardData = [[], []],
        this.selected = false
        this.myPickedCard = null
        this.timer = setInterval(this.countTime,1000)
      }
      // 내가 시작버튼을 누른 경우
      else if (!this.balanceGame.isStart && !this.gameStarted) {
        this.balanceGame.curMember = 0
        this.balanceGame.totalTime = 10,
        this.balanceGame.isStart = true
        const random = this.makeRandomNum(0, 52)
        this.balanceGame.randomNum = random
        this.myPickedCard = null
        this.sendGameInfo()
      }


      this.balanceGame.isEnd = false
      this.dataInput = false

    },
    makeRandomNum : function(min,max){
      var random = Math.floor(Math.random()*(max-min+1)+min)
      return random
    },
    writeText:function(){
      if(this.balanceGame.isWrite == false){
        this.dataInput = true
        this.balanceGame.isWrite = true
        this.sendGameInfo()
      }
      else{
        alert('다른 사용자가 문제를 작성중입니다')
      }
      
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
    },
    countTime:function(){
      this.balanceGame.totalTime = this.balanceGame.totalTime - 1
      if (this.balanceGame.totalTime <= 0) {

        if(this.balanceGame.cardData[0].length > this.balanceGame.cardData[1].length){
          this.winCard = 0
        }else{
          this.winCard = 1
        }

        clearInterval(this.timer)
        this.balanceGame.isStart = false
        this.gameStarted = false
        this.balanceGame.isEnd = true
        this.balanceGame.isWrite = false

        if(this.winCard == this.myPickedCard){
          this.win.play()
        }
        else{
          this.lose.play()
        }
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
        this.gameStart()
      }
      else if (!this.balanceGame.isStart && this.gameStarted) {
        alert('게임 끝')

      }
    },
  },
  mounted:function(){
    this.sound = document.querySelector('.audio')
    this.sound.volume = 0.5
    this.win = document.querySelector('.win')
    this.lose = document.querySelector('.lose')
    this.win.volume = 0.3
    this.lose.volume = 0.3
  }
}
</script>

<style scoped>
.dialog{
  overflow:scroll;
  -ms-overflow-style: none;
}
::-webkit-scrollbar{
  display: none;
}
.camera{
  margin: 0%;
  padding: 0%;
}
.balance-game {
  max-width: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center; 
  align-items: center;
  padding: 2rem;
  background-color: rgb(245, 241, 225);
  border-radius: 20px;
}
.game{
  text-align: center;
}
.question-box{
  /* position:absolute; */
  display: table;
  width: 300px;
  height: 150px;
  text-align: center;
  position: relative;
}
.question-box:hover{
  transform: scale(1.2);
  translate: .3s;
}
.card-box{
  position: relative;
}
.vs{
  position:absolute;
  vertical-align: middle;
  top: 50%;
  left: 50%;
  width: 80px;
  transform:translate(-50%,-50%);
}
.card-img{
  width:100%;
}
.question-box:hover{
  font-size: large;
}
.card-box{
  position: relative;
}
.vs{
  position:absolute;
  vertical-align: middle;
  top: 50%;
  left: 50%;
  width: 80px;
  transform:translate(-50%,-50%);
}
.question-text{
  position:absolute;
  display: table-cell;
  vertical-align: middle;
  color: white;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
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
.result-box{
  min-height: 100px;
  text-align: center;
  /* border: 1px solid black; */
  border-radius: 10px;
  background-color: rgb(245, 241, 225);
  vertical-align: middle;
  padding-top: 10px;
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
