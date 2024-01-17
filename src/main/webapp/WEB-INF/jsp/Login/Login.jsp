<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <title>Sistema - Moxos</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="theme-color" content="#305496">
    <link rel="icon" type="image/jpg" href="<c:url value="/public/image/logo.ico" />"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>

<body>

<section class="material-half-bg">
    <div class="cover"></div>
</section>

<section class="login-content">
    <div class="logo">
    </div>
    <div class="login-box">
        <form class="login-form" id="forma" name='forma'
              action='${pageContext.request.contextPath}/authentication' method='post'>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="twofactor" id="twofactor" value="${twofactor}">
            <h3 class="login-head"><img src="<c:url value="/public/image/moxos.png"/>" style="width: 90%;"></h3>
            <h4 class="text-center user-name">Te damos la bienvenida</h4>
            <p class="text-center text-muted">${login}</p>
            <input type="hidden" id="ubicacion" name="ubicacion"/>
            <c:choose>
                <c:when test="${twofactor==true}">
                    <input type="hidden" name="apodo" id="apodo" value="${login}">
                    <div class="input-group mb-3">
                        <span class="input-group-text"><i class="fa fa-key" aria-hidden="true"></i></span>
                        <input type="password" class="form-control" placeholder="Contraseña" required id="clave"
                               name='clave'>
                        <span class="input-group-text"><i data-toggle="toggle" style="cursor: pointer;"
                                                          class="fa fa-eye-slash" aria-hidden="true"></i></span>
                    </div>
                    <div class="mb-3">
                        <label>Introducir el codigo</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fa fa-key" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" placeholder="Codigo de verificacion" required
                                   autofocus id="code"
                                   name='code'>
                        </div>
                    </div>
                    <div class="mb-3 btn-container">
                        <a class="btn btn-primary" href="<c:url value="/signin"/>" style="width: 120px; float: left"><i
                                class="fa fa-arrow-left"></i> Atras</a>
                        <button class="btn btn-primary" style="width: 120px; float: right"><i
                                class="fa fa-arrow-right"></i> Siguiente
                        </button>
                    </div>
                    <div class="mb-3 text-end">
                        <p class="semibold-text mb-2"><a href="<c:url value="/recovery/twofactor"><c:param name="apodo" value="${login}"></c:param></c:url>">Recuperar contraseña ?</a></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="apodo" id="apodo" value="${login}">
                    <div class="input-group mb-3">
                        <span class="input-group-text"><i class="fa fa-key" aria-hidden="true"></i></span>
                        <input type="password" class="form-control" placeholder="Contraseña" required id="clave"
                               name='clave'>
                        <span class="input-group-text"><i data-toggle="toggle" style="cursor: pointer;"
                                                          class="fa fa-eye-slash" aria-hidden="true"></i></span>
                    </div>
                    <div class="input-group mb-3">
                        <img id="captcha_id" name="imgCaptcha" src="captcha.jpg">
                        <a href="javascript:;" title="Cambiar captcha"
                           onclick="document.getElementById('captcha_id').src = 'captcha.jpg?' + Math.random();  return false">
                            <i style="cursor: pointer;" class="fa fa-refresh" aria-hidden="true"></i></a>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><i class="fa fa-user-secret" aria-hidden="true"></i></span>
                        <input type="text" class="form-control" placeholder="Introducir codigo capchat" required
                               autofocus name='captcha'>
                    </div>
                    <div class="mb-3 btn-container">
                        <a class="btn btn-primary" href="<c:url value="/signin"/>" style="width: 120px; float: left"><i
                                class="fa fa-arrow-left"></i> Atras</a>
                        <button class="btn btn-primary" style="width: 120px; float: right"><i
                                class="fa fa-arrow-right"></i> Siguiente
                        </button>
                    </div>
                    <div class="mb-3 text-end">
                        <p class="semibold-text mb-2"><a href="#" data-toggle="flip">Recuperar contraseña ?</a></p>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
        <form class="forget-form" id='formarecovery' action='<c:url value="/recovery"/>' method='GET'>
            <h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Recuperacion</h3>
            <h4 class="text-center user-name">Cuenta</h4>
            <p class="text-center text-muted">${login}</p>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="correo" value="${login}"/>
            <c:choose>
                <c:when test="${twofactor==true}">
                </c:when>
                <c:otherwise>
                    <div class="input-group mb-3">
                        <img id="captcha_id_recovery" name="captcha_id_recovery" src="captcha.jpg">
                        <a href="javascript:;" title="Cambiar captcha"
                           onclick="document.getElementById('captcha_id_recovery').src = 'captcha.jpg?' + Math.random();  return false">
                            <i style="cursor: pointer;" class="fa fa-refresh" aria-hidden="true"></i></a>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><i class="fa fa-user-secret" aria-hidden="true"></i></span>
                        <input type="text" class="form-control" placeholder="Introducir codigo de verificacion" required
                               autofocus name='captcha' id='captcha'>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="mb-3 btn-container">
                <button id="btnrecovery"
                        type="button" onclick="enviarFormulario(this)" class="btn btn-primary btn-block"><i
                        class="fa fa-unlock fa-lg fa-fw"></i> Resetear
                </button>
            </div>
            <div class="mb-3 text-end">
                <p class="semibold-text mb-0"><a href="#" id="idRetorno" data-toggle="flip"><i
                        class="fa fa-angle-left fa-fw"></i>
                    regresar</a></p>
            </div>
        </form>
    </div>
    <div id="toastcontainer" class="toast-container position-fixed top-0 end-0 p-3">
        <c:if test="${authenticationError != null}">

            <div id="errortoast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header bg-danger text-white">
                    <i class="fa fa-bell" aria-hidden="true"></i>&nbsp;&nbsp;
                    <strong class="me-auto"> Notificacion</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    <c:out value="${authenticationError}"/>
                </div>
            </div>
        </c:if>
    </div>
</section>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/public/js/togglepassword.js" />"></script>
<script src="<c:url value="/public/js/login.js?v=1" />"></script>
<script>
    <c:if test="${authenticationError != null}">
    let toast = document.getElementById('errortoast');
    window.onload = load();

    function load() {
        getLocation();
        let toastBootstrap = bootstrap.Toast.getOrCreateInstance(toast)
        toastBootstrap.show();
    }

    </c:if>

    function enviarFormulario(input) {
        input.disabled = true;
        input.innerHTML = '<i class="fa fa-spinner fa-spin fa-3x fa-fw" aria-hidden="true"></i>' + ' <span class="sr-only">Enviando...</span>';
        document.getElementById('idRetorno').classList.add('invisible');
        document.getElementById('formarecovery').submit();
    }
</script>
</body>
</html>