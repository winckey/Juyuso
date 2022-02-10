<template>
    <div>
        <v-row>
            <v-col>
                <v-sheet height="70">
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
                        <div v-if="!isFriend">
                            <v-btn v-if="!todayChecked" @click="onBtnClick">출석체크</v-btn>
                        </div>
                    </v-toolbar>
                </v-sheet>
                <v-sheet height="450" max-height="100%">
                    <v-calendar
                        ref="calendar"
                        v-model="value"
                        :type="type"
                        :events="events"
                        @moved="onChange"
                    >
                        
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
import {mapState} from 'vuex'

export default {
    data:() => ({
        type: 'month',
        value: null,
        todayChecked: false,
        events: [],
        isFriend: false
    }),
    props: {
        user: Object
    },
    computed: {
        ...mapState('accounts', {stateUser:'user'})
    },
    mounted() {
        this.value = this.getTodayDate()

        axios.get(`${process.env.VUE_APP_API_URL}/attendance`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(({ data }) => {
                console.log(data)
                const events = []

                data.attendanceList.forEach( ({ date }) => {
                    events.push({
                        name: '🍺출석완료🍺',
                        start: date,
                        color: '#4DB6AC'
                    })

                    if (date === this.getTodayDate()) this.todayChecked = true;
                })

                this.events = events
            }).catch(err => {
                console.log(err)
            })

        if (this.user.id != this.stateUser.id) {
            this.isFriend = true 
        } 
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
                        name: '🍺출석완료🍺',
                        start: date,
                        color: '#4DB6AC'
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
                    color: '#4DB6AC'
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
