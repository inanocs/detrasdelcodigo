<template>
  <v-row justify="center">
    <v-dialog v-model="seVisualiza" max-width="600" @click:outside="closeModal">
      <Login v-if="visualization == 'login'" :forward="this.$route.path" />
      <Register v-if="visualization == 'register'" />
    </v-dialog>
  </v-row>
</template>

<script>
import Login from "@/views/Login.vue";
import Register from "@/views/Registro.vue";
export default {
  name: "ModalComponent",
  components: { Login, Register },
  props: {
    visualization: { type: String },
  },
  data() {
    return {
      dialog: false,
    };
  },

  computed: {
    seVisualiza() {
      return this.visualization !== "";
    },
  },

  methods: {
    closeModal() {
      this.$emit("closeModal", false);
    },
  },
};
</script>

<style></style>
