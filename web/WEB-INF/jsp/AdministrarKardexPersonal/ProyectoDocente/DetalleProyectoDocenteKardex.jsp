<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <META HTTP-EQUIV="Cache-Control" CONTENT="max-age='0'" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <META http-equiv="expires" content="0" />
        <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT" />
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        <title>Sistema Integrado -  Moxos</title>
        <link rel="stylesheet" href="<c:url value='/Public/Css/main.css'/>" type="text/css">
        <link rel="stylesheet" href="<c:url value='/Public/Css/FontAwesome/css/fontawesome-all.css'/>"> 
    </head>
    <body class="app sidebar-mini rtl">
        <c:if test="${!empty id_rol}">
            <main class="app-content3">
                <div class="app-title">
                    <div>
                        <h1><i class="far fa-address-card"></i>&nbsp; ${nombres}</h1> 
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <c:if test="${result.status !=''}">
                            <div class="alert ${result.status} alert-dismissible fade show" role="alert">
                                <strong>Aviso!</strong> ${result.message}.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="row"> 
                    <div class="col-sm-12">
                        <div class="tile"> 
                            <form name="forma" class="unlock-form" action="<c:url value="/IndexKardexPersonal.fautapo"/>" method="GET">     
                                <input type="hidden" name="id_persona" value='${id_persona}' /> 
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fas fa-home"></i></button>
                            </form>
                            <h3 class="title">Proyectos realizados por el docente</h3>
                            <div class="tile-title-w-btn">
                                <input class="form-control mr-sm-2" type="search" id="buscardetalleproyectodocente" placeholder="Buscar titulo"  onkeyup="filterlist('buscardetalleproyectodocente', 'listproyectodocente')">
                                <form name="forma" class="unlock-form" action="<c:url value="/FormularioRegistroProyectoKardex.fautapo"/>" method="GET">     
                                    <input type="hidden" name="id_persona" value='${id_persona}' /> 
                                    <input type="hidden" name="id_persona_kardex" value='${id_persona_kardex}' /> 
                                    <input type="hidden" name="root" value='total' /> 
                                    <button type="submit" class="btn btn-primary icon-btn"> <i class="fas fa-user-plus"></i></button>
                                </form>
                            </div>
                            <div class="tile-body">
                                <ul class="list-unstyled" id="listproyectodocente">
                                    <c:forEach var="detalle" items="${detalles}"> 
                                        <li class="media" id="li${detalle.id_personas_proyecto}">
                                            <img src="<c:url value="/imagenes/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)" data-id="${detalle.id_personas_proyecto}" data-tabla="proyectodocente" onMouseOver="this.style.cursor = 'pointer'">
                                            <div class="media-body">
                                                <h5 class="mt-0 mb-1">${detalle.nombreproyecto}</h5>
                                                <p>
                                                    <b>Inversion:</b>&nbsp;${detalle.inversion}<br>
                                                    <b>Financiador:</b>&nbsp;${detalle.financiador}<br>
                                                    <b>Origen del proyecto:</b>&nbsp;${detalle.origendelproyectoproyecto}<br>
                                                    <b>Gestion:</b>&nbsp;${detalle.gestion}<br>
                                                    <b>Estado:</b>&nbsp;${detalle.estado}<br>
                                                    <b>Funcion:</b>&nbsp;${detalle.funcion}<br>
                                                    <b>Modalidad:</b>&nbsp;${detalle.modalidad}<br>
                                                    <b>Duracion de proyecto:</b>&nbsp;${detalle.duracionproyecto}<br>
                                                    <b>Tipo de proyecto:</b>&nbsp;${detalle.tipodeproyecto}<br>
                                                    <b>¿Es un representante sindical?</b>&nbsp;${detalle.representaciondirigencial ? 'SI' : 'NO'}<br>
                                                    <c:if test="${id_rol==8}">
                                                        <c:if test="${detalle.aprobado}">
                                                            <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                                                        </c:if>
                                                        <c:if test="${!detalle.aprobado}">
                                                            <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${id_rol!=8}">
                                                    <div class="toggle-flip">
                                                        <label>
                                                            Desea aprobar el documento? <input type="checkbox" name="chk${detalle.id_personas_proyecto}" data-proyectodocente="${detalle.id_personas_proyecto}" onclick="actualizaraprobacion(this)" <c:out value="${detalle.aprobado ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span>
                                                        </label>
                                                    </div> 
                                                </c:if>
                                                </p>
                                                <div class="text-right">
                                                    <div class="btn-group" role="group" aria-label="Basic example">
                                                        <form name="forma"  action="<c:url value="/FileDownload.fautapo"/>" method="GET">
                                                            <input type="hidden" name="name" value='${detalle.documento}' /> 
                                                            <input type="hidden" name="directorio" value='proyectodocente' /> 
                                                            <button class="btn btn-primary" type="submit"><i class="fas fa-download"></i></button>
                                                        </form>
                                                        &nbsp;
                                                        <form name="forma"  action="<c:url value="/FormularioRegistroProyectoKardex.fautapo"/>" method="GET">
                                                            <input type="hidden" name="id_personas_proyecto" value='${detalle.id_personas_proyecto}' />
                                                            <input type="hidden" name="id_persona" value='${id_persona}' />
                                                            <button class="btn btn-primary" type="submit"><i class="fas fa-user-edit"></i></button>
                                                        </form>
                                                        &nbsp; 
                                                        <button class="btn btn-primary" data-proyectodocente="${detalle.id_personas_proyecto}" data-name="li${detalle.id_personas_proyecto}" onclick="deleteitem(this, 'li${detalle.id_personas_proyecto}')" type="submit"><i class="far fa-trash-alt"></i></button>

                                                    </div>
                                                </div>
                                            </div>
                                        </li>   
                                    </c:forEach> 
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="vistaprevia" tabindex="-1" role="dialog" aria-labelledby="examplevistaprevia" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div id="imgvistaprevia" class="modal-body"> 
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Entiendo</button> 
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </c:if>
        <script src="<c:url value='/Public/Js/jquery/jquery-3.3.1.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/jquery/popper.min.js'/>" ></script>
        <script src="<c:url value='/Public/Js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/main.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/pace.min.js'/>"></script>
        <script src="<c:url value="/Public/Js/sweetalert.min.js"/>"></script>
        <script src="<c:url value="/Public/Js/filtermedia.js"/>"></script>
        <script>
                                                            $(".alert").delay(4000).slideUp(1000, function () {
                                                                $(this).alert('close');
                                                            });
                                                            function vistapreviadocumentos(input) {
                                                                var data = {
                                                                    id: $(input).data("id"),
                                                                    tabla: $(input).data("tabla")
                                                                };
                                                                $.get('./VistapreviaimagenDocumentos.fautapo', data, function (data)
                                                                {
                                                                    if (data.status === 'OK') {
                                                                        if (data.type === 'pdf') {
                                                                            $('#imgvistaprevia').html('<iframe  src="' + data.base64 + '" width=100% height=600></iframe> ')
                                                                        } else {
                                                                            $('#imgvistaprevia').html('<img src="' + data.base64 + '" class="img-fluid" alt="Responsive image">');
                                                                        }
                                                                        AbrirModal("#vistaprevia");
                                                                    } else {
                                                                        swal("Oops", data.message, "error");
                                                                    }
                                                                });
                                                            }

                                                            function AbrirModal(id)
                                                            {
                                                                $(id).modal({
                                                                    keyboard: false
                                                                });
                                                            }
                                                            function actualizaraprobacion(input) {
                                                                var data = {
                                                                    id_personas_proyecto: $(input).data("proyectodocente"),
                                                                    estado: $(input).prop('checked')
                                                                };
                                                                $.get('./ModificarEstadoAprobacionProyectoEvaluacion.fautapo', data, function (data)
                                                                {
                                                                    if (data.status !== "OK") {
                                                                        swal("Oops", data.message, "error");
                                                                    }
                                                                }).fail(function (jqXHR, textStatus, errorThrown) {
                                                                    if (jqXHR.status === 0) {
                                                                        swal("Oops", 'No conectarse: Verifique la red.', "error");
                                                                    } else if (jqXHR.status == 404) {
                                                                        swal("Oops", 'Página solicitada no encontrada [404]', "error");
                                                                    } else if (jqXHR.status == 500) {
                                                                        swal("Oops", 'Error interno del servidor [500].', "error");
                                                                    } else if (textStatus === 'parsererror') {
                                                                        swal("Oops", 'El análisis JSON solicitado ha fallado.', "error");
                                                                    } else if (textStatus === 'timeout') {
                                                                        swal("Oops", 'Error de tiempo de espera.', "error");
                                                                    } else if (textStatus === 'abort') {
                                                                        swal("Oops", 'Solicitud de Ajax abortada.', "error");
                                                                    } else {
                                                                        swal("Oops", 'Error no detectado:' + jqXHR.responseText, "error");
                                                                    }

                                                                });
                                                            }
                                                            function deleteitem(input) {
                                                                var data = {
                                                                    id_personas_proyecto: $(input).data("proyectodocente"),
                                                                    name: $(input).data("name")
                                                                };
                                                                swal({
                                                                    title: "¿Desea continuar con la eliminacion del registro?",
                                                                    text: "Presiona aceptar para continuar..",
                                                                    icon: "warning",
                                                                    buttons: {
                                                                        cancel: {
                                                                            text: "Cancelar",
                                                                            value: true,
                                                                            visible: true,
                                                                            className: 'btn btn-danger',
                                                                        },
                                                                        Registrar: {
                                                                            text: "Aceptar",
                                                                            value: "Eliminar",
                                                                            className: 'btn btn-primary',
                                                                        }
                                                                    },
                                                                }).then((value) => {
                                                                    switch (value) {
                                                                        case "Eliminar":

                                                                            $.get('./EliminarProyectoDocente.fautapo', data, function (data)
                                                                            {
                                                                                if (data.status === "OK") {
                                                                                    var item = document.getElementById(data.id);
                                                                                    item.parentNode.removeChild(item);
                                                                                } else {
                                                                                    swal("Oops", data.message, "error");
                                                                                }
                                                                            }).fail(function (jqXHR, textStatus, errorThrown) {
                                                                                if (jqXHR.status === 0) {
                                                                                    swal("Oops", 'No conectarse: Verifique la red.', "error");
                                                                                } else if (jqXHR.status == 404) {
                                                                                    swal("Oops", 'Página solicitada no encontrada [404]', "error");
                                                                                } else if (jqXHR.status == 500) {
                                                                                    swal("Oops", 'Error interno del servidor [500].', "error");
                                                                                } else if (textStatus === 'parsererror') {
                                                                                    swal("Oops", 'El análisis JSON solicitado ha fallado.', "error");
                                                                                } else if (textStatus === 'timeout') {
                                                                                    swal("Oops", 'Error de tiempo de espera.', "error");
                                                                                } else if (textStatus === 'abort') {
                                                                                    swal("Oops", 'Solicitud de Ajax abortada.', "error");
                                                                                } else {
                                                                                    swal("Oops", 'Error no detectado:' + jqXHR.responseText, "error");
                                                                                }

                                                                            });
                                                                            break;
                                                                        default:
                                                                    }
                                                                });
                                                            }
        </script>
    </body>
</html>