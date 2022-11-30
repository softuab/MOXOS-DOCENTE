$(document).ready(function () {
    var Data = JSON.parse($('#datosjson').val());
    var token = $("meta[name='_csrf']").attr("content");
    var request, request1;
    var TR;
    $("#fevaluacion").on('submit', function (evt) {
        evt.preventDefault();
        $.ajaxSetup({
            headers: {'X-CSRF-TOKEN': token}
        });
        if (Evaluar(Data))
        {

            var cadena = "id_asignacion=" + $("#id_asignacion").val() + "&id_programa=" + $("#id_programa").val() + "&id_modelo_ahorro=" + $("#id_modelo_ahorro").val();
            $.each(Data, function (i, l) {
                cadena = cadena + ("&cantidad:" + Data[i] + "=" + $("#cantidad" + Data[i]).val() + "&ponderacion:" + Data[i] + "=" + $("#ponderacion" + Data[i]).val());
            });
            request = $.ajax({url: "./ContinuarDefinicionFase.fautapo", method: "POST", data: cadena});
            request.done(function (response) {
                if (response.status === "OK") {
                    var div = document.createElement("div");
                    div.innerHTML = response.message;
                    swal({text: "¿Desea continuar con el registro de parametros de calificacion?",
                        content: div, buttons: {cancel: {text: "No, modificar", value: true, visible: true, className: 'btn btn-danger', }, Registrar: {text: "Si modificar", value: "Registrar", className: 'btn btn-primary', }},
                    })
                            .then((value) => {
                                switch (value) {
                                    case "Registrar":
                                        request1 = $.ajax({url: "./ConfirmarDefinicion.fautapo", method: "POST", data: cadena});
                                        request1.done(function (response) {
                                            if (response.status === "OK") {
                                                window.open('./definirEvaluacion/entrada.fautapo', '_self');
                                            } else {
                                                swal("Advertencia", response.message, "error");
                                            }
                                        });
                                        request1.fail(function (jqXHR, textStatus) {
                                            swal({title: "Oops", text: "Problemas con el servidor o la conexion de red: " + textStatus, icon: "error", buttons: {aceptar: {text: "Aceptar", value: true, visible: true, className: "btn btn-primary", closeModal: true}}});
                                        });
                                        break;
                                    default:
                                        swal({title: "Oops", text: "No se guardaron los datos correctamente", icon: "error", buttons: {aceptar: {text: "Aceptar", value: true, visible: true, className: "btn btn-primary", closeModal: true}}});
                                }
                            });
                } else {
                    swal("Oops", response.message, "error");
                }
            });
            request.fail(function (jqXHR, textStatus) {
                swal("Oops", "Problemas con el servidor o la conexion de red: " + textStatus, "error");
            });
        } else {
            swal({
                title: "Oops",
                text: "La sumatoria total de las ponderaciones debe ser igual 100 puntos",
                icon: "error",
                buttons: {
                    aceptar: {
                        text: "Aceptar",
                        value: true,
                        visible: true,
                        className: "btn btn-primary",
                        closeModal: true
                    }
                }
            });
        }
    });

    function Evaluar(Data)
    {
        var value = 0;
        $.each(Data, function (i, l) {
            value = value + parseInt($("#ponderacion" + Data[i]).val());
        });
        if (value !== 100)
            return false;
        else
            return true;
    }
});