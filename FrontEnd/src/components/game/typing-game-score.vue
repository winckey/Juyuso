<template>
  <div v-if="streamManager" class="d-flex flex-column justify-content-center">
    
    <p>{{ score }}</p>
    <div style="position:relative">
        <img src="@/assets/basic_profile.png" width="10%" alt="score" :style="nowScore" style="position:absolute; z-index:2; top:-900%;" class="typing-image">
        <v-progress-linear
        indeterminate
        color="#4DB6AC"
        style="z-index:1;"
        ></v-progress-linear>
    </div>
  </div>
</template>

<script>
export default {
    name: 'TypingGameScore',
    props: {
        streamManager: Object,
        score: Number
    },
    computed: {
        nowScore: function () {
            return {'left':`${this.score/6*100}%`}
        }
    }

}
</script>

<style scoped>
.typing-image {
    animation:motion 0.5s linear 0s infinite alternate; 
    margin-top: 0;
}
        
@keyframes motion {
	0% {margin-top: 0px;}
	100% {margin-top: 5px;}
}

</style>