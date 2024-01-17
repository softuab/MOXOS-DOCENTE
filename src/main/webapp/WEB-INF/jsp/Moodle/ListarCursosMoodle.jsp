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
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<c:if test="${!empty id_rol}">
    <main id="contenedor" class="app-content3">
        <div class="app-title">
            <div>
                <h1><i class="fa fa-address-card"></i> ${nombres}</h1>
                <p>${parametro.sigla} - ${parametro.materia} GRUPO ${parametro.grupo} </p>
                <p><c:out value="${parametro.programa}"/></p>
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
            <ul class="app-breadcrumb breadcrumb">
                <li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
                <li class="breadcrumb-item">${parametro.fase}</li>
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
                                <input type="hidden" name="gestion" value="${parametro.gestion}">
                                <input type="hidden" name="periodo" value="${parametro.periodo}">
                                <button class="btn btn-primary btn-lg btn-block" type="submit"><i
                                        class="fa fa-home fa-lg"></i> Retornar
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <c:if test="${mensajeerror != null}">
                                <form id="forma" action="<c:url value="/crearCurso"/>" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type=hidden name="id_materia"
                                           value="<c:out value="${parametro.id_materia}"/>">
                                    <input type=hidden name="id_grupo" value="<c:out value="${parametro.id_grupo}"/>">
                                    <input type=hidden name="id_programa"
                                           value="<c:out value="${parametro.id_programa}"/>">
                                    <input type=hidden name="gestion" value="<c:out value="${parametro.gestion}"/>">
                                    <input type=hidden name="periodo" value="<c:out value="${parametro.periodo}"/>">
                                    <input type=hidden name="id_docente"
                                           value="<c:out value="${parametro.id_docente}"/>">
                                    <input type=hidden name="apellidos"
                                           value="<c:out value="${persona.paterno} ${persona.materno}"/>">
                                    <input type=hidden name="nombres" value="<c:out value="${persona.nombres}"/>">
                                    <input type=hidden name="id_persona" value="<c:out value="${persona.id_persona}"/>">
                                    <input type=hidden name="correo" value="<c:out value="${persona.correo}"/>">
                                    <input type=hidden name="programa" value="<c:out value="${parametro.programa}"/>">
                                    <input type=hidden name="materia" value="<c:out value="${parametro.materia}"/>">
                                    <input type=hidden name="sigla" value="<c:out value="${parametro.sigla}"/>">
                                    <input type=hidden name="grupo" value="<c:out value="${parametro.grupo}"/>">
                                    <input type=hidden name="fase" value="<c:out value="${parametro.fase}"/>">
                                    <button type="submit" class="btn btn-primary" id="btncrear"><i
                                            class="fa fa-plus"></i> Crear curso moodle
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${mensajeerror != null}">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-6 mx-auto">
                                    <div class="card border-secondary mb-3">
                                        <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                                            !!
                                        </div>
                                        <div class="card-body text-secondary">
                                            No existe cursos creados para mostrar
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${mensajeerror == null}">
                        <span class="d-block p-2 bg-primary text-white"><i class="fa fa-address-card"></i>&nbsp;  Matricular Docente</span>
                        <div id="contenido" class="row">
                            <div class="col">
                                <table class="table table-borderless">
                                    <thead class="cf">
                                    <tr>
                                        <th>NOMBRE DEL CURSO</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="asignacion" items="${cursosmoodle}" varStatus="contador">
                                        <tr>
                                            <td data-title="NOMBRE DEL CURSO">
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
                                                <c:if test="${asignacion.matricular}">
                                                    <button type="button" class="btn btn-primary"
                                                            data-username="${usermoodle.moodle_username}"
                                                            data-idnumber="${persona.id_persona}"
                                                            data-idcurso="${asignacion.id_moodle_cursos}"
                                                            data-idcursomoodle="${asignacion.id_cursos_moodle}"
                                                            id="load${contador.index}"
                                                            onclick="matricularmaestro(this);"
                                                            data-loading="<i class='fa fa-spinner fa-spin'></i> Matriculando">
                                                        Matricular
                                                    </button>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>

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
    <div id="errortoast"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/app/main/moodle/changedpassword.js" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    <c:if test="${mensajeerror != null}">
    let form = new Form(document.getElementById('forma'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
    </c:if>
    let toast = new ToastModal(document.getElementById('errortoast'), {});
    let formretorno = new Form(document.getElementById('retornar'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });

    function matricularmaestro(btn) {
        btn.disabled = true;
        btn.innerHTML = btn.dataset.loading;
        let data = {
            username: btn.dataset.username,
            idnumber: btn.dataset.idnumber,
            idcurso: btn.dataset.idcurso,
            idcursomoodle: btn.dataset.idcursomoodle
        };
        Post('<c:url value="/matricularCursoDocente" />', data)
            .then(function (data) {
                if (data.status === "OK") {
                    btn.className = "btn btn-success";
                    btn.innerHTML = 'Matriculado con exito';
                } else {
                    toast.show({
                        classNameToast: 'danger',
                        textBody: data.message,
                        titleText: "Aviso",
                        subtitleText: ""
                    });
                    btn.className = "btn btn-danger";
                    btn.innerHTML = 'Matriculado sin exito';
                }
            })
            .catch(function (error) {
                toast.show({
                    classNameToast: 'danger',
                    textBody: "Problemas con el servidor",
                    titleText: "Aviso",
                    subtitleText: ""
                });
                btn.className = "btn btn-primary";
                btn.innerHTML = 'Reintentar matricular';
                btn.disabled = false;
            });
    }
</script>
</body>
</html>
