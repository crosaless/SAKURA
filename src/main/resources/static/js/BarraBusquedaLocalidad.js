document.getElementById('searchInput').addEventListener('input', function() {
            var filter = this.value.toUpperCase();
            var rows = document.getElementById('localidadesTable').getElementsByTagName('tr');
            for (var i = 0; i < rows.length; i++) {
                var nameCell = rows[i].getElementsByTagName('td')[0];
                var postalCodeCell = rows[i].getElementsByTagName('td')[1];
                var provinceCell = rows[i].getElementsByTagName('td')[2];
                if (nameCell || postalCodeCell || provinceCell) {
                    var nameText = nameCell.textContent || nameCell.innerText;
                    var postalCodeText = postalCodeCell.textContent || postalCodeCell.innerText;
                    var provinceText = provinceCell.textContent || provinceCell.innerText;
                    if (nameText.toUpperCase().indexOf(filter) > -1 || postalCodeText.toUpperCase().indexOf(filter) > -1 || provinceText.toUpperCase().indexOf(filter) > -1) {
                        rows[i].style.display = '';
                    } else {
                        rows[i].style.display = 'none';
                    }
            }
    }
});

