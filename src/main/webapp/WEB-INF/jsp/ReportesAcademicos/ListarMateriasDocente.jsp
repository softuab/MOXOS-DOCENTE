<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
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
                <h1><i class="fa fa-address-card"></i>&nbsp; ${usuario}</h1>
                <p>Lista de reporte notas evaluacion Estudiantes - Docente ${periodo}/${gestion} </p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="tile">
                    <c:if test="${ empty lDatosAsignacion}">
                        <div class="Card-Informacion">
                            <div class="card border-secondary mb-3" style="max-width: 18rem;">
                                <div class="card-header"><i class="fas fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO !!
                                </div>
                                <div class="card-body text-secondary">
                                    <p class="card-text">
                                        No existen materias asignadas para la gesti&oacute;n <c:out value="${gestion}"/>,
                                        periodo <c:out value="${periodo}"/><br/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${ !empty lDatosAsignacion}">
                        <span class="d-block p-2 bg-primary text-white"><i class="fa fa-address-card"></i>&nbsp;Materias asignadas</span>
                        <div class="responsive">
                            <table class="col-md-12 table table-sm">
                                <thead class="cf">
                                <tr>
                                    <th class="text-center">FASE</th>
                                    <th class="text-center">EVALUACI&Oacute;N</th>
                                    <th class="text-center">CARRERA/PROGRAMA</th>
                                    <th class="text-center">GRUPO</th>
                                    <th class="text-center">SIGLA</th>
                                    <th class="text-center">MATERIA</th>
                                    <th class="text-center">PLAN</th>
                                    <th class="text-center">IMPRIMIR</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="asignacion" items="${lDatosAsignacion}" varStatus="contador">
                                    <!-- ********** Esto es para el efecto ************ -->
                                    <tr>
                                        <td data-label="FASE">
                                            <c:out value="${asignacion.fase}"/>
                                        </td>
                                        <td data-label="EVALUACIÓN">
                                            <c:out value="${asignacion.tipo_evaluacion}"/>
                                        </td>
                                        <td data-label="CARRERA/PROGRAMA">
                                            <c:out value="${asignacion.programa}"/>
                                        </td>
                                        <td data-label="GRUPO">
                                            <c:out value="${asignacion.grupo}"/>
                                        </td>
                                        <td data-label="SIGLA">
                                            <c:out value="${asignacion.sigla}"/>
                                        </td>
                                        <td data-label="MATERIA">
                                            <c:out value="${asignacion.materia}"/>
                                        </td>
                                        <td data-label="MATERIA AHORRO">
                                            <c:out value="${asignacion.id_plan}"/>
                                        </td>
                                        <td data-label="IMPRIMIR" class="text-center">
                                            <form name='forma<c:out value="${contador.count}"/>' method="GET"
                                                  action="<c:url value='/reportes/imprimirEvaluacion'/>">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                                <a class="btn btn-primary"
                                                   href='javascript:document.forma<c:out value="${contador.count}"/>.submit();'><i
                                                        class="fa fa-print"></i> </a>
                                                <input type="hidden" name="nombres" value="<c:out value='${usuario}'/>">
                                                <input type=hidden name="id_asignacion"
                                                       value="<c:out value="${asignacion.id_asignacion}"/>">
                                                <input type=hidden name="id_materia"
                                                       value="<c:out value="${asignacion.id_materia}"/>">
                                                <input type=hidden name="materia"
                                                       value="<c:out value="${asignacion.materia}"/>">
                                                <input type=hidden name="sigla"
                                                       value="<c:out value="${asignacion.sigla}"/>">
                                                <input type=hidden name="id_grupo"
                                                       value="<c:out value="${asignacion.id_grupo}"/>">
                                                <input type=hidden name="grupo"
                                                       value="<c:out value="${asignacion.grupo}"/>">
                                                <input type=hidden name="id_programa"
                                                       value="<c:out value="${asignacion.id_programa}"/>">
                                                <input type=hidden name="programa"
                                                       value="<c:out value="${asignacion.programa}"/>">
                                                <input type=hidden name="id_fase"
                                                       value="<c:out value="${asignacion.id_fase}"/>">
                                                <input type=hidden name="id_tipo_evaluacion"
                                                       value="<c:out value="${asignacion.id_tipo_evaluacion}"/>">
                                                <input type=hidden name="tipo_evaluacion"
                                                       value="<c:out value="${asignacion.tipo_evaluacion}"/>">
                                                <input type=hidden name="gestion" value="<c:out value="${gestion}"/>">
                                                <input type=hidden name="periodo" value="<c:out value="${periodo}"/>">
                                                <input type=hidden name="id_docente"
                                                       value="<c:out value="${asignacion.id_docente}"/>">
                                                <input type=hidden name="id_modelo_ahorro"
                                                       value="<c:out value="${asignacion.id_modelo_ahorro}"/>">
                                                <input type=hidden name="id_departamento"
                                                       value="<c:out value="${asignacion.id_departamento}"/>">
                                                <input type=hidden name="id_tipo_grado"
                                                       value="<c:out value="${asignacion.id_tipo_grado}"/>">
                                            </form>
                                        </td>

                                    </tr>

                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </main>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
</body>
</html>