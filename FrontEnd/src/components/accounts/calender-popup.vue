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
                        @click="$refs.calendar.prev()">
                        <v-icon small>
                            mdi-chevron-left
                        </v-icon>
                    </v-btn>
                    <v-btn
                        fab
                        text
                        small
                        color="grey darken-2"
                        @click="$refs.calendar.next()">
                        <v-icon small>
                            mdi-chevron-right
                        </v-icon>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn v-if="!todayChecked" @click="onBtnClick">출석체크</v-btn>
                </v-toolbar>
                <v-sheet height="500">
                    <v-calendar
                        ref="calendar"
                        v-model="value"
                        :type="type"
                        :events="events"
                        @moved="onChange"
                    >
                        <!-- <template
                            :stamp="stamp">
                            <div>
                                <p>text</p>
                                {{ stamp }}
                            </div>
                        </template> -->
                    </v-calendar>
                </v-sheet>

            </v-col>
        </v-row>
    </div>
</template>

<script>
import axios from 'axios'
/**
 * ## 추가 구현 사항
 * 1. 현재 월달력에서 이전, 다음달 날짜 클릭 막기 or 이벤트 막기 (타이틀 옆 화살표 버튼으로만 이동)
 *  --> 현재 moved 이벤트 (화살표 버튼 클릭 이벤트) 처리만 되어 있으므로 달력 내에서 이전/다음달 날짜 클릭시 이벤트 처리가 안되어 있음
 *  --> 다음/이전 달 클릭 이벤트 처리를 막든가 or 다음/이전달 클릭시에도 출첵 불러올 수 있도록 추가 처리 하든가
 * 2. 
 */
export default {
    data:() => ({
        type: 'month',
        value: null,
        todayChecked: false,
        events: [],
    }),
    mounted() {
        this.value = this.getTodayDate()

        axios.get(`${process.env.VUE_APP_API_URL}/attendance`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(({ data }) => {
                console.log(this.value)
                console.log(data)
                const events = []

                data.attendanceList.forEach( ({ date }) => {
                    events.push({
                        name: '출석체크',
                        start: date,
                        color: 'blue'
                    })

                    if (date === this.getTodayDate()) this.todayChecked = true;
                })

                this.events = events
            }).catch(err => {
                console.log(err)
            })
    },
    methods: {
        onChange(e) {
            const { year, month } = e

            axios.get(`${process.env.VUE_APP_API_URL}/attendance/${year}/${month}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(({ data }) => {
                console.log(this.value)
                console.log(data)
                const events = []

                data.attendanceList.forEach( ({ date }) => {
                    events.push({
                        name: '출석체크',
                        start: date,
                        color: 'blue'
                    })

                    if (date === this.getTodayDate()) this.todayChecked = true;
                })

                this.events = events
            }).catch(err => {
                console.log(err)
            })
        },
        onBtnClick() {
            axios.post(`${process.env.VUE_APP_API_URL}/attendance`, {}, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(() => {
                this.events.push({
                    name: '출석체크',
                    start: this.getTodayDate(),
                    color: 'blue'
                })

                this.todayChecked = true;
            }).catch(err => {
                console.log(err)
            })
        },
        getTodayDate() {
            const date = new Date();
            const year = date.getFullYear();
            const month = ("0" + (1 + date.getMonth())).slice(-2);
            const day = ("0" + date.getDate()).slice(-2);
            return `${year}-${month}-${day}`;
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
