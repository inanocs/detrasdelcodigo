const { BASE_URL, AUTH_ENDPOINT, TOKEN_PREFIX } = require("../config/");
const authService = require("../auth");

exports.findAll = async function findAll({
  titulo = "",
  pagina = 0,
  size = 6,
}) {
  const res = await fetch(
    `${BASE_URL}/posts/?titulo=${titulo}&page=${pagina}&size=${size}`,
    {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
      },
    }
  );

  const data = await res.json();

  return data;
};

exports.findById = async function findById(idpost) {
  try {
    const res = await fetch(`${BASE_URL}/posts/id/${idpost}`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
      },
    });

    const data = await res.json();

    return data;
  } catch (err) {
    return { status: err.status, ...err };
  }
};

exports.createPost = async function createPost(formData) {
  try {
    const res = await fetch(`${AUTH_ENDPOINT}/posts`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
        Authorization: `${TOKEN_PREFIX} ${authService.getUserSessionToken()}`,
      },
      method: "POST",
      body: formData,
    });

    const data = await res.json();

    return { status: res.status, mensaje: "Post creado con éxito", ...data };
  } catch (err) {
    return err;
  }
};

exports.findPostsByUsername = async function findPostsByUsername(
  username,
  { titulo = "", pagina = 0, size = 6, sort = "", tag = "", categoria = "" }
) {
  const res = await fetch(
    `${BASE_URL}/posts/${username}/?titulo=${titulo}&pagina=${pagina}&size=${size}&sort=${sort}&categoria=${categoria}&tag=${tag}`,
    {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
      },
    }
  );

  const data = await res.json();

  return data;
};
exports.getPostsExtraData = async function getPostsExtraData(username) {
  const res = await fetch(`${BASE_URL}/posts/${username}/extradata/`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
    },
  });

  const data = await res.json();

  return data;
};
