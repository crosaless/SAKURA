
class SignUpRequest {
  username;
  email;
  roles = []
  password;

  constructor(email, username, password, roles) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
}

function darAltaUsuario(){
  event.preventDefault()
  let email = document.getElementById("fieldEmail");
  let usuario = document.getElementById("fieldUsername");
  let pass = document.getElementById("exampleInputPassword1");
  let checkboxes = document.getElementsByClassName("roles");

  let roles = []
  for(i=0;i<checkboxes.length;i++){
      if (checkboxes[i].checked) {
          roles.push(checkboxes[i].getAttribute('nombre'));
      }
  }

  fetch(domain+"/api/auth/signup", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(new SignUpRequest(email.value, usuario.value, pass.value,roles)),
  }).then(function (response) {
    if (response.status == 200) {
      response.json().then((json) => {
        console.log(json);
        alert("Usuario creado existosamente");
        window.location.reload();
      });
    } else {
      response.json().then((json) => {
        console.log(json);
        alert(JSON.parse(JSON.stringify(json)).message);
      });
    }
  });
}

function editarUsuario(id){

  event.preventDefault()
  class dtoUsuarioEdit{
    id; username;password; email; roles;
    constructor(id,username, password,email, roles) {
      this.email = email;
      this.username = username;
      this.password = password;
      this.rol = roles;
      this.id=id;
    }
  }

  let email = document.getElementById("fieldEmail");
  let username = document.getElementById("username");
  let password = document.getElementById("password");
  let checkboxes = document.getElementsByClassName("roles");
  const params = new URL(window.location).searchParams;
  const oldUsername = params.get('username'); 

  let roles = []
  for(i=0;i<checkboxes.length;i++){
      if (checkboxes[i].checked) {
          roles.push(checkboxes[i].getAttribute('nombre'));
      }
  }

  fetch(domain+"/api/auth/editUser", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(new dtoUsuarioEdit(id, username.value,password.value, email.value,roles)),
  }).then(function (response) {
    if (response.status == 200) {
      response.json().then((json) => {
        console.log(json);
        alert("Usuario editado existosamente");
        window.location.href=url+"/sistema/administracion/editar/usuario/?username="+username.value;    //CORREGIR PORQUE REDIRECCIONA A UN USUARIO NO EXISTENTE CUANDO SE MODIFICA EL USERNAME
      });
    } else {
      response.json().then((json) => {
        console.log(json);
        alert(JSON.parse(JSON.stringify(json)).message);
      });
    }
  });

}

function darBajaUsuario(id){
  fetch(domain+"/api/auth/darBaja?id="+id, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    }
  }).then(function (response) {
    if (response.status == 200) {
      response.json().then((json) => {
        console.log(json);
        alert("Usuario dado de baja exitosamente");
        window.location.reload();    
      });
    } else {
      response.json().then((json) => {
        console.log(json);
        alert(JSON.parse(JSON.stringify(json)).message);
      });
    }
  });

}

function registrarCliente() {
  event.preventDefault()
  let email = document.getElementById("fieldEmail");
  let usuario = document.getElementById("fieldUsername");
  let pass = document.getElementById("exampleInputPassword1");
  let newUser = new SignUpRequest(email.value, usuario.value, pass.value,["cliente"]);

  fetch(domain+"/api/auth/signup", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newUser),
  }).then(function (response) {
    if (response.status == 200) {
      response.json().then((json) => {
        console.log(json);
        alert("Se ha registrado exitosamente, por favor inicie sesión");
        window.location.href = domain+"/login";
      });
    } else {
      response.json().then((json) => {
        console.log(json);
        alert(JSON.parse(JSON.stringify(json)).message);
      });
    }
  });
}
