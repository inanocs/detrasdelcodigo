const { BASE_URL, AUTH_ENDPOINT, TOKEN_PREFIX } = require("../config/");

const getUserSessionToken = () => sessionStorage.getItem("ddcToken");
const setUserSessionToken = () => sessionStorage.setItem("ddcToken", token);

exports.loginUsername = async function loginUsername(username) {
  const res = await fetch(`${BASE_URL}/auth/login`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify(username),
  });

  const data = await res.json();

  if (res.status >= 200 && res.status <= 399 && data.token) {
    sessionStorage.setItem("ddcToken", `${data.token}`);
    return { status: res.status, mensaje: "Usuario logueado con éxito", data };
  }
  return {
    status: res.status,
    ...data,
    mensaje: "Usuario o contraseña incorrectos",
  };
};

exports.getUserLoggedInfo = async function() {
  try {
    const res = await fetch(`${AUTH_ENDPOINT}`, {
      mode: "cors",
      headers: {
        "Access-Control-Allow-Origin": "http://localhost:9001",
        "Content-Type": "application/json",
        Authorization: `${TOKEN_PREFIX} ${getUserSessionToken()}`,
      },
      method: "GET",
    });

    const data = await res.json();

    return data;
  } catch (err) {}
};

exports.getUserSessionToken = function getUserSessionToken() {
  return sessionStorage.getItem("ddcToken");
};

exports.setUserSessionToken = function setUserSessionToken(token) {
  sessionStorage.setItem("ddcToken", token);
};

exports.logout = function logout() {
  sessionStorage.removeItem("ddcToken");
};
