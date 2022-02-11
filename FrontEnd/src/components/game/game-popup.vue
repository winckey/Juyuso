<template>
  <v-dialog
    v-model="dialog"
    width="500"
  >
    <v-card class="p-3 d-flex flex-column align-center">
      <v-card-title>
        게임선택
      </v-card-title>

      <v-item-group>
        <div class="container-fluid">
          <div class="row">
            <div class="col" v-for="game in games" :key="game.name">
              <v-item v-slot="{ active, toggle }">
                <v-card
                width="200"
                  :style="[ backgroundToggle(game)]"
                  :color="active ? 'primary' : ''"
                  @click="[toggle(), selectGame(game)]"
                  class="game-card d-flex justify-content-center align-center"
                  height="200">
                  <div class="game-name">
                    <span v-if="game != selectedGame">
                      {{ game.name }}
                    </span>
                    <v-icon size=100 color="#1CFD9F" v-else>
                      mdi-check
                    </v-icon>
                  </div>

                </v-card>
              </v-item>
            </div>
          </div>
        </div>
      </v-item-group>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn
          color="green darken-1"
          text
          @click="[sendGameInfo(), switchGameMode(selectedGame.name), dialog = false]"
        >
          선택
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'GamePopup',
  data: function () {
    return {
      dialog: false,
      selectedGame: this.gameMode,
      games: [
        {name: '타자연습', img: 'typing'},
        {name: '타이타닉', img: 'typing'},
        {name: '그림그리기', img: 'painting'},
        {name: '밸런스', img: 'balance'}
      ],
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'gameMode',
      'session'
    ]),
    
  },
  methods: {
    ...mapActions('openviduStore', [
      'switchGameMode'
    ]),
    selectGame: function (game) {
      this.selectedGame = game
      console.log(this.selectedGame)
    },
    backgroundToggle(game) {
      if (this.selectedGame == game) {
        return { backgroundImage: 'linear-gradient( rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.5) ), '+ 'url(' + require(`@/assets/${game.img}.jpg`) + ')' }
      }
      else {
        return { backgroundImage: 'linear-gradient( rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5) ), ' + 'url(' + require(`@/assets/${game.img}.jpg`) + ')' }
      }
    },
    sendGameInfo: function () {
      if (this.selectedGame.name === '타이타닉') {
        let members = this.session.streamManagers.map(stream => {
          return {
            connectionId: stream.stream.connection.connectionId,
            username: JSON.parse(stream.stream.connection.data).clientData
          }
        })
        let gameInfo = {
          type: 'Titanic',
          members: members.sort(() => Math.random() - 0.5),
          isEnd: false,
          curMember: 0,
          curAmount: 0,
          maxAmount: Math.random() * 50 + members.length * 30,
        }
        this.session.signal({
          data: JSON.stringify(gameInfo),
          to: [],
          type: 'game-info'
        })
      }
      else if (this.selectedGame.name === '밸런스') {
        let members = []
        this.session.streamManagers.forEach(stream => {
          members.push({
            connectionId: stream.stream.connection.connectionId,
            username: JSON.parse(stream.stream.connection.data).clientData,
            isSelected: false,
          })
        })
        let gameInfo = {
          type: 'Balance',
          members: members.sort(() => Math.random() - 0.5),
          isStart: false,
          isEnd: false,
          curMember:0,
          totalTime: 60,
          cardData:[[], []],
        }
        console.log(gameInfo)
        this.session.signal({
          data: JSON.stringify(gameInfo),
          to: [],
          type: 'game-info'
        })
      }
    }
  }
}
</script>

<style scoped>
  .game-card {
    position: relative;
    background-size : cover;
    /* background-image: linear-gradient( rgba(0, 0, 0, 0.9), rgba(0, 0, 0, 0.1) )  */
  }

  .game-name {
    font-size: 1.3rem;
    color: white;
    font-weight: 600;
  }
</style>