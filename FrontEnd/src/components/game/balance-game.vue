<template>
  <div v-if="balanceGame">
    <audio class="bgaudio" src="@/assets/sound/game_background.mp3"></audio>
    <audio class="audio" src="@/assets/sound/balance_click.wav"></audio>
    <v-row>
      <v-col cols="8">
        <div>
          <user-video class="col-md-4" :stream-manager="publisher"/>
        </div>
        <div>
          <div>
            <div class="game-box">
              <v-card class="balance-game">
                <v-container class="game">
                  <p>🍺밸런스 게임🥃</p>
                  <v-btn @click="[gameStart(),makeRandomNum()]" :disabled="balanceGame.isStart">시작</v-btn>
                  <v-container fluid class="flex"
                    v-if="dataInput">
                    <!-- <v-col
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
                    </v-col> -->
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
                          <p class="question-text">{{balanceGame.gameData[n-1][balanceGame.randomNum]}}</p>
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
        </div>
        <div>
          <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        </div>
      </v-col>

    </v-row>

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
        isWrite: false,
        isStart: false,
        isEnd:false,
        totalTime: 5,
        cardData: [[], []],
        curMember:0,
        members:[],
        gameData:[
          ['평생 백수 월 250','토마토맛 토', '처음만난 이상형과 커피','최준한테 모닝키스 받기','평생동안 사생활 노출','비오는 날 젖은 양말','1년동안 핸드폰 없이 살기','팔만대장정 다 읽기','트름할 때 방구소리','대학원생 하기',
          '계속 자도 피곤하기','평생 여름','친구의 애인과 바람','진정한 우정','19살로 돌아가서 수능 다시 보기','똥 안먹었는데 똥 먹었다고 소문나기(뉴스특보로 나옴)','아무 일 없이 씻고 8천원 내기','50% 확률로 10억 받기','스윙칩만 8달 동안 먹기','내가 좋아하는 사람이 날 싫어하기',
          '혼자 뷔페 가기','참다참다 야밤에 먹는 라면','모르는게 약','버거킹','탄산 없는 콜라','물렁물렁한 복숭아','코털 긴 애인','소음 공해','내 애인 패션 투명 스키니진','환승 이별',
          '이성 친구와 1박하는 애인','머리카락 없는 애인','애인 하루 100번 연락','매번 술 먹고 데리러 오라는 애인','내 속옷에 친구 손','모르는 사람 집에 애인 속옷','제일 싫어하는 사람과 같이 1억 복권 당첨','추성훈 선수한테 맞고 이국종 교수한테 수술받기','감자튀김에 간장 찍어먹기','탄산 없는 탄산음료',
          '열 손가락도 모자라 발가락에 까지 다이아 끼워주는 나 밖에 모르는 바보 이명박','한도 없는 카드를 쥐어주며 사고 싶은 거 다 사게 해주지만 집 안에서 나체로 생활하는 김경진','평생 떡볶이만 먹기','배터리 5% 데이터 가능','차가운 피자','재미없는 비행기 1시간','예쁜 글씨체 획득','이상적인 얼굴로 살기','머리 아픔','바선생 24시간 관찰'],
          ['평생 직장임 월 1000(연차없음)','토맛 토마토','10년지기 절친 생일파티','모닝에 치이기','평생동안 노출','비오는 날 새 신발','1년동안 친구 없이 살기','대장내시경 팔만번 하기','방구 뀔 때 트름소리','대학교 10년 다니기',
          '계속 먹어도 배고프기','평생 겨울','애인의 친구와 바람','진정한 사랑','그냥 이대로 살기','진짜 똥 먹었는데 아무도 모르기','사람 많은 목욕탕에서 넘어지고 8만원 받기(목욕탕 안에 모든 사람한테 주목받고 걱정 받음)','그냥 5000만원 받기','스윙스한테 800만원 주기','내가 싫어하는 사람이 날 좋아하기',
          '혼자 놀이공원 가기','등산 후 먹는 파전 + 막걸리','아는게 힘','맘스터치','치즈 없는 피자','딱딱한 복숭아','겨털 긴 애인','송해 고음','형광 핑크 망고 나시','잠수 이별',
          '전 애인과 단 둘이 술 마시는 애인','머릿속 빈 애인','한 달에 한번 연락','매번 술 먹으면 연락 두절되는 애인','친구 속옷 안에 내 손','애인집에 모르는 사람 속옷','그냥 살기','이국종 교수한테 맞고 추성훈 선수에게 수술받기','회에 케챱 찍어먹기','녹아서 액체가 된 아이스크림',
          '묶어놓고 나 때리는 조인성','사채 끌어다쓰는 도박꾼 원빈','평생 떡볶이 안 먹기','배터리 100% 데이터 와이파이 불가','따듯하고 치즈 듬뿍 들어간 고급 피자 끝부분','재미있는 비행기 10시간','엄청난 필력 획득','1억 받기','배 아픔','자는데 같은 방에서 바선생 돌아다니기']
        ],
        randomNum : 0
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
        const random = this.makeRandomNum(0, 50)
        this.balanceGame.randomNum = random
        this.sendGameInfo()
        console.log('랜덤 번호(this.random)',this.random)
        console.log('램덤 번호(balance.randomNum)',this.balanceGame.randomNum)
      }


      this.balanceGame.isEnd = false
      console.log(this.balanceGame.cardData)
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
        this.balanceGame.isWrite = false
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
    this.bgsound.volume = 0.1
    this.sound.volume = 0.5
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
