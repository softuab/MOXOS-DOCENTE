<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>Detalle de Programa Analitico ${model.periodo}/${model.gestion} </p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="row mb-3">
                    <div>
                        <table class="col-md-12 table-bordered table-condensed cf">
                            <thead class="thead-dark cf text-center">
                            <tr class="table-info">
                                <th colspan="2">DATOS DOCENTE</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="width: 40%">FACULTAD:</td>
                                <td style="width: 60%"><c:out value="${programaAnalitico.facultad}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">CARRERA:</td>
                                <td style="width: 60%"><c:out value="${programaAnalitico.departamento}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">ASIGNATURA:</td>
                                <td style="width: 60%"><c:out value="${programaAnalitico.sigla}"/> - <c:out
                                        value="${programaAnalitico.materia}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">DOCENTE:</td>
                                <td style="width: 60%"><c:out value="${programaAnalitico.nombres}"/> <c:out
                                        value="${programaAnalitico.paterno}"/> <c:out
                                        value="${programaAnalitico.materno}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">CELULAR:</td>
                                <td style="width: 60%"><c:out value="${programaAnaliticoDatosMateria.celular}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">CORREO:</td>
                                <td style="width: 60%"><c:out value="${programaAnaliticoDatosMateria.correo}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">GESTION ACADEMICA:</td>
                                <td style="width: 60%">
                                    <c:out value="${model.periodo}"/> / <c:out value="${model.gestion}"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                        <table class="col-md-12 table-bordered table-condensed cf">
                            <thead class="thead-dark cf text-center">
                            <tr class="table-info">
                                <th colspan="2">DATOS MATERIA</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="width: 40%">AREA</td>
                                <td style="width: 60%"><c:out value="${programaAnaliticoDatosMateria.area}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">SIGLA ASIGNATURA</td>
                                <td style="width: 60%"><c:out value="${programaAnaliticoDatosMateria.sigla}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">NIVEL (AÑO O SEMESTRE)</td>
                                <td style="width: 60%"><c:out value="${nivel_academico}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">CICLO</td>
                                <td style="width: 60%"><c:out
                                        value="${programaAnaliticoDatosMateria.tipo_materia}"/></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">PRE-REQUISITO</td>
                                <td style="width: 60%">${htmlrequisito}</td>
                            </tr>
                            <tr>
                                <td style="width: 40%">HORAS TEORICAS/SEMANA</td>
                                <td style="width: 60%"><c:out value="${programaAnaliticoDatosMateria.hrs_teoricas}"/>
                                    Hrs.
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 40%">HORAS PRACTICAS/SEMANA</td>
                                <td style="width: 60%"><c:out value="${programaAnaliticoDatosMateria.hrs_practicas}"/>
                                    Hrs.
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 40%">TOTAL HORAS ACADEMICAS/SEMANA</td>
                                <td style="width: 60%"><c:out
                                        value="${programaAnaliticoDatosMateria.hrs_teoricas + programaAnaliticoDatosMateria.hrs_practicas}"/>
                                    Hrs.
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 40%">TOTAL HORAS TRABAJADAS/SEMANA</td>
                                <td style="width: 60%"><c:out
                                        value="${(programaAnaliticoDatosMateria.hrs_teoricas + programaAnaliticoDatosMateria.hrs_practicas)*4 }"/>
                                    Hrs.
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 40%">TOTAL HORAS TRABAJADAS/SEMESTRE</td>
                                <td style="width: 60%"></td>
                            </tr>
                            <tr>
                                <td style="width: 40%">TOTAL CREDITOS</td>
                                <td style="width: 60%"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <form method='post' action="<c:url value='RegistrarProgramaAnalitico'/>">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type=hidden name="id_docente" value="<c:out value="${model.id_docente}"/>">
                            <input type=hidden name="id_persona" value="<c:out value="${id_persona}"/>">
                            <input type=hidden name="id_asignacion" value="<c:out value="${model.id_asignacion}"/>">
                            <input type=hidden name="id_plan" value="<c:out value="${model.id_plan}"/>">
                            <input type=hidden name="id_grupo" value="<c:out value="${model.id_grupo}"/>">
                            <input type=hidden name="id_materia" value="<c:out value="${model.id_materia}"/>">
                            <input type=hidden name="gestion" value="<c:out value="${model.gestion}"/>">
                            <input type=hidden name="periodo" value="<c:out value="${model.periodo}"/>">
                            <input type=hidden name="nombres" value="<c:out value="${nombres}"/>">
                            <input type=hidden name="materia" value="<c:out value="${model.materia}"/>">
                            <input type=hidden name="grupo" value="<c:out value="${model.grupo}"/>">
                            <div class="mb-3">
                                <label for="exampleFormControlInput1">Detalle grado academico(M. Sc. Ing.)</label>
                                <input type="text" class="form-control" name="nro_resolucion" placeholder="M. Sc. Ing.">
                            </div>
                            <div class="mb-3">
                                <label for="exampleFormControlTextarea1">Perfil Académico y Laboral.</label>
                                <textarea class="form-control" name="observacion" rows="3"></textarea>
                            </div>
                            <div class="mb-3 text-right">
                                <button type="submit" class="btn btn-primary">Elaborar Programa Analitico</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="loader"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="static/js/loader.js?v=3" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), { textAction: 'Cargando...', UrlImage: '<c:url value="/static/image/logominiatura.png" />' });
    document.addEventListener("DOMContentLoaded", function () {
        loadercontent.show();
        setTimeout(function () {
            loadercontent.hide();
        }, 2700);
    });
</script>
</body>
</html>
