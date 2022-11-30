var token = $("meta[name='_csrf']").attr("content");
var request;
function modificarpassword(input) {
    var data = {
        id_persona: $(input).data("persona"),
        indice: $(input).data("indice")
    };
    $.get('./ModificarUsuario.fautapo', data, function (data)
    {
        $('#content').html(data);
        AbrirModal("#idmodificarusuario");
    });
}
function AbrirModal(id)
{
    $(id).modal({
        keyboard: false
    });
}
$("#guardardatos").on("click", function () {
    if ($("#repetircontrasenia").data("estado") === false) {
        document.getElementById("repetircontrasenia").className = "form-control is-invalid";
    } else {
        $.ajaxSetup({
            headers: {'X-CSRF-TOKEN': token}
        });
        var data = {
            idnumber: $("#idnumber").val(),
            password: $("#contrasenia").val(),
            confirmar_password: $("#repetircontrasenia").val(),
            firstname: $("#firstname").val(),
            lastname: $("#lastname").val(),
            email: $("#email").val(),
            id: $("#iduser").val(),
            usuario: $("#usernamemoodle").val(),
            indice: $("#indice").val()
        };
        request = $.ajax({url: "./GuardarCambios.fautapo", method: "POST", data: data, datatype: 'json'});
        request.done(function (response) {
            if (response.status === "OK") {
                $("#idmodificarusuario .close").click();
                $("#pass" + response.indice).html(response.password);
            } else {
                swal("Oops", response.message, "error");
            }
        });
    }
});