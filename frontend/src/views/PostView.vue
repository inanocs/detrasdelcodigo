<template>
  <v-container class="mt-16">
    <v-overlay :value="post == null">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
    <article v-if="post != null">
      <Post :post="post" />

      <!-- TODO: Caja de comentarios (Mirar si tengo tiempo también de plantear una lógica de respuestas) -->
      <ComentariosPost :lista-comentarios="comentarios" />
    </article>
  </v-container>
</template>

<script>
import Post from "@/components/Post.vue";
import ComentariosPost from "@/components/ComentariosPost.vue";
const postService = require("../api/posts");
export default {
  name: "PostView",
  components: { Post, ComentariosPost },
  data: () => ({
    post: null,
    notFound: false,
  }),
  computed: {
    comentarios() {
      return this.post != null && this.post.comentarios != null
        ? this.post.comentarios
        : [];
    },
  },
  async created() {
    const res = await postService.findById(this.$route.params.idpost);

    setTimeout(
      () =>
        res.status == 404 || res.code == 404
          ? (this.notFound = true)
          : (this.post = res),
      1000
    );
  },
};
</script>

<style></style>
