
//Modelo

class DTOUpdateProducto {
  id;
  precio;
  nombre;
  categoria;
  imagen;

  constructor(id, precio, nombre, categoria, imagen) {
    this.id = id;
    this.precio = precio;
    this.nombre = nombre;
    this.categoria = categoria;
    this.imagen = imagen;
  }
}

class DTOCreateProducto{
  nombre;
  precio;
  categoria;
  marca;
  imagen;

  constructor(nombre, precio, categoria, marca, imagen) {
    this.nombre = nombre;
    this.precio = precio;
    this.categoria = categoria;
    this.marca = marca;
    this.imagen = imagen;
  }

}

class DTOTalleStock{
  nombre;
  stock;

  constructor(nombre,stock){
    this.nombre = nombre;
    this.stock = stock;

  }

}

function darBaja(id) {
  if (confirm("¿Esta seguro que desea dar de baja este producto?")) {
    fetch(domain + "/producto/admin/darBaja/" + id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(function (response) {
      console.log(response.status);
      if (response.status == 200) {
        alert("El producto se ha dado de baja");
        window.location.href = domain + "/admin/inicio";
      } else {
        alert("Se ha producido un error, intente nuevamente");
      }
    });
  }
}



function actualizar(id) {
  let nombre = document.getElementById("name");
  let precio = document.getElementById("productPrice");
  let categoria = document.getElementById("categoria");
  let imagen = document.getElementById("imagen");

  fetch(domain + "/producto/admin/actualizar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(
      new DTOUpdateProducto(
        id,
        precio.value,
        nombre.value,
        categoria.value,
        imagen.value
      )
    ),
  }).then(function (response) {
    console.log(response.status);
    if (response.status == 200) {
      alert("El producto se ha actualizado correctamente");
      window.location.reload;
    } else {
      alert("Se ha producido un error, intente nuevamente");
    }
  });
}



function crear() {
    let nombre = document.getElementById("name").value;
    let precio = document.getElementById("productPrice").value;
    let categoria = document.getElementById("categoria").value;
    let marca = document.getElementById("marca").value;
    let imagen = document.getElementById("imagen").value;

    if (!nombre || !precio || !categoria || !marca || !imagen) {
        alert("Por favor, complete todos los campos.");
        return;
    }
    
    let producto = new DTOCreateProducto(nombre, precio, categoria, marca, imagen);
    
    let productoJSON = JSON.stringify(producto);
    //ya cree el dto (javascript)
    fetch(domain + "/producto/nuevo", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: productoJSON
  }).then(function (response) {
    console.log(response.status);
    if (response.status == 200) {
      alert("El producto se ha creado correctamente");
      window.location.reload;
    } else {
      alert("Se ha producido un error, intente nuevamente");
    }
  });
}



function actualizarStock(id){
  let inputs = document.getElementsByClassName("stockTxt");
  let listaDtos = []; 

  for(i=0;i<inputs.length;i++){
    let talle = inputs[i].getAttribute("nombre");
    let dto = new DTOTalleStock(talle, inputs[i].value);
    listaDtos.push(dto);

  }

  fetch(domain + "/producto/actualizar/stock?id="+id, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(
     listaDtos
    ),
  }).then(function (response) {
    console.log(response.status);
    if (response.status == 200) {
      alert("El stock se ha actualizado correctamente");
      window.location.href = domain + "/admin/inicio";
    } else {
      alert("Se ha producido un error, intente nuevamente");
    }
  });

}

