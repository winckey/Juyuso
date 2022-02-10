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
          <v-tab-item v-for="filter in filterType" :key="filter.name">
            <v-card flat height="300" class="d-flex flex-column align-center">
              <v-radio-group
              v-if="tab == 0"
                v-model="audioFilter"
                row>
                <v-radio 
                  v-for="(type, idx) in filter.types"
                  :key="type.name"
                  :label="type.name"
                  :value="idx">
                  </v-radio>
              </v-radio-group>
              
              <div v-if="tab == 0 && audioFilter != -1">
                {{ filter.types[audioFilter].description }}
                <div v-if="audioFilter == 0">
                  <v-slider
                    v-model="pitch"
                    step="0.2"
                    ticks
                    min="0"
                    max="2"
                    thumb-label
                  ></v-slider>
                  <div style="text-align: center">

                  {{ pitch }}
                  </div>
                </div>
              </div>
            </v-card>
          </v-tab-item>
          <!-- 비디오 필터 -->
      </v-tabs-items>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="green darken-1"
          text
          @click="[dialog = false, setFilter()]"
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
      videoFilter: -1,
      filterType: [
        {
          name: '오디오 필터', 
          types: [
            { name: 'pitch', description: '목소리의 높낮이를 조절할 수 있는 필터입니다.'},
            { name: 'audioecho', description: '목소리의 울림을 조절할 수 있는 필터입니다.'}
          ]
        },
        {
          name: '비디오 필터', 
          types: [
            { name: 'pitch', description: '목소리의 높낮이를 조절할 수 있는 필터입니다.'},
            { name: 'audioecho', description: '목소리의 울림을 조절할 수 있는 필터입니다.'}
          ]
        }
      ],
      pitch: 1,
    }
  },
  methods: {
    isFiltered: function () {
      return Object.keys(this.publisher.stream).includes('filter')
    },
    setFilter: function () {
      console.log(this.publisher)
      if (this.isFiltered()) {
        this.removeFilter()
      }
      this.publisher.stream.applyFilter('GStreamerFilter', {"command": `pitch pitch=${this.pitch}`})
      console.log(this.publisher)
    },
    removeFilter: function () {
      this.publisher.stream.removeFilter()
      console.log(this.publisher)
    }
  }
}
</script>

<style>

</style>