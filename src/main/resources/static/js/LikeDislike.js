// Script adicional para manejar "like" y "dislike"
document.addEventListener('DOMContentLoaded', function() {
    const idCliente = user.id;
    const idProducto = productoId;
    const likeButton = document.getElementById('likeButton');
    const dislikeButton = document.getElementById('dislikeButton');

    likeButton.addEventListener('click', function() {
        puntuarProducto(idProducto, idCliente, 1);
    });

    dislikeButton.addEventListener('click', function() {
        puntuarProducto(idProducto, idCliente, 2);
    });

    function puntuarProducto(idProducto, idCliente, puntuacion) {
        fetch(`/productos/${idProducto}/puntuacion`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                'idCliente': idCliente,
                'puntuacion': puntuacion
            })
        }).then(response => {
            if (response.ok) {
                alert("la puntuacion se a guardado correctamente")
                window.location.reload();
            } else {
                console.error('Error al puntuar el producto');
            }
        }).catch(error => console.error('Error:', error));
    }
});

