<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
            <h1><i class="fa fa-address-card"></i>&nbsp; Detalle de planilla aguinaldo</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>RUBRO</th>
                        <th>CI</th>
                        <th>NOMBRE COMPLETO</th>
                        <th>CARGO</th>
                        <th>DEPENDENCIA</th>
                        <th>LOCALIDAD</th>
                        <th>TOTAL GANADO</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${model}">
                    <td>${item.Rubro}</td>
                    <td>${item.CI}</td>
                    <td>${item.NombreCompleto}</td>
                    <td>${item.Cargo}</td>
                    <td>${item.Dependendencia}</td>
                    <td>${item.Localidad}</td>
                    <td>${item.TotGanado}</td>
                    <td><a  href="${pageContext.request.contextPath}/rrhh/aguinaldo/Descargar?id=${item.ID}" class="btn btn-primary btn-sm" >Descargar</a>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
</body>
</html>

