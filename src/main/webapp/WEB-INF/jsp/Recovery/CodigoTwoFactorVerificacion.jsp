<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title>Sistema - Moxos</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="icon" type="image/jpg" href="<c:url value="/public/image/logo.ico" />"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>

<section class="lockscreen-content">
    <div style="min-width: 350px;" class="lock-box">
        <h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Recupera tu cuenta</h3>
        <h4 class="h3 mb-3 font-weight-normal text-center"><c:out value="${nombrecompleto}"/></h4>
        <form:form name="forma" method="post" cssClass="unlock-form" modelAttribute="model"
                   action="${pageContext.request.contextPath}/recovery/twofactor">
            <form:hidden path="id_docente"/>
            <form:hidden path="nombre_completo"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <p class="mb-3 text-center">
                Para restablecer su contraseña, ingrese el código de verificación <br>que se envió a su celular por google autenticator.
            </p>
            <div class="mb-3">
                <label for="inputEmail" class="sr-only">Codigo de verificacion</label>
                <form:input cssClass="form-control" placeholder="789456" path="token"/>
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