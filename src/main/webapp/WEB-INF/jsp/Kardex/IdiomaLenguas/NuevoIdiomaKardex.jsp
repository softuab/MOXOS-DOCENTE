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
                               action="${pageContext.request.contextPath}/RegistrarPersonaIdiomaKardex"
                               enctype="multipart/form-data">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <form:hidden path="id_persona_kardex"/>
                        <form:hidden path="id_persona"/>
                        <form:hidden path="UUID"/>
                        <form:hidden path="url_idioma"/>
                        <form:hidden path="root"/>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Lengua</label>
                            <div class="col-md-9">
                                <form:select path="tipo_idioma" cssClass="form-select">
                                    <optgroup label="Seleccionar lengua">
                                        <form:options items="${tipo_idiomas}" itemLabel="value" itemValue="id"/>
                                    </optgroup>
                                </form:select>
                                <small class="form-text text-muted" id="tipo_idiomaHelp">El tipo de lengua que
                                    habla.</small>
                                <form:errors path="tipo_idioma" cssClass="invalid"></form:errors>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Descripcion del curso de idioma realizado<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <form:textarea cssClass="form-control" path="descripcion_idioma" rows="3"
                                               required=""></form:textarea>
                                <form:errors path="descripcion_idioma" cssClass="invalid"></form:errors>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Lee el idioma?<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <div class="toggle-flip">
                                    <label>
                                        <input type="checkbox" name="text_lee" <c:out
                                                value="${model.lee ? 'checked': ''}"/>><span class="flip-indecator"
                                                                                             data-toggle-on="SI"
                                                                                             data-toggle-off="NO"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Escribe el idioma?<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <div class="toggle-flip">
                                    <label>
                                        <input type="checkbox" name="text_escribe" <c:out
                                                value="${model.escribe ? 'checked': ''}"/>><span class="flip-indecator"
                                                                                                 data-toggle-on="SI"
                                                                                                 data-toggle-off="NO"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Documento original del titulo de idioma<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <input class="form-control-file" name="Image" id="Image" type="file"
                                       aria-describedby="url_idiomaHelp"
                                       accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                <small class="form-text text-muted" id="url_idiomaHelp">Cargar imagen del documento
                                    original de titulo de idioma</small>
                                <form:errors path="url_idioma" cssClass="invalid"></form:errors>
                            </div>
                        </div>

                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                curriculum?</label>
                            <div class="col-md-9">
                                <div class="toggle-flip">
                                    <label>
                                        <input type="checkbox" name="text_mostrar" <c:out
                                                value="${model.mostrar ? 'checked': ''}"/>><span class="flip-indecator"
                                                                                                 data-toggle-on="SI"
                                                                                                 data-toggle-off="NO"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="tile-footer">
                            <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                            </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                          href="${pageContext.request.contextPath}/ListaIdiomaLenguas?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
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