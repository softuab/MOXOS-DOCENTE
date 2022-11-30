<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="imagetoolbar" content="no">
        <META HTTP-EQUIV="Cache-Control" CONTENT="max-age='0'" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <META http-equiv="expires" content="0" />
        <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        <title>Sistema Integrado -  Moxos</title>  
        <link rel="stylesheet" href="<c:url value='/Public/Css/main.css'/>" type="text/css">
        <link rel="stylesheet" href="<c:url value='/Public/FontAwesome/css/fontawesome-all.css'/>">  
    </head>
    <body class="app sidebar-mini rtl">
        <c:if test="${!empty id_rol}">
            <main id="contenedor" class="app-content3">
                <div class="app-title">
                    <div>
                        <h1><i class="far fa-address-card"></i>&nbsp; ${nombres}</h1>
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
                                    <form:form cssClass="bootstrap-form needs-validation" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/UsuariosCursosMoodle.fautapo">
                                        <div class="row">
                                            <div class="col-md-12 mb-3">
                                                <label for="username">Usuario</label>
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
                                                    <label for="firstName">Nombres</label>
                                                <form:input cssClass="form-control" readonly="true" path="nombre"></form:input>
                                                <form:errors path="nombre" cssClass="invalid"></form:errors> 
                                                </div>
                                                <div class="col-md-6 mb-3">
                                                    <label for="lastName">Apellidos</label>
                                                <form:input cssClass="form-control" readonly="true" path="apellidos"></form:input>
                                                <form:errors path="apellidos" cssClass="invalid"></form:errors>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 mb-3">
                                                    <label for="contrasenia">Contraseña</label>
                                                <form:password cssClass="form-control"   path="password"></form:password>
                                                <form:errors path="password" cssClass="invalid"></form:errors>
                                                </div>
                                                <div class="col-md-6 mb-3">
                                                    <label for="repetircontrasenia">Repetir contraseña</label>
                                                <form:password cssClass="form-control"   path="confirmar_password"></form:password>
                                                <form:errors path="confirmar_password" cssClass="invalid"></form:errors>
                                                </div>
                                            </div>
                                            <div class="mb-3">
                                                <label for="correo">Correo</label>
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
        <script src="<c:url value='/Public/jquery/jquery-3.3.1.min.js'/>"></script> 
        <script src="<c:url value='/Public/jquery/umd/popper.min.js'/>" ></script>
        <script src="<c:url value='/Public/bootstrap/js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/main.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/pace.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/bootstrap-notify.min.js'/>"></script>
        <script src="<c:url value="/Public/SweetAlert2/sweetalert.min.js"/>"></script>
        <script>
                                            document.oncontextmenu = function () {
                                                return false;
                                            } 
        </script>
    </body>
</html>
