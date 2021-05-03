import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

const authService = require("../api/auth");

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: { title: "Inicio" },
  },
  {
    path: "/about",
    name: "About",
    meta: { title: "About" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/users/:username",
    name: "Username",
    meta: { title: "Perfil" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Username.vue"),
  },
  {
    path: "/register",
    name: "Registro",
    meta: { title: "Registro" },

    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Registro.vue"),
  },
  {
    path: "/login",
    name: "Login",
    meta: { title: "Iniciar sesión" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Login.vue"),
  },
  {
    path: "/crearpost",
    name: "CreacionPost",
    meta: { title: "Crear post" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/CrearPost.vue"),
  },
  {
    path: "/search",
    name: "search",
    meta: { title: "Buscar posts" },
    query: { titulo: "" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Search.vue"),
  },
  {
    path: "/posts/:idpost",
    name: "post",
    meta: { title: "Post" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/PostView.vue"),
  },
  {
    path: "/categorias/",
    name: "categorias",
    meta: { title: "Categorías" },
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Categorias.vue"),
  },
  {
    path: "*",
    name: "NotFound",
    meta: { title: "404 - NOT FOUND" },
    component: () => import(/* webpackChunkName: "about" */ "../views/404.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

const authRoutes = ["Login", "Registro"];
const needAuthRoutes = ["CreacionPost"];
router.beforeEach((to, from, next) => {
  //Aqui checas, si tiene una sesion o un token y el usuario quiere ir a la ruta login, no lo permites //y lo mandas a la raiz

  if (authRoutes.includes(to.name) && authService.getUserSessionToken()) {
    router.push("/");
  } else if (
    needAuthRoutes.includes(to.name) &&
    !authService.getUserSessionToken()
  ) {
    router.push("/login");
  }
  next();
});

router.afterEach((to, from) => {
  document.title = to.meta.title || DEFAULT_TITLE;
});

export default router;
