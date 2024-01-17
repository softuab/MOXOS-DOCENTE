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
        <h3 class="login-head"><img style="width: 260px" src="<c:url value="/public/image/moxos.png"/>"
                                    style="width: 90%;"></h3>
        <h4 class="text-center user-name">Acceder</h4>
        <p class="text-center text-muted">Usa tu Cuenta Institucional</p>
        <c:if test="${authenticationError != null}">
            <p class="text-center invalid">${authenticationError}</p>
        </c:if>
        <form:form name="forma" method="post" cssClass="unlock-form" modelAttribute="model"
                   action="${pageContext.request.contextPath}/signin">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="mb-3">
                <label>Correo institucional</label>
                <div class="input-group">
                    <span class="input-group-text">@</span>
                    <input type="text" class="form-control" placeholder="Usuario" required autofocus id="apodo"
                           name='apodo' value="${login}">
                </div>
            </div>
            <div class="mb-3 btn-container">
                <button class="btn btn-primary btn-block" type="submit"><i class="fa fa-arrow-right"></i> Siguiente
                </button>
            </div>
        </form:form>
    </div>
    <div id="loader"></div>
    <c:if test="${authenticationError == null}">
        <div class="modal fade zoom" id="anuncio" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">
                        <img src="<c:url value='/public/image/comunicado.png'/>" class="img-fluid"
                             alt="Responsive image">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="waves-effect waves-red btn-flat" data-bs-dismiss="modal">ACEPTAR
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</section>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script>
    <c:if test="${authenticationError == null}">
    window.onload = load();

    function load() {
        let modal = new bootstrap.Modal(document.getElementById("anuncio"));
        modal.show();
    }

    </c:if>

</script>
</body>
</html>