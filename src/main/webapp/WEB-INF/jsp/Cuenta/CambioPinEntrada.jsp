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
    <link href="<c:url value="/public/css/main.css?v=1" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>

<section class="lockscreen-content">
    <div class="lock-box"><img class="rounded-circle user-image" src="${simagen}">
        <h4 class="text-center user-name">${nombres}</h4>
        <p class="text-center text-muted">Cambio clave(PIN) docente</p>
        <form:form method="post" cssClass="unlock-form" modelAttribute="model"
                   action="${pageContext.request.contextPath}/cambioPinDocente/avisoCambioPin">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="mb-3">
                <form:password path="clave" cssClass="form-control"
                               placeholder="Introducir contraseña antigua"></form:password>
                <form:errors path="clave" cssClass="invalid"></form:errors>
            </div>
            <div class="mb-3 btn-container">
                <button class="btn btn-primary btn-block" type="submit"><i class="fa fa-search"></i>&nbsp; Buscar
                </button>
            </div>
        </form:form>
    </div>
</section>
<div id="loader"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let form = new Form(document.getElementById('model'), {target: document.getElementById('loader'), text: 'Enviando..', url: '<c:url value="/static/image/logominiatura.png" />' });
</script>
</body>
</html>