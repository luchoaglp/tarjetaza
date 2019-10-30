$(function() {

    $(".button-collapse").sideNav();
    $('[data-toggle="popover-hover"]').popover({
        html: true,
        trigger: 'hover',
        placement: 'left'
    });

    let credits = [];

    const $table = $('#table');
    const $tableBody = $table.find('tbody');
    const $tableBodyTr = $tableBody.find('tr');

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

    let btnClicked = false;

    $tableBodyTr.on('click', 'a', function(event) {
        event.preventDefault();
        btnClicked = true;
        window.location = $(this).attr('href');
    });

    $table.on('click', 'tr', function() {

        if(!btnClicked) {

            const row = $(this);

            const id = parseInt(row.find("td:nth-child(1)").html());
            const $checkbox = row.children('td:nth-child(9)').children("div.form-check").children("input[type='checkbox']");

            if($checkbox.length > 0) {

                if(!credits.includes(id)) {
                    $checkbox.prop('checked', true);
                    credits.push(id);
                } else {
                    $checkbox.prop('checked', false);
                    credits = credits.filter(credit => credit !== id);
                }
            }
        }
    });

    $('#submit');

    $('#submit').click(function() {

        if(credits.length > 0) {

            $(this).prop("disabled", true);
            //$(this).removeClass('btn-outline-white').addClass('btn-outline-blue-grey')

            $('#envelope').addClass('d-none');
            $('#spinner').removeClass('d-none');

            $.ajax({
              type : "POST",
              url : "/api/credits/process",
              data : {
                credits: credits
              }

            }).done(function(data) {

                location.reload();

            }).fail(function(jqXHR, textStatus) {
                console.log(jqXHR, textStatus);
            });

        }
    });
});