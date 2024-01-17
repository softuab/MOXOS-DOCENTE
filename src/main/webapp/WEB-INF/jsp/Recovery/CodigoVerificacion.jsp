<jsp:useBean id="now" class="java.util.Date"/>
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
    <link rel="icon" type="image/jpg" href="<c:url value="/public/image/logo.ico" />"/>
</head>
<body>

<section class="material-half-bg">
    <div class="cover"></div>
</section>

<section class="login-content">
    <div class="logo">

    </div>
    <div class="login-box">
        <form:form cssClass="login-form" modelAttribute="model" action="${pageContext.request.contextPath}/recovery" method='post'>
            <h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Recupera tu cuenta</h3>
            <h4 class="h3 mb-3 font-weight-normal text-center"><c:out value="${nombrecompleto}"/></h4>
            <form:hidden path="id_docente"/>
            <form:hidden path="nombre_completo"/>
            <p class="mb-3 text-justify">
                Para restablecer su contraseña, ingrese el código de verificación que se envió a su correo electrónico.
            </p>
            <div class="mb-3">
                <label for="inputEmail" class="sr-only">Codigo de verificacion</label>
                <form:input cssClass="form-control" placeholder="7E486110-A533-409D-BFBB-029D39F50283" path="token"/>
                <form:errors path="token" cssClass="invalid"/>
            </div>
            <div class="mb-3 btn-container">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><i class="fa fa-arrow-right"></i> Siguiente</button>
            </div>
        </form:form>
    </div>
</section>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
</body>
</html>