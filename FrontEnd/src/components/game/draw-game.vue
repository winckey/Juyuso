<template>
  <div>
    <div class="tool-box">
      <v-tooltip right color="white">
        <template v-slot:activator="{ on, attrs }">
          <v-menu
            v-on="on"
            v-bind="attrs"
            v-model="colorDialog"
            :close-on-content-click="false"
            :nudge-width="200"
            offset-x
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="m-1"
                  fab
                  :color="color"
                  dark
                  v-bind="attrs"
                  v-on="on"
                  inline
                >
                <v-icon :elevation="8" :color="iconColor">mdi-palette</v-icon>
                </v-btn>
              </template>

              <v-card>
                <v-card-title>
                  펜 색 정하기
                </v-card-title>
                <v-color-picker
                  v-model="color"
                  dot-size="25"
                  swatches-max-height="200"
                ></v-color-picker>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="green darken-1"
                    text
                    @click="dialog = false"
                  >
                    CLOSE
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </template>
          <span>펜 색상</span>
      </v-tooltip>
      
      <v-menu 
        :close-on-content-click="false" 
        :nudge-width="200" 
      offset-y>
        <template v-slot:activator="{on}">
          <v-btn
            class="m-1"
            fab 
            dark
            :color="color" 
            v-on="on">
            <v-icon :color="iconColor">mdi-pencil</v-icon>
          </v-btn>
        </template>

        <v-card height="70" class="d-flex align-end">
          <v-slider label="굵기" min="1" max="20" height="12" tick-size="6" class="pt-5 px-3" thumb-label :thumb-size="24" v-model="penWidth"></v-slider>
        </v-card>
      </v-menu>
      <v-btn
        @click="erase = !erase"
        class="m-1"
        fab 
        dark
        :color="color">
        <v-icon :color="iconColor">{{ erase ? 'mdi-close' : 'mdi-delete' }}</v-icon>
      </v-btn>
    </div>
    <div class="game-box">
      <canvas
        id="canvas"
        ref="canvas"
        @mousedown.left="initDraw"
        @mouseup.left="finishDraw"
        @mousemove="draw"
        @mouseout="finishDraw"
        width=1200;
        height=550;
      ></canvas>
      <div class="video-grid">
        <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import UserVideo from '@/components/table/user-video.vue'

export default {
  name: 'DrawGame',
  components: {
    UserVideo
  },
  props: {
    subscribers: Array,
    publisher: Object,
  },
  data: function () {
    return {
      colorDialog: false,
      color: '#000000',
      penWidth: '2',
      canvas: null,
      context: null,
      drawable: false,
      erase: false,
      pos: {
        x1: -1,
        y1: -1,
        x2: -1,
        y2: -1,
      }
    }
  },
  computed: {
    ...mapState('openviduStore', ['session']),
    iconColor: function () {
      if (this.color) {
        let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(this.color);
        let r= parseInt(result[1], 16);
        let g= parseInt(result[2], 16);
        let b= parseInt(result[3], 16);
        if (r >= 190 && g >= 190 && b >= 190) {
          return 'black'
        }
        else {
          return 'white'
        }
      }
      else {
        return null
      }
    }
  },
  mounted: function () {
    this.canvas = document.getElementById('canvas')
    this.context = document.getElementById('canvas').getContext("2d")
    this.drawSession()
  },
  methods: {
    drawSession: function () {
      this.session.on('signal:draw', (event) => {
        let data = JSON.parse(event.data)
        if (data.erase) {

          this.context.clearRect(data.x1 < data.x2 ? data.x1 : data.x2, data.y1 < data.y2 ? data.y1 : data.y2, 20, 20);
        }
        else {
          this.context.lineWidth = data.penWidth;
          this.context.lineCap = "round";
          this.context.strokeStyle = data.color;
          this.context.beginPath();
          this.context.moveTo(data.x1, data.y1);
          this.context.lineTo(data.x2, data.y2);
          this.context.stroke();
        }
      })
    },
    initDraw: function (event) {
      this.drawable = true;
      this.pos.x1 = event.offsetX;
      this.pos.y1 = event.offsetY;
      this.pos.x2 = this.pos.x1 + 1
      this.pos.y2 = this.pos.y1 + 1
      let data = {
        penWidth: this.penWidth,
        color: this.color,
        erase: this.erase,
        ...this.pos
      }
      this.session.signal({
        data: JSON.stringify(data),
        to: [],
        type: 'draw'
      });
    },
    
    draw: function (event) {
      if (this.drawable) {
        this.pos.x2 = event.offsetX;
        this.pos.y2 = event.offsetY;

        let data = {
          penWidth: this.penWidth,
          color: this.color,
          erase: this.erase,
          ...this.pos
        }
        this.session.signal({
          data: JSON.stringify(data),
          to: [],
          type: 'draw'
        });
        this.pos.x1 = this.pos.x2
        this.pos.y1 = this.pos.y2
      }
    },
    finishDraw: function () {
      this.drawable = false;
    },
  },
}
</script>

<style scoped>
  #canvas {
    position: absolute;
    background: rgb(97, 97, 97, 0);
    z-index: 2;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%)
  }
  .game-box {
    height: 81vh;
    width: 85vw;
    position: relative;
  }
  .tool-box {
    position: fixed;
    width: 64px;
    top: 10vh;
    left: 20px;
    height: 200px;
    background: rgb(255, 255, 255);
    z-index: 2;
    border-radius: 10px;
    box-shadow: 0.5px 0.5px 4px 0px rgb(192, 192, 192);
  }

  .video-grid {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    display: grid;
    width: 1200px;
    height: 550px;
    grid-template-columns: 400px 400px 400px;
    grid-template-rows: 275px 275px;
    background: white
  }
</style>