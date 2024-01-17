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
                <p>Lista de materias para elaborar programa analitico ${periodo}/${gestion} </p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <c:if test="${ empty datosAsignacion}">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-6 mx-auto">
                                <div class="card border-secondary mb-3">
                                    <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                                        !!
                                    </div>
                                    <div class="card-body text-secondary">
                                        <p class="card-text">
                                            No existen materias asignadas para la gesti&oacute;n <c:out
                                                value="${gestion}"/>, periodo <c:out value="${periodo}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${ !empty datosAsignacion}">
                    <div class="tile">
                        <span class="d-block p-2 bg-primary text-white"><i class="fa fa-address-card"></i>&nbsp;Administrar Libretas</span>
                        <div class="responsive">
                            <table class="col-md-12 table table-sm" >
                                <thead class="cf">
                                <tr>
                                    <th>FASE</th>
                                    <th>EVALUACION</th>
                                    <th>CARRERA/PROGRAMA</th>
                                    <th>GRUPO</th>
                                    <th>SIGLA</th>
                                    <th>MATERIA</th>
                                    <th>PLAN DE ESTUDIO</th>
                                    <th>ADMINISTRACION</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="asignacion" items="${datosAsignacion}" varStatus="contador">
                                    <tr>
                                        <td data-label="FASE">
                                            <c:out value="${asignacion.fase}"/>
                                        </td>
                                        <td data-label="EVALUACION">
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
                                        <td data-label="PLAN">
                                            <c:out value="${asignacion.id_plan}"/>
                                        </td>

                                        <td data-label="ACCION">
                                            <a class="btn btn-primary"
                                               href='javascript:enviarsolicitud(document.formaEvaluaciones${contador.count});'>
                                                <i class="fa fa-cog"></i></a>
                                            <c:if test="${asignacion.id_dct_programa_analitico != null}">
                                                <a class="btn btn-primary"
                                                   href='javascript:enviarsolicitud(document.formaimprimir${contador.count});'> <i
                                                        class="fa fa-file-pdf-o"></i></a>
                                                <form name='formaimprimir<c:out value="${contador.count}"/>' method='get'
                                                      action="<c:url value='/programaanaanalitico/impresion/entrada'/>">
                                                    <input type=hidden name="id_dct_programa_analitico"
                                                           value="<c:out value="${asignacion.id_dct_programa_analitico}"/>">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                           value="${_csrf.token}"/>
                                                    <input type=hidden name="id_asignacion"
                                                           value="<c:out value="${asignacion.id_asignacion}"/>">
                                                    <input type=hidden name="id_materia"
                                                           value="<c:out value="${asignacion.id_materia}"/>">
                                                    <input type=hidden name="materia"
                                                           value="<c:out value="${asignacion.materia}"/>">
                                                    <input type=hidden name="id_grupo"
                                                           value="<c:out value="${asignacion.id_grupo}"/>">
                                                    <input type=hidden name="grupo"
                                                           value="<c:out value="${asignacion.grupo}"/>">
                                                    <input type=hidden name="id_programa"
                                                           value="<c:out value="${asignacion.id_programa}"/>">
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
                                                    <input type=hidden name="id_plan"
                                                           value="<c:out value="${asignacion.id_plan}"/>">
                                                    <input type=hidden name="id_departamento"
                                                           value="<c:out value="${asignacion.id_departamento}"/>">
                                                    <input type=hidden name="id_mencion"
                                                           value="<c:out value="${asignacion.id_mencion}"/>">
                                                    <input type=hidden name="id_tipo_grado"
                                                           value="<c:out value="${asignacion.id_tipo_grado}"/>">
                                                </form>
                                            </c:if>
                                            <form name='formaEvaluaciones<c:out value="${contador.count}"/>'
                                                  method='post'
                                                  action="${pageContext.request.contextPath}/ElaborarProgramaAnalitico">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                                <input type=hidden name="id_asignacion"
                                                       value="<c:out value="${asignacion.id_asignacion}"/>">
                                                <input type=hidden name="id_materia"
                                                       value="<c:out value="${asignacion.id_materia}"/>">
                                                <input type=hidden name="materia"
                                                       value="<c:out value="${asignacion.materia}"/>">
                                                <input type=hidden name="id_grupo"
                                                       value="<c:out value="${asignacion.id_grupo}"/>">
                                                <input type=hidden name="grupo"
                                                       value="<c:out value="${asignacion.grupo}"/>">
                                                <input type=hidden name="id_programa"
                                                       value="<c:out value="${asignacion.id_programa}"/>">
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
                                                <input type=hidden name="id_plan"
                                                       value="<c:out value="${asignacion.id_plan}"/>">
                                                <input type=hidden name="id_departamento"
                                                       value="<c:out value="${asignacion.id_departamento}"/>">
                                                <input type=hidden name="id_mencion"
                                                       value="<c:out value="${asignacion.id_mencion}"/>">
                                                <input type=hidden name="id_tipo_grado"
                                                       value="<c:out value="${asignacion.id_tipo_grado}"/>">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </main>
    <div id="loader"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Enviando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });

    function enviarsolicitud(form) {
        loadercontent.show();
        form.submit();
    }
</script>
</body>
</html>
