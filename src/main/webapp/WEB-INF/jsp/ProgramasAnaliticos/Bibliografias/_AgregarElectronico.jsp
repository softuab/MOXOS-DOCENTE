<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<form:form  method="post" onsubmit="return false;" modelAttribute="model"
           action="${pageContext.request.contextPath}/programaanaanalitico/bibliografias/agregarelectronico">
    <form:hidden path="id_prg_a_bibliografia"/>
    <form:hidden path="id_dct_programa_analitico"/>
    <form:hidden path="id_estado"/>
    <form:hidden path="ult_usuario"/>
    <form:hidden path="tipo_referncia"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="mb-3">
        <label for="tipobibliografia">Tipo de bibliografia:</label>
        <form:select cssClass="form-select" path="tipobibliografia" items="${model.tipos}" itemLabel="value" itemValue="id"/>
        <form:errors path="tipobibliografia" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="tipobibliografiaHelp">Definir el tipo de bibliografia del documento.</small>
    </div>
    <div class="mb-3">
        <label for="ubicacion">Ubicacion:</label>
        <form:input path="ubicacion" cssClass="form-control" placeholder="Ubicacion"/>
        <form:errors path="ubicacion" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="ubicacionHelp">Definir la ubicacion fisica del documento.</small>
    </div>
    <div class="mb-3">
        <label for="autor">Autor:</label>
        <form:input path="autor" cssClass="form-control" placeholder="Autor"/>
        <form:errors path="autor" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="autorHelp">Autor Apellido e inicial(es) de los nombre(s).</small>
    </div>
    <div class="mb-3">
        <label for="text_fecha_publicacion">Fecha de publicación:</label>
        <form:input type="date" path="text_fecha_publicacion" cssClass="form-control date"/>
        <form:errors path="text_fecha_publicacion" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="text_fecha_publicacionHelp">Fecha de publicación.</small>
    </div>
    <div class="mb-3">
        <label for="titulo_documento">Titulo del documento:</label>
        <form:input path="titulo_documento" cssClass="form-control" placeholder="Titulo del documento"/>
        <form:errors path="titulo_documento" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id=titulo_documentoituloHelp">Título del documento.</small>
    </div>
    <div class="mb-3">
        <label for="text_fecha_consulta">Fecha de consulta:</label>
        <form:input type="date" path="text_fecha_consulta" cssClass="form-control date"/>
        <form:errors path="text_fecha_consulta" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="fecha_consultaHelp">Fecha de consulta.</small>
    </div>
    <div class="mb-3">
        <label for="url">Dirección web:</label>
        <form:input path="url" cssClass="form-control" placeholder="Dirección web"/>
        <form:errors path="url" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="urlHelp">Dirección URL - Universal Resource Locator.</small>
    </div>
</form:form>