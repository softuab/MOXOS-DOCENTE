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
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>Lista de materias para elaborar programa analitico ${periodo}/${gestion} </p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <form id="formcrear"  method='post'
                                  action="${pageContext.request.contextPath}/InicioElaboracionProgramaAnalitico">
                                <input type=hidden name="id_asignacion" value="<c:out value="${model.id_asignacion}"/>">
                                <input type=hidden name="id_materia" value="<c:out value="${model.id_materia}"/>">
                                <input type=hidden name="materia" value="<c:out value="${model.materia}"/>">
                                <input type=hidden name="id_grupo" value="<c:out value="${model.id_grupo}"/>">
                                <input type=hidden name="grupo" value="<c:out value="${model.grupo}"/>">
                                <input type=hidden name="id_programa" value="<c:out value="${model.id_programa}"/>">
                                <input type=hidden name="id_fase" value="<c:out value="${model.id_fase}"/>">
                                <input type=hidden name="id_tipo_grado" value="<c:out value="${model.id_tipo_grado}"/>">
                                <input type=hidden name="id_tipo_evaluacion"
                                       value="<c:out value="${model.id_tipo_evaluacion}"/>">
                                <input type=hidden name="tipo_evaluacion"
                                       value="<c:out value="${model.tipo_evaluacion}"/>">
                                <input type=hidden name="gestion" value="<c:out value="${model.gestion}"/>">
                                <input type=hidden name="periodo" value="<c:out value="${model.periodo}"/>">
                                <input type=hidden name="id_docente" value="<c:out value="${model.id_docente}"/>">
                                <input type=hidden name="id_plan" value="<c:out value="${model.id_plan}"/>">
                                <input type=hidden name="id_departamento"
                                       value="<c:out value="${model.id_departamento}"/>">
                                <input type=hidden name="id_mencion" value="<c:out value="${model.id_mencion}"/>">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="button" onclick="enviarsolicitud('formcrear')" class="btn btn-success">
                                    <i class="fa fa-plus"></i> Crear nuevo programa analitico
                                </button>
                            </form>
                        </div>
                    </div>
                    <hr>
                    <form:form cssClass="row" method="post" modelAttribute="model"
                               action="${pageContext.request.contextPath}/ImportarProgramaAnalitico">
                        <form:hidden path="id_asignacion"/>
                        <form:hidden path="id_materia"/>
                        <form:hidden path="materia"/>
                        <form:hidden path="id_grupo"/>
                        <form:hidden path="grupo"/>
                        <form:hidden path="id_programa"/>
                        <form:hidden path="id_fase"/>
                        <form:hidden path="id_tipo_evaluacion"/>
                        <form:hidden path="tipo_evaluacion"/>
                        <form:hidden path="gestion"/>
                        <form:hidden path="periodo"/>
                        <form:hidden path="id_docente"/>
                        <form:hidden path="id_plan"/>
                        <form:hidden path="id_departamento"/>
                        <form:hidden path="id_mencion"/>
                        <form:hidden path="id_tipo_grado"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group col-8">
                            <form:select cssClass="form-select" path="id_dct_programa_analitico"
                                         items="${listarpgranalitico}" itemLabel="value" itemValue="id"/>
                            <small class="form-text text-muted" id="id_dct_programa_analiticoHelp">Seleccionar el
                                programa analitico a importar.</small>
                        </div>
                        <div class="form-group col-4">
                            <button onclick="enviarsolicitud('model')" class="btn btn-primary btn-block" id="btnImportar" type="button"><i
                                    class="fa fa-fw fa-lg fa-check-circle"></i>Importar Programa Analitico
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="loader"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Enviando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });
    function enviarsolicitud(form) {
        loadercontent.show();
        document.getElementById(form).submit();
    }
    const selectElement = document.getElementById('id_dct_programa_analitico');
    window.onload = function () {
        const opciones = selectElement.options;
        if (opciones.length === 0) {
            btnImportar.disabled = true;
        }
    }
</script>
</body>
</html>