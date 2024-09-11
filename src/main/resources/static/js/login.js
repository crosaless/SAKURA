function getToken() {
  return JSON.parse(window.sessionStorage.getItem("JwtResponse"));
}



document.addEventListener("DOMContentLoaded", function () {
  if (getToken()) {
    document.getElementById("btnLogout").style.display = "inline";
    document.getElementById("btnLogin").style.display = "none";
    document.getElementById("btnCarrito").style.display = "inline";
    document.getElementById("btnFacturas").style.display = "inline";
  }
});



function irCarrito() {
  user = JSON.parse(sessionStorage.getItem("JwtResponse"));
  location.href =
  domain+"/carrito/" + user.id;
}



function verMisCompras(){
  let user = JSON.parse(sessionStorage.getItem('JwtResponse'));
  window.location.href = domain+"/compras/"+user.id;
}

class LoginRequest {
  username;
  password;

  constructor(username, password) {
    this.username = username;
    this.password = password;
  }
}

document.getElementById("btnSubmit").addEventListener("click", loginUsuario);

function loginUsuario(e) {
  e.preventDefault();
  let usuario = document.getElementById("usernametxt");
  let pass = document.getElementById("passwordtxt");
  let user = new LoginRequest(usuario.value, pass.value);
  console.log(user.password + " " + user.username);

  fetch(domain + "/api/auth/signin", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  }).then(function (response) {
    console.log(response.status);
    if (response.status == 200) {
      response.json().then((json) => {
        console.log(json);
        sessionStorage.setItem("JwtResponse", JSON.stringify(json));
        window.location.href = getInitialScreen(JSON.parse(JSON.stringify(json)).roles);
        document.cookie =
          "autenticacion=" +
          JSON.parse(sessionStorage.getItem("JwtResponse")).token;
        +"Path=/; Expires=Sat, 18 Nov 2023 02:25:43 GMT;";
      });
    } else {
      alert("Se ha producido un error, revise las credenciales e intente nuevamente");
      response.json().then((json) => {
          console.log(json);
      });
    }
  });

  //.then(window.location.href = "http://localhost:9000/");
}

function getInitialScreen(roles) {
  let counter = 0;
  let cliente = false;
  let admin_negocio = false;
  let admin_sistema = false;
  for(var i=0;i<roles.length;i++){
    counter +=1;
    switch(roles[i]){
      case "cliente":
        cliente = true;
        break;
      case "admin_negocio":
        admin_negocio = true;
        break;
      case "admin_sistema":
        admin_sistema = true;
        break;
    }
    if(cliente == true && counter == 1){
      return domain;
    }
    if (admin_negocio && counter <= 2){
      return  domain+"/admin/inicio";
    }
    if(admin_sistema && counter <= 3){
      return domain+"/sistema/administracion";
    }


  }
  /*
  switch(roles){
    case "cliente":
      return domain;
    case "admin_negocio":
      return 
    case "admin_sistema":
      return domain+"/sistema/administracion"

  }*/

}

function cerrarSesion() {
  window.sessionStorage.removeItem("JwtResponse");
  document.cookie =
    "autenticacion =; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
  return (window.location.href =
    domain+"/login");
}

function enviarMailContraseña(){
  
  let username = document.getElementById("usernametxt").value;
  fetch(domain + "/api/auth/RestablecerContraseña/enviarMail?username="+username, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  }).then(function (response) {
    if (response.status == 200) {
      response.json().then((json) => {
        alert("Se ha enviado un email con los pasos a seguir para restablecer tu contraseña a la direccion de correo registrada con tu cuenta");
      });
    } else {
      response.json().then((json) => {
        alert("Se ha producido un error al enviar el mail")
        console.log(json);
      });
    }
  });
}

function restablecerPassword(){

  const url = window.location;
  let newPsw = document.getElementById("newPswTxt").value
  let newPsw2 = document.getElementById("newPswTxt2").value
  if(newPsw != newPsw2) {
    alert("Las contraseñas ingresadas no coinciden"); 
    return ;
  }

  const urlObj = new URL(url);
  const params = urlObj.searchParams;
  const token = params.get('token');  
  dto = new dtoContraseña(token,newPsw);
  fetch(domain + "/api/auth/contraseña/restablecer", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(dto)
  }).then(function (response) {
    console.log(response.status);
    if (response.status == 200) {
      response.json().then((json) => {
        console.log(json);
        alert("La contraseña se ha restablecido exitosamente")
        window.location.href = domain+"/login";

      });
    } else {
      response.json().then((json) => {
        alert("Se ha producido un error y no se pudo restablecer la contraseña")
        console.log(json);
      });
    }
  });

}

class dtoContraseña{
  token;
  newPassword;
  constructor(token, newPassword){
    this.token=token;
    this.newPassword=newPassword;

  }

}
