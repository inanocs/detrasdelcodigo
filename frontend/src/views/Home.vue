<template>
  <div class="home">
    <div class="container mt-16">
      <v-overlay :value="posts.posts.content == null">
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
      <div class="row justify-center">
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
  computed: {
    ...mapState(["posts"]),

    loading() {
      return this.posts.length == 0;
    },

    pageInfo() {
      return {
        totalPages: this.posts.posts.totalPages,
        totalElements: this.posts.posts.totalElements,
        extraInfo: this.posts.posts.pageable,
      };
    },
  },
  data() {
    return {
      page: 1,
    };
  },

  methods: {
    leerDatos() {
      const actualPage = this.page - 1;
      this.$store.dispatch("dispatchPosts", { pagina: actualPage });
    },
    cambiarPagina() {
      this.leerDatos();
    },
  },

  mounted() {
    this.$store.dispatch("dispatchPosts");
  },
};
</script>
