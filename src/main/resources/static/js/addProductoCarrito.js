console.log("loaded");
const button = document.getElementById("add-producto-btn");
const cantidad = document.getElementById("cantidad");
const ir_Carrito = document.getElementById("ir-carrito");

button.addEventListener("click", function () {
  agregarACarrito();
});

function agregarACarrito() {
let select = document.getElementById("talles");
let talle = select.options[select.selectedIndex].text;
  const productoId = window.location.pathname.split("/")[2];
  const item = { producto: productoId, cantidad: cantidad.value, talle: talle};
  const user = JSON.parse(sessionStorage.getItem("JwtResponse"));
  if (user == null) {
    if (confirm("Inicie sesion para continuar con la compra")) {
      window.location.href = domain+"/login";
    }
  }
//https://ecommerce-production-5d12.up.railway.app
  fetch(
    domain+"/api/v1/carrito/" +
      user.id +
      "/item",
    {
      method: "POST",
      body: JSON.stringify(item),
      headers: {
        "Content-Type": "application/json",
      },
    }
  ).then((res) => {
    if (res.status == 201) {
      ir_Carrito.style.display = "inline";
    }
  });
}

ir_Carrito.addEventListener("click", function () {
  const user = JSON.parse(sessionStorage.getItem("JwtResponse"));
  window.location.href = "/carrito/" + user.id;
});
