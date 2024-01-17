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
    <link href="<c:url value="/public/css/main.css?v=1" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
    <style>
        .bd-callout {
            --bs-link-color-rgb: rgb(10, 88, 202);
            --bs-code-color: #ab296a;
            padding: 1.25rem;
            margin-top: 1.25rem;
            margin-bottom: 1.25rem;
            color: #664d03;
            background-color: #fff3cd;
            border-left: 0.25rem solid #ffe69c;
        }
    </style>
</head>
<body class="app sidebar-mini rtl">
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>Activar/Desactivar vertificacion de dos pasos</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="row">
                    <div class="col mb-3">
                        <div class="bd-callout bd-callout-warning">
                            <strong>Nota!</strong> Para activar la verificación de dos pasos debes seguir los pasos:<br>
                            <ul style="padding-top: 25px; list-style: none">
                                <li>1. Instalar Google Authenticator en el móvil.</li>
                                <li>2. Activar la verificación en dos pasos en la cuenta y mostrar el código QR.</li>
                                <li>3. <strong>Escanear el código QR con Google Authenticator.</strong></li>
                                <li>4. Iniciar sesión en el sitio web introduciendo tu nombre de usuario y contraseña.
                                </li>
                                <li>5. Introducir ne la web el código numérico que se muestra en Google Authenticator.
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        ¿Desea activar la verificacion de dos pasos?
                        <div class="toggle-flip" style="float: right">
                            <label>
                                <c:choose>
                                    <c:when test="${twofactor}">
                                        <input type="checkbox" checked onchange="enable2FA(this)" ><span class="flip-indecator"
                                                                                                 data-toggle-on="SI"
                                                                                                 data-toggle-off="NO"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" onchange="enable2FA(this)" ><span class="flip-indecator"
                                                                                                 data-toggle-on="SI"
                                                                                                 data-toggle-off="NO"></span>
                                    </c:otherwise>
                                </c:choose>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col text-center">
                        <img id="imageQR" class="invisible" src="">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="loader"></div>
</main>
</div>>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js" />"></script>
<script src="<c:url value="/static/js/ajax.js" />"></script>
<script>
    function enable2FA(input) {
        let image = document.getElementById('imageQR');
        if (input.checked) {
            image.classList.remove('invisible');
        }
        else {
            image.classList.add('invisible');
        }
        set2FA(input.checked, image)
    }

    function set2FA(use2FA, image) {
        let data = {use2FA: use2FA}
        Post('./update/2fa', data)
            .then(function (data) {
                if (data.status === 'OK') {
                    image.src = data.message;
                }
            })
            .catch(function () {

            })
    }
</script>
</body>
</html>