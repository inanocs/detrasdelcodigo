<template>
  <div id="app">
    <v-app>
      <Navbar />
      <router-view />
      <Footer />
    </v-app>
  </div>
</template>

<script>
import Navbar from "@/components/NavBar.vue";
import Footer from "@/components/Footer.vue";

export default {
  components: { Navbar, Footer },

  created() {
    this.$store.dispatch("dispatchAuthUser");
  },
};
</script>

<style lang="scss">
body {
  width: 100%;
  display: flex;
  flex-direction: column;
}
#app {
  background-color: #e9ebee;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  // text-align: center;
  color: #2c3e50;
}

/* #nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
} */

.cover {
  object-fit: cover;
}
</style>
