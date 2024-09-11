document.getElementById('searchInput').addEventListener('input', function() {
            var filter = this.value.toUpperCase();
            var rows = document.getElementById('provinciasTable').getElementsByTagName('tr');
            for (var i = 0; i < rows.length; i++) {
                var nameCell = rows[i].getElementsByTagName('td')[0];
                if (nameCell) {
                    var nameText = nameCell.textContent || nameCell.innerText;
                    if (nameText.toUpperCase().indexOf(filter) > -1) {
                        rows[i].style.display = '';
                    } else {
                        rows[i].style.display = 'none';
                    }
                }
            }
        });

