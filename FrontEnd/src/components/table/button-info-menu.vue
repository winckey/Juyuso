<template>
  <v-menu
    bottom
    v-model="menu"
    :close-on-content-click="false"
    offset-y
  >
    <template v-slot:activator="{ on: menu, attrs }">
      <v-tooltip bottom :open-on-click="false" :open-on-focus="false">
        <template v-slot:activator="{ on: tooltip }">
          <v-btn 
            icon 
            dark
            v-bind="attrs"
            v-on="{ ...tooltip, ...menu }">
            <v-icon>mdi-gesture-tap-button</v-icon>
          </v-btn>
        </template>
        <span>버튼 정보 확인</span>
      </v-tooltip>
    </template>


    <v-card
      width="500"
      class="card-background"
      >
        <div class="inner-card-background p-2">
          <v-card-text>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <v-icon dense>mdi-volume-high</v-icon>
              </v-btn>
              마이크를 끄고 켤 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <v-icon dense>mdi-camera-outline</v-icon>
              </v-btn>
              카메라를 끄고 켤 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <v-icon dense>mdi-bullhorn-outline</v-icon>
              </v-btn>
              다른 방과 소통할 수 있는 확성기 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <v-icon dense>mdi-movie-filter</v-icon>
              </v-btn>
              음성변조, 비디오 필터를 적용할 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <v-icon dense>mdi-chat-processing</v-icon>
              </v-btn>
              현재 같은 방에 있는 사람들과 채팅을 할 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                color="blue darken-2"
                fab
                dark
                small>
                <v-icon dense>mdi-controller-classic-outline</v-icon>
              </v-btn>
              다양한 게임을 할 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <v-icon dense>mdi-compare</v-icon>
              </v-btn>
              현재 방에 적용되어있는 테마(배경화면)을 바꿀 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                color="error"
                dark
                small>
                <v-icon dense>mdi-application-export</v-icon>
              </v-btn>
              방을 나갈 수 있는 버튼입니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <img src="@/assets/beer_icon.png" style="width: 30px">
              </v-btn>
              맥주마신 양을 기록할 수 있습니다. 술 커밋 기록에 적용됩니다.
            </div>
            <div>
              <v-btn
                class="mr-3 my-1"
                fab
                small>
                <img src="@/assets/soju_icon.png" style="width: 30px">
              </v-btn>
              소주마신 양을 기록할 수 있습니다. 술 커밋 기록에 적용됩니다.
            </div>
          </v-card-text>
        </div>
      </v-card>
  </v-menu>
</template>

<script>
export default {
  name: 'ButtomInfoMenu',
  data: function () {
    return {
      menu: false,
      showPassword: false,
      showCopyText: false
    }
  },
}
</script>

<style scoped>
  .card-background {
    background-size: cover
  }
  
  .inner-card-background {
    background: rgba(255, 255, 255, 0.93);
    height: 100%
  }
  .copy-text {
    color: #878787;
    transition-property: all;
    transition-duration: .5s;
  }

</style>