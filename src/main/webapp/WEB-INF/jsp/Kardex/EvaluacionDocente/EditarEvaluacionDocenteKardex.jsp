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
            <div class="col-sm-12">
                <div class="tile">
                    <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                               action="${pageContext.request.contextPath}/RegistrarModificarEvaluacion"
                               enctype="multipart/form-data">
                        <form:hidden path="id_persona_kardex"/>
                        <form:hidden path="id_persona"/>
                        <form:hidden path="UUID"/>
                        <form:hidden path="id_evaluacion_docente"/>
                        <form:hidden path="url_certificado_evaluacion"/>
                        <form:hidden path="root"/>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Gestion<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <form:input cssClass="form-control" path="gestion"
                                            aria-describedby="gestionHelp"></form:input>
                                <form:errors path="gestion" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="gestionHelp">Gestion en el que se realico la
                                    evaluacion.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Periodo<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <form:select cssClass="form-select" path="periodo" items="${detalleperiodo}"
                                             itemLabel="value" itemValue="id"/>
                                <form:errors path="periodo" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="periodoHelp">Periodo en el que se realico la
                                    evaluacion.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Asignatura o area<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <form:input cssClass="form-control" path="asignatura"
                                            aria-describedby="asignaturaHelp"></form:input>
                                <form:errors path="asignatura" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="asignaturaHelp">Asignatura o area.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Puntaje<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <form:input cssClass="form-control" path="puntaje"
                                            aria-describedby="puntajeHelp"></form:input>
                                <form:errors path="puntaje" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="puntajeHelp">Puntaje de la evaluacion.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Documento original del la evaluacion<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <input class="form-control-file" name="Image" id="Image" type="file"
                                       aria-describedby="url_certificado_evaluacionHelp"
                                       accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                <form:errors path="url_certificado_evaluacion" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="url_certificado_evaluacionHelp">Cargar imagen
                                    del documento original de la evaluacion realizada</small>
                            </div>
                        </div>
                        <div class="tile-footer">
                            <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                            </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                          href="${pageContext.request.contextPath}/ListarEvaluacionDocente?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </main>
    <div id="loader"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let enviar = new Form(document.getElementById('model'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
</script>
</body>
</html>
