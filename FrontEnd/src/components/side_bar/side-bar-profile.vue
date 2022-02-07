<template>
    <div>
        <v-card
            class="p-3"
            v-if="isLogin">
            <div class="card-style">
                <div>
                    <v-avatar
                        class="grey"
                        size="80"
                    ></v-avatar>
                </div>
                <div>
                    <v-list-item-content>
                        <v-list-item-title class="text-h5 mb-1">
                            <p>{{ user.nickname }}</p>
                        </v-list-item-title>
                        <v-list-item-subtitle>자기소개: {{ user.description }}</v-list-item-subtitle>
                    </v-list-item-content>  
                </div>

                <v-card-actions class="d-flex justify-content-evenly">
                    <div class="mypage">
                        <v-btn @click="goMyPage"
                            plain>
                            마이페이지
                        </v-btn>
                    </div>
                    <div class="logout">
                        <v-btn
                            plain
                            @click="logout">
                            로그아웃
                        </v-btn>
                    </div>
                </v-card-actions>
            </div>
        </v-card>
        <v-card
            v-else
            class="card-style">
            <v-btn
                class="m-3 p-3"
                @click="goToLogin">로그인해주세요</v-btn>
        </v-card>
    </div>
</template>

<script>
import axios from 'axios'
// import { mapGetters } from 'vuex'

export default {
    data(){
        return{
            isLogin: false,
            user : ''
        }
    },
    methods:{
        goMyPage: function () {
            this.$router.push({name: 'MyPage', params: {userId: this.user.id}})
            },
        goToLogin: function () {
            this.$router.push({ name: 'Login' })
            },
        logout: function(){
            console.log('isLogin')
            this.isLogin = false
            localStorage.removeItem('jwt')
            this.$router.push({ name: 'Main' })    
        },
        setToken : function(){
            const token = localStorage.getItem('jwt')
            const config = {
                Authorization: `Bearer ${token}`
            }
            return config
        },
    },
    created: function(){
        axios({
            method: 'get',
<<<<<<< HEAD
            url: `${process.env.VUE_APP_API_URL}/users/info`,
=======
            url: `${process.env.VUE_APP_API_URL}/users/me`,
>>>>>>> 05f0408c998e9e18f806b1d47dbc401c28b08cf4
            headers: this.setToken()
        })
            .then(res =>{
                console.log(res.data.user)
                const userInfo = res.data.user
                this.user = userInfo
            })
            .catch(err => {
                console.log(err)
            })
        const token = localStorage.getItem('jwt')
        if(token){
            this.isLogin=true
        }
    },

}
</script>

<style scoped>
.card-style{
    text-align: center;
}
.logout-btn{
    float: right;
    text-align: right;
}
/* .mypage{
    position :relative;
}
.logout{
    position : relative;top: -36px;
    float: right;
    text-align: right;
} */
</style>
