<template>
  <v-container class="mt-16">
    <v-snackbar
      v-if="respuesta != null"
      v-model="snackbar"
      :timeout="2000"
      :color="respuestaType"
    >
      {{ respuesta.mensaje }}

      <template v-slot:action="{ attrs }">
        <v-btn color="black" text v-bind="attrs" @click="snackbar = false">
          Cerrar
        </v-btn>
      </template>
    </v-snackbar>
    <v-card class="p-5">
      <h2 class="text-h2 text-center">Crear Post</h2>
      <v-form
        class="mt-6"
        @submit.prevent="crearPost"
        enctype="multipart/form-data"
      >
        <v-text-field
          clearable
          filled
          outlined
          v-model="titulo"
          :error-messages="tituloErrors"
          color="indigo"
          label="Título"
          @input="$v.titulo.$touch()"
          @blur="$v.titulo.$touch()"
        ></v-text-field>
        <v-textarea
          v-model="descripcion"
          auto-grow
          clearable
          :error-messages="descripcionErrors"
          filled
          outlined
          color="indigo"
          label="Descripción"
          rows="1"
          @input="$v.descripcion.$touch()"
          @blur="$v.descripcion.$touch()"
        ></v-textarea>

        <v-select
          v-model="categoria"
          :items="categorias"
          :error-messages="categoriaErrors"
          label="Categoría"
          outlined
          item-value="idcategoria"
          item-text="descripcion"
          @input="$v.categoria.$touch()"
          @blur="$v.categoria.$touch()"
        ></v-select>

        <h5>Contenido</h5>
        <Editor
          :content-data="contenido"
          :limpiar="limpiarEditor"
          @pasarDatos="setContent"
        />
        <div
          class=" mt-3  v-messages theme--light error--text"
          v-if="contenido.length == 0"
        >
          <div class="v-messages__wrapper">
            <div class="v-messages__message">El contenido es obligatorio</div>
          </div>
        </div>
        <v-combobox
          class="mt-3"
          outlined
          v-model="tags"
          :items="tagsApi"
          item-value="idtag"
          item-text="descripcion"
          :search-input.sync="search"
          label="Tags"
          multiple
          persistent-hint
          hide-selected
          chips
        ></v-combobox>
        <v-file-input
          class="mt-5"
          label="Imagen de portada"
          outlined
          append-icon="mdi-camera"
          color="indigo"
          v-model="portada"
          chips
          counter
          show-size
          truncate-length="17"
        ></v-file-input>
        <v-btn
          class="mr-4"
          type="submit"
          outlined
          color="green"
          :loading="loadingSubmitButton"
        >
          Enviar
        </v-btn>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import Editor from "@/components/Editor.vue";
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";
const categoriaService = require("../api/categoria");
const tagService = require("../api/tags");
const postsService = require("../api/posts");
export default {
  name: "CrearPost",
  components: { Editor },
  mixins: [validationMixin],

  validations: {
    titulo: { required },
    descripcion: {
      required,
    },
    categoria: { required },
    contenido: { required },
  },
  data() {
    return {
      titulo: "",
      descripcion: "",
      categoria: 1,
      contenido: "",
      portada: null,
      categorias: [],
      tags: [],
      tagsApi: [],
      search: null,
      loadingSubmitButton: false,
      respuesta: null,
      limpiarEditor: false,
      snackbar: false,
    };
  },

  computed: {
    respuestaType() {
      return this.respuesta.status >= 200 && this.respuesta.status <= 399
        ? "green"
        : "red";
    },
    tituloErrors() {
      const errors = [];
      if (!this.$v.titulo.$dirty) return errors;
      !this.$v.titulo.required && errors.push("El título es obligatorio.");
      return errors;
    },
    descripcionErrors() {
      const errors = [];
      if (!this.$v.descripcion.$dirty) return errors;
      !this.$v.descripcion.required &&
        errors.push("La descripción es obligatoria.");
      return errors;
    },
    categoriaErrors() {
      const errors = [];
      if (!this.$v.categoria.$dirty) return errors;
      !this.$v.categoria.required && errors.push("La categoría es obligatoria");
      return errors;
    },
  },
  methods: {
    setContent(payload) {
      this.contenido = payload;
    },
    clear() {
      this.titulo = "";
      this.descripcion = "";
      this.categoria = 1;
      this.contenido = "";
      this.portada = null;
      this.tags = [];
    },
    async crearPost() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        const formData = new FormData();
        formData.append(
          "post",
          new Blob(
            [
              JSON.stringify({
                titulo: this.titulo,
                descripcion: this.descripcion,
                contenido: this.contenido,
                idcategoria: this.categoria,
                idTags: this.tags.map((tag) => tag.idtag),
              }),
            ],
            { type: "application/json" }
          )
        );
        if (this.portada != null) {
          formData.append("img", this.portada);
        }
        this.respuesta = await postsService.createPost(formData);
        this.snackbar = true;
        if (this.respuesta.status >= 200 && this.respuesta.status <= 399) {
          this.clear();
          this.limpiarEditor = true;
          setTimeout(() => (this.limpiar = !this.limpiar), 10);
        }
      }
    },
  },
  watch: {
    tags(val) {
      //   TODO: Hacer que solo pueda escribir tags que vengan de la BDD
      if (val.length > 5) {
        this.$nextTick(() => this.tags.pop());
      }
    },
  },
  async created() {
    this.categorias = await categoriaService.findAll();
    this.tagsApi = await tagService.findAll();
  },
};
</script>

<style>
@import "~vue2-editor/dist/vue2-editor.css";

/* Import the Quill styles you want */
@import "~quill/dist/quill.core.css";
@import "~quill/dist/quill.bubble.css";
@import "~quill/dist/quill.snow.css";
</style>
