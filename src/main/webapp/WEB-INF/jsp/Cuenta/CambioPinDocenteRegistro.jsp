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
    <link href="<c:url value="/public/css/main.css?v=1" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/pschecker.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>Modificacion de contraseña</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="row">
                    <div class="col mb-3">
                        <div class="strength-indicator">
                            <div class="meter">
                            </div>
                            Las contraseñas fuertes contienen de 8 a 16 caracteres, no incluyen palabras o nombres
                            comunes,
                            y combina letras mayúsculas, minúsculas, números y símbolos.
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="password-container">
                            <form:form name="forma" method="post" cssClass="unlock-form" modelAttribute="model"
                                       action="${pageContext.request.contextPath}/cambioPinDocente/registrarNuevoPin">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <form:hidden path="id_docente"/>
                                <input type="hidden" name="csrfPreventionSalt"
                                       value="<c:out value='${csrfPreventionSalt}'/>"/>
                                <div class="mb-3">
                                    <label for="nueva_clave">Nueva contraseña</label>
                                    <form:password cssClass="form-control" path="nueva_clave"/>
                                    <form:errors path="nueva_clave" cssClass="invalid"></form:errors>
                                </div>
                                <div class="mb-3">
                                    <label for="nueva_clave">Confirmar nueva contraseña</label>
                                    <form:password cssClass="form-control" path="conf_nueva_clave"/>
                                    <form:errors path="conf_nueva_clave" cssClass="invalid"></form:errors>
                                </div>
                                <input type="submit" class="btn btn-primary btn-block" value="Modificar">
                                <div>Los campos con <strong class="text-danger">(*)</strong>, son obligatorios.</div>
                            </form:form>
                        </div>
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
<script src="<c:url value="/static/js/formload.js" />"></script>
<script src="<c:url value="/static/js/pschecker.js" />"></script>
<script>
    let form = new Form(document.getElementById('model'), {target: document.getElementById('loader'), text: 'Enviando..', url: '<c:url value="/static/image/logominiatura.png" />' });
    let pschecker=new psChecker(document.getElementById('nueva_clave'),document.getElementById('conf_nueva_clave'),{});
</script>
</body>
</html>
