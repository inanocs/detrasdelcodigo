<template>
  <div class="about mt-16">
    <h1>This is an about page</h1>
    <pre>{{ user }}</pre>
  </div>
</template>

<script>
const authService = require("../api/auth");
export default {
  data: () => ({
    user: null,
  }),

  async created() {
    const info = await authService.getUserLoggedInfo();
    this.user = info;
  },
};
</script>

<style></style>
