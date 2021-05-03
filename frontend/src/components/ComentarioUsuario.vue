<template>
  <v-row v-if="comentarios != null">
    <div
      class="col-md-12"
      v-for="comentario in comentarios"
      :key="comentario.idcomentario"
    >
      <div class="media g-mb-30 media-comment">
        <router-link :to="{ path: `/users/${comentario.usuario.username}` }">
          <img
            class="d-flex g-width-50 g-height-50 rounded-circle g-mt-3 g-mr-15 cover"
            :src="comentario.usuario.avatar"
            alt="Image Description"
          />
        </router-link>
        <div class="media-body u-shadow-v18 g-bg-secondary g-pa-30">
          <div class="g-mb-15">
            <h5 class="h5 g-color-gray-dark-v1 mb-0">
              <router-link
                :to="{ path: `/users/${comentario.usuario.username}` }"
              >
                {{ comentario.usuario.username }}
              </router-link>
            </h5>
            <span class="g-color-gray-dark-v4 g-font-size-12">{{
              comentario.createdAt
            }}</span>
          </div>
          <div class="ql-snow height--auto">
            <div
              class="ql-editor height--auto"
              v-html="comentario.contenido"
            ></div>
          </div>

          <ul class="list-inline d-sm-flex my-0">
            <li class="list-inline-item ml-auto">
              <a
                class="u-link-v5 g-color-gray-dark-v4 g-color-primary--hover"
                href="#!"
              >
                <i class="fa fa-reply g-pos-rel g-top-1 g-mr-3"></i>
                Responder
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </v-row>
</template>

<script>
export default {
  name: "ComentarioUsuario",
  props: {
    comentarios: {
      type: Array,
      default: function() {
        return [];
      },
    },
  },
};
</script>

<style>
@media (min-width: 0) {
  .g-mr-15 {
    margin-right: 1.07143rem !important;
  }
}
@media (min-width: 0) {
  .g-mt-3 {
    margin-top: 0.21429rem !important;
  }
}

.g-height-50 {
  height: 50px;
}

.g-width-50 {
  width: 50px !important;
}

@media (min-width: 0) {
  .g-pa-30 {
    padding: 2.14286rem !important;
  }
}

.g-bg-secondary {
  background-color: #fafafa !important;
}

.u-shadow-v18 {
  box-shadow: 0 5px 10px -6px rgba(0, 0, 0, 0.15);
}

.g-color-gray-dark-v4 {
  color: #777 !important;
}

.g-font-size-12 {
  font-size: 0.85714rem !important;
}

.media-comment {
  margin-top: 20px;
}

.height--auto {
  height: auto !important;
  min-height: auto;
}
</style>
