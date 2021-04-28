const { BASE_URL, AUTH_ENDPOINT, TOKEN_PREFIX } = require("../config/");
const authService = require("../auth");

exports.crearComentario = async function crearComentario(comentario) {
  try {
    const res = await fetch(`${BASE_URL}/comentarios/`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
        "Content-Type": "application/json",
        Authorization: `${TOKEN_PREFIX} ${authService.getUserSessionToken()}`,
      },
      method: "POST",
      body: JSON.stringify(comentario),
    });

    const data = await res.json();

    return { status: res.status, mensaje: "Comentario creado con éxito", data };
  } catch (err) {
    return err;
  }
};
