<template>
  <v-container class="mt-16">
    <v-alert v-if="respuesta != null" outlined :type="respuestaType" text>
      {{ respuesta.mensaje }}
    </v-alert>
    <v-card class="p-5">
      <form @submit.prevent="submit">
        <h2 class="text-h2 text-center">Login</h2>
        <v-text-field
          v-model="username"
          :error-messages="usernameErrors"
          label="Nombre de usuario"
          @input="$v.username.$touch()"
          @blur="$v.username.$touch()"
        ></v-text-field>
        <v-text-field
          v-model="password"
          :error-messages="passwordErrors"
          :append-icon="showPassword1 ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPassword1 ? 'text' : 'password'"
          label="Contraseña"
          @input="$v.password.$touch()"
          @blur="$v.password.$touch()"
          @click:append="showPassword1 = !showPassword1"
        ></v-text-field>
        <v-btn
          class="mr-4"
          type="submit"
          outlined
          color="green"
          :loading="loadingSubmitButton"
        >
          Enviar
        </v-btn>
        <v-btn @click="clear">
          Limpiar
        </v-btn>
        <v-btn class="ml-5" text color="primary" to="/register">
          O crea una cuenta
        </v-btn>
      </form>
    </v-card>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";

const authService = require("../api/auth/");
export default {
  props: {
    forward: { type: String, default: "/" },
  },
  mixins: [validationMixin],

  validations: {
    username: { required },
    password: {
      required,
    },
  },

  data: () => ({
    username: "",
    password: "",
    showPassword1: false,
    respuesta: null,
    loadingSubmitButton: false,
  }),

  computed: {
    respuestaType() {
      return this.respuesta.status >= 200 && this.respuesta.status <= 399
        ? "success"
        : "error";
    },
    usernameErrors() {
      const errors = [];
      if (!this.$v.username.$dirty) return errors;
      !this.$v.username.required &&
        errors.push("El nombre de usuario es obligatorio.");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.required &&
        errors.push("La contraseña es obligatoria.");
      return errors;
    },
  },

  methods: {
    async submit() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.loadingSubmitButton = true;
        const data = {
          username: this.username,
          password: this.password,
        };
        this.respuesta = await authService.loginUsername(data);

        if (
          this.respuesta != null &&
          this.respuesta.status >= 200 &&
          this.respuesta.status <= 399
        ) {
          this.clear();
          setTimeout(() => {
            if (this.forward == this.$route.path) {
              location.reload();
            } else {
              this.$router.push({ path: this.forward });
            }
            this.$store.dispatch("dispatchAuthUser");
          }, 1500);
        } else {
          this.clearPassword();
        }
        this.loadingSubmitButton = false;
      }
    },
    clear() {
      this.$v.$reset();
      this.username = "";
      this.password = "";
    },

    clearPassword() {
      this.password = "";
    },
  },

  created() {
    if (authService.getUserSessionToken()) {
      this.$router.push({ path: "/" });
    }
  },
};
</script>

<style></style>
