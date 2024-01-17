<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<body>
<section class="material-half-bg">
    <div class="cover"></div>
</section>
<section class="lockscreen-content">|
    <div class="lock-box"><img class="rounded-circle user-image" src="${simagen}">
        <h4 class="text-center user-name">${usuario}</h4>
        <p class="text-center text-muted">Seleccionar Gestion y Mes</p>
        <form:form name="forma" method="post" cssClass="unlock-form" modelAttribute="model" action="${pageContext.request.contextPath}/rrhh/aguinaldo/ListarFunciones">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form:hidden path="carnet"/>
            <form:hidden path="tipo"/>
            <div class="mb-3">
                <label class="control-label">GESTION</label>
                <form:select path="iDGestion" cssClass="form-control form-select">
                    <form:options items="${model.gestion}" itemValue="id" itemLabel="value"/>
                </form:select>
            </div>
            <div class="mb-3">
                <label class="control-label">TIPO DE AGUINALDO</label>
                <form:select path="iDAguinaldo" cssClass="form-control form-select" >
                    <form:options items="${model.aguinaldo}" itemValue="id" itemLabel="value"/>
                </form:select>
            </div>
            <div class="form-group btn-container">
                <button class="btn btn-primary btn-block" type="submit"><i class="fa fa-unlock fa-lg"></i>GENERAR BOLETAS </button>
            </div>
        </form:form>
    </div>
</section>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script>
    function CargarPlanillas() {
        var html = '';
        var data = {
            iDGestion: $("#iDGestion").val(),
            idMes: $("#idMes").val()
        }
        $.ajax({
            type: 'GET',
            url: "${pageContext.request.contextPath}/rrhh/ListarPlanillas.fautapo",
            data: data,
            cache: false
        }).done(function (value) {
            $.each(value, (index, item) => {
                html += '<option value="' + item.Id + '">' + item.Value + '</option>';
            });
            document.getElementById('iDPlanilla').innerHTML = html;
        }).fail(function (jqXHR, textStatus) {
            console.log("Ocurrio un error en el servidor");
        });
    }
</script>
</body>
</html>
