const BASE_URL = "http://localhost:8080";
module.exports = {
  BASE_URL,
  TOKEN_PREFIX: "Bearer",
  AUTH_ENDPOINT: `${BASE_URL}/users/me`,
};
