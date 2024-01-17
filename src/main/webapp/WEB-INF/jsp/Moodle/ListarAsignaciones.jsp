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
<c:if test="${!empty id_rol}">
    <main class="app-content3">
        <div class="app-title">
            <div>
                <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
                <p>Lista de designaciones de materias por el periodo ${periodo}/ ${gestion} para creacion de cursos
                    virtuales</p>
                <p><strong>Usuario:</strong> &nbsp;&nbsp;${usermoodle.moodle_username}<br/>
                    <strong>Contraseña:</strong>&nbsp;&nbsp;<span id="pass1">********************</span>
                    <button id="pass641" data-pass="${usermoodle.moodle_passwordbase64}" data-encode="false"
                            class="btn btn-light btn-sm text-dark" onClick="mostrarpassword(1, this)"><i id="icon1"
                                                                                                         class="fa fa-eye"></i>
                    </button>
                    <button class="btn btn-primary btn-sm" id="formaActualizar1"
                            data-persona="<c:out value="${usermoodle.id_persona_moxos}"/>" data-indice="1"
                            onclick="modificarpassword(this)">Cambiar contraseña
                    </button>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <c:if test="${ empty datosAsignacion}">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-6 mx-auto">
                                <div class="card border-secondary mb-3">
                                    <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                                        !!
                                    </div>
                                    <div class="card-body text-secondary">
                                        <p class="card-text">
                                            No existen materias asignadas para la gesti&oacute;n <c:out
                                                value="${gestion}"/>, periodo <c:out value="${periodo}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${ !empty datosAsignacion}">
                    <div class="tile">
                        <span class="d-block p-2 bg-primary text-white"><i class="fa fa-address-card"></i>&nbsp;Administrar Libretas</span>
                        <div class="responsive">
                            <table class="col-md-12 table table-sm" >
                                <thead class=" text-center">
                                <tr>
                                    <th scope="col">EVALUACION</th>
                                    <th scope="col">CARRERA/PROGRAMA</th>
                                    <th scope="col">GRUPO</th>
                                    <th scope="col">SIGLA</th>
                                    <th scope="col">MATERIA</th>
                                    <th scope="col">PLAN</th>
                                    <th scope="col">ADMINISTRACION</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="asignacion" items="${datosAsignacion}" varStatus="contador">
                                    <c:if test="${asignacion.id_tipo_evaluacion == 1}">
                                        <tr>
                                            <td data-label="EVALUACION"><c:out
                                                    value="${asignacion.tipo_evaluacion}"/></td>
                                            <td data-label="CARRERA/PROGRAMA"><c:out
                                                    value="${asignacion.programa}"/></td>
                                            <td data-label="GRUPO"><c:out value="${asignacion.grupo}"/></td>
                                            <td data-label="SIGLA"><c:out value="${asignacion.sigla}"/></td>
                                            <td data-label="MATERIA"><c:out value="${asignacion.materia}"/></td>
                                            <td data-label="PLAN"><c:out value="${asignacion.id_plan}"/></td>
                                            <td data-label="ACCION">
                                                <div class="dropdown">
                                                    <button class="btn btn-info dropdown-toggle" type="button"
                                                            id="dropdownMenuButton1" data-bs-toggle="dropdown"
                                                            aria-expanded="false">Administracion
                                                    </button>
                                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                        <li>
                                                            <a class="dropdown-item"
                                                               href='javascript:enviarsolicitud(document.formalistamoodle${contador.count});'>Crear
                                                                Aula virtual</a>
                                                        </li>
                                                        <div class="dropdown-divider"></div>
                                                        <li>
                                                            <a class="dropdown-item"
                                                               href='javascript:enviarsolicitud(document.formaLibretas<c:out value="${contador.count}"/>);'>Matricular
                                                                Estudiante</a>
                                                        </li>
                                                        <div class="dropdown-divider"></div>
                                                        <li>
                                                            <a class="dropdown-item"
                                                               href='javascript:enviarsolicitud(document.formamatricula<c:out value="${contador.count}"/>);'>Matriculados</a>
                                                        </li>

                                                        <form name='formalistamoodle${contador.count}' method='post'
                                                              action="<c:url value='/ListarCursosMoodle'/>">
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                            <input type=hidden name="id_asignacion"
                                                                   value="<c:out value="${asignacion.id_asignacion}"/>">
                                                            <input type=hidden name="id_materia"
                                                                   value="<c:out value="${asignacion.id_materia}"/>">
                                                            <input type=hidden name="materia"
                                                                   value="<c:out value="${asignacion.materia}"/>">
                                                            <input type=hidden name="id_grupo"
                                                                   value="<c:out value="${asignacion.id_grupo}"/>">
                                                            <input type=hidden name="grupo"
                                                                   value="<c:out value="${asignacion.grupo}"/>">
                                                            <input type=hidden name="id_programa"
                                                                   value="<c:out value="${asignacion.id_programa}"/>">
                                                            <input type=hidden name="id_fase"
                                                                   value="<c:out value="${asignacion.id_fase}"/>">
                                                            <input type=hidden name="id_tipo_evaluacion"
                                                                   value="<c:out value="${asignacion.id_tipo_evaluacion}"/>">
                                                            <input type=hidden name="tipo_evaluacion"
                                                                   value="<c:out value="${asignacion.tipo_evaluacion}"/>">
                                                            <input type=hidden name="gestion"
                                                                   value="<c:out value="${gestion}"/>">
                                                            <input type=hidden name="periodo"
                                                                   value="<c:out value="${periodo}"/>">
                                                            <input type=hidden name="id_docente"
                                                                   value="<c:out value="${asignacion.id_docente}"/>">
                                                            <input type=hidden name="programa"
                                                                   value="<c:out value="${asignacion.programa}"/>">
                                                            <input type=hidden name="sigla"
                                                                   value="<c:out value="${asignacion.sigla}"/>">
                                                            <input type=hidden name="fase"
                                                                   value="<c:out value="${asignacion.fase}"/>">
                                                            <input type=hidden name="id_tipo_grado"
                                                                   value="<c:out value="${asignacion.id_tipo_grado}"/>">
                                                        </form>
                                                        <form name='formaLibretas<c:out value="${contador.count}"/>'
                                                              method='post'
                                                              action='<c:url value="/administrarCursosListarAlumnosProgramados"/>'>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                            <input type=hidden name="id_asignacion"
                                                                   value="<c:out value="${asignacion.id_asignacion}"/>">
                                                            <input type=hidden name="id_materia"
                                                                   value="<c:out value="${asignacion.id_materia}"/>">
                                                            <input type=hidden name="materia"
                                                                   value="<c:out value="${asignacion.materia}"/>">
                                                            <input type=hidden name="id_grupo"
                                                                   value="<c:out value="${asignacion.id_grupo}"/>">
                                                            <input type=hidden name="grupo"
                                                                   value="<c:out value="${asignacion.grupo}"/>">
                                                            <input type=hidden name="id_programa"
                                                                   value="<c:out value="${asignacion.id_programa}"/>">
                                                            <input type=hidden name="id_fase"
                                                                   value="<c:out value="${asignacion.id_fase}"/>">
                                                            <input type=hidden name="id_tipo_evaluacion"
                                                                   value="<c:out value="${asignacion.id_tipo_evaluacion}"/>">
                                                            <input type=hidden name="tipo_evaluacion"
                                                                   value="<c:out value="${asignacion.tipo_evaluacion}"/>">
                                                            <input type=hidden name="gestion"
                                                                   value="<c:out value="${gestion}"/>">
                                                            <input type=hidden name="periodo"
                                                                   value="<c:out value="${periodo}"/>">
                                                            <input type=hidden name="id_docente"
                                                                   value="<c:out value="${asignacion.id_docente}"/>">
                                                            <input type=hidden name="id_modelo_ahorro"
                                                                   value="<c:out value="${asignacion.id_modelo_ahorro}"/>">
                                                            <input type=hidden name="id_departamento"
                                                                   value="<c:out value="${asignacion.id_departamento}"/>">
                                                            <input type=hidden name="id_tipo_grado"
                                                                   value="<c:out value="${asignacion.id_tipo_grado}"/>">
                                                            <input type=hidden name="avanzado"
                                                                   value="<c:out value="${avanzado}"/>">
                                                        </form>
                                                        <form name='formamatricula<c:out value="${contador.count}"/>'
                                                              method='post'
                                                              action='<c:url value="/listarEstudiantesMatriculados"/>'>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                            <input type="hidden" name="fase"
                                                                   value="<c:out value="${asignacion.fase}"/>">
                                                            <input type="hidden" name="nombres"
                                                                   value="<c:out value="${nombres}"/>">
                                                            <input type="hidden" name="programa"
                                                                   value="<c:out value="${asignacion.programa}"/>">
                                                            <input type="hidden" name="periodo"
                                                                   value="<c:out value="${periodo}"/>">
                                                            <input type="hidden" name="gestion"
                                                                   value="<c:out value="${gestion}"/>">
                                                            <input type="hidden" name="id_programa"
                                                                   value="<c:out value="${asignacion.id_programa}"/>">
                                                            <input type="hidden" name="id_grupo"
                                                                   value="<c:out value="${asignacion.id_grupo}"/>">
                                                            <input type="hidden" name="id_materia"
                                                                   value="<c:out value="${asignacion.id_materia}"/>">
                                                        </form>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div id="loader"></div>
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
    </main>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=6" />"></script>
<script src="<c:url value="static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/app/main/moodle/changedpassword.js" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Enviando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });
    function enviarsolicitud(form) {
        loadercontent.show();
        form.submit();
    }
</script>
</body>
</html>