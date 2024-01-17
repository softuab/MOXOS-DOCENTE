<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<body class="app sidebar-mini rtl">
<main id="contenedor" class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>${parametros.materia} </p>
            <p><c:out value="${parametros.programa}"/></p>
        </div>
        <ul class="app-breadcrumb breadcrumb">
            <li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
            <li class="breadcrumb-item">${parametros.fase}</li>
        </ul>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <div class="tile">
                <div class="row mb-3">
                    <div class="col-10"></div>
                    <div class="col-2">
                        <form id="retornar" method="post"
                              action="<c:url value="/regresarListarAsignacionesAdministrarCursos" />">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="gestion" value="${parametros.gestion}">
                            <input type="hidden" name="periodo" value="${parametros.periodo}">
                            <button class="btn btn-primary btn-lg btn-block" type="submit"><i
                                    class="fa fa-home fa-lg"></i> Retornar
                            </button>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <span class="d-block p-2 bg-primary text-white"><i class="fa fa-address-card"></i>   Lista de Estudiantes Matriculados</span>
                        <div class="responsive">
                            <table class="table table-borderless">
                                <thead class="cf">
                                <tr>
                                    <th scope="col">DETALLE</th>
                                    <th scope="col">USUARIO</th>
                                    <th scope="col">CONTRASEÑA</th>
                                    <th scope="col">ADMINISTRACION</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="asignacion" items="${cursosmoodle}" varStatus="contador">
                                    <tr>
                                        <td data-label="DETALLE">
                                            <b>Estudiante: </b><c:out
                                                value="${asignacion.moodle_nombres_usuario} ${asignacion.moodle_apellidos_usuario}"/><br>
                                            <b>Facultad: </b><c:out value="${asignacion.facultad}"/><br>
                                            <b>Carrera: </b><c:out value="${asignacion.programa}"/><br>
                                            <b>Materia: </b><c:out value="${asignacion.materias}"/><br>
                                            <b>Paralelo: </b><c:out value="${asignacion.grupo}"/><br>
                                            <b>Gestion: </b><c:out value="${asignacion.gestion}"/><br>
                                            <b>Periodo: </b> <c:out value="${asignacion.periodo}"/><br>
                                            <b>Detalle curso moodle: </b><a
                                                href="http://sistemas.uabjb.edu.bo/CursosPregrado"
                                                target="_blank"><c:out
                                                value="${asignacion.moodle_detallecurso}"/></a><br>
                                        </td>
                                        <td data-label="USUARIO">
                                            <c:if test="${asignacion.moodle_username==''}">
                                                Sin definir
                                            </c:if>
                                            <c:if test="${asignacion.moodle_username!=''}">
                                                <span id="user${contador.count}"><c:out
                                                        value="${asignacion.moodle_username}"/></span>
                                            </c:if>
                                        </td>
                                        <td data-label="CONTRASEÑA">
                                            <span id="pass${contador.count}"><c:out
                                                    value="${asignacion.moodle_password}"/></span>
                                        </td>
                                        <td data-label="ADMINISTRACION">
                                            <button class="btn btn-info" id="formaActualizar${contador.count}"
                                                    data-persona="<c:out value="${asignacion.id_persona_moxos}"/>"
                                                    data-indice="${contador.count}"
                                                    onclick="modificarpassword(this)">Cambiar Contraseña
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade zoom" id="idmodificarusuario" data-bs-backdrop="static" data-bs-keyboard="false"
             tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div id="content" class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btncancelar" class="waves-effect waves-red btn-flat"
                                data-bs-dismiss="modal">CANCELAR
                        </button>
                        <button type="button" class="waves-effect waves-red btn-flat" onclick="guardarResultados()">
                            ACEPTAR
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="loader"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/app/main/moodle/changedpasswordstudent.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let formretorno = new Form(document.getElementById('retornar'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
</script>
</body>
</html>
