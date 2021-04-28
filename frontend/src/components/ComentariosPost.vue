<template>
  <div v-if="comentarios != null">
    <v-row class="mt-8">
      <h4 class="mx-4">Comentarios: {{ comentarios.length }}</h4>
    </v-row>
    <v-row>
      <v-divider class="mx-4"></v-divider>
    </v-row>
    <BoxComentarios
      class="mx-auto mb-6"
      @comentariocreado="asignarComentario"
    />
    <ComentarioUsuario :comentarios="comentarios" />
  </div>
</template>

<script>
import ComentarioUsuario from "@/components/ComentarioUsuario.vue";
import BoxComentarios from "@/components/BoxComentarios.vue";
export default {
  name: "ComentariosPost",
  components: { ComentarioUsuario, BoxComentarios },
  props: {
    listaComentarios: {
      type: Array,
      default: [],
    },
  },

  data() {
    return {
      comentarios: [],
    };
  },

  created() {
    this.comentarios = this.listaComentarios;
  },

  methods: {
    asignarComentario(comentario) {
      this.comentarios.unshift(comentario);
    },
  },
};
</script>

<style></style>
