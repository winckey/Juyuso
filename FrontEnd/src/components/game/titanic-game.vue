<template>
  <div>
    <v-progress-linear
      v-if="titanicGame"
      color="light-blue"
      height="15"
      :value="titanicGame.curAmount"
      :buffer-value="titanicGame.maxAmount"
      striped
    ></v-progress-linear>
    <user-video class="col-md-4" :stream-manager="publisher"/>
    <user-video class="col-md-4" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
    <v-btn
      :disabled="!MyTurn"
      color="white"
      @mousedown="setStartTime"
      @mouseup="setEndTime">클릭</v-btn>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import UserVideo from '@/components/table/user-video.vue'

export default {
  name: 'TitanicGame',
  components: {
    UserVideo
  },
  props: {
    subscribers: Array,
    publisher: Object,
  },
  data: function () {
    return {
      clickStart: 0,
      MyTurn: false,
      myConnectionId: null,
      timerId: null,
      titanicGame: {
        type: 'Titanic',
        maxAmount: 0,
        curAmount: 0,
        curMember: 0,
        members: [],
      }
    }
  },
  mounted: function () {

    this.myConnectionId = this.publisher.stream.connection.connectionId
    this.isMyTurn()
    console.log(this.publisher)
  },
  methods: {
    isMyTurn: function () {
      this.MyTurn = this.myConnectionId == this.titanicGame.members[this.titanicGame.curMember]
    },
    setStartTime: function () {
      this.titanicGame.curAmount += 1
      this.timerId = setInterval(this.calculateTime, 100);
    },
    calculateTime: function () {
      this.titanicGame.curAmount += 1
      if (this.titanicGame.curAmount > this.titanicGame.maxAmount) {
        clearInterval(this.timerId)
        alert(this.titanicGame.members[this.titanicGame.curMember] + '넘침')
      }
      this.sendGameInfo()
    },
    setEndTime: function () {
      clearInterval(this.timerId)
      this.titanicGame.curMember = (this.titanicGame.curMember + 1) % this.titanicGame.members.length
      this.sendGameInfo()
    },
    sendGameInfo: function () {
      this.session.signal({
        data: JSON.stringify(this.titanicGame),
        to: [],
        type: 'game-info'
      })
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'session',
      'gameInfo',
    ])
  },
  watch: {
    gameInfo: function () {
      this.titanicGame = {...JSON.parse(this.gameInfo)}
      this.isMyTurn()
    }
  }
}
</script>

<style>

</style>