<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
            <h1><i class="fa fa-address-card"></i>&nbsp; ${usuario}</h1>
            <p>Detalle de calendarios de calificaciones por materias en el periodo ${periodo}/${gestion}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="bs-component">
                <div class="p-5 mb-4 bg-light border rounded-3">
                    <div class="container-fluid py-5">
                        <h1 class="display-5 fw-bold">Aviso, importante!</h1>
                        <p class="col-md-12 fs-5 text-center"><strong>Resolución Rectoral Nro 13-A/2017
                            27 de Enero de 2017
                            Reglamento de Introducción de Registro de Calificaciones de la U.A.B.</strong></p>
                        <p class="col-md-12 fs-6">
                            <strong>Articulo 8</strong> <br/>
                            El incumplimiento de los plazos establecidos en el Calendario Académico
                            para cada periodo o gestión académica vigente, para el registro
                            de calificaciones serán consideradas a los docentes como una
                            infracción y estarán sujeras a sanciones discipliniarias de acuerdo
                            a lo establecido en el presente reglamento y reglamento de administración
                            de personal.
                        </p>
                        <a class="btn btn-primary btn-lg"
                           href="https://www.uabjb.edu.bo/uabjb/images//NORMATIVA/Reglamentoregistrocalificaciones.pdf"
                           role="button">Leer mas..</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="table-responsive-lg">
                    <table class="table" aria-describedby="calendario">
                        <thead>
                        <tr>
                            <th class="text-center" scope="calendario" colspan="2">CALENDARIO DE CALIFICACION</th>
                        </tr>
                        <tr>
                            <th class="text-center" scope="Gestion">GESTION/PERIODO</th>
                            <th class="text-center" scope="Evaluacion">DETALLE</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="aux" value=""/>
                        <c:forEach items="${ListaCarreraDetalle}" var="value">
                            <c:choose>
                                <c:when test="${value.carrera!=aux}">
                                    <tr>
                                        <td colspan="2"><h5 class="text-uppercase"><strong>${value.carrera}</strong>
                                        </h5></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <h3 class="title"><span
                                                    class="badge badge-secondary">${value.periodo}/${value.gestion}</span>
                                            </h3>
                                        </td>
                                        <td>
                                            <strong>Tipo Evaluacion: </strong> ${value.titulo}<br>
                                            <strong>Tipo nota: </strong> ${value.tipoNota}<br>
                                            <strong>Fecha: </strong> <i class="fa fa-calendar"></i><c:if
                                                test="${value.esAmpliacion}"> Ampliado hasta </c:if> ${value.fechainicio}
                                            <c:if test="${value.fechainicio != value.fechafin}"><i
                                                    class="fa fa-calendar"></i> ${value.fechafin}</c:if>
                                        </td>
                                    </tr>
                                    <c:set var="aux" value="${value.carrera}"/>
                                </c:when>
                                <c:when test="${value.carrera==aux}">
                                    <tr>
                                        <td>
                                            <h3 class="title"><span
                                                    class="badge badge-secondary">${value.periodo}/${value.gestion}</span>
                                            </h3>
                                        </td>
                                        <td>
                                            <strong>Tipo Evaluacion: </strong> ${value.titulo}<br>
                                            <strong>Tipo nota: </strong> ${value.tipoNota}<br>
                                            <strong>Fecha: </strong> <i class="fa fa-calendar"></i><c:if
                                                test="${value.esAmpliacion}"> Ampliado hasta </c:if> ${value.fechainicio}
                                            <c:if test="${value.fechainicio != value.fechafin}"><i
                                                    class="fa fa-calendar"></i> ${value.fechafin}</c:if>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
</body>
</html>
