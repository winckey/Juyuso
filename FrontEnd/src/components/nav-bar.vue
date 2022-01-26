<template>
  <div>
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
      v-model="drawer"
      absolute
      right
      temporary
    >
    <div class="grey lighten-4">
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
                v-model="username"
                @keyup.enter="[friendsSearch(), openSearchList()]">   
                </v-text-field>           
        </div>
      </div>
      <div>
        <div>
            <v-col
                align="center"
                justify="space-around">
                <v-btn text
                  @click="openFriendsList">
                    <img src="@/assets/friends.png" alt="">
                </v-btn>
                <v-btn text
                  @click="openChat">
                    <img src="@/assets/chat.png" alt="">
                </v-btn>
                <v-btn text
                  @click="openSearchList">
                    <img src="@/assets/search.png" alt="">
                </v-btn>
            </v-col>
        </div>
      </div>
      <div v-if="menu=='friendList'">
        <friend-list
          class="list-size">
        </friend-list>
      </div>
      <div v-else-if="menu=='chat'">
        <div>chat list</div>
      </div>
      <div v-else-if="menu=='searchList'">
        <div>searchList</div>
      </div>
    </div>
    </v-navigation-drawer>
    
  </div>
</template>

<script>

import TableSearch from '@/components/table_list/table-search.vue'
import FriendList from '@/components/side_bar/friend-list.vue'
import SideBarProfile from '@/components/side_bar/side-bar-profile.vue'

export default {
  name: 'NavBar',
  components:{

    TableSearch,
    FriendList,
    SideBarProfile
  },
  data: function () {
    return {
      searchInput: null,
      drawer: false,
      menu:'friendList',
      username:'',
    }
  },
  methods: {
    goToTableList: function() {
      this.$router.push({ name: 'TableList' })
    },
    friendsSearch: function(){
      const username = this.username
      console.log(username)
      this.$store.dispatch('searchUserData',username)
    },
    openFriendsList:function(){
      this.menu = 'friendList'
      console.log(this.menu)
    },
    openChat:function(){
      this.menu = 'chat'
      console.log(this.menu)
    },
    openSearchList:function(){
      this.menu = 'searchList'
      console.log(this.menu)
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
