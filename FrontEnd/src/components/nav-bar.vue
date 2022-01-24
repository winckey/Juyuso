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
      <span class="my-auto">
      <v-text-field
        v-model="searchInput"
        class="shrink my-2" 
        solo
        clearable
        rounded
        dense
        style="width: 300px"
      ></v-text-field>
      </span>
      <v-btn icon dark>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
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
    <div class="grey">
      <div>
        <side-bar-profile></side-bar-profile>
      </div>
      <div class="m-2">
        <friend-search></friend-search>
      </div>
      <div>
        <friend-list-menu></friend-list-menu>
      </div>
      <div>
        <friend-list
          class="list-size">
        </friend-list>
      </div>
    </div>
    
      <!-- <v-list
        nav
        dense
      >
        <v-list-item-group
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-title>profile</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title><friend-search></friend-search></v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title><friend-list-menu></friend-list-menu></v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title><friend-list></friend-list></v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list> -->
    </v-navigation-drawer>
    
  </div>
</template>

<script>
import FriendListMenu from '@/components/side_bar/friend-list-menu.vue'
import FriendList from '@/components/side_bar/friend-list.vue'
import FriendSearch from '@/components/side_bar/friend-search.vue'
import SideBarProfile from '@/components/side_bar/side-bar-profile.vue'

export default {
  name: 'NavBar',
  components:{
    FriendListMenu,
    FriendList,
    FriendSearch,
    SideBarProfile
  },
  data: function () {
    return {
      searchInput: null,
      drawer: false,
    }
  },
  methods: {
    goToTableList: function() {
      this.$router.push({ name: 'TableList' })
    }
  }

}
</script>

<style scoped>
  .search-bar {
    width: 200px;
    background: white;
  }

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
