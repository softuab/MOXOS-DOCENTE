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
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<c:if test="${!empty id_rol}">
    <main id="contenedor" class="app-content3">
        <div class="app-title">
            <div>
                <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            </div>
            <ul class="app-breadcrumb breadcrumb">
                <li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
                <li class="breadcrumb-item">${fase}</li>
            </ul>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="tile">
                    <div class="row">
                        <div class="col-md-12 order-md-2 mb-12">
                            <h4 class="mb-3">Actualizar usuario y contraseña</h4>
                            <form:form cssClass="bootstrap-form needs-validation" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/UsuariosCursosMoodle">
                                <div class="row">
                                    <div class="col-md-12 mb-3">
                                        <label for="usuario">Usuario</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">@</span>
                                            </div>
                                            <form:input cssClass="form-control" readonly="true" path="usuario"></form:input>
                                            <form:errors path="usuario" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="nombre">Nombres</label>
                                        <form:input cssClass="form-control" readonly="true" path="nombre"></form:input>
                                        <form:errors path="nombre" cssClass="invalid"></form:errors>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="apellidos">Apellidos</label>
                                        <form:input cssClass="form-control" readonly="true" path="apellidos"></form:input>
                                        <form:errors path="apellidos" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="password">Contraseña</label>
                                        <form:password cssClass="form-control"   path="password"></form:password>
                                        <form:errors path="password" cssClass="invalid"></form:errors>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="confirmar_password">Repetir contraseña</label>
                                        <form:password cssClass="form-control"   path="confirmar_password"></form:password>
                                        <form:errors path="confirmar_password" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="email">Correo</label>
                                    <form:input type="email" readonly="true" cssClass="form-control"   path="correo"></form:input>
                                    <form:errors path="correo" cssClass="invalid"></form:errors>
                                </div>
                                <form:hidden path="idnumber"/>
                                <form:hidden path="aux"/>
                                <form:hidden path="gestion"/>
                                <form:hidden path="periodo"/>
                                <form:hidden path="nombres"/>
                                <form:hidden path="avanzado"/>
                                <form:hidden path="clave"/>
                                <button class="btn btn-primary btn-lg btn-block" type="submit">Continuar con el registro</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let form = new Form(document.getElementById('model'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
    document.oncontextmenu = function () {
        return false;
    }
</script>
</body>
</html>
