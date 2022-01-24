<template>
    <div>
        <v-card
            class="p-3"
            >
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
                        박씨네
                        </v-list-item-title>
                        <v-list-item-subtitle>자기소개를 자유롭게 입력합니다. 나는 멋쟁이</v-list-item-subtitle>
                    </v-list-item-content>  
                </div>

                <v-card-actions>
                <v-btn @click="goMyPage"
                    plain>
                    마이페이지
                </v-btn>
                <v-btn class="logout-btn"
                    plain>
                    로그아웃
                </v-btn>
                </v-card-actions>
            </div>

        </v-card>
    </div>
</template>

<script>
export default {
    methods:{
        goMyPage: function () {
            this.$router.push({name: 'MyPage'})
            },        
    }

}
</script>

<style>
.card-style{
    text-align: center;
}
.logout-btn{
    float: right;
    text-align: right;
}
</style>
