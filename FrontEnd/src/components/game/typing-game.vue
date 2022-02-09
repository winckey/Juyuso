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
                <div class="my-info">
                    <div>
                        시간: <span class="time">{{ typingGame.time }}</span>초
                    </div>
                    <div>
                        내 점수: <span class="score">{{ score }}</span>점
                    </div>
                </div>
                <v-btn class="button" color="primary" @click="startGame" v-if="this.typingGame.allPlaying===false">게임 시작</v-btn>
                <v-btn class="button loading" color="grey" v-else>게임 진행 중</v-btn>
        </v-card>
    </div>
    <v-dialog v-model="typingGame.isEnd" width="400px">
        <div>
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
            wordDisplay: '시좍',
            wordInput: null,
            score: 0,
            isPlaying: false,
            timeInterval: null,
            words: ['우리가좍','쏴주', '맥쥬', '와잉', '으악', '낄낄', '걀걀', '요수 밤봐돠',
             '막궐리', '청춘은 바로 지금', '해웅데', '강알리', '웨불러', '드러눕자', '오마이갓김치'],
            typingGame: {
                type: 'Typing',
                time: 6,
                allPlaying: false,
                isEnd: false,
                scoreResult: [],
                scoreResultObject: [],
                members: [],
                winner: null
            }
        }
    },
    computed: {
        ...mapState('openviduStore', ['session', 'gameInfo']),
        ...mapState('accounts', ['user'])
    },
    mounted: function () {
        this.typingGame.members = this.session.streamManagers.map(stream => {
            console.log('mounted')
            return {
                connectionId: stream.stream.connection.connectionId,
                username: JSON.parse(stream.stream.connection.data).clientData
            }
        })

        console.log('for문 전')
        for(let i=0; i<this.typingGame.members.length; i++) {
            let item = { [this.typingGame.members[i].username] : 0 }
            this.typingGame.scoreResultObject.push(item)
        } 
        console.log(this.typingGame.scoreResultObject)

        this.sendInfo()
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
            this.typingGame.time > 0 ? this.typingGame.time -= 1 : this.typingGame.allPlaying=false;
            console.log('countdown 진행 중')

            this.sendInfo()
            
            if (this.typingGame.allPlaying===false) {
                
                console.log('게임 0초 직후 --> score에 정보 담을 것')
                this.typingGame.scoreResult.push('나 드러왔따')
                this.typingGame.scoreResult.push([-this.score, this.user.nickname])
                this.sendInfo()
                this.endGame()
            }
        },
        startGame: function () {
            this.isPlaying = true

            if (this.isPlaying) {
                this.typingGame.allPlaying = true
                this.sendInfo()
            }
            this.timeInterval=setInterval(this.countDown, 1000)
        },
        endGame: function () {
            console.log('타자게임 끝')
            this.isPlaying = false
            clearInterval(this.timeInterval)

            console.log(this.typingGame.scoreResult)
            console.log(this.typingGame.scoreResult.length)
            
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
            if (this.typingGame.allPlaying) {
                this.isPlaying = true
            }
            console.log('watch')
            if (this.typingGame.scoreResult) {
                if (this.typingGame.scoreResult.length === this.typingGame.members.length) {
                    this.typingGame.isEnd = true
                    console.log(this.typingGame.scoreResult.sort())
                    this.typingGame.winner = this.typingGame.scoreResult.sort()[0][1]
                    this.sendInfo()
                }
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

.my-info {
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
