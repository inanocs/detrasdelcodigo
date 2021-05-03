<template>
  <v-card class="px-5 mt-5" v-if="post != null">
    <v-row>
      <v-col class="text-center">
        <h1>{{ post.titulo }}</h1>
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col cols="1">
        <router-link :to="{ path: `/users/${post.usuario.username}` }">
          <v-avatar>
            <img class="cover" :src="post.usuario.avatar" alt="" />
          </v-avatar>
        </router-link>
      </v-col>
      <v-col>
        <v-row>
          <v-col cols="5">
            <h5>
              <router-link :to="{ path: `/users/${post.usuario.username}` }">{{
                post.usuario.username
              }}</router-link>
            </h5>
            <h6 class="font-weight-bold">Creado el: {{ post.createdAt }}</h6>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <!-- ql-container -->
        <div class="ql-snow ">
          <div class="ql-editor height--auto" v-html="post.contenido"></div>
        </div>
      </v-col>
    </v-row>
    <v-row v-if="post.tags.length != 0">
      <v-col cols="10">
        <v-chip class="ma-2" color="pink" label text-color="white">
          <v-icon left>
            mdi-label
          </v-icon>
          Tags
        </v-chip>
        <v-chip
          color="pink"
          outlined
          class="mr-3"
          v-for="tag in post.tags"
          :key="tag.idtag"
        >
          {{ tag.descripcion }}
        </v-chip>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import "highlight.js/styles/monokai-sublime.css";
export default {
  name: "Post",
  props: ["post"],
};
</script>

<style>
@import "~vue2-editor/dist/vue2-editor.css";

/* Import the Quill styles you want */
@import "~quill/dist/quill.core.css";
@import "~quill/dist/quill.bubble.css";
@import "~quill/dist/quill.snow.css";

.height--auto {
  min-height: auto;
}
</style>
