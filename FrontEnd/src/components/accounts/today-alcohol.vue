<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="650px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="#4DB6AC"
          dark
          v-bind="attrs"
          v-on="on"
          rounded
          x-large
        >
          오늘 주량 추가
        </v-btn>
      </template>

      <v-alert
      :value="isSuccess"
        dense
        type="success"
      >
        저장되었습니다!
      </v-alert>


      <v-card class="p-2" >
        <v-card-title class="justify-content-center">
          <span class="h2">오늘 주량 추가</span>
        </v-card-title>
        <v-spacer></v-spacer>

        <v-card-text>
          <v-container>
            <v-row >

              <v-col cols="4">
                <div>
                  <h2>{{ sojuBottle }}</h2>
                </div>
                <div class="img-container">
                  <img src="@/assets/add_soju.png" alt="bottle" @click="addSojuBottle">
                </div>
              </v-col>

              <v-col cols="4">
                <div>
                  <h2>{{ sojuGlass }}</h2>
                </div>
                <div class="img-container">
                  <img src="@/assets/add_soju_glass.png" alt="glass" @click="addSojuGlass">
                </div>
              </v-col>

              <v-col cols="4">
                <div>
                  <h2>{{ beer }}</h2>
                </div>
                <div class="img-container">
                  <img src="@/assets/add_beer.png" alt="beer" @click="addBeer">
                </div>
              </v-col>

            </v-row>
            <div class="mt-5">
              <h4 class="d-flex justify-content-center">주량 계산 = 소주 {{ sojuBottle }}병 * 7 + 소주 {{ sojuGlass }}잔 + 맥주 {{ beer }}캔 * 3</h4>
            </div>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="[resetCount(), dialog = false, isSuccess=false]"
          >
            닫기
          </v-btn>

          <v-btn
            color="blue darken-1"
            text
            @click="resetCount"
          >
            초기화
          </v-btn>


          <v-btn
            color="blue darken-1"
            text
            @click="drinking"
          >
            저장
          </v-btn>
        </v-card-actions>


        </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import {mapState} from 'vuex'

import axios from 'axios'

export default {
    name: 'TodayAlcohol',
    data: function () {
        return {
            dialog: false,
            isSuccess: false,
            sojuBottle: 0,
            sojuGlass: 0,
            beer: 0
        }
    },
    methods: {
     
      addSojuBottle: function (){
        this.sojuBottle += 1
      },
      addSojuGlass: function (){
        this.sojuGlass += 1
      },
      addBeer: function (){
        this.beer += 1
      },
      resetCount: function (){
        this.sojuBottle = 0
        this.sojuGlass = 0
        this.beer = 0
      },
      drinking: function () {
        const item = {
          beer: this.beer * 3,
          date: this.endDate, 
          soju: (this.sojuBottle * 7) + this.sojuGlass
        }
        
        axios({
                method: 'POST',
                url: `${process.env.VUE_APP_API_URL}/drinking/history`,
                headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}` },
                data: item
            })
                .then(res => {
                    console.log(res)
                    const emitItem = {
                      date: this.endDate,
                      count: (this.sojuBottle * 7) + this.sojuGlass + this.beer * 3
                    }
                    this.$emit('updateDrinkingInfo', emitItem)
                })
                // .catch(err => {
                //     console.log(err)
                // })
      }
    },
    computed: {
      ...mapState('accounts', ['user']),
      endDate: function () {
      var today = new Date();
      var year = today.getFullYear();
      var month = ('0' + (today.getMonth() + 1)).slice(-2);
      var day = ('0' + today.getDate()).slice(-2);
      var dateString = year + '-' + month  + '-' + day;   
      return dateString
      }
    }
}
</script>

<style scoped>

div h2 {
  display: flex;
  justify-content: center;
  padding: 10px;
}

div img {
  display: block;
  margin: auto;
  cursor: pointer;
}

div img:hover {
  transform: scale(1.3);
}

.img-container {
  height: 322px;
  display: flex;
}
</style>
