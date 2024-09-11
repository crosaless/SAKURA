
function crearVista(){
    let nombre = document.getElementById("vista").value;

    fetch(domain + "/api/v1/vistas/crear?nombre="+nombre, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
  
      }).then(function (response) {
        console.log(response.status);
        if (response.status == 200) {
          alert("La vista se ha dado de alta correctamente");
          window.location.reload();
        } else {
          alert("Se ha producido un error, intente nuevamente");
        }
      }); 
}

function darBajaVista(id){


    fetch(domain + "/api/v1/vistas/baja?id="+id, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
  
      }).then(function (response) {
        console.log(response.status);
        if (response.status == 200) {
          alert("La vista se ha dado de baja correctamente");
          window.location.reload();
        } else {
          alert("Se ha producido un error, intente nuevamente");
        }
      }); 
  }

  
function actualizarVista(id){

    let vista = document.getElementById("vista").value;
    fetch(domain + "/api/v1/vistas/editar?id="+id+"&nombre="+vista, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
  
      }).then(function (response) {
        console.log(response.status);
        if (response.status == 200) {
          alert("La vista se ha actualizado correctamente");
          window.location.reload();
        } else {
          alert("Se ha producido un error, intente nuevamente");
        }
      }); 
  }