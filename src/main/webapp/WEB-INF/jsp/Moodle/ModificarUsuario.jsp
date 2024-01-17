<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form:form method="post" cssClass="unlock-form" modelAttribute="model"
           action="${pageContext.request.contextPath}/guardarCambios">
    <form:hidden path="idnumber"/>
    <form:hidden path="firstname"/>
    <form:hidden path="lastname"/>
    <form:hidden path="email"/>
    <form:hidden path="id"/>
    <form:hidden path="indice"/>
    <div class="mb-3">
        <label for="username">Usuario</label>
        <div class="input-group">
            <div class="input-group-prepend"><span class="input-group-text">@</span></div>
            <form:input path="username" cssClass="form-control"  readonly="true" />
            <form:errors path="username" cssClass="invalid"></form:errors>
        </div>
    </div>
    <div class="mb-3">
        <label for="password">Contraseña</label>
        <form:password path="password" cssClass="form-control"  />
        <form:errors path="password" cssClass="invalid"></form:errors>
    </div>
    <div class="mb-3">
        <label for="confirmar_password">Repetir contraseña</label>
        <form:password path="confirmar_password" cssClass="form-control" />
        <form:errors path="confirmar_password" cssClass="invalid"></form:errors>
    </div>
</form:form>
