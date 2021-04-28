const { BASE_URL } = require("../config/");
exports.findAll = async function findAll() {
  const res = await fetch(`${BASE_URL}/categorias/`, {
    mode: "cors",
    headers: {
      "Access-Control-Allow-Origin": "http://localhost:9001",
      "Content-Type": "application/json",
    },
    method: "GET",
  });

  const data = await res.json();

  return data;
};
