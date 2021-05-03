<template>
  <v-form class="p-2" @submit.prevent="actualizarAvatar">
    <v-alert v-if="respuesta != null" outlined :type="respuestaType" text>
      {{ respuesta.mensaje }}
    </v-alert>
    <h4 class="primary--text mb-5">Editar foto de perfil</h4>
    <v-row class="align-end">
      <v-col cols="3">
        <v-avatar size="95">
          <img class="cover" :src="imgSrc" :alt="`${user.username} avatar`" />
        </v-avatar>
      </v-col>

      <v-col cols="6">
        <v-file-input
          @click:clear="resetProfilePic"
          @change="actualizarPreview"
          v-model="imgData"
          :rules="rules"
          accept="image/png, image/jpeg, image/bmp"
          placeholder="Elige una foto"
          prepend-icon="mdi-camera"
          dense
          outlined
          label="Avatar"
        ></v-file-input>
      </v-col>
    </v-row>
    <v-btn class="mt-6" color="green" outlined type="submit">Actualizar</v-btn>
  </v-form>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const userService = require("../api/users");
export default {
  name: "EditPicProfile",
  computed: {
    ...mapState({
      user: (state) => state.users.user,
    }),
    respuestaType() {
      return this.respuesta.status >= 200 && this.respuesta.status <= 399
        ? "success"
        : "error";
    },
  },
  data: () => ({
    imgSrc: null,
    imgData: null,
    rules: [
      (value) =>
        !value ||
        value.size < 4000000 ||
        "El tamaño de la foto debe ser menos de 4 MB!",
    ],
    respuesta: null,
  }),
  created() {
    this.imgSrc = this.user.avatar;
  },

  methods: {
    ...mapMutations(["setUser", "setAuthUser"]),
    async actualizarAvatar() {
      if (this.imgData != null) {
        const formData = new FormData();
        formData.append("img", this.imgData);
        this.respuesta = await userService.actualizarAvatar(formData);
        if (this.respuestaType == "success") {
          const newUser = { ...this.user };
          newUser.avatar = this.respuesta.avatar;
          this.setUser(newUser);
          this.setAuthUser(newUser);
        }
      }
    },
    actualizarPreview() {
      /* const reader = new FileReader();
      reader.onload = (e) => (this.imgSrc = e.target.result);
      reader.readAsDataURL(this.imgData); */
      if (this.imgData != null) {
        this.imgSrc = URL.createObjectURL(this.imgData);
      }
    },
    resetProfilePic() {
      this.imgSrc = this.user.avatar;
      this.imgData = null;
    },
  },
};
</script>

<style>
.cover {
  object-fit: cover;
}
</style>
