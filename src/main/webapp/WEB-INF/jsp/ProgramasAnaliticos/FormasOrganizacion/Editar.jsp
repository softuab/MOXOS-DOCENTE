<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form method="post" onsubmit="return false;" modelAttribute="model"
           action="${pageContext.request.contextPath}/programaanaanalitico/formasorganizacion/editar">
    <form:hidden path="id_prg_a_distribucion"/>
    <form:hidden path="totalHoras"/>
    <form:hidden path="horasAnterior"/>
    <form:hidden path="id_dct_programa_analitico"/>
    <div class="mb-3">
        <label for="horas">Horas:</label>
        <form:input path="horas" cssClass="form-control" type="number"/>
        <form:errors path="horas" cssClass="invalid"></form:errors>
    </div>
</form:form>
