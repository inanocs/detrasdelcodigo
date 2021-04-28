const service = require("../../api/posts/");

const initialState = {
  posts: [],
};

export default {
  state: initialState,
  mutations: {
    setPosts(state, payload) {
      state.posts = payload;
    },
  },
  actions: {
    async dispatchPosts(
      { commit },
      payload = { titulo: "", pagina: 0, size: 6 }
    ) {
      const posts = await service.findAll(payload);
      commit("setPosts", posts);
    },
  },
};
