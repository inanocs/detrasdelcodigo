<template>
  <v-container class="mt-16">
    <v-alert v-if="respuesta != null" outlined :type="respuestaType" text>
      {{ respuesta.mensaje }}
    </v-alert>

    <v-card class="p-5">
      <form @submit.prevent="submit">
        <h2 class="text-h2 text-center">Registro</h2>
        <v-text-field
          v-model="nombre"
          :error-messages="nombreErrors"
          :counter="25"
          label="Nombre"
          @input="$v.nombre.$touch()"
          @blur="$v.nombre.$touch()"
        ></v-text-field>
        <v-text-field
          v-model="apellidos"
          :error-messages="apellidosErrors"
          :counter="25"
          label="Apellidos"
          @input="$v.apellidos.$touch()"
          @blur="$v.apellidos.$touch()"
        ></v-text-field>
        <v-text-field
          v-model="username"
          :error-messages="usernameErrors"
          :counter="25"
          label="Nombre de usuario"
          @input="$v.username.$touch()"
          @blur="$v.username.$touch()"
        ></v-text-field>
        <v-text-field
          v-model="email"
          :error-messages="emailErrors"
          label="E-mail"
          @input="$v.email.$touch()"
          @blur="$v.email.$touch()"
        ></v-text-field>
        <v-text-field
          v-model="password"
          :error-messages="passwordErrors"
          :append-icon="showPassword1 ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPassword1 ? 'text' : 'password'"
          hint="Al menos 8 caracteres"
          label="Contraseña"
          @input="$v.password.$touch()"
          @blur="$v.password.$touch()"
          @click:append="showPassword1 = !showPassword1"
        ></v-text-field>
        <v-text-field
          v-model="repeatPassword"
          :error-messages="repeatPasswordErrors"
          :append-icon="showPassword2 ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPassword2 ? 'text' : 'password'"
          hint="Al menos 8 caracteres"
          label="Repetir contraseña"
          @input="$v.password.$touch()"
          @blur="$v.password.$touch()"
          @click:append="showPassword2 = !showPassword2"
        ></v-text-field>
        <v-checkbox
          v-model="checkbox"
          :error-messages="checkboxErrors"
          label="Acepto los terminos y condiciones"
          @change="$v.checkbox.$touch()"
          @blur="$v.checkbox.$touch()"
        ></v-checkbox>

        <v-btn class="mr-4" type="submit" :loading="loadingSubmitButton">
          Enviar
        </v-btn>
        <v-btn @click="clear">
          Limpiar
        </v-btn>
      </form>
    </v-card>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import {
  required,
  maxLength,
  email,
  sameAs,
  minLength,
} from "vuelidate/lib/validators";

const usersService = require("../api/users/");

export default {
  props: {
    forward: { type: String, default: "/" },
  },
  mixins: [validationMixin],

  validations: {
    username: { required, maxLength: maxLength(25) },
    email: { required, email },
    checkbox: {
      checked(val) {
        return val;
      },
    },
    nombre: { required, maxLength: maxLength(25) },
    apellidos: { required, maxLength: maxLength(25) },
    password: {
      required,
      minLength: minLength(8),
    },
    repeatPassword: {
      sameAsPassword: sameAs("password"),
    },
  },

  data: () => ({
    username: "",
    nombre: "",
    apellidos: "",
    email: "",
    checkbox: false,
    password: "",
    repeatPassword: "",
    showPassword1: false,
    showPassword2: false,
    respuesta: null,
    loadingSubmitButton: false,
  }),

  computed: {
    respuestaType() {
      return this.respuesta.status >= 200 && this.respuesta.status <= 399
        ? "success"
        : "error";
    },
    checkboxErrors() {
      const errors = [];
      if (!this.$v.checkbox.$dirty) return errors;
      !this.$v.checkbox.checked &&
        errors.push("Debes aceptar los terminos y condiciones");
      return errors;
    },
    selectErrors() {
      const errors = [];
      if (!this.$v.select.$dirty) return errors;
      !this.$v.select.required && errors.push("Item is required");
      return errors;
    },
    usernameErrors() {
      const errors = [];
      if (!this.$v.username.$dirty) return errors;
      !this.$v.username.maxLength &&
        errors.push("username must be at most 10 characters long");
      !this.$v.username.required &&
        errors.push("El nombre de usuario es obligatorio.");
      return errors;
    },
    emailErrors() {
      const errors = [];
      if (!this.$v.email.$dirty) return errors;
      !this.$v.email.email && errors.push("Must be valid e-mail");
      !this.$v.email.required && errors.push("E-mail is required");
      return errors;
    },
    nombreErrors() {
      const errors = [];
      if (!this.$v.nombre.$dirty) return errors;
      !this.$v.nombre.maxLength &&
        errors.push("El nombre debe contener 25 caracteres máximo");
      !this.$v.nombre.required && errors.push("El nombre es obligatorio.");
      return errors;
    },
    apellidosErrors() {
      const errors = [];
      if (!this.$v.apellidos.$dirty) return errors;
      !this.$v.apellidos.maxLength &&
        errors.push("Los apellidos deben contener 25 caracteres máximo");
      !this.$v.apellidos.required &&
        errors.push("Los apellidos son obligatorios.");
      return errors;
    },

    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.minLength &&
        errors.push("La contraseña debe contener mínimo 8 caracteres");
      !this.$v.password.required &&
        errors.push("La contraseña es obligatoria.");
      return errors;
    },
    repeatPasswordErrors() {
      const errors = [];
      if (!this.$v.repeatPassword.$dirty) return errors;
      !this.$v.repeatPassword.sameAsPassword &&
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
          username: this.username,
          email: this.email,
          password: this.password,
          nombre: this.nombre,
          apellidos: this.apellidos,
        };
        this.respuesta = await usersService.registerUsername(data);

        if (
          this.respuesta != null &&
          this.respuesta.status >= 200 &&
          this.respuesta.status <= 399
        ) {
          this.clear();
          setTimeout(() => this.$router.push({ path: "/login" }), 1500);
        }
        this.loadingSubmitButton = false;
      }
    },
    clear() {
      this.$v.$reset();
      this.username = "";
      this.email = "";
      this.nombre = "";
      this.apellidos = "";
      this.password = "";
      this.repeatPassword = "";
      this.checkbox = false;
    },
  },
};
</script>

<style></style>
