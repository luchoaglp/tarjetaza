$(function() {

    $(".button-collapse").sideNav();
    $('[data-toggle="popover-hover"]').popover({
        html: true,
        trigger: 'hover',
        placement: 'left'
    });

    const $table = $('#table');

    $table.DataTable({
        order: [[ 0, "desc" ]],
        scrollY: '276px',
        scrollCollapse: true
    });

    $('#table_wrapper').find('label').each(function () {
        $(this).parent().append($(this).children());
    });
    $('#table_wrapper input[type="search"]').attr("placeholder", "Buscar");
    $('#table_wrapper input[type="search"]').removeClass('form-control-sm');
    $('#table_wrapper .dataTables_length').addClass('d-flex flex-row');
    $('#table_wrapper .dataTables_filter').addClass('md-form');
    $('#table_wrapper select').removeClass('custom-select custom-select-sm form-control form-control-sm');
    $('#table_wrapper select').addClass('mdb-select');
    $('#table_wrapper .mdb-select').materialSelect();
    $('#table_wrapper .dataTables_filter').find('label').remove();

    $("#table_filter input").focus();
});