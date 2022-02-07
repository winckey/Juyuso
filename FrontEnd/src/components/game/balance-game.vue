<template>
	<div>
		<div>
      <div>
        <p>타이머 되나?</p>
        <v-btn @click="gameStart">시작</v-btn>
        <div>
          {{ balanceGame.totalTime }}
        </div>
      </div>
      <v-container fluid class="flex">
        <v-row>
          <v-col>
            <v-btn @click="cardOneCount">
              <v-hover>
                <v-card>
                  card1 {{ balanceGame.cardOneData }}
                </v-card>
              </v-hover>
            </v-btn>
            <v-btn @click="cardTwoCount">
              <v-hover>
                <v-card>
                  card2 {{ balanceGame.cardTwoData }}
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
      titmer:null,
      resetButton: false,
      balanceGame: {
        roomNum:'',
        type: 'Balance',
        totalTime: 60,
        cardOneData:[],
        cardTwoData:[],
        //gameInfo안에
        curMember:0,
        members:[],
      },
      player:[],
      cardclick:false,

    }
  },
  methods:{
    gameStart:function(){
      this.balanceGame.totalTime = 60,
      this.balanceGame.cardOneData = [],
      this.balanceGame.cardTwoData = []
    },
    cardOneCount:function(){
      this.balanceGame.cardOneData += this.publisher.session.connection.data.substr(15,17)
      console.log(this.this.balanceGame.members[this.balanceGame.curMember])
    },
    cardTwoCount:function(){
      this.balanceGame.cardTwoData += this.balanceGame.members[this.balanceGame.curMember]
      console.log(this.publisher)
    },
    countTime:function(){
      this.balanceGame.totalTime = this.balanceGame.totalTime - 1
    }
  },
  mounted(){
    setInterval(this.countTime,1000)
  }

}
</script>

<style>

</style>
