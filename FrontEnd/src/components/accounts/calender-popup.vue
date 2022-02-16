<template>
    <div>
        <v-dialog max-width="650px" max-height="650px" v-model="dialog">
            <template v-slot:activator="{ on, attrs }">
                <v-img src="@/assets/Group 57.png" alt="calendar"
                    v-bind="attrs"
                    v-on="on"></v-img>
            </template>

            <v-card width="99%" height="100%">
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
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import axios from 'axios'
import {mapState} from 'vuex'

export default {
    data:() => ({
        dialog: false,
        type: 'month',
        value: null,
        todayChecked: false,
        events: [],
    }),
    props: {
        user: Object
    },
    computed: {
        ...mapState('accounts', {stateUser:'user'}),
        isFriend: function () {
            if (this.user.id != this.stateUser.id) {
                return true
            } else {
                return false 
            }
        },
        getUrl: function () {
            if (this.isFriend) {
                return `${process.env.VUE_APP_API_URL}/attendance?friendId=${this.user.id}`
            } else {
                return `${process.env.VUE_APP_API_URL}/attendance`
            }
        }
    },
    created() {
        this.value = this.getTodayDate()
        axios.get(this.getUrl, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(({ data }) => {
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
            })
 
    },


    methods: {
        onChange(e) {
            const { year, month } = e
            
            let url = `${process.env.VUE_APP_API_URL}/attendance/${year}/${month}`
            if (this.isFriend) {
                url = `${process.env.VUE_APP_API_URL}/attendance/${year}/${month}?friendId=${this.user.id}`
            } 

            axios.get(url, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(({ data }) => {
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
            })
        },
        onBtnClick() {
            axios.post(`${process.env.VUE_APP_API_URL}/attendance`, {}, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`
                }
            }).then(() => {
                this.events.push({
                    name: '🍺출석완료🍺',
                    start: this.getTodayDate(),
                    color: '#4DB6AC'
                })

                this.todayChecked = true;
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
