<template>
  <div class="container mt-16">
    <v-overlay :value="posts.posts.content == null">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
    <div class="row justify-center">
      <h3 v-if="posts.posts.content == null || posts.posts.content.length == 0">
        No se han encontrado coincidencias
      </h3>
      <Post-Card
        v-for="post in posts.posts.content"
        :key="post.id"
        :post="post"
      />
    </div>
    <div
      class="text-center"
      v-if="posts.posts.content != null || posts.posts.content.length != 0"
    >
      <v-container>
        <v-row justify="center">
          <v-col cols="8">
            <v-container class="max-width">
              <v-pagination
                v-model="page"
                class="my-4"
                :length="pageInfo.totalPages"
                @input="cambiarPagina"
                :total-visible="7"
              ></v-pagination>
            </v-container>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
import PostCard from "@/components/PostCard.vue";
import { mapState } from "vuex";
export default {
  name: "Home",
  components: {
    PostCard,
  },
  data() {
    return {
      page: 1,
      titulo: "",
    };
  },
  computed: {
    ...mapState(["posts"]),
    pageInfo() {
      return {
        totalPages: this.posts.posts.totalPages,
        totalElements: this.posts.posts.totalElements,
        extraInfo: this.posts.posts.pageable,
      };
    },

    loading() {
      return this.posts.length == 0;
    },
  },

  created() {
    this.titulo = this.$route.query.titulo || "";
    this.$store.dispatch("dispatchPosts", { titulo: this.titulo });
  },
  methods: {
    leerDatos() {
      this.titulo = this.$route.query.titulo || "";
      const actualPage = this.page - 1;
      this.$store.dispatch("dispatchPosts", {
        titulo: this.titulo,
        pagina: actualPage,
      });
    },
    cambiarPagina() {
      this.leerDatos();
    },
  },
  beforeDestroy() {
    this.$store.posts = [];
  },
};
</script>

<style></style>
