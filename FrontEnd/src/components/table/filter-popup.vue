<template>
  <v-dialog
    v-model="dialog"
    max-width="500"
  >
    <v-card>
      <v-toolbar
        color="cyan"
        dark
        flat
      >
        <v-toolbar-title>필터 적용</v-toolbar-title>

        <v-spacer></v-spacer>
        <template v-slot:extension>
          <v-tabs
            v-model="tab"
            fixed-tabs
          >
          
            <v-tabs-slider color="yellow"></v-tabs-slider>

            <v-tab
              v-for="filter in filterType"
              :key="filter.name"
            >
              {{ filter.name }}
            </v-tab>
          </v-tabs>
        </template>
      </v-toolbar>
        <v-tabs-items v-model="tab">
          <v-tab-item>
            <v-card flat height="300" class="d-flex flex-column align-center">
              <v-radio-group
              v-if="tab == 0"
                v-model="audioFilter"
                row>
                <v-radio 
                  v-for="(type, idx) in filterType[tab].types"
                  :key="type.name"
                  :label="type.name"
                  :value="idx">
                  </v-radio>
              </v-radio-group>
              
              <div v-if="tab == 0 && audioFilter != -1">
                {{ filterType[tab].types[audioFilter].description }}
                <div v-if="audioFilter == 0">
                  <v-slider
                    v-model="pitch"
                    step="0.1"
                    ticks
                    min="0.6"
                    max="1.6"
                    thumb-label
                  ></v-slider>
                  <div style="text-align: center">

                  높낮이 : {{ pitch }}
                  </div>
                </div>
                <div v-else-if="audioFilter == 1">
                  <v-slider
                    v-model="pitch"
                    step="0.1"
                    ticks
                    min="0.2"
                    max="0.9"
                    thumb-label
                  ></v-slider>
                  <div style="text-align: center">
                  강도 : {{ pitch }}
                  </div>
                </div>
              </div>
            </v-card>
          </v-tab-item>
          <!-- 비디오 필터 -->
          <v-tab-item>
            <v-card flat height="500" class="d-flex flex-column align-center">
              <v-item-group>
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-3" v-for="filter in filterType[tab].types" :key="filter.name">
                      <v-item v-slot="{ active, toggle }">
                        <v-card
                          width="100"
                          :style="[ backgroundToggle(filter)]"
                          :color="active ? 'primary' : ''"
                          @click="[toggle(), setVideoFilter(filter)]"
                          class="filter-card d-flex justify-content-center align-end"
                          height="100">
                          <div class="filter-name">
                            <span v-if="videoFilter != filter">
                              {{ filter.name }}
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
              
            </v-card>
          </v-tab-item>
      </v-tabs-items>
      <v-card-subtitle>*필터는 최대 1개만 적용가능합니다</v-card-subtitle>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          v-show="tab == 0"
          color="green darken-1"
          text
          @click="[dialog = false, setAudioFilter()]"
        >
          적용
        </v-btn>
        <v-btn
          v-show="isFiltered()"
          color="green darken-1"
          text
          @click="[dialog = false, removeFilter()]"
        >
          필터 해제
        </v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'FilterPopup',
  computed: {
    ...mapState('openviduStore', [
      'publisher'
    ])
  },
  data: function () {
    return {
      tab: 0,
      radios:0,
      dialog: false,
      audioFilter: -1,
      videoFilter: null,
      filterType: [
        {
          name: '음성변조', 
          types: [
            { name: '헬륨가스', description: '목소리의 높낮이를 조절할 수 있는 필터입니다.'},
            { name: '에코', description: '목소리의 울림을 조절할 수 있는 필터입니다.'}
          ]
        },
        {
          name: '비디오 이펙트', 
          types: [
            { name: 'bulge', img: 'bulge'},
            { name: 'dicetv', img: 'dicetv'},
            { name: 'agingtv', img: 'agingtv'},
            { name: 'optv', img: 'optv'},
            { name: 'quarktv', img: 'quarktv'},
            { name: 'radioactv', img: 'radioactv'},
            { name: 'revtv', img: 'revtv'},
            { name: 'rippletv', img: 'rippletv'},
            { name: 'shagadelictv', img: 'shagadelictv'},
            { name: 'streaktv', img: 'streaktv'},
            { name: 'vertigotv', img: 'vertigotv'},
            { name: 'warptv', img: 'warptv'},
            { name: 'twirl', img: 'twirl'},
            { name: 'square', img: 'square'},
            { name: 'mirror', img: 'mirror'},
            { name: 'pinch', img: 'pinch'},
          ]
        }
      ],
      pitch: 1,
    }
  },
  methods: {
    backgroundToggle(filter) {
      if (this.videoFilter == filter) {
        return { backgroundImage: 'linear-gradient( rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.5) ), url(' + require(`@/assets/filter/${filter.img}.png`) + ')' }
      }
      else {
        return { backgroundImage: 'linear-gradient( rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.1) ), url(' + require(`@/assets/filter/${filter.img}.png`) + ')' }
      }
    },
    isFiltered: function () {
      return Object.keys(this.publisher.stream).includes('filter')
    },
    setAudioFilter: function () {
      console.log(this.publisher)
      this.videoFilter = null
      if (this.isFiltered()) {
        this.removeFilter()
      }
      if (this.audioFilter == 0) {
        this.publisher.stream.applyFilter('GStreamerFilter', {"command": `pitch pitch=${this.pitch}`})
      }
      else {
        this.publisher.stream.applyFilter('GStreamerFilter', {"command": `audioecho delay=40000000 intensity=${this.pitch} feedback=0.7`})

      }
      console.log(this.publisher)
    },
    setVideoFilter: function (filter) {
      this.audioFilter = -1
      this.videoFilter = filter
      if (this.isFiltered()) {
        this.removeFilter()
      }
      this.publisher.stream.applyFilter('GStreamerFilter', {"command": `${filter.name}`})
      console.log(this.publisher)
    },
    removeFilter: function () {
      this.publisher.stream.removeFilter()
      console.log(this.publisher)
    }
  }
}
</script>

<style scoped>
  .filter-card {
    background-size : cover;
  }
  .filter-name {
    color: white;
  }

</style>