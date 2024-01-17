<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<form:form   method="post" onsubmit="return false;" modelAttribute="model"
           action="${pageContext.request.contextPath}/programaanaanalitico/bibliografias/agregarrevista">
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
        <label for="titulo">Título del artículo:</label>
        <form:input path="titulo" cssClass="form-control" placeholder="Título del artículo"/>
        <form:errors path="titulo" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="tituloHelp">Título del artículo.</small>
    </div>
    <div class="mb-3">
        <label for="titulo_documento">Título de la revista:</label>
        <form:input path="titulo_documento" cssClass="form-control" placeholder="Edicion del documento"/>
        <form:errors path="titulo_documento" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="titulo_documentoHelp">Título de la revista.</small>
    </div>
    <div class="mb-3">
        <label for="Volumen">Volumen:</label>
        <form:input path="Volumen" cssClass="form-control" placeholder="Volumen"/>
        <form:errors path="Volumen" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="VolumenHelp">Volumen del documento.</small>
    </div>
    <div class="mb-3">
        <label for="numero">Número:</label>
        <form:input path="numero" cssClass="form-control" placeholder="Número"/>
        <form:errors path="numero" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="numeroHelp">Número si es una revista de paginación separada.</small>
    </div>
    <div class="mb-3">
        <label for="paginas">Páginas:</label>
        <form:input path="paginas" cssClass="form-control" placeholder="Páginas"/>
        <form:errors path="paginas" cssClass="invalid"></form:errors>
        <small class="form-text text-muted" id="paginasHelp">Páginas si es un periódico o magacín se utiliza p. o pp. antes del número o números de la página. Si se trata de una revista, únicamente se indica los números de página sin poner p. o pp..</small>
    </div>
</form:form>

