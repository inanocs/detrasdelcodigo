const authService = require("../../api/auth");
const jwt = require("jsonwebtoken");

exports.decodeToken = function decodeToken() {
  return jwt.verify(
    authService.getUserSessionToken(),
    process.env.VUE_APP_JWT_SECRET
  );
};
