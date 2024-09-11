document.addEventListener('DOMContentLoaded', function() {
    const usuario = "negoAdmin"/*user.id*/; 
    document.getElementById('usuario').textContent = usuario;

    const fechaActual = new Date().toLocaleDateString();
    document.getElementById('fechaActual').textContent = fechaActual;

    document.getElementById('downloadPdf').addEventListener('click', function() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();
        
        doc.setFontSize(18);
        doc.setFont("helvetica", "bold");
        doc.text("SAKURA", 10, 10);
        
        doc.setFontSize(16);
        doc.setFont("helvetica", "bold");
        doc.text("Ranking de Productos puntuados", 10, 20);
        
        doc.setFontSize(12);
        doc.setFont("helvetica", "normal");
        doc.text(`Generado por: ${usuario} el ${fechaActual}`, 10, 30);

        
        const startY = 40; 

        doc.autoTable({
            startY: startY,
            head: [['#', 'Nombre del Producto', 'Cantidad de Me Gusta', 'Cantidad de No Me Gusta']],
            body: Array.from(document.querySelectorAll('.table tbody tr')).map(row => {
                const cells = row.querySelectorAll('td, th');
                return [
                    cells[0].textContent,
                    cells[2].textContent,
                    cells[3].textContent,
                    cells[4].textContent
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

        doc.save('reporteRankingPuntuados.pdf');
    });
});

      


