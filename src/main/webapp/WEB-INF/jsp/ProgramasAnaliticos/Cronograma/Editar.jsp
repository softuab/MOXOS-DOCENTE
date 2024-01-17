<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<form:form method="post" onsubmit="return false;" modelAttribute="model"
           action="${pageContext.request.contextPath}/programaanaanalitico/cronograma/editar">
    <form:hidden path="id_prg_a_cronograma"/>
    <form:hidden path="id_dct_programa_analitico"/>
    <form:hidden path="id_estado"/>
    <form:hidden path="ult_usuario"/>
    <form:hidden path="tipo_de_clase"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="mb-3">
        <label for="text_fecha">Dia y Fecha:</label>
        <input type="date" class="form-control date" id="text_fecha" name="text_fecha"
               value="<fmt:formatDate value="${model.fecha}" pattern="yyyy-MM-dd" />">
        <form:errors path="text_fecha" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="tipos_de_clases">Tipo de clase:</label>
        <c:set var="selecteds" value="${fn:split(model.tipo_de_clase, ',')}"/>
        <c:set var="encontrado" value="false" />
        <select name="tipos_de_clases" id="tipos_de_clases" class="form-select select" onchange="obtenerSeleccion(this)"
                multiple="true">
            <c:forEach var="item" items="${group}">
                <c:forEach items="${selecteds}" var="codigo">
                    <c:if test="${codigo == item.id}">
                        <c:set var="encontrado" value="true" />
                    </c:if>
                </c:forEach>
                <c:choose>
                    <c:when test="${encontrado == true}">
                        <option value="${item.id}" selected>${item.value}</option>
                        <c:set var="encontrado" value="false" />
                    </c:when>
                    <c:otherwise>
                        <option value="${item.id}">${item.value}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <form:errors path="tipo_de_clase" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="titulo_de_clase">Titulo de la clase:</label>
        <form:textarea path="titulo_de_clase" cssClass="form-control" rows="5"/>
        <form:errors path="titulo_de_clase" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="tiempo_a_emplear">Tiempo a emplear:</label>
        <form:input path="tiempo_a_emplear" cssClass="form-control"/>
        <form:errors path="tiempo_a_emplear" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="observaciones">Observaciones:</label>
        <form:textarea path="observaciones" cssClass="form-control" rows="5"/>
        <form:errors path="observaciones" cssClass="invalid"></form:errors>
    </div>
</form:form>

