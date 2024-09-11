//generar grafico
document.addEventListener('DOMContentLoaded', function() {
  const usuario = /*user.id*/ "negoAdmin"; // Usuario autenticado
  document.getElementById('usuario').textContent = usuario;

   const fechaActual = new Date().toLocaleDateString();
   document.getElementById('fechaActual').textContent = fechaActual;

   const urlParams = new URLSearchParams(window.location.search);
   const fechaDesde = new Date(urlParams.get('fechaDesde') || 'No especificada');
   const fechaHasta = new Date(urlParams.get('fechaHasta') || 'No especificada');
   document.getElementById('rangoFechas').textContent = `${fechaDesde.toISOString().split('T')[0]} al ${fechaHasta.toISOString().split('T')[0]}`;

   
   const ventasTable = document.getElementById('ventasTable');
   const ventas = [];
     for (let i = 1, row; row = ventasTable.rows[i]; i++) {
         let fecha = new Date(row.cells[0].textContent);
         let cantidad = parseInt(row.cells[1].textContent);
        ventas.push({ fecha: fecha, cantidad: cantidad });
     }

   // Crear un array de fechas y ventas con cero inicializados
    const completeSalesData = [];
      for (let d = new Date(fechaDesde); d <= fechaHasta; d.setDate(d.getDate() + 1)) {
          completeSalesData.push({ fecha: new Date(d), cantidad: 0 });
      }

    
    ventas.forEach(venta => {
       const index = completeSalesData.findIndex(data => data.fecha.toISOString().split('T')[0] === venta.fecha.toISOString().split('T')[0]);
       if (index !== -1) {
            completeSalesData[index].cantidad = venta.cantidad;
       }
    });

  
   const salesLabels = completeSalesData.map(v => v.fecha.toISOString().split('T')[0]);
   const salesData = completeSalesData.map(v => v.cantidad);

   
   var ctx = document.getElementById('salesChart').getContext('2d');
   var salesChart = new Chart(ctx, {
      type: 'line',
        data: {
            labels: salesLabels,
            datasets: [{
              label: 'Cantidad de ventas',
              data: salesData,
              borderColor: 'rgba(0, 123, 255, 1)',
              backgroundColor: 'rgba(0, 123, 255, 0.1)',
              fill: true
            }]
        },
       options: {
            scales: {
                y: {
                   beginAtZero: true
                }
            }
        }
    });
});
        


//Hacer el PDF
document.addEventListener('DOMContentLoaded', function() {
    const usuario = "negoAdmin";
    const fechaActual = new Date().toLocaleDateString();

    const urlParams = new URLSearchParams(window.location.search);
    const fechaDesde = urlParams.get('fechaDesde') || 'No especificada';
    const fechaHasta = urlParams.get('fechaHasta') || 'No especificada';

    document.getElementById('downloadPdf').addEventListener('click', function() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        doc.setFontSize(18);
        doc.setFont("helvetica", "bold");
        doc.text("SAKURA", 10, 10);
        
        // Título del reporte
        doc.setFontSize(16);
        doc.setFont("helvetica", "bold");
        doc.text("Ranking de los 10 productos más comprados", 10, 20);

        // Información del reporte
        doc.setFontSize(12);
        doc.setFont("helvetica", "normal");
        doc.text(`Generado por: ${usuario} el ${fechaActual}`, 10, 30);
        doc.text(`Rango de fechas: ${fechaDesde} al ${fechaHasta}`, 10, 40);

        // Capturar el gráfico como imagen
        html2canvas(document.querySelector(".chart-container"), { scale: 2, useCORS: true }).then(canvas => {
            const imgData = canvas.toDataURL('image/jpeg', 0.98);

            // Calcular el tamaño de la imagen para que se ajuste a la página A4
            const pageWidth = doc.internal.pageSize.getWidth();
            const pageHeight = doc.internal.pageSize.getHeight();
            const margen = 10;
            const maxImgWidth = pageWidth - 2 * margen;
            const maxImgHeight = pageHeight - 2 * margen - 50; // Restar espacio para el texto

            let imgWidth = canvas.width;
            let imgHeight = canvas.height;

            // Ajustar el tamaño de la imagen manteniendo la relación de aspecto
            if (imgWidth > maxImgWidth) {
                imgWidth = maxImgWidth;
                imgHeight = (canvas.height * imgWidth) / canvas.width;
            }

            if (imgHeight > maxImgHeight) {
                imgHeight = maxImgHeight;
                imgWidth = (canvas.width * imgHeight) / canvas.height;
            }
            // Aumentar el tamaño del gráfico en el PDF
            //imgWidth *= 1.4;
            //imgHeight *= 1.4;

            // Centrar la imagen en la página
            const imgX = (pageWidth - imgWidth) / 2;
            const imgY = 50;

            doc.addImage(imgData, 'JPEG', imgX, imgY, imgWidth, imgHeight);

            let finalY = 50 + imgHeight + 10;

            // Agregar la tabla al PDF
            doc.autoTable({
                startY: finalY,
                head: [['#', 'Producto', 'Precio']],
                body: Array.from(document.querySelectorAll('.table tbody tr')).map(row => {
                    const cells = row.querySelectorAll('td, th');
                    return [
                        cells[0].textContent,
                        cells[2].textContent,
                        cells[3].textContent
                    ];
                }),
                headStyles: { fillColor: [255, 0, 127] },
                didDrawPage: function (data) {
                    let pageSize = doc.internal.pageSize;
                    let pageHeight = pageSize.height ? pageSize.height : pageSize.getHeight();
                    doc.setFontSize(9);
                    doc.text('Página ' + doc.internal.getNumberOfPages(), 10, pageHeight - 10);
                }
            });

            doc.save('reporteMasComprados.pdf');
        });
    });
});






        

