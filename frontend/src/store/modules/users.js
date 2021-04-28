const service = require("../../api/users/");
const postService = require("../../api/posts/");
const authService = require("../../api/auth");

const initialState = {
  users: [],
  user: {},
  userposts: [],
  authUser: {},
};

export default {
  state: initialState,
  mutations: {
    setUsers(state, payload) {
      state.users = payload;
    },
    setUser(state, payload) {
      state.user = payload;
    },
    setUserPosts(state, payload) {
      state.userposts = payload;
    },

    setAuthUser(state, payload) {
      state.authUser = payload;
    },
  },
  actions: {
    async dispatchUsers({ commit }) {
      const users = await service.findAll();
      commit("setUsers", users);
    },
    async dispatchUser({ commit }, username) {
      const user = await service.findByUsername(username);
      commit("setUser", user);
    },
    async dispatchUserPosts(
      { commit },
      payload = {
        username,
        titulo: "",
        pagina: 0,
        size: 6,
        sort: "",
        tag: "",
        categoria: "",
      }
    ) {
      const { username, ...metadata } = payload;
      const posts = await postService.findPostsByUsername(username, payload);
      commit("setUserPosts", posts);
    },

    async dispatchAuthUser({ commit }) {
      const token = authService.getUserSessionToken();
      if (token) {
        const user = await authService.getUserLoggedInfo();
        commit("setAuthUser", user);
      }
    },
  },
};
