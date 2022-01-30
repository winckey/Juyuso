<template>
  <div oncontextmenu="return false">
    <v-app-bar
      absolute
      color="#11111f"
    >
    <button class="h-100 sub-logo" @click="goToTableList">
      <v-img 
        src="@/assets/sub_logo.png"
        contain
        height="100%"
        alt=""/>
    </button>
      <v-spacer></v-spacer>
      <table-search/>
      <v-btn icon dark>
        <v-icon>mdi-bell-outline</v-icon>
      </v-btn>
      <v-app-bar-nav-icon dark @click.stop="drawer=!drawer"></v-app-bar-nav-icon>

    </v-app-bar>

    <!-- 사이드 바 -->
    <v-navigation-drawer
      oncontextmenu="return false"
      width="400"
      v-model="drawer"
      absolute
      right
      temporary
    >
      
    <div class="grey lighten-4" @contextmenu.prevent>
      <div>
        <side-bar-profile></side-bar-profile>
      </div>
      <div>
        <!-- 검색창 -->
        <div>
          <v-text-field
            class="shrink my-2"
            solo
            rounded
            dense
            v-model="searchInput"
            @keyup.enter="friendsSearch">   
            </v-text-field>           
        </div>
      </div>
      <!-- <friend-tab
        ref="FriendTab"></friend-tab> -->
      <FriendTab ref="Friendtab"/>
    </div>
    </v-navigation-drawer>
    
  </div>
</template>

<script>

import TableSearch from '@/components/table_list/table-search.vue'
import SideBarProfile from '@/components/side_bar/side-bar-profile.vue'
import FriendTab from '@/components/side_bar/friend-tab.vue'
import { mapActions } from 'vuex'

export default {
  name: 'NavBar',
  components:{
    FriendTab,
    TableSearch,
    SideBarProfile
  },
  data: function () {
    return {
      searchInput: null,
      drawer: false,
      username:'',
    }
  },
  methods: {
    ...mapActions('friends', [
      'friendList',
      'searchUserData'
    ]),
    goToTableList: function() {
      this.$router.push({ name: 'TableList' })
    },
    friendsSearch: function(){
      if (this.searchInput != null) {
        console.log(this.searchInput)
        this.searchUserData(this.searchInput)
        this.$refs.Friendtab.tab = 2
      }
    },
  },
  watch: {
    drawer: function (val) {
      const token = localStorage.getItem('jwt') ? true : false
      if (token && val) {
        this.friendList()
      }
    } 
  }
}
</script>

<style scoped>
  .sub-logo {
    position: absolute;
    left: 50%;
    top: 10%;
    transform: translate(-50%);
  }

  .list-size {
    width: 100vw;
    height: 100vh;
  }
</style>