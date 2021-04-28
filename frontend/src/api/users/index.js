const { BASE_URL } = require("../config/");

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
