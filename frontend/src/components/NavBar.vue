<template>
  <div id="nav" class="overflow-hidden" style="position: relative;">
    <v-app-bar color="dark" dark app flat>
      <v-container>
        <v-row class="align-center">
          <router-link class="list__link v-toolbar__title white--text" to="/"
            >Detrás del código</router-link
          >

          <v-btn
            v-for="(link, i) in links"
            :key="i"
            class="button--no-borders ml-3  hidden-sm-and-down"
            rounded
            text
            @click="onClick($event, link)"
          >
            {{ link.text }}
          </v-btn>

          <v-spacer></v-spacer>
          <v-app-bar-nav-icon
            class="hidden-md-and-up"
            @click="drawer = true"
          ></v-app-bar-nav-icon>
          <form class="mr-5" @submit.prevent="buscarPosts">
            <v-text-field
              class="hidden-sm-and-down"
              append-icon="mdi-magnify"
              flat
              label="Busca un post"
              v-model="tituloPost"
              placeholder="Busca un post..."
              hide-details
              solo-inverted
              style="max-width: 300px;"
            />
          </form>

          <v-menu v-if="!estaLogeado" bottom min-width="200px" rounded offset-y>
            <template v-slot:activator="{ on }">
              <v-btn icon x-large v-on="on">
                <v-avatar color="brown" size="42">
                  <span class="white--text headline">Gu</span>
                  <!-- <span class="white--text headline">{{loginUser.username }}</span> -->
                </v-avatar>
              </v-btn>
            </template>
            <v-card>
              <v-list-item-content class="justify-center">
                <div class="mx-auto text-center px-3 py-2">
                  <v-avatar color="brown">
                    <span class="white--text headline">Gu</span>
                  </v-avatar>
                  <h3>Invitado</h3>
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    to="/login"
                    color="primary"
                    rounded
                    class="list__link white--text"
                    >Login</v-btn
                  >
                  <v-btn
                    to="/register"
                    color="green"
                    rounded
                    class="ml-4 list__link white--text"
                    >Registro</v-btn
                  >
                </div>
              </v-list-item-content>
            </v-card>
          </v-menu>

          <v-menu
            :key="loginUser.username"
            v-if="estaLogeado"
            bottom
            min-width="200px"
            rounded
            offset-y
          >
            <template v-slot:activator="{ on }">
              <v-btn icon x-large v-on="on">
                <v-avatar class="cover" color="brown" size="42">
                  <img
                    class="cover"
                    :src="loginUser.avatar"
                    :alt="loginUser.username"
                  />
                  <!-- <span class="white--text headline">{{loginUser.username }}</span> -->
                </v-avatar>
              </v-btn>
            </template>
            <v-card>
              <v-list-item-content class="justify-center">
                <div class="mx-auto text-center">
                  <v-avatar color="brown">
                    <span class="white--text headline">{{
                      loginUser.nombre.substring(0, 2)
                    }}</span>
                  </v-avatar>
                  <h3>{{ loginUser.nombre }}</h3>
                  <v-chip color="black" small class="white--text"
                    >@{{ loginUser.username }}</v-chip
                  >
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    :to="{ path: `/users/${loginUser.username}` }"
                    class="list__link"
                    depressed
                    rounded
                    text
                  >
                    <v-icon>mdi-account</v-icon>
                    Perfil
                  </v-btn>
                  <v-divider class="my-3"></v-divider>
                  <v-btn depressed rounded text>
                    <v-icon>mdi-account-settings</v-icon>
                    Editar cuenta
                  </v-btn>
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    :to="{ path: `/crearpost` }"
                    class="list__link"
                    depressed
                    rounded
                    text
                  >
                    <v-icon>mdi-post</v-icon>
                    Crear post
                  </v-btn>
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    @click="logout"
                    :loading="onLogoutProcess"
                    depressed
                    rounded
                    text
                  >
                    Cerrar sesión
                  </v-btn>
                </div>
              </v-list-item-content>
            </v-card>
          </v-menu>
        </v-row>
        <v-navigation-drawer v-model="drawer" absolute temporary>
          <v-list dense>
            <v-list-item-group
              v-model="group"
              active-class="indigo--text text--accent-4"
            >
              <v-list-item
                v-for="(link, i) in links"
                :key="i"
                class="list__link"
                :to="{ path: link.href }"
              >
                <v-list-item-icon>
                  <v-icon>{{ link.icon }}</v-icon>
                </v-list-item-icon>
                <v-list-item-title>{{ link.text }}</v-list-item-title>
              </v-list-item>
            </v-list-item-group>
            <v-list-item>
              <form @submit.prevent="buscarPosts">
                <v-text-field
                  append-icon="mdi-magnify"
                  flat
                  v-model="tituloPost"
                  label="Busca un post"
                  placeholder="Busca un post..."
                  hide-details
                  solo-inverted
                  style="max-width: 300px;"
                />
              </form>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>

        <!-- Component modal para el logout -->
        <v-dialog v-model="onLogoutProcess" hide-overlay persistent width="300">
          <v-card color="indigo" dark>
            <v-card-text>
              Por favor espere...
              <v-progress-linear
                indeterminate
                color="white"
                class="mb-0"
              ></v-progress-linear>
            </v-card-text>
          </v-card>
        </v-dialog>
      </v-container>
    </v-app-bar>
  </div>
</template>

<script>
const authService = require("../api/auth");
import { mapState, mapMutations } from "vuex";

export default {
  name: "Navbar",
  data: () => ({
    drawer: false,
    group: null,
    tituloPost: "",
    links: [
      {
        text: "Home",
        href: "/",
        icon: "mdi-home",
      },
      {
        text: "About",
        href: "/about",
        icon: "mdi-account",
      },
      {
        text: "Posts",
        href: "/posts",
        icon: "mdi-post",
      },
      {
        text: "Categorias",
        href: "/categorias",
        icon: "mdi-shape",
      },
    ],
    onLogoutProcess: false,
  }),

  computed: {
    estaLogeado() {
      return !!this.loginUser.username;
    },

    ...mapState({
      loginUser: (state) => state.users.authUser,
    }),
  },
  methods: {
    ...mapMutations(["setAuthUser"]),
    onClick(e, item) {
      e.stopPropagation();

      this.$router.push({ path: item.href });
    },
    buscarPosts() {
      this.$store.dispatch("dispatchPosts", { titulo: this.tituloPost });
      const titulo = this.tituloPost;
      this.tituloPost = "";
      this.$router.push({
        path: "/search",
        query: { titulo },
      });
    },

    logout() {
      authService.logout();
      this.onLogoutProcess = true;
      setTimeout(() => {
        this.onLogoutProcess = !this.onLogoutProcess;
        this.setAuthUser({});
        this.$router.push({ path: "/" });
      }, 1500);
    },
  },
};
</script>

<style>
.button--no-borders {
  border: none;
}

.list__link,
.list__link:hover {
  text-decoration: none;
}
</style>
