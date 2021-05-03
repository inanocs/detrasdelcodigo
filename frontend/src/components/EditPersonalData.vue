<template>
  <v-form class="p-2" @submit.prevent="submit">
    <v-alert v-if="respuesta != null" outlined :type="respuestaType" text>
      {{ respuesta.mensaje }}
    </v-alert>
    <h4 class="primary--text mb-5">Editar datos personales</h4>
    <v-text-field
      filled
      outlined
      v-model="nombre"
      :error-messages="nombreErrors"
      :counter="25"
      label="Nombre"
      @input="$v.nombre.$touch()"
      @blur="$v.nombre.$touch()"
    ></v-text-field>
    <v-text-field
      filled
      outlined
      v-model="apellidos"
      :error-messages="apellidosErrors"
      :counter="25"
      label="Apellidos"
      @input="$v.apellidos.$touch()"
      @blur="$v.apellidos.$touch()"
    ></v-text-field>
    <v-text-field
      filled
      outlined
      v-model="email"
      :error-messages="emailErrors"
      label="E-mail"
      @input="$v.email.$touch()"
      @blur="$v.email.$touch()"
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
import { mapState, mapMutations } from "vuex";
import { validationMixin } from "vuelidate";
import { required, maxLength, email } from "vuelidate/lib/validators";
const userService = require("../api/users");
export default {
  name: "EditPersonalData",
  mixins: [validationMixin],
  validations: {
    email: { required, email },
    nombre: { required, maxLength: maxLength(25) },
    apellidos: { required, maxLength: maxLength(25) },
  },

  data: () => ({
    nombre: "",
    apellidos: "",
    email: "",
    loadingSubmitButton: false,
    respuesta: null,
  }),
  computed: {
    ...mapState({
      user: (state) => state.users.user,
    }),
    respuestaType() {
      return this.respuesta.status >= 200 && this.respuesta.status <= 399
        ? "success"
        : "error";
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
  },
  methods: {
    ...mapMutations(["setUser", "setAuthUser"]),
    async submit() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.loadingSubmitButton = true;
        const data = {
          email: this.email,
          nombre: this.nombre,
          apellidos: this.apellidos,
        };

        // TODO: Hacer en el API la acción de editar usuario
        this.respuesta = await userService.actualizarDatosPersonales(data);

        if (this.respuestaType == "success") {
          const newUser = { ...this.user };
          newUser.email = this.email;
          newUser.nombre = this.nombre;
          newUser.apellidos = this.apellidos;
          this.setUser(newUser);
          this.setAuthUser(newUser);
        }
        this.loadingSubmitButton = false;
      }
    },
    clear() {
      this.$v.$reset();
      this.email = "";
      this.nombre = "";
      this.apellidos = "";
    },
  },

  created() {
    this.nombre = this.user.nombre;
    this.apellidos = this.user.apellidos;
    this.email = this.user.email;
  },
};
</script>

<style></style>
