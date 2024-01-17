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
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>

<section class="lockscreen-content">
    <div class="lock-box"><img class="rounded-circle user-image" src="${simagen}">
        <h4 class="text-center user-name">${usuario}</h4>
        <p class="text-center text-muted">Administrar Cursos Aulas Virtuales</p>
        <form:form name="forma" method="post" cssClass="unlock-form" modelAttribute="model"
                   action="${pageContext.request.contextPath}/ListarAsignacionesAdministrarCursos">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="hora" value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />'/>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="inputGroupSelect01">Gestion</label>
                </div>
                <form:select path="gestion" cssClass="form-select">
                    <c:forEach var="i" begin="1990" end="${gestion}">
                        <c:choose>
                            <c:when test="${i == gestion}">
                                <option selected value="${i}"><c:out value="${i}"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="${i}"><c:out value="${i}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="inputGroupSelect01">Periodo</label>
                </div>
                <form:select path="periodo" cssClass="form-select">
                    <c:forEach var="i" begin="1" end="2">
                        <c:choose>
                            <c:when test="${i == periodo}">
                                <option selected value="${i}"><c:out value="${i}"/></option>
                            </c:when>
                            <c:otherwise>
                                <option value="${i}"><c:out value="${i}"/></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </div>
            <div class="mb-3">
                <form:password path="clave" cssClass="form-control"
                               placeholder="Introduzca su contraseña"></form:password>
                <form:errors path="clave" cssClass="invalid"></form:errors>
            </div>
            <div class="mb-3 btn-container">
                <button class="btn btn-primary btn-block" type="submit"><i class="fa fa-unlock fa-lg"></i>  Ingresar
                </button>
            </div>
        </form:form>
    </div>
    <div id="loader"></div>
</section>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let form = new Form(document.getElementById('model'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
</script>
</body>
</html>