<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="row">
    <div class="col">
        <div class="mb-3">
            <label for="">Tipo de referencia bibliografica</label>
            <select disabled id="idtiporeferenciabibliografica" class="form-select">
                <c:forEach var="item" items="${group.items}">
                    <option value="${item.id}" <c:if test="${group.tipo_referncia==item.id}">selected</c:if> >${item.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>
<div class="row">
    <div class="col" id="divtiporeferenciabibliografica">
        <c:if test="${group.tipo_referncia==1}">
            <form:form name="forma" onsubmit="return false;"  method="post" modelAttribute="model"
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
        </c:if>
        <c:if test="${group.tipo_referncia==2}">
            <form:form name="forma" onsubmit="return false;" method="post" modelAttribute="model"
                       action="${pageContext.request.contextPath}/programaanaanalitico/bibliografias/editarrevista">
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
                    <input name="text_fecha_publicacion" id="text_fecha_publicacion" type="date" value="<fmt:formatDate value="${model.fecha_publicacion}" pattern="yyyy-MM-dd" />" class="form-control date" >
                    <form:errors path="text_fecha_publicacion" cssClass="invalid"></form:errors>
                    <small class="form-text text-muted" id="fecha_publicacionHelp">Fecha de publicación.</small>
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
        </c:if>
        <c:if test="${group.tipo_referncia==3}">
            <form:form name="forma" onsubmit="return false;" method="post" modelAttribute="model"
                       action="${pageContext.request.contextPath}/programaanaanalitico/bibliografias/editarelectronico">
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
                    <input name="text_fecha_publicacion" id="text_fecha_publicacion" type="date" value="<fmt:formatDate value="${model.fecha_publicacion}" pattern="yyyy-MM-dd" />" class="form-control date" >
                    <form:errors path="text_fecha_publicacion" cssClass="invalid"></form:errors>
                    <small class="form-text text-muted" id="fecha_publicacionHelp">Fecha de publicación.</small>
                </div>
                <div class="mb-3">
                    <label for="titulo_documento">Titulo del documento:</label>
                    <form:input path="titulo_documento" cssClass="form-control" placeholder="Titulo del documento"/>
                    <form:errors path="titulo_documento" cssClass="invalid"></form:errors>
                    <small class="form-text text-muted" id=titulo_documentoituloHelp">Título del documento.</small>
                </div>
                <div class="mb-3">
                    <label for="text_fecha_consulta">Fecha de consulta:</label>
                    <input name="text_fecha_consulta" id="text_fecha_consulta" type="date" value="<fmt:formatDate value="${model.fecha_consulta}" pattern="yyyy-MM-dd" />" class="form-control date" >
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
        </c:if>
    </div>
</div>