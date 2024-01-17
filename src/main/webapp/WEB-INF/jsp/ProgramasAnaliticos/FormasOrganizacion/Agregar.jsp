<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form method="post" onsubmit="return false;" modelAttribute="model"
           action="${pageContext.request.contextPath}/programaanaanalitico/formasorganizacion/agregar">
    <form:hidden path="id_prg_a_distribucion"/>
    <form:hidden path="id_dct_programa_analitico"/>
    <form:hidden path="id_prg_a_contenido"/>
    <form:hidden path="id_estado"/>
    <form:hidden path="ult_usuario"/>
    <form:hidden path="totalHoras"/>
    <div class="mb-3">
        <label for="tipo_forma">Tipo:</label>
        <select name="tipo_forma" id="tipo_forma" onchange="llenarLista(this,document.getElementById('id_prg_a_formas'))" class="form-select">
            <c:forEach var="item" items="${model.tipo}">
                <option value="${item.id}" <c:if test="${item.selected}">selected</c:if>>${item.value}</option>
            </c:forEach>
        </select>
        <form:errors path="tipo_forma" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="id_prg_a_formas">Forma de organizacion:</label>
        <select name="id_prg_a_formas" id="id_prg_a_formas" class="form-select">
            <c:forEach var="group" items="${model.items}">
                <optgroup label="${group.group}">
                    <c:forEach var="item" items="${group.items}">
                        <option value="${item.id}" <c:if test="${item.selected}">selected</c:if>>${item.value}</option>
                    </c:forEach>
                </optgroup>
            </c:forEach>
        </select>
        <form:errors path="id_prg_a_formas" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="horas">Horas:</label>
        <form:input path="horas" cssClass="form-control" type="number"/>
        <form:errors path="horas" cssClass="invalid"></form:errors>
    </div>
</form:form>