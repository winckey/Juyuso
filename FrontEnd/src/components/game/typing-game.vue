<template>
  <div>
    <audio class="correctAudio" src="@/assets/sound/correct.mp3"></audio>
    <audio class="nopeAudio" src="@/assets/sound/nope.mp3"></audio>
    <v-row>
      
      <v-col cols="4">
        <div class="video-grid" :style="videoGrid">
          <user-video  :stream-manager="publisher"/>
          <user-video  v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        </div>
      </v-col>
      <v-col cols="4">
        <div class="video-grid" :style="videoGrid">
          <img src="@/assets/chat.png" alt="dd">
          <img src="@/assets/chat.png" alt="dd">
          <img src="@/assets/chat.png" alt="dd">
          <img src="@/assets/chat.png" alt="dd">

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
                  <v-btn class="button" color="#4DB6AC" @click="beforeGame" v-if="this.typingGame.allPlaying===false">게임 시작</v-btn>
                  <v-btn class="button" color="white" style="cursor:not-allowed" v-else>게임 진행 중</v-btn>
          </v-card>
      </v-col>
    </v-row>
      <v-dialog v-model="typingGame.isEnd" width="400px"  >
          <div>
              <!-- <v-img src="@/assets/typing-game/celebrate.png"></v-img> -->
              <v-card  class="p-3">
                  <div class="d-flex flex-column" style="text-align: center">
                      <h3>축하합니다</h3>
                      <hr>
                      <v-card-text style="font-size: 1.2rem">🧃{{typingGame.winner}}님의 승리란다 얘둘앙🧃</v-card-text>
                  </div>
                  <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="green darken-1"
                      text
                      @click="typingGame.isEnd = false"
                  >
                      확인
                  </v-btn>
                  </v-card-actions>
              </v-card>
          </div>
      </v-dialog>
  </div>
  
</template>

<script>
import { mapState } from 'vuex'
import UserVideo from '@/components/table/user-video.vue'

export default {
  name: 'TypingGame',
  props: {
    subscribers: Array,
    publisher: Object,
  },
  components: {
    UserVideo
  },
  data: function () {
    return {
      
      correctAudio: null,
      nopeAudio: null,
      wordDisplay: '시좍',
      wordInput: null,
      score: 0,
      isPlaying: false,
      timeInterval: null,
      members: [],
      scoreResultObject: {},
      words: ['우리가좍', '요수 밤봐돠','샹숑가수', '최참판댁', '한양 양장점', '기린 그림', 
      '내가 그린 기린 그림', '확률분포표', '홑겹창살', '참치꽁치찜', '김치참치꽁치치',
       '청춘은 바로 지금', '오마이갓김치', '왕밤빰', '영동용봉탕', 
       '반품상품', '강력접착제', '브레드킹 김핑퐁', '하울의 무빙이 오지는 성'],
      typingGame: {
        type: 'Typing',
        time: 6,
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
          gap: 1+'vh',
          gridTemplateRows: 'repeat('+`${this.typingGame.members.length}`+','+ (80/`${this.typingGame.members.length}`)+'vh)'

      }
    }
  },
  mounted: function () {
    this.correctAudio = document.querySelector('.correctAudio')
    this.nopeAudio = document.querySelector('.nopeAudio')
    this.correctAudio.volumne = 0.01
    this.nopeAudio.volumne = 0.1
    this.members = this.session.streamManagers.map(stream => {
      return {
        connectionId: stream.stream.connection.connectionId,
        username: JSON.parse(stream.stream.connection.data).clientData
      }
    })
    for(let i=0; i<this.members.length; i++) {
      this.scoreResultObject[this.members[i].username] = 0
    } 
    console.log('repeat('+`${this.typingGame.members.length}`+','+ (80/`${this.typingGame.members.length}`)+'vh)')
  },
  methods: {
    check: function () {
      if (this.wordInput === this.wordDisplay) {
        this.correctAudio.play()
        this.score += 1
        console.log(JSON.parse(this.publisher.stream.connection.data).clientData)
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
            time: 6,
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
      this.timeInterval=setInterval(this.countDown, 1000)

    },
      // else if (!this.isPlaying && this.typingGame.allPlaying) {
      //   this.timeInterval=setInterval(this.countDown, 1000)
      // }
    endGame: function () {
      this.isPlaying = false
      clearInterval(this.timeInterval)
      console.log(this.typingGame)
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
      console.log('repeat('+`${this.typingGame.members.length}`+','+ (80/`${this.typingGame.members.length}`)+'vh)')
    },
    changeWord: function () {
      const index = Math.floor((Math.random() * this.words.length))
      this.wordDisplay = this.words[index]
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
      console.log(this.typingGame)
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

.typing-game {
  border: 2px solid #dadada;
  border-radius: 7px;
  max-width: 75vw;
  max-height: 100vh;
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
    margin-top: 3rem;
    font-size: 2rem;
    color: #fff;
    text-shadow: 0 0 7px #fff, 0 0 42px #0fa;
    color: white;
    text-align: center;
}


.word-input {
    padding: 0.5rem;
    width: 250px;
}

.my-info {
    margin-top: 2rem;
    font-size: 1rem;
    display: flex;
    justify-content: space-between;
    width: 250px;
}

.time, .score {
    font-size: 1.5rem;
    /* color: white; */
    color: #fff;
    text-shadow: 0 0 21px #fff, 0 0 42px #0fa,
      0 0 82px #0fa, 0 0 92px #0fa, 0 0 102px #0fa, 0 0 151px #0fa;
}


.button {
    width: 250px;
    color: white;
    margin-top: 2rem;
    margin-bottom: 2rem;
}


.video-grid {
  display: grid;
  grid-template-columns: 28vw;
  grid-template-rows: repeat(6, 12vh);
  gap: 1vh;

  
  

}
</style>
