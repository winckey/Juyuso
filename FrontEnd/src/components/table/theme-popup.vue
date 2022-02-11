<template>
  <v-dialog
    v-model="dialog"
    width="500"
  >
    <v-card class="p-3 d-flex flex-column align-center">
      <v-card-title>
        테마변경
      </v-card-title>

      <v-item-group>
        <v-row>
          <v-col
            v-for="n in 4"
            :key="n"
            col="12"
            md="6"
          >
            <v-item v-slot="{ toggle }">
              <v-card
                :style="[ backgroundToggle(n)]"
                class="theme-card d-flex justify-content-center align-center"
                dark
                height="150"
                width="210"
                @click="[toggle(), selectTheme(n)]"
              >
                <div>
                  <v-icon v-if="selected == n" size=100 color="#1CFD9F">
                    mdi-check
                  </v-icon>
                </div>
              </v-card>
            </v-item>
          </v-col>
        </v-row>
      </v-item-group>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn
          color="green darken-1"
          text
          @click="[switchTheme(), dialog = false]"
        >
          변경
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios'
import { mapState } from 'vuex'

export default {
  name: 'ThemePopup',
  props: {
    roomInfo: Object,
  },
  data: function () {
    return {
      dialog: false,
      selected: this.roomInfo.theme
    }
  },
  computed: {
    ...mapState('openviduStore', [
      'session',
      'publisher'
    ])
  },
  methods: {
    backgroundToggle(n) {
      if (this.selected == n) {
        return { backgroundImage: 'linear-gradient( rgba(255, 255, 255, 0.5), rgba(255, 255, 255, 0.5) ), '+ 'url(' + require(`@/assets/theme/${n}.jpg`) + ')' }
      }
      else {
        return { backgroundImage: 'url(' + require(`@/assets/theme/${n}.jpg`) + ')' }
      }
    },
    selectTheme(n) {
      this.selected = n
    },
    switchTheme() {
      axios({
        method: 'PATCH',
        url: `${process.env.VUE_APP_API_URL}/meeting/modify/${this.roomInfo.meetingId}`,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}`},
        data: { url: this.selected }
      })
      .then(() => {
        let data = {
          theme: this.selected,
          username: JSON.parse(this.publisher.stream.connection.data).clientData
        }
        this.session.signal({
          data: JSON.stringify(data),
          to: [],
          type: 'theme'
        })
      })
    }
  }
}
</script>

<style scoped>
  .theme-card {
    position: relative;
    background-size : cover;
    /* background-image: linear-gradient( rgba(0, 0, 0, 0.9), rgba(0, 0, 0, 0.1) )  */
  }
</style>