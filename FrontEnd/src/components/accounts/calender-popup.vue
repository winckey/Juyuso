<template>
    <div>
        <v-row>
            <v-col cols="12">
                <v-toolbar>
                    <div>
                        <v-toolbar-title
                            v-if="$refs.calendar">
                            {{ $refs.calendar.title }}
                        </v-toolbar-title>
                    </div>
                    <v-btn
                        fab
                        text
                        small
                        color="grey darken-2"
                        @click="prev">
                        <v-icon small>
                            mdi-chevron-left
                        </v-icon>
                    </v-btn>
                    <v-btn
                        fab
                        text
                        small
                        color="grey darken-2"
                        @click="next">
                        <v-icon small>
                            mdi-chevron-right
                        </v-icon>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn @click="attendence">출석체크하기</v-btn>
                </v-toolbar>
                <v-sheet height="500">
                    <v-calendar
                        ref="calendar">
                        <template
                            :stamp="stamp">
                            <div>
                                <p>text</p>
                                {{ stamp }}
                            </div>
                        </template>
                    </v-calendar>
                </v-sheet>

            </v-col>
        </v-row>
    </div>
</template>

<script>
  export default {
      data:() => ({
          stamp:[],
      }),
      mounted(){
          this.$refs.calendar.checkChange()
      },
      methods:{
          prev(){
              this.$refs.calendar.prev()
          },
          next(){
              this.$refs.calendar.next()
          },
          attendence(){
              this.stamp.push(new Date());
              console.log(this.stamp)
          }
      }
  }
</script>

<style scoped>
.custom-calendar{
    width: 80%;
    align-content: center;
}
</style>
