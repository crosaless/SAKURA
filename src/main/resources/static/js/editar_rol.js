
function actualizarRol(id){

    let rolName = document.getElementById("rolName");
    let checkboxes = document.getElementsByClassName("permisos");

    var permisosMarcados = [];
    for(i=0;i<checkboxes.length;i++){
        if (checkboxes[i].checked) {
            permisosMarcados.push(checkboxes[i].getAttribute('nombre'));
        }
    }


    fetch(domain + "/api/v1/roles/editar/rol", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(
            rol = new Rol(
                id,
                rolName.value, 
                permisosMarcados)
        ),
      }).then(function (response) {
        console.log(response.status);
        if (response.status == 200) {
          alert("El rol se ha actualizado correctamente");
          window.location.reload();
        } else {
          alert("Se ha producido un error, intente nuevamente");
        }
      }); 
}

function darBajaRol(id){


  fetch(domain + "/api/v1/roles/editar/rol?id="+id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },

    }).then(function (response) {
      console.log(response.status);
      if (response.status == 200) {
        alert("El rol se ha dado de baja correctamente");
        window.location.reload();
      } else {
        alert("Se ha producido un error, intente nuevamente");
      }
    }); 
}

function crearRol(){

  let rolName = document.getElementById("rolName");
  let checkboxes = document.getElementsByClassName("permisos");

  var permisosMarcados = [];
  for(i=0;i<checkboxes.length;i++){
      if (checkboxes[i].checked) {
          permisosMarcados.push(checkboxes[i].getAttribute('nombre'));
      }
  }


  fetch(domain + "/api/v1/roles/crear", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(
          rol = new Rol(
              null,
              rolName.value, 
              permisosMarcados)
      ),

    }).then(function (response) {
      console.log(response.status);
      if (response.status == 200) {
        alert("El rol se ha dado de alta correctamente");
        window.location.reload();
      } else {
        alert("Se ha producido un error, intente nuevamente");
      }
    }); 
}




class Rol{
    id;
    nombre;
    permisos = []; 

    constructor(id, name, permisos){
        this.id=id;
        this.nombre=name;
        this.permisos=permisos;
    }
}


