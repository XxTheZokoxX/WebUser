$(document).ready(function () {

    $.ajax({
        "url": "http://localhost:8084/CRUD_Vista_JSTL/api/users",
        "type": "GET",
        "success": function (respuestaArrayUsers) {

            respuestaArrayUsers.forEach(function (user) {
                let htmlTRnuevaFila = $("#data_table").html() +
                        `<tr><td>${user.email}</td>
                        <td>${user.name}</td>
                    </tr>`;
                $("#data_table").html(htmlTRnuevaFila);
            });
        },
        "error": function () {
            alert("Error en peticion AJAX");
        }
    });

    $("#btnSend").click(function () {
        let objUser = {
            "email": $("#email").val(),
            "password": $("#password").val(),
            "name": $("#name").val(),
            "age": $("#age").val()
        };
        $.post({"url": "api/users",
            "contentType": "application/json;charset=UTF-8",
            "data": JSON.stringify(objUser),
            "success":
                    function (respuestaUserObj) {
                        $("#id").val(respuestaUserObj.id);
                    }
        });
    });
    $("#btnDel").click(function () {
        $.ajax({"url": "api/users",
            "method": "DELETE",
            "contentType": "application/json;charset=UTF-8",
            // Enviamos como JSON
            "data": JSON.stringify({ "id": $("#id").val() }), 
            "success":
                    function (respuestaText) {
                        alert(respuestaText);
                    }, // Enviamos el obj como parámetros de formulario
            "error":
                    function (respuestaText) {
                        alert(respuestaText);
                    }
        });
    });
    
    $("#btnUpdate").click(function () {
        let objUser = {
            "id": $("#id").val(),
            "email": $("#email").val(),
            "password": $("#password").val(),
            "name": $("#name").val(),
            "age": $("#age").val()
        };
        $.ajax({"url": "api/users",
            "method": "PUT",
            "contentType": "application/json;charset=UTF-8",
            "data": JSON.stringify(objUser),
            "success":
                    function (respuestaUserObj) {
                        $("#id").val(respuestaUserObj.id);
                    }
        });
    });
});
