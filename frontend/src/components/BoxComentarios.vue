<template>
  <v-row>
    <v-snackbar v-model="snackbar" :timeout="2000" color="green">
      {{ res.mensaje }}

      <template v-slot:action="{ attrs }">
        <v-btn color="black" text v-bind="attrs" @click="snackbar = false">
          Cerrar
        </v-btn>
      </template>
    </v-snackbar>
    <v-card class="w-100 p-4" v-if="!isLogged">
      <p>
        Para comentar necesitas estar logueado.
        <a
          href="/login"
          @click.prevent="displayModal('login')"
          to="/login"
          event=""
        >
          Inicia sesión</a
        >
        o
        <a @click.prevent="displayModal('register')" href="/register"
          >Regístrate</a
        >
      </p>
      <ModalComponent
        :visualization="modalVisualization"
        :activated="modalActivado"
        @closeModal="resetModalValues"
      />
    </v-card>

    <v-card v-if="isLogged" class="p-2 w-100">
      <div>
        <h4>¡Deja tu comentario!</h4>
        <Editor
          class="height-100"
          :limpiar="limpiar"
          :content-data="content"
          @pasarDatos="setContent"
        />
        <div
          class=" mt-3  v-messages theme--light error--text"
          v-if="comentarioVacio"
        >
          <div class="v-messages__wrapper">
            <div class="v-messages__message">El contenido es obligatorio</div>
          </div>
        </div>
        <v-btn color="green" outlined class="mt-2" @click="enviar">
          Comentar
        </v-btn>
      </div>
    </v-card>
  </v-row>
</template>

<script>
import Editor from "@/components/Editor.vue";
import ModalComponent from "@/components/ModalComponents.vue";
const authService = require("../api/auth");
const comentariosService = require("../api/comentarios");
export default {
  name: "BoxComentarios",
  components: { Editor, ModalComponent },

  data: () => ({
    content: "",
    limpiar: false,
    snackbar: false,
    comentarioVacio: false,
    res: { mensaje: "Mensaje de prueba" },
    modalVisualization: "",
    modalActivado: false,
  }),

  computed: {
    isLogged() {
      return (
        authService.getUserSessionToken() != null ||
        authService.getUserSessionToken() != undefined
      );
    },
  },
  methods: {
    setContent(payload) {
      this.content = payload;
    },

    displayModal(ruta) {
      this.modalVisualization = ruta;
      this.modalActivado = true;
    },
    resetModalValues(payload) {
      this.modalVisualization = "";
      this.modalActivado = payload;
    },

    async enviar() {
      if (this.content.length > 0) {
        this.comentarioVacio = false;
        const idpost = this.$route.params.idpost;
        const data = { contenido: this.content, idpost };

        this.res = await comentariosService.crearComentario(data);

        if (this.res.status >= 200 && this.res.status <= 399) {
          this.$emit("comentariocreado", this.res.data);
        }

        this.snackbar = true;

        this.limpiar = true;

        setTimeout(() => (this.limpiar = !this.limpiar), 10);
      } else {
        this.comentarioVacio = true;
      }
    },
  },
};
</script>

<style>
@import "~vue2-editor/dist/vue2-editor.css";

/* Import the Quill styles you want */
@import "~quill/dist/quill.core.css";
@import "~quill/dist/quill.bubble.css";
@import "~quill/dist/quill.snow.css";

.height-100 .ql-editor {
  min-height: 100px;
}
</style>
