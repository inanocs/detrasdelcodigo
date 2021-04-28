<template>
  <div class="wrap">
    <vue-editor
      v-model="content"
      :editorOptions="editorOptions"
      :editorToolbar="customToolbar"
      @text-change="pasarDatos"
    />
  </div>
</template>

<script>
import hljs from "highlight.js";
import "highlight.js/styles/monokai-sublime.css";
import { VueEditor } from "vue2-editor/dist/vue2-editor.core.js";
export default {
  props: {
    "content-data": { type: String },
    limpiar: { type: Boolean, default: false },
  },
  computed: {
    editorOptions() {
      return {
        theme: "snow",
        modules: {
          syntax: {
            /*  highlight: (text) => window.hljs.highlightAuto(text).value, */
            // or

            highlight(text) {
              const result = hljs.highlightAuto(text);
              return result.value;
            },
          },
        },
      };
    },
  },

  watch: {
    limpiar(nuevoValor, antiguoValor) {
      this.content = "";
    },
  },
  methods: {
    pasarDatos() {
      this.$emit("pasarDatos", this.content);
    },
  },
  components: { VueEditor },

  data() {
    return {
      limpiarTexto: this.$props.limpiar,
      content: this.$props.contentData,
      customToolbar: [
        [{ header: [false, 1, 2, 3, 4, 5, 6] }],
        ["bold", "italic", "underline", "strike"], // toggled buttons
        [
          { align: "" },
          { align: "center" },
          { align: "right" },
          { align: "justify" },
        ],
        ["blockquote", "code-block"],
        [{ list: "ordered" }, { list: "bullet" }, { list: "check" }],
        [{ indent: "-1" }, { indent: "+1" }], // outdent/indent
        [{ color: [] }, { background: [] }], // dropdown with defaults from theme
        ["link"],
        ["clean"], // remove formatting button
      ],
    };
  },
};
</script>

<style lang="css">
.wrap {
  background-color: #fff;
  width: 100%;
}
</style>
