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
                <div>
                  <img src="@/assets/add_soju.png" alt="bottle" @click="addSojuBottle">
                </div>
              </v-col>

              <v-col cols="4">
                <div>
                  <h2>{{ sojuGlass }}</h2>
                </div>
                <div>
                  <img src="@/assets/add_soju_glass.png" alt="glass" @click="addSojuGlass">
                </div>
              </v-col>

              <v-col cols="4">
                <div>
                  <h2>{{ beer }}</h2>
                </div>
                <div>
                  <img src="@/assets/add_beer.png" alt="beer" @click="addBeer">
                </div>
              </v-col>

            </v-row>
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
import {mapActions} from 'vuex'

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
      ...mapActions('drinking', ['addDrinking']),
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
          soju: (this.sojuBottle * 7) + this.sojuGlass,
          beer: this.beer * 3
        }
        console.log(item)
        this.addDrinking(item)
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
  transform: scale(1.2);
}
</style>
