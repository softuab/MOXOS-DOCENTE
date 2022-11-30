var TR, list, li, h5, p;
var token = $("meta[name='_csrf']").attr("content");
var request;
$(document).on('click', ".btn-primary", function (e)
{
    var type = $(this).data('contenido');
    $.ajaxSetup({
        headers: {'X-CSRF-TOKEN': token}
    });
    switch (type) {
        case "insert":
            list = document.getElementById("listcontenidos");
            desactive("insert");
            document.getElementById('guardarcontenido').disabled = true;
            var data = {
                id_dct_programa_analitico: $("#id_contenido_dct_programa_analitico").val(),
                contenido: $("#contenido").val(),
                objetivo_instructivo: $("#objetivo_instructivo").val(),
                conocimientos: $("#conocimientos").val(),
                habilidades: $("#habilidades").val(),
                valores: $("#valores").val()
            };
            request = $.ajax({url: "./RegistrarContenidos.fautapo", method: "POST", data: data, datatype: 'json'});
            request.done(function (response) {
                if (response.status === "OK") {
                    list.innerHTML += response.message;
                    $("#idModalcontenido .close").click();
                    document.getElementById('guardarcontenido').disabled = false;
                    active("insert");
                } else {
                    swal("Oops", response.message, "error");
                }
            });
            break;
        case "detail":
            var id = $(this).data('edit');
            Detallecontenido('./DetalleContenidos.fautapo', id);
            h5 = document.getElementById("title" + id);
            p = document.getElementById("content" + id);
            AbrirModal('#myModal_contenido');
            break;
        case "update":
            e.preventDefault();
            document.getElementById('guardardatoseditadoscontenido').disabled = true;
            desactive("update");
            var data = {
                id_prg_a_contenido: $("#id_prg_a_contenido_edit").val(),
                contenido: $("#contenido_edit").val(),
                objetivo_instructivo: $("#objetivo_instructivo_edit").val(),
                conocimientos: $("#conocimientos_edit").val(),
                habilidades: $("#habilidades_edit").val(),
                valores: $("#valores_edit").val()
            };
            request = $.ajax({url: "./EditarContenidos.fautapo", method: "POST", data: data, datatype: 'json'});
            request.done(function (response) {
                if (response.status === "OK") {
                    $("#myModal_contenido .close").click();
                    h5.innerHTML = response.title;
                    p.innerHTML = response.message;
                    document.getElementById('guardardatoseditadoscontenido').disabled = false;
                    active("update");
                } else {
                    swal("Oops", response.message, "error");
                }
            });
            break;
        case "delete":
            e.preventDefault();
            list = document.getElementById("listcontenidos");
            swal({title: "¿Desea continuar con la eliminacion del registro?", text: "Presiona aceptar para continuar..", icon: "warning", buttons: {cancel: {text: "Cancelar", value: true, visible: true, className: 'btn btn-danger', }, Registrar: {text: "Aceptar", value: "Eliminar", className: 'btn btn-primary', }},
            }).then((value) => {
                switch (value) {
                    case "Eliminar":
                        var id = $(this).data('delete');
                        li = document.getElementById("li" + id);
                        var data = {id: id}
                        request = $.ajax({url: "./EliminarContenidos.fautapo", method: "POST", data: data, datatype: 'json'});
                        request.done(function (response) {
                            console.log(response);
                            if (response.status === 'OK')
                            {
                                list.removeChild(li);
                            } else
                            {
                                swal({title: "Atencion!", text: response.message, icon: "error", timer: 1500, buttons: {aceptar: {text: "Aceptar", value: true, visible: true, className: "btn btn-primary", closeModal: true}}});
                            }
                        });
                        break;
                    default:
                        swal({title: "Cancelado", text: "Se cancelo la eliminacion del registro.", icon: "success", timer: 1500, buttons: {aceptar: {text: "Aceptar", value: true, visible: true, className: "btn btn-primary", closeModal: true}}});
                }
            });
            break;
    }
});
function desactive(status) {
    $("#status" + status).removeClass("invisible");
    $("#statu" + status + "text").removeClass("invisible");
    $("#status" + status + "start" + status).removeClass("visible");

    $("#status" + status).addClass("visible");
    $("#statu" + status + "text").addClass("visible");
    $("#status" + status + "start" + status).addClass("invisible");
}
function active(status) {
    $("#status" + status).removeClass("visible");
    $("#statu" + status + "text").removeClass("visible");
    $("#status" + status + "start" + status).removeClass("invisible");

    $("#status" + status).addClass("invisible");
    $("#statu" + status + "text").addClass("invisible");
    $("#status" + status + "start" + status).addClass("visible");
}
function AbrirModal(id)
{
    $(id).modal({
        keyboard: false
    });
}
function Detallecontenido(url, id)
{
    var data = {id: id}
    request = $.ajax({url: url, method: "POST", data: data, datatype: 'json'});
    request.done(function (response) {
        console.log(response);
        $("#id_prg_a_contenido_edit").val(response.id_prg_a_contenido);
        $("#contenido_edit").val(response.contenido);
        $("#objetivo_instructivo_edit").val(response.objetivo_instructivo);
        $("#conocimientos_edit").val(response.conocimientos);
        $("#habilidades_edit").val(response.habilidades);
        $("#valores_edit").val(response.valores);
    });
} 