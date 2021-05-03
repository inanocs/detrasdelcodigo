const { BASE_URL, AUTH_ENDPOINT, TOKEN_PREFIX } = require("../config/");
const authService = require("../auth");
exports.findAll = async function findAll() {
  const res = await fetch(`${BASE_URL}/users/`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
    },
  });

  const data = await res.json();

  return data;
};
exports.findByUsername = async function findByUsername(username) {
  const res = await fetch(`${BASE_URL}/users/${username}`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
    },
  });

  const data = await res.json();

  return data;
};
exports.findPostsByUsername = async function findPostsByUsername(username) {
  const res = await fetch(`${BASE_URL}/users/${username}/posts`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
    },
  });

  const data = await res.json();

  return data;
};

exports.registerUsername = async function registerUsername(username) {
  const res = await fetch(`${BASE_URL}/users/`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify(username),
  });

  const data = await res.json();

  if (res.status >= 200 && res.status <= 399) {
    return { status: res.status, mensaje: "Usuario creado con éxito", data };
  }
  return { status: res.status, ...data };
};

exports.actualizarAvatar = async function actualizarAvatar(formData) {
  try {
    const res = await fetch(`${AUTH_ENDPOINT}/edit/avatar`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
        Authorization: `${TOKEN_PREFIX} ${authService.getUserSessionToken()}`,
      },
      method: "PUT",
      body: formData,
    });

    const data = await res.json();

    return {
      status: res.status,
      mensaje: "Avatar actualizado con éxito",
      ...data,
    };
  } catch (err) {
    return err;
  }
};

exports.actualizarDatosPersonales = async function actualizarDatosPersonales(
  formData
) {
  try {
    const res = await fetch(`${AUTH_ENDPOINT}/edit/personaldata`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
        "Content-Type": "application/json",
        Authorization: `${TOKEN_PREFIX} ${authService.getUserSessionToken()}`,
      },
      method: "PUT",
      body: JSON.stringify(formData),
    });

    const data = await res.json();

    return {
      status: res.status,
      mensaje: "Información actualizada con éxito",
      ...data,
    };
  } catch (err) {
    return err;
  }
};
exports.actualizarPassword = async function actualizarPassword(formData) {
  try {
    const res = await fetch(`${AUTH_ENDPOINT}/edit/password`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
        "Content-Type": "application/json",
        Authorization: `${TOKEN_PREFIX} ${authService.getUserSessionToken()}`,
      },
      method: "PUT",
      body: JSON.stringify(formData),
    });

    if (res.status != 204) {
      const data = await res.json();

      return { status: res.status, ...data };
    }
    return {
      status: res.status,
      mensaje: "Contraseña actualizada con éxito",
    };
  } catch (err) {
    console.log(err);
    return err;
  }
};
