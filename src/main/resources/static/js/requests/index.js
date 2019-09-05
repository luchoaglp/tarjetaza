$(function() {

    $(".button-collapse").sideNav();

    let requests = [];

    const $table = $('#table');
    const $tableBody = $table.find('tbody');
    const $tableBodyTr = $tableBody.find('tr');

    $table.DataTable({
        "order": [[ 0, "desc" ]],
        "scrollCollapse": true,
    });

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

                if(!requests.includes(id)) {
                    $checkbox.prop('checked', true);
                    requests.push(id);
                } else {
                    $checkbox.prop('checked', false);
                    requests = requests.filter(request => request !== id);
                }
            }
        }
        //console.log(requests);
    });

    $('#submit').click(function() {

        if(requests.length > 0) {

            $.ajax({
              type : "POST",
              url : "/api/requests/process",
              data : {
                requests: requests
              }

            }).done(function(data) {

                location.reload();

            }).fail(function(jqXHR, textStatus) {
                console.log(jqXHR, textStatus);
            });

        }
    });
});