<template>
  <div>
    <audio class="correctAudio" src="@/assets/sound/correct.mp3"></audio>
    <audio class="nopeAudio" src="@/assets/sound/nope.mp3"></audio>
    <audio class="gameResult" src="@/assets/sound/game_result.mp3"></audio>
    <v-row class="entire-box">
      <img src="" alt="">
      <v-col cols="4" class="d-flex justify-content-center align-items-center">
        <div class="video-grid" :style="videoGrid">
          <user-video  :stream-manager="publisher"/>
          <user-video  v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        </div>
      </v-col>
      <v-col cols="4" class="d-flex justify-content-center align-items-center">
        <div class="video-grid" :style="videoGrid">
          <typing-game-score :stream-manager="publisher" 
          :score="typingGame.scoreResultObject[JSON.parse(publisher.stream.connection.data).clientData]"/>
          <typing-game-score v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"  
          :score="typingGame.scoreResultObject[JSON.parse(sub.stream.connection.data).clientData]"/>
        </div>
      </v-col>
      <v-col class="d-flex justify-content-center align-items-center" cols="4" >
          <v-card class="typing-game">
                  <div class="d-flex justify-content-center" v-if="typingGame.isBefore" style="color: white">
                    <h1>{{ typingGame.countBeforeGame }}</h1>
                  </div>
                  <div class="header">
                      <h2>🍺술향기 타자연습🍺</h2>
                  </div>
                  <div class="word-display">
                      <h2>{{ wordDisplay }}</h2>
                  </div>
                  <div class="mt-2">
                      <v-text-field
                          label="단어를 입력하시오"
                          solo
                          dense
                          class="word-input"
                          v-model="wordInput"
                          @keyup.enter="check"
                          :disabled="!isPlaying"
                          :autofocus="this.typingGame.allPlaying"
                      ></v-text-field>
                  </div>
                  <div class="my-info"> 
                      <div class="time">
                          시간: <span >{{ typingGame.time }}</span>초
                      </div>
                      <div class="score">
                          내 점수: <span >{{ score }}</span>점
                      </div>
                  </div>
                  <v-btn class="button" color="#4DB6AC" dark @click="beforeGame" v-if="!typingGame.allPlaying&& !typingGame.isEnd && !typingGame.isBefore">게임 시작</v-btn>
                  <v-btn class="button prep-button" color="white"  v-if="typingGame.allPlaying">게임 진행 중</v-btn>
                  <v-btn class="button prep-button" color="white"  v-if="typingGame.isBefore">대기 중</v-btn>
                  <div class="d-flex flex-column" v-if="typingGame.isEnd">
                    <v-btn class="button" color="#4DB6AC" dark @click="reset()">한판 더</v-btn>
                    <v-btn class="button" style="color: #4DB6AC" @click="switchGameMode(undefined)">게임 종료</v-btn>
                  </div>
          </v-card>
    <div v-if="typingGame.isEnd"  class="celebration-box">
        <v-card  class="p-3" width="300px">
            <div class="d-flex flex-column align-items-center" style="text-align: center">
                <h3>축하합니다</h3>
                <img src="@/assets/basic_profile.png" width="100px" alt="celebrate" class="celebrate-img">
                <hr>
                <v-card-text style="font-size: 1.2rem">🧃{{typingGame.winner}}님 덜 취했군요🧃</v-card-text>
            </div>
            <v-spacer></v-spacer>
        </v-card>
    </div>
      </v-col>
    </v-row>
      
  </div>
  
</template>

<script>
import { mapActions, mapState } from 'vuex'
import UserVideo from '@/components/table/user-video.vue'
import TypingGameScore from '@/components/game/typing-game-score.vue'

export default {
  name: 'TypingGame',
  props: {
    subscribers: Array,
    publisher: Object,
  },
  components: {
    UserVideo,
    TypingGameScore
  },
  data: function () {
    return {
      correctAudio: null,
      nopeAudio: null,
      gameResultSound: null, 
      wordDisplay: '시좍',
      wordInput: null,
      score: 0,
      isPlaying: false,
      timeInterval: null,
      members: [],
      scoreResultObject: {},
      words: ['우리가좍!!', '요수 밤봐돠 말고 해운대','내가 기린 그린 기린', '최참판댁 뒤 김판참', '한양 양장점 앞', '기린 그림', '난방 방법 변경 방법', '도시 찹쌀 촌찹쌀', '오늘이 금요일이라면,,',
      '내가 그린 기린 그림', '확률분포표', '홑겹창살', '참치꽁치찜', '김치참치꽁치치치', '역전 석점슛', 'dijkstra', 'bellman-ford', '커커커커커피 한 잔 plz',
       '붕당정책 탕평책', '오마이갓김치', '왕밤빰', '영동 용봉탕', '게솰샥수핀', '경찰청 창살', '단팥맛 통찐빵', '설사vs변비', '한녀허름 소나기', 
       '반품하려다 하지 않았쥐', '강력접착제', '브레드킹 김핑퐁', '하울의 무빙이 오지는 성', '깐 콩깍지', '얼죽아?ㄴㄴ', '떫디 떫은 왕떫은 감', '내 양말이 어허디히있나~',
       '영월 칡국수', '공간감각 무감각', '팥죽깨죽', '스위스에서 온 스미스씨', '닥터페퍼 닥터페퍼','어! 느새, 부터 힙! 합은 안 멋져', '쑤쑤쑥떡이 먹고파'],
      typingGame: {
        type: 'Typing',
        time: 30,
        countBeforeGame: 3,
        allPlaying: false,
        isBefore: false,
        isEnd: false,
        scoreResult: [],
        scoreResultObject: {},
        members: [],
        winner: null
      },
    }
  },
  computed: {
    ...mapState('openviduStore', ['session', 'gameInfo']),
    ...mapState('accounts', ['user']),
    videoGrid: function () {
      return {
          display: 'grid',
          gridTemplateColumns: 28+'vw',
          gap: 0.5+'vh',
          gridTemplateRows: this.typingGame.members.length === 1 ? 
          'repeat('+`${this.typingGame.members.length}`+','+ (80/2)+'vh)' :
          'repeat('+`${this.typingGame.members.length}`+','+ (80/`${this.typingGame.members.length}`)+'vh)'

      }
    }
  },
  mounted: function () {
    this.correctAudio = document.querySelector('.correctAudio')
    this.nopeAudio = document.querySelector('.nopeAudio')
    this.correctAudio.volumne = 0.01
    this.nopeAudio.volumne = 0.1
    this.gameResultSound = document.querySelector('.gameResult')
    this.gameResultSound.volume = 0.2
    this.members = this.session.streamManagers.map(stream => {
      return {
        connectionId: stream.stream.connection.connectionId,
        username: JSON.parse(stream.stream.connection.data).clientData
      }
    })
    for(let i=0; i<this.members.length; i++) {
      this.scoreResultObject[this.members[i].username] = 0
    } 
  },
  methods: {
    ...mapActions('openviduStore', ['switchGameMode']),
    check: function () {
      if (this.wordInput === this.wordDisplay) {
        this.correctAudio.play()
        this.score += 1
        this.typingGame.scoreResultObject[JSON.parse(this.publisher.stream.connection.data).clientData] += 1
        this.wordInput = null
        this.changeWord()
        this.sendInfo()
      } else {
        this.nopeAudio.play()
        this.wordInput = null
      }
    },
    countDown: function () {
      this.typingGame.time > 0 ? this.typingGame.time -= 1 : this.typingGame.allPlaying=false;
      if (this.typingGame.allPlaying===false) {
        this.endGame()
      }
      this.sendInfo()
    },
    beforeGame: function () {
      this.wordInput = null
      this.typingGame.isBefore = true 
      this.sendInfo()
      let count = setInterval(() => {
        this.typingGame.countBeforeGame -- 
        this.sendInfo()
        if (this.typingGame.countBeforeGame === 0) {
          clearInterval(count)
          this.startGame()
        }
      }, 1000)
    },
    startGame: function () {
      this.isPlaying = true
      if (this.isPlaying) {
          this.typingGame = {
            type: 'Typing',
            time: 30,
            allPlaying: true,
            isBefore: false,
            isEnd: false,
            scoreResult: [],
            scoreResultObject: {...this.scoreResultObject},
            members: this.members,
            winner: null
          }
          this.sendInfo()
      }
      this.changeWord()
      this.timeInterval=setInterval(this.countDown, 1000)

    },
    endGame: function () {
      this.isPlaying = false
      clearInterval(this.timeInterval)
      let winner = []
      let maxValue = 0
      for (let name in this.typingGame.scoreResultObject) {
        if (this.typingGame.scoreResultObject[name] > maxValue) {
          winner = [name]
          maxValue = this.typingGame.scoreResultObject[name]
        }
        else if (this.typingGame.scoreResultObject[name] == maxValue) {
          winner.push(name)
        }
      }
      this.typingGame.isEnd = true
      this.typingGame.winner = winner[0]
      this.sendInfo()
      this.gameResultSound.play()
    },
    changeWord: function () {
      const index = Math.floor((Math.random() * this.words.length))
      this.wordDisplay = this.words[index]
    },
    reset: function () {
      this.score = 0
      this.wordInput = null
      this.wordDisplay = '시좍'

      for(let i=0; i<this.members.length; i++) {
        this.scoreResultObject[this.members[i].username] = 0
      } 

      this.typingGame = {
        type: 'Typing',
        time: 30,
        countBeforeGame: 3,
        allPlaying: false,
        isBefore: false,
        isEnd: false,
        scoreResult: [],
        scoreResultObject: {},
        members: [],
        winner: null
      }
      
      this.beforeGame()

    },
    sendInfo: function () {
      this.session.signal({
        data: JSON.stringify(this.typingGame),
        to: [],
        type: 'game-info'
      })
    }
      
  },
  watch: {
    gameInfo: function () {
      this.typingGame = {...JSON.parse(this.gameInfo)}
      if (!this.isPlaying && this.typingGame.allPlaying) {
        this.timeInterval=setInterval(this.countDown, 1000)
      }
      if (this.typingGame.allPlaying) {
        this.isPlaying = true
      }
    },
  }

}
</script>

<style scoped>

.entire-box {
    height: 83vh;
    width: 85vw;
    position: relative;
  }

.typing-game {
  border: 2px solid #dadada;
  border-radius: 7px;
  width: 25vw;
  height: 70vh;
  display: flex;
  flex-direction: column;
  justify-content: center; 
  align-items: center;
  padding: 1rem;
  background-image: linear-gradient( rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.5) ), url(https://i.pinimg.com/originals/07/fd/31/07fd31cf7290acb47f6329bb0a95b726.gif);
  background-size: cover;
  background-position: center;

}

.typing-game:focus {
    outline: none;
    border-color: #9ecaed;
    box-shadow: 0 0 10px #9ecaed;
}

.header {
    width: 100%;
    text-align: center;
    padding: 1rem;
    color: #fff;
    text-shadow: 0 0 21px #fff, 0 0 42px #0fa,
      0 0 82px #0fa, 0 0 92px #0fa, 0 0 102px #0fa, 0 0 151px #0fa;
    color: white;
}

.word-display {
    margin-top: 2rem;
    font-size: 2rem;
    color: #fff;
    text-shadow: 0 0 7px #fff, 0 0 42px #0fa;
    color: white;
    text-align: center;
}


.word-input {
    border-radius: 10px;
    padding: 0.5rem;
    width: 18vw;
}

.my-info {
    margin-top: 2rem;
    font-size: 1rem;
    display: flex;
    justify-content: space-between;
    width: 18vw;
}

.time, .score {
    font-size: 1.5rem;
    color: #fff;
    text-shadow: 0 0 21px #fff, 0 0 42px #0fa,
      0 0 82px #0fa, 0 0 92px #0fa, 0 0 102px #0fa, 0 0 151px #0fa;
}


.button {
    border-radius: 10px;
    width: 18vw;
    margin-top: 1.5vh;
    margin-bottom: 2vh;
    font-size: 1.2rem;
}

.prep-button {
  cursor:not-allowed; 
  color: #4DB6AC;
}

.video-grid {
  display: grid;
  grid-template-columns: 28vw;
  grid-template-rows: repeat(6, 12.5vh);
  gap: 1vh;
}

.celebration-box {
  z-index:2;
  position: absolute;
  top: 25%;
  left: 40%;
}

.celebrate-img{
  animation:motion 0.5s linear 0s infinite alternate; 
  margin-top: 0;
}

@keyframes motion {
	0% {margin-top: 0px;}
	100% {margin-top: 5px;}
}
</style>
