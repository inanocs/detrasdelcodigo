<template>
  <v-footer class="py-4 mt-auto" dark height="auto">
    <v-container class="mx-auto">
      <v-row>
        <p class="col-12 text-center mb-0">
          Copyright: Fernando Cabezas Sánchez
        </p>
        <div class="col-12 text-center mb-0">
          <a href="https://github.com/inanocs" class="list__link">
            <v-chip class="cursor--pointer">
              <v-icon class="mr-2">mdi-github</v-icon>
              <span>@inanocs</span>
            </v-chip>
          </a>
        </div>

        <!-- <v-spacer />

        <v-btn class="mr-0" square title="Go to top">
          <v-icon>mdi-chevron-up</v-icon>
        </v-btn> -->
      </v-row>
    </v-container>
  </v-footer>
</template>
<script>
export default {
  name: "Footer",

  data: () => ({
    items: [
      {
        href: "#!",
        icon: "mdi-twitter",
      },
      {
        href: "#!",
        icon: "mdi-instagram",
      },
      {
        href: "#!",
        icon: "mdi-facebook",
      },
      {
        href: "#!",
        icon: "mdi-google-plus",
      },
      {
        href: "#!",
        icon: "mdi-reddit",
      },
      {
        href: "#!",
        icon: "mdi-discord",
      },
      {
        href: "#!",
        icon: "mdi-pinterest",
      },
    ],
  }),
};
</script>

<style>
.cursor--pointer {
  cursor: pointer;
}
</style>
