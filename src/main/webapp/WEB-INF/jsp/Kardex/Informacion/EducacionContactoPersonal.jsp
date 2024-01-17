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
    </head>
    <body class="app sidebar-mini rtl">
        <main class="app-content3">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-address-card"></i>&nbsp; Contacto personal</h1> 
                </div>
            </div>
            <div class="row"> 
                <div class="col-sm-12">
                    <div class="tile">
                        <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/kardex/RegistrarContactoPersonal" enctype="multipart/form-data">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona_kardex"/>
                            <form:hidden path="id_persona"/>
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Direccion:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="direccion" cssClass="form-control" placeholder="Direccion del docente"></form:input>
                                    <form:errors path="direccion" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Telefono:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="telefonocelular" cssClass="form-control" placeholder="Telefono del docente"></form:input>
                                    <form:errors path="telefonocelular" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Direccion de correo institucional:</label>
                                    <div class="col-md-9">
                                    <form:input type="email" path="correoinsitucional" cssClass="form-control" placeholder="Correo electronico" required=""></form:input>
                                    <form:errors path="correoinsitucional" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="tile-footer">
                                    <div class="row">
                                        <div class="col-md-8 col-md-offset-3">
                                            <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/IndexKardexPersonal?id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                        </div>
                                    </div>
                                </div>
                        </form:form> 
                    </div>
                </div>
            </div> 
        </main>
        <script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
        <script src="<c:url value="/public/js/popper.min.js" />"></script>
    </body>
</html>