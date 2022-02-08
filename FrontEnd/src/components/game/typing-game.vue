<template>
  <div>
    <div>
        <user-video class="col-md-4" :stream-manager="publisher"/>
        <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
    </div>
    <div class="game-box">
        <v-card class="typing-game">
            <div class="header">
                    <h2>🍺술향기 타자 연습🍺</h2>
                </div>
                <div class="word-display">
                    <h2>{{ wordDisplay }}</h2>
                </div>
                <div class="word-input-box">
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
                <div class="game-info">
                    <div>
                        남은 시간: <span class="time">{{ time }}</span>초
                    </div>
                    <div>
                        획득 점수: <span class="score">{{ score }}</span>점
                    </div>
                </div>
                <v-btn class="button" color="primary" @click="startGame" v-if="isPlaying===false">게임 시작</v-btn>
                <v-btn class="button loading" color="grey" @click="startGame" v-else>게임 진행 중</v-btn>
        </v-card>
  </div>
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
            wordDisplay: '드루와',
            wordInput: null,
            time: 10,
            score: 0,
            isPlaying: false,
            timeInterval: null,
            words: ['쏴주', '맥쥬', '와잉', '으악', '낄낄', '걀걀', '요수 밤봐돠', '막궐리', '청춘은 바로 지금', '해웅데']
        }
    },
    computed: {
        ...mapState('openviduStore', ['session'])
    },
    methods: {
        check: function () {
            if (this.wordInput === this.wordDisplay) {
                console.log(this.wordInput)
                this.score += 1
                this.wordInput = null
                this.changeWord()
            } else {
                this.wordInput = null
            }
        },
        countDown: function () {
            this.time > 0 ? this.time -= 1 : this.isPlaying=false;
            if (this.isPlaying===false) {
                this.endGame()
            }
        },
        startGame: function () {
            this.isPlaying = true
            this.timeInterval=setInterval(this.countDown, 1000)
        },
        endGame: function () {
            clearInterval(this.timeInterval)
        },
        changeWord: function () {
            const index = Math.floor((Math.random() * this.words.length))
            console.log(index)
            this.wordDisplay = this.words[index]
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
.typing-game {
    max-width: 500px;
   display: flex;
   flex-direction: column;
   justify-content: center; 
   align-items: center;
   padding: 2rem;
   border: solid aqua;
}

.header {
    background: skyblue;
    width: 100%;
    text-align: center;
    padding: 1rem;
    color: white;
}

.word-display {
    margin-top: 3rem;
    font-size: 2rem;
    color: skyblue;
    text-align: center;
}

.word-input-box {
    margin-top: 2rem;
}

.word-input {
    padding: 0.5rem;
    width: 250px;
}

.game-info {
    margin-top: 2rem;
    font-size: 1rem;
    display: flex;
    justify-content: space-between;
    width: 250px;
}

.time, .score {
    font-size: 2rem;
}

.button {
    width: 250px;
    color: white;
    margin-top: 2rem;
    margin-bottom: 2rem;
}

.loading {
    background: red;
    cursor: not-allowed;
}
</style>