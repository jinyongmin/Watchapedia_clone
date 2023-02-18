window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('datatablesSimple2');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});
