<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<form:form name="forma" method="post" modelAttribute="model"
           action="${pageContext.request.contextPath}/programaanaanalitico/bibliografias/editardocumento">
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
        <label for="anio">Año de publicacion:</label>
        <form:input path="anio" cssClass="form-control" placeholder="Año de publicacion"/>
        <form:errors path="anio" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="anioHelp">Año de publicación (entre paréntesis).</small>
    </div>
    <div class="mb-3">
        <label for="titulo">Titulo del documento:</label>
        <form:input path="titulo" cssClass="form-control" placeholder="Titulo del documento"/>
        <form:errors path="titulo" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="tituloHelp">Título del trabajo y subtítulo, si hay, separados por dos puntos (en itálicas o negritas).</small>
    </div>
    <div class="mb-3">
        <label for="ubicacion">Edicion del documento:</label>
        <form:input path="edicion" cssClass="form-control" placeholder="Edicion del documento"/>
        <form:errors path="edicion" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="edicionHelp">Edición a partir de la segunda edición, se abrevia con (ed.) (minúsculas y va entre paréntesis).</small>
    </div>
    <div class="mb-3">
        <label for="ubicacion">Lugar de publicación:</label>
        <form:input path="lugar" cssClass="form-control" placeholder="Lugar de publicación"/>
        <form:errors path="lugar" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="lugarHelp">Lugar de publicación del documento.</small>
    </div>
    <div class="mb-3">
        <label for="editorial">Editorial:</label>
        <form:input path="editorial" cssClass="form-control" placeholder="Editorial"/>
        <form:errors path="editorial" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="editorialHelp">Editorial del documento.</small>
    </div>
</form:form>
