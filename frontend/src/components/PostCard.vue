<template>
  <v-card
    @click="goToPost"
    :loading="loading"
    class="mt-4 ml-3 my-12 post-card"
    width="300"
    max-width="500"
  >
    <template slot="progress">
      <v-progress-linear
        color="deep-purple"
        height="10"
        indeterminate
      ></v-progress-linear>
    </template>

    <v-img height="200" :src="post.portada"></v-img>
    <div class="post-card__content">
      <v-card-title>{{ post.titulo }}</v-card-title>

      <v-card-text>
        <div>Creado el:{{ post.createdAt }}</div>
        <div>
          {{ post.descripcion | descripcionPost }}
        </div>
      </v-card-text>

      <v-divider class="mx-4"></v-divider>
      <v-card-text>
        <v-chip-group active-class="deep-purple accent-4 white--text" column>
          <v-chip
            class="pink pink--text"
            outlined
            v-for="tag in post.tags"
            :key="tag.id"
            >{{ tag.descripcion }}</v-chip
          >
        </v-chip-group>
      </v-card-text>
      <v-card-actions>
        <v-list-item class="grow">
          <router-link
            :to="{ path: `/users/${post.usuario.username}` }"
            class="d-flex align-center"
          >
            <v-list-item-avatar color="grey darken-3">
              <v-img
                class="elevation-6 cover"
                alt=""
                :src="post.usuario.avatar"
              ></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ post.usuario.username }}</v-list-item-title>
            </v-list-item-content>
          </router-link>
          <v-row align="center" justify="end">
            <span class="mr-1">·</span>
            <v-icon class="mr-1">
              mdi-comment
            </v-icon>
            <span class="subheading">{{ post.comentarios.length }}</span>
          </v-row>
        </v-list-item>
      </v-card-actions>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "PostCard",
  props: {
    post: {
      type: Object,
    },
  },

  computed: {
    loading() {
      return this.post == null;
    },
  },

  methods: {
    goToPost() {
      this.$router.push({ path: `/posts/${this.post.idpost}` });
    },
  },
  filters: {
    descripcionPost(descripcion) {
      return descripcion.length > 110
        ? `${descripcion.substring(0, 110)}...`
        : descripcion;
    },
  },
};
</script>

<style>
.post-card {
  box-sizing: border-box;
  height: 570px;
}

.post-card__content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 370px;
}
</style>
