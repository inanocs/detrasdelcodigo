<template>
  <div class="mt-3">
    <h1>Disfruta nuestras mejores categorías</h1>
    <v-carousel
      v-model="model"
      hide-delimiter-background
      cycle
      show-arrows-on-hover
      v-if="categorias.length != 0"
    >
      <v-carousel-item
        v-for="categoria in categorias"
        :key="categoria.idcategoria"
        @click="navigate(categoria.idcategoria)"
        eager
        :src="categoria.img"
      >
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-3 bg-dark p-2">{{ categoria.descripcion }}</div>
        </v-row>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<script>
const categoriaService = require("../api/categoria");
export default {
  name: "CategoriasCarousel",
  data() {
    return {
      colors: ["primary", "secondary", "yellow darken-2", "red", "orange"],
      model: 0,
      categorias: [],
      categoriasImg: [
        "https://hitech-webdesign.com/wp-content/uploads/2019/03/desarrollo-web.jpg",
        "https://owius.com/wp-content/uploads/2019/07/desarrollo-de-aplicaciones-moviles-hibridas.jpg",
        "https://www.itmastersmag.com/wp-content/uploads/2021/01/shutterstock_1078387013-2048x1024.jpg",
        "https://bs-uploads.toptal.io/blackfish-uploads/blog/post/seo/og_image_file/og_image/15493/0712-Bad_Practices_in_Database_Design_-_Are_You_Making_These_Mistakes_Dan_Social-754bc73011e057dc76e55a44a954e0c3.png",
      ],
    };
  },
  methods: {
    navigate(key) {
      console.log(key);
    },
  },
  async created() {
    const categorias = await categoriaService.findAll();
    this.categorias = categorias.map((categoria, idx) => ({
      img: this.categoriasImg[idx],
      ...categoria,
    }));
  },
};
</script>

<style></style>
