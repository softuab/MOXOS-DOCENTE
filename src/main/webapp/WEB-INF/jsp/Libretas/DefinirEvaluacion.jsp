<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>${datosAsignacion.sigla} - ${parametros.materia} GRUPO ${datosAsignacion.grupo}
                - ${parametros.periodo}/ ${parametros.gestion} </p>
            <p><c:out value="${parametros.programa}"/></p>
        </div>
        <ul class="app-breadcrumb breadcrumb">
            <li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
            <li class="breadcrumb-item">${datosAsignacion.fase}</li>
        </ul>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="row mb-3">
                    <div class="col">
                        <form id="retornar" method="post"
                              action="<c:url value="/libretas/retornorListarAsignaciones" />">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="gestion" value="${parametros.gestion}">
                            <input type="hidden" name="periodo" value="${parametros.periodo}">
                            <button class="btn btn-primary btn-sm" style="float: right" type="submit"><i
                                    class="fa fa-home fa-lg"></i> Retornar
                            </button>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <span class="d-block p-2 bg-primary text-white mb-3"><i class="fa fa-edit"></i>    Tipo de Evaluaci&oacute;n :&nbsp;<c:out
                                value="${datosAsignacion.tipo_evaluacion}"/></span>
                        <div class="row">
                            <div class="col">
                                <div id="tblDefinicion" class="container-form">
                                    <div id="formDefincion">
                                        <c:set var="sumapoderado" value="${0}"/>
                                        <table id="tbl_definirnotas" class="table table-striped table-sm">
                                            <thead class="cf">
                                            <tr>
                                                <th>Nro.</th>
                                                <th>TIPO NOTA</th>
                                                <th>CANTIDAD</th>
                                                <th>PORCENTAJE</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="nota" items="${lTiposNotas}" varStatus="contador">
                                                <tr>
                                                    <td><c:out value="${contador.count}"/></td>
                                                    <td><c:out value="${nota.tipo_nota}"/></td>
                                                    <c:if test="${nota.cantidad==0}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test = "${nota.id_tipo_nota == 5}">
                                                                    <input type="number" class="form-control cantidad"
                                                                           data-tipo="${nota.id_tipo_nota}"
                                                                           value="1" readonly
                                                                           onkeyup='validarValores(this)'
                                                                           required>
                                                                </c:when>
                                                                <c:when test = "${nota.id_tipo_nota == 6}">
                                                                    <input type="number" class="form-control cantidad"
                                                                           data-tipo="${nota.id_tipo_nota}"
                                                                           value="1" readonly
                                                                           onkeyup='validarValores(this)'
                                                                           required>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input type="number" class="form-control cantidad"
                                                                           data-tipo="${nota.id_tipo_nota}"
                                                                           value="<c:out value="${nota.cantidad}"/>"
                                                                           onkeyup='validarValores(this)'
                                                                           required>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${nota.cantidad>=1}">
                                                        <td><c:out value="${nota.cantidad}"/></td>
                                                    </c:if>
                                                    <td>
                                                        <c:if test="${nota.ponderacion==0}">
                                                            <c:if test="${nota.porcentaje!=0}">
                                                                <input type="number" class="form-control ponderacion"
                                                                       value="<c:out value="${nota.porcentaje}"/>"
                                                                       readonly
                                                                       onkeyup='validarValores(this)'
                                                                       required>
                                                            </c:if>
                                                            <c:if test="${nota.porcentaje==0}">
                                                                <input type="number" class="form-control ponderacion"
                                                                       value="<c:out value="${nota.ponderacion}"/>"
                                                                       onkeyup='validarValores(this)'
                                                                       required>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${nota.ponderacion>=1}">
                                                            <c:out value="${nota.ponderacion}"/>
                                                        </c:if>
                                                        <c:set var="sumapoderado"
                                                               value="${sumapoderado +nota.ponderacion}"/>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                        <input type=hidden id="id_asignacion"
                                               value="<c:out value="${parametros.id_asignacion}"/>">
                                        <input type=hidden id="id_programa"
                                               value="<c:out value="${parametros.id_programa}"/>">
                                        <input type=hidden id="id_fase"
                                               value="<c:out value="${datosAsignacion.id_fase}"/>">
                                        <input type=hidden id="id_tipo_evaluacion"
                                               value="<c:out value="${datosAsignacion.id_tipo_evaluacion}"/>">
                                        <input type=hidden id="id_modelo_ahorro"
                                               value="<c:out value="${datosAsignacion.id_modelo_ahorro}"/>">
                                        <input type=hidden id="id_departamento"
                                               value="<c:out value="${parametros.id_departamento}"/>">
                                        <div class="text-center">
                                            <c:if test="${sumapoderado!=100}">
                                                <button type="button" id="btnaceptar" onclick="guardarCambios()"
                                                        class="btn btn-primary" disabled>Aceptar
                                                </button>
                                            </c:if>
                                        </div>
                                    </div>
                                    <c:if test="${sumapoderado!=100}">
                                        <div id="loading" class="lienzo invisible">
                                            <div class="m-loader mr-4">
                                                <svg class="m-circular" viewBox="25 25 50 50">
                                                    <circle class="path" cx="50" cy="50" r="20" fill="none"
                                                            stroke-width="4" stroke-miterlimit="10"></circle>
                                                </svg>
                                            </div>
                                            <h3 class="l-text">Enviando</h3>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm mx-auto">
                                            <div class="card border-secondary mb-3">
                                                <div class="card-header text-center"><i class="fa fa-info-circle"></i>
                                                    ACLARACIONES:
                                                </div>
                                                <div class="card-body text-secondary">
                                                    <strong style="color: #E72823">* Tipo de nota:</strong> Actividades
                                                    a ser evaluadas.<br/>
                                                    <strong style="color: #E72823">* Cantidad:</strong> Numero de
                                                    actividades a ser evaluadas definidas de acuerdo a
                                                    su proyecto academico.<br/>
                                                    <strong style="color: #E72823">* Porcentaje:</strong> Valor
                                                    porcentual de las actividades a ser evaluadas definidas de acuerdo a
                                                    su proyecto academico.<br/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="loader"></div>
<div id="errortoast"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=6" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script>
    let toast = new ToastModal(document.getElementById('errortoast'), {});
    let formretorno = new Form(document.getElementById('retornar'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
</script>
<script src="<c:url value="/static/js/app/main/libretas/definicionlibreta.js" />"></script>
</body>
</html>