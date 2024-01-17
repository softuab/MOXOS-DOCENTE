<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <title>Sistema - Moxos</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<c:if test="${!empty id_rol}">
    <main class="app-content3">
        <div class="app-title">
            <div>
                <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <c:if test="${result.status !=''}">
                    <div class="alert ${result.status} alert-dismissible fade show" role="alert">
                        <strong>Aviso!</strong> ${result.message}.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="tile">
                    <form name="forma" class="unlock-form" action="<c:url value="/IndexKardexPersonal"/>" method="GET">
                        <input type="hidden" name="id_persona" value='${id_persona}'/>
                        <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-home"></i></button>
                    </form>
                    <h3 class="title">Modalidad de ingreso</h3>
                    <div class="tile-title-w-btn">
                        <input class="form-control mr-sm-2" type="search" id="buscardetallemodalidad"
                               placeholder="Buscar titulo"
                               onkeyup="filterlist('buscardetallemodalidad', 'listmodalidad')">
                        <form name="forma" class="unlock-form"
                              action="<c:url value="/FormularioRegistroModalidadIngresoKardex"/>" method="GET">
                            <input type="hidden" name="id_persona" value='${id_persona}'/>
                            <input type="hidden" name="id_persona_kardex" value='${id_persona_kardex}'/>
                            <input type="hidden" name="root" value='total'/>
                            <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                            </button>
                        </form>
                    </div>
                    <div class="tile-body">
                        <ul class="list-unstyled" id="listmodalidad">
                            <c:forEach var="detalle" items="${detallemodalidad}">
                                <li class="media" id="li${detalle.id_modalidadingreso}">
                                    <img src="<c:url value="/static/image/Document.png" />" height="50px" width="50px"
                                         class="align-self-center mr-3 rounded-circle"
                                         onclick="vistapreviadocumentos(this)"
                                         data-url="<c:url value="/VistapreviaimagenDocumentos" />"
                                         data-id="${detalle.id_modalidadingreso}"
                                         data-tabla="modalidadingreso" onMouseOver="this.style.cursor = 'pointer'">
                                    <div class="media-body">
                                        <h5 class="mt-0 mb-1">${detalle.programa}</h5>
                                        <p>
                                            <b>Modalidad ingreso:</b>&nbsp;${detalle.modalidadingreso}<br>
                                            <b>Fecha Emision:</b>&nbsp;<fmt:formatDate pattern="dd/MM/yyyy"
                                                                                       value="${detalle.fechaingreso}"/>
                                            <br>
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
                                                Desea aprobar el documento? <input type="checkbox"
                                                                                   name="chk${detalle.id_modalidadingreso}"
                                                                                   data-modalidad="${detalle.id_modalidadingreso}"
                                                                                   onclick="actualizaraprobacion(this)"
                                                <c:out value="${detalle.aprobado ? 'checked': ''}"/>><span
                                                    class="flip-indecator" data-toggle-on="SI"
                                                    data-toggle-off="NO"></span>
                                            </label>
                                        </div>
                                        </c:if>
                                        </p>
                                        <div class="text-end">
                                            <div class="btn-group" role="group" aria-label="Basic example">
                                                <form name="forma" action="<c:url value="/FileDownload"/>" method="GET">
                                                    <input type="hidden" name="name"
                                                           value='${detalle.url_modalidadingreso}'/>
                                                    <input type="hidden" name="directorio" value='modalidades'/>
                                                    <button class="btn btn-primary" type="submit"><i
                                                            class="fa fa-download"></i></button>
                                                </form>
                                                &nbsp;
                                                <form name="forma"
                                                      action="<c:url value="/FormularioRegistroModalidadIngresoKardex"/>"
                                                      method="GET">
                                                    <input type="hidden" name="id_modalidadingreso"
                                                           value='${detalle.id_modalidadingreso}'/>
                                                    <input type="hidden" name="id_persona" value='${id_persona}'/>
                                                    <button class="btn btn-primary" type="submit"><i
                                                            class="fa fa-pencil"></i></button>
                                                </form>
                                                &nbsp;
                                                <button class="btn btn-primary"
                                                        data-modalidad="${detalle.id_modalidadingreso}"
                                                        data-name="li${detalle.id_modalidadingreso}"
                                                        data-url="<c:url value="/EliminarModalidadIngreso" />"
                                                        onclick="deleteitem(this, 'li${detalle.id_modalidadingreso}')"
                                                        type="submit"><i class="fa fa-trash"></i></button>

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
        <div class="modal fade zoom" id="vistaprevia" data-bs-backdrop="static" data-bs-keyboard="false"
             tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div id="imgvistaprevia" class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btncancelar" class="waves-effect waves-red btn-flat"
                                data-bs-dismiss="modal">Entiendo
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <div id="errortoast"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/sweetalert.min.js" />"></script>
<script src="<c:url value="/static/js/app/main/kardex/vistaprevia.js" />"></script>
<script src="<c:url value="/static/js/app/main/kardex/fiter.js" />"></script>
<script src="<c:url value="/static/js/app/main/kardex/deleteitems.js" />"></script>
<script>
    <c:if test="${result.status !=''}">
    const alertElement = document.querySelector('.alert');
    setTimeout(() => {
        setTimeout(() => {
            alertElement.style.display = 'none';
        }, 1000);
        let bsAlert = new bootstrap.Alert(alertElement);
        bsAlert.close();
    }, 4000);
    </c:if>
    function deleteitem(input){
        let data = {
            id_modalidadingreso: input.dataset.modalidad,
            name: input.dataset.name
        };
        deleteelement(input.dataset.url, data);
    }
</script>
</body>
</html>