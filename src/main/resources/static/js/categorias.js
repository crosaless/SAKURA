class DTOCategoria {
    nombre;
    tipo_talle;
  
    constructor(nombre, tipo_talle) {
      this.nombre = nombre;
      this.tipo_talle = tipo_talle;

    }
  }

  const checkboxes = document.querySelectorAll('.talles');
      
  // Iteramos sobre cada checkbox
  checkboxes.forEach(function(checkbox) {
      // Añadimos un event listener para el evento 'change'
      checkbox.addEventListener('change', function() {
          // Si el checkbox actual está marcado
          if (this.checked) {
              // Deshabilitamos todos los demás checkboxes
              checkboxes.forEach(function(otherCheckbox) {
                  if (otherCheckbox !== checkbox) {
                      otherCheckbox.disabled = true;
                  }
              });
          } else {
              // Si el checkbox actual está desmarcado, habilitamos todos los checkboxes
              checkboxes.forEach(function(otherCheckbox) {
                  otherCheckbox.disabled = false;
              });
          }
      });
  });
  
function crear() {
  let nombre = document.getElementById("nombre");
  let checkboxes = document.getElementsByClassName("talles");

  let seleccionTalle = "";
  for(i=0;i<checkboxes.length;i++){
      if (checkboxes[i].checked) {
          seleccionTalle = checkboxes[i].getAttribute('nombre');
          console.log(seleccionTalle)
      }
  }
    fetch(domain + "/api/categorias/nueva", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(
         new DTOCategoria(
            nombre.value, 
            seleccionTalle)
    ),
    }).then(function (response) {
      console.log(response.status);
      if (response.status == 200) {
        alert("La categoria se ha creado correctamente");
        window.location.href = domain + "/admin/inicio";
      } else {
        alert("Se ha producido un error, intente nuevamente");
      }
    });
}

function actualizar(id) {
  let nombre = document.getElementById("nombre");
  let checkboxes = document.getElementsByClassName("talles");

  let seleccionTalle = "";
  for(i=0;i<checkboxes.length;i++){
      if (checkboxes[i].checked) {
          seleccionTalle = checkboxes[i].getAttribute('nombre');
          console.log(seleccionTalle)
      }
  }
    fetch(domain + "/api/categorias/actualizar?id="+id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(
         new DTOCategoria(
            nombre.value, 
            seleccionTalle)
    ),
    }).then(function (response) {
      console.log(response.status);
      if (response.status == 200) {
        alert("La categoria se ha actualizado correctamente");
        window.location.href = domain + "/admin/inicio";
      } else {
        alert("Se ha producido un error, intente nuevamente");
      }
    });
}

function darBaja(id) {
  if (confirm("¿Esta seguro que desea dar de baja esta categoria?")) {
    fetch(domain + "/api/categorias/eliminar?id=" + id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(function (response) {
      console.log(response.status);
      if (response.status == 200) {
        alert("La categoria se ha dado de baja");
        window.location.href = domain + "/admin/inicio";
      } else {
        alert("Se ha producido un error, intente nuevamente");
      }
    });
  }
}
