<template>
  <v-form class="p-2" @submit.prevent="submit">
    <v-alert v-if="respuesta != null" outlined :type="respuestaType" text>
      {{ respuesta.mensaje }}
    </v-alert>
    <h4 class="primary--text mb-5">Cambiar contraseña</h4>
    <v-text-field
      filled
      outlined
      v-model="oldPassword"
      :error-messages="oldPasswordErrors"
      :append-icon="showPassword1 ? 'mdi-eye' : 'mdi-eye-off'"
      :type="showPassword1 ? 'text' : 'password'"
      label="Contraseña actual"
      @input="$v.oldPassword.$touch()"
      @blur="$v.oldPassword.$touch()"
      @click:append="showPassword1 = !showPassword1"
    ></v-text-field>
    <v-text-field
      filled
      outlined
      v-model="newPassword"
      :error-messages="newPasswordErrors"
      :append-icon="showPassword2 ? 'mdi-eye' : 'mdi-eye-off'"
      :type="showPassword2 ? 'text' : 'password'"
      hint="Al menos 8 caracteres"
      label="Nueva contraseña"
      @input="$v.newPassword.$touch()"
      @blur="$v.newPassword.$touch()"
      @click:append="showPassword2 = !showPassword2"
    ></v-text-field>
    <v-text-field
      filled
      outlined
      v-model="repeatNewPassword"
      :error-messages="repeatNewPasswordErrors"
      :append-icon="showPassword3 ? 'mdi-eye' : 'mdi-eye-off'"
      :type="showPassword3 ? 'text' : 'password'"
      hint="Al menos 8 caracteres"
      label="Repite nueva contraseña"
      @input="$v.repeatNewPassword.$touch()"
      @blur="$v.repeatNewPassword.$touch()"
      @click:append="showPassword3 = !showPassword3"
    ></v-text-field>
    <v-btn
      class="mr-4"
      color="green"
      outlined
      type="submit"
      :loading="loadingSubmitButton"
    >
      Enviar
    </v-btn>
    <v-btn @click="clear" outlined color="red">
      Limpiar
    </v-btn>
  </v-form>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, minLength, sameAs } from "vuelidate/lib/validators";
const userService = require("../api/users");
export default {
  name: "EditPassword",
  mixins: [validationMixin],

  validations: {
    oldPassword: { required },
    newPassword: {
      required,
      minLength: minLength(8),
    },
    repeatNewPassword: {
      sameAsPassword: sameAs("newPassword"),
    },
  },

  data: () => ({
    oldPassword: "",
    newPassword: "",
    repeatNewPassword: "",
    showPassword1: false,
    showPassword2: false,
    showPassword3: false,
    respuesta: null,
    loadingSubmitButton: false,
  }),

  computed: {
    respuestaType() {
      return this.respuesta.status >= 200 && this.respuesta.status <= 399
        ? "success"
        : "error";
    },
    oldPasswordErrors() {
      const errors = [];
      if (!this.$v.oldPassword.$dirty) return errors;
      !this.$v.oldPassword.required && errors.push("El nombre es obligatorio.");
      return errors;
    },
    newPasswordErrors() {
      const errors = [];
      if (!this.$v.newPassword.$dirty) return errors;
      !this.$v.newPassword.minLength &&
        errors.push("La contraseña debe contener mínimo 8 caracteres");
      !this.$v.newPassword.required &&
        errors.push("La contraseña es obligatoria.");
      return errors;
    },
    repeatNewPasswordErrors() {
      const errors = [];
      if (!this.$v.repeatNewPassword.$dirty) return errors;
      !this.$v.repeatNewPassword.sameAsPassword &&
        errors.push("Las contraseñas deben ser iguales");
      return errors;
    },
  },

  methods: {
    async submit() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.loadingSubmitButton = true;
        const data = {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword,
          repeatNewPassword: this.repeatNewPassword,
        };

        this.respuesta = await userService.actualizarPassword(data);

        console.log(this.respuesta);

        if (this.respuestaType == "success") {
          this.clear();
        }
        this.loadingSubmitButton = false;
      }
    },
    clear() {
      this.$v.$reset();
      this.oldPassword = "";
      this.newPassword = "";
      this.repeatNewPassword = "";
    },
  },
};
</script>

<style></style>
