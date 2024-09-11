
document.getElementById('editar').addEventListener('click', function() {
  document.querySelectorAll('#form-perfil input, #form-perfil select').forEach(function(element) {
        element.removeAttribute('readonly');
        element.removeAttribute('disabled');
  });
  document.getElementById('editar').style.display = 'none';
  document.getElementById('action-buttons').style.display = 'flex';
});
 document.getElementById('cancelar').addEventListener('click', function() {
    window.location.reload();
});

document.getElementById('provincia').addEventListener('change', function() {
    var provinciaId = this.value;
    document.getElementById('hiddenIdProvincia').value = provinciaId; // Update hidden field for idProvincia

    fetch('/cliente/localidades/' + provinciaId)
        .then(response => response.json())
        .then(localidades => {
            console.log(localidades); // Añadido para depuración
            var localidadSelect = document.getElementById('localidad');
            localidadSelect.innerHTML = '';
            localidades.forEach(function(localidad) {
                var option = document.createElement('option');
                option.value = localidad.codigoPostal;
                option.text = localidad.nombreLocalidad;
                localidadSelect.appendChild(option);
            });

            var selectedProvincia = document.querySelector('#provincia option:checked').textContent;
            document.getElementById('hiddenNombreProvincia').value = selectedProvincia;
        })
        .catch(error => {
            console.error('Error fetching localidades:', error);
        });
});

document.getElementById('localidad').addEventListener('change', function() {
    var selectedLocalidad = document.querySelector('#localidad option:checked');
    document.getElementById('hiddenNombreLocalidad').value = selectedLocalidad.textContent;
    document.getElementById('hiddenCodigoPostal').value = selectedLocalidad.value;
    document.getElementById('hiddenIdLocalidad').value = selectedLocalidad.value; // Update hidden field for idLocalidad
});

document.getElementById('form-perfil').addEventListener('submit', function(event) {
    if (document.getElementById('password').value === '') {
        alert('La contraseña no puede estar vacía');
        event.preventDefault();
    }
});


