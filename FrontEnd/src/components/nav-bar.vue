<template>
  <div>
    <v-app-bar
      absolute
      color="#11111f"
    >
    <button class="h-100 sub-logo">
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
      <v-list
        nav
        dense
      >
        <v-list-item-group
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item>
            <v-list-item-title>Foo</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Bar</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Fizz</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-title>Buzz</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: 'NavBar',
  data: function () {
    return {
      searchInput: null,
      drawer: false,
    }
  },
  methods: {
    goToTables: function() {
      this.$router.push({ name: 'Tables' })
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
</style>
