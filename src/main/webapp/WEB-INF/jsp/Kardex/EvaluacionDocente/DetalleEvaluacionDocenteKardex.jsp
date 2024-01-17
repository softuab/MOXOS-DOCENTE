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
                    <h3 class="title">Evaluacion docente</h3>
                    <div class="tile-title-w-btn">
                        <input class="form-control mr-sm-2" type="search" id="buscardetalleevaluacion"
                               placeholder="Buscar titulo"
                               onkeyup="filtertable('buscardetalleevaluacion', 'listevaluacion')">
                        <form name="forma" class="unlock-form"
                              action="<c:url value="/FormularioRegistroEvaluacionKardex"/>" method="GET">
                            <input type="hidden" name="id_persona" value='${id_persona}'/>
                            <input type="hidden" name="id_persona_kardex" value='${id_persona_kardex}'/>
                            <input type="hidden" name="root" value='total'/>
                            <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                            </button>
                        </form>
                    </div>
                    <div class="tile-body">
                        <div class="responsive">
                            <table class="col-md-12 table table-sm" id="listevaluacion">
                                <thead class="cf">
                                <tr>
                                    <th>GESTION/PERIODO</th>
                                    <th>ASIGNATURA O AREA</th>
                                    <th>PUNTAJE</th>
                                    <th>ESTADO</th>
                                    <th>ACCIONES</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="detalle" items="${detalles}">
                                    <tr id="li${detalle.id_evaluacion_docente}">
                                        <td data-label="GESTION/PERIODO">
                                            <c:out value="${detalle.gestion}"/> / <c:out value="${detalle.periodo}"/>
                                        </td>
                                        <td data-label="ASIGNATURA O AREA">
                                            <c:out value="${detalle.asignatura}"/>
                                        </td>
                                        <td data-label="PUNTAJE">
                                            <c:out value="${detalle.puntaje}"/>
                                        </td>
                                        <td data-label="ESTADO">
                                            <c:if test="${id_rol==8}">
                                                <c:if test="${detalle.aprobado}">
                                                    <b>El documento se
                                                        encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                                                </c:if>
                                                <c:if test="${!detalle.aprobado}">
                                                    <b>El documento se
                                                        encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${id_rol!=8}">
                                                <div class="toggle-flip">
                                                    <label>
                                                        Desea aprobar el documento? <input type="checkbox"
                                                                                           name="chk${detalle.id_evaluacion_docente}"
                                                                                           data-metodo="${detalle.id_evaluacion_docente}"
                                                                                           onclick="actualizaraprobacion(this)"
                                                        <c:out value="${detalle.aprobado ? 'checked': ''}"/>><span
                                                            class="flip-indecator" data-toggle-on="SI"
                                                            data-toggle-off="NO"></span>
                                                    </label>
                                                </div>
                                            </c:if>
                                        </td>
                                        <td data-title="ACCIONES">
                                            <div class="btn-group" role="group">
                                                <form name="evaluacion${detalle.id_evaluacion_docente}"
                                                      action="<c:url value="./FileDownload"/>" method="GET">
                                                    <input type="hidden" name="name"
                                                           value='${detalle.url_certificado_evaluacion}'/>
                                                    <input type="hidden" name="directorio" value='evaluacion'/>
                                                    <button class="btn btn-primary" type="submit"><i
                                                            class="fa fa-download"></i></button>
                                                </form>
                                                &nbsp;
                                                <form name="forma"
                                                      action="<c:url value="/FormularioRegistroEvaluacionKardex"/>"
                                                      method="GET">
                                                    <input type="hidden" name="id_evaluacion_docente"
                                                           value='${detalle.id_evaluacion_docente}'/>
                                                    <input type="hidden" name="id_persona" value='${id_persona}'/>
                                                    <button class="btn btn-primary" type="submit"><i
                                                            class="fa fa-pencil"></i></button>
                                                </form>
                                                &nbsp;
                                                <button class="btn btn-primary"
                                                        data-metodo="${detalle.id_evaluacion_docente}"
                                                        data-name="li${detalle.id_evaluacion_docente}"
                                                        onclick="deleteitem(this, 'li${detalle.id_evaluacion_docente}')"
                                                        data-url="<c:url value="/EliminarEvaluacionDocente"/>"
                                                        type="submit"><i class="fa fa-trash"></i></button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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
    function deleteitem(input) {
        let data = {
            id_evaluacion_docente: input.dataset.metodo,
            name: input.dataset.name
        };
        deleteelement(input.dataset.url, data);
    }
</script>
</body>
</html>