<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
            <h1><i class="fa fa-address-card-o" aria-hidden="true"></i>&nbsp; ${nombres}</h1>
            <p>Modificacion de contraseña</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 mx-auto">
                        <div class="px-4 py-5 my-5 text-center">
                            <div class="card border-secondary mb-3">
                                <div class="card-header"><i class="fa fa-info-circle" aria-hidden="true"></i>&nbsp;&nbsp;RECOMENDACIONES:
                                </div>
                                <div class="card-body text-secondary">
                                    <form id="model" action="<c:url value="/cambioPinDocente/ingresarNuevoPin"/>"
                                          method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <p class="card-text text-start">
                                            <strong class="text-danger">Atenci&oacute;n señor(a) docente:</strong>  <c:out
                                                value="${nombres}"/><br/>
                                            <strong class="text-danger">*</strong> Utilize caracteres validos [A-Z],[a-z]
                                            y
                                            [1-9].<br/>
                                            <strong class="text-danger">*</strong> No utilize palabras del diccionario ni
                                            nombres
                                            propios.<br/>
                                            <strong class="text-danger">*</strong> Invente una palabra que pueda
                                            recordar.<br/>
                                            <strong class="text-danger">*</strong> Componga palabras e inserte
                                            números.<br/>
                                        </p>
                                        <div class="text-center"><input class="btn btn-primary" type=submit
                                                                        value="Buscar">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="loader"></div>
</main>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
</body>
</html>


