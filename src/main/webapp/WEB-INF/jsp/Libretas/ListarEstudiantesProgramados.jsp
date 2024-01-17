<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
                <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
                <p>${parametro.sigla} - ${parametro.materia} GRUPO ${parametro.grupo} </p>
                <p><c:out value="${parametro.programa}"/></p>
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
                        <div class="col">
                            <form id="retornar" method="post"
                                  action="<c:url value="/libretas/retornorListarAsignaciones" />">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="gestion" value="${parametro.gestion}">
                                <input type="hidden" name="periodo" value="${parametro.periodo}">
                                <button class="btn btn-primary btn-sm" style="float: right" type="submit"><i
                                        class="fa fa-home fa-lg"></i> Retornar
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <span class="d-block p-2 bg-primary text-white"><i class="fa fa-edit"></i> &nbsp; Administrar Libretas :&nbsp;<c:out
                                    value="${parametro.tipo_evaluacion}"/></span>
                            <div class="row">
                                <div class="col">
                                    <form id="forma1" name="forma1" method="post">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <label class="input-group-text" for="id_tipo_nota_s">Tipo
                                                    evaluacion</label>
                                            </div>
                                            <select class="form-select" name="id_tipo_nota_s" id="id_tipo_nota_s">
                                                <option value=""> Seleccionar Tipo evaluacion</option>
                                                <c:forEach var="items" items="${listaItems}">
                                                    <c:choose>
                                                        <c:when test="${items.id_tipo_nota == id_tipo_nota}">
                                                            <option selected
                                                                    value="<c:out value="${items.id_lbr_tipo_nota}"/>:<c:out value="${items.cantidad}"/>">
                                                                <c:out value="${items.cantidad}"/> - <c:out
                                                                    value="${items.tipo_nota}"/> <c:out
                                                                    value="${items.ponderacion}"/>%
                                                            </option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="<c:out value="${items.id_lbr_tipo_nota}"/>:<c:out value="${items.cantidad}"/>">
                                                                <c:out value="${items.cantidad}"/> - <c:out
                                                                    value="${items.tipo_nota}"/> <c:out
                                                                    value="${items.ponderacion}"/>%
                                                            </option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <input type="hidden" name="id_asignacion"
                                               value="<c:out value="${parametro.id_asignacion}"/>">
                                        <input type="hidden" name="id_programa"
                                               value="<c:out value="${parametro.id_programa}"/>">
                                        <input type="hidden" name="id_tipo_grado"
                                               value="<c:out value="${parametro.id_tipo_grado}"/>">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="id_materia"
                                               value="<c:out value="${parametro.id_materia}"/>">
                                        <input type="hidden" name="grupo"
                                               value="<c:out value="${parametro.grupo}"/>">
                                        <input type="hidden" name="programa"
                                               value="<c:out value="${parametro.programa}"/>">
                                        <input type="hidden" name="sigla"
                                               value="<c:out value="${parametro.sigla}"/>">
                                        <input type="hidden" name="fase"
                                               value="<c:out value="${parametro.fase}"/>">
                                        <input type="hidden" name="id_grupo"
                                               value="<c:out value="${parametro.id_grupo}"/>">
                                        <input type="hidden" name="id_departamento"
                                               value="<c:out value="${parametro.id_departamento}"/>">
                                        <input type="hidden" name="id_fase"
                                               value="<c:out value="${parametro.id_fase}"/>">
                                        <input type="hidden" name="id_tipo_evaluacion"
                                               value="<c:out value="${parametro.id_tipo_evaluacion}"/>">
                                        <input type="hidden" name="tipo_evaluacion"
                                               value="<c:out value="${parametro.tipo_evaluacion}"/>">
                                        <input type="hidden" name="id_modelo_ahorro"
                                               value="<c:out value="${parametro.id_modelo_ahorro}"/>">
                                        <input type="hidden" name="materia"
                                               value="<c:out value="${parametro.materia}"/>">
                                        <input type="hidden" name="gestion"
                                               value="<c:out value="${parametro.gestion}"/>">
                                        <input type="hidden" name="periodo"
                                               value="<c:out value="${parametro.periodo}"/>">
                                        <input type="hidden" id="ubicacion" name="ubicacion"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div id="alert" class="alert alert-danger invisible" role="alert"></div>
                        </div>
                    </div>
                    <c:if test="${id_tipo_nota != null && mensajeerror == null}">
                        <div class="row">
                            <div class="col text-center">
                                <h3>Estudiantes matriculados ${parametro.periodo}/${parametro.gestion}</h3>
                            </div>
                        </div>
                        <div id="contenido" class="row">
                            <div class="col">
                                <c:if test="${mensajeerror != null}">
                                    <div class="Card-Informacion">
                                        <div class="card border-secondary mb-3" style="max-width: 18rem;">
                                            <div class="card-header"><i class="fas fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                                                !!
                                            </div>
                                            <div class="card-body text-secondary">
                                                <p class="card-text">
                                                    <c:out value="${mensajeerror}"/><br/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="mx-auto" style="overflow-x:auto;">
                                    <table class="tableX table table-sm table-striped table-bordered table-hover">
                                        <thead class="text-center">
                                        <tr>
                                            <th rowspan="2">No.</th>
                                            <th rowspan="2">R.U.</th>
                                            <th rowspan="2">NOMBRE COMPLETO</th>
                                            <th rowspan="2">AULA<BR/>DESCONCENTRADA</th>
                                            <c:forEach var="cantNotas" items="${numItems}" varStatus="contador">
                                                <th>NOTA <c:out value="${contador.count}"/></th>
                                            </c:forEach>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- LISTA DE ESTUDIANTES, EVALUACION REGULAR  -->
                                        <c:if test="${parametro.id_tipo_grado == 1 && parametro.id_tipo_evaluacion != '2'}">
                                            <c:set var="contadorA" value="1"/>
                                            <c:forEach var="datos" items="${listaNotas.pageList}" varStatus="contador">
                                                <c:if test="${!empty datos.notas}">
                                                    <tr>
                                                        <td><c:out value="${contadorA}"/></td>
                                                        <td><c:out value="${datos.id_estudiante}"/></td>
                                                        <td><c:out value="${datos.nombres}"/></td>
                                                        <td><c:out value="${datos.sede_desconcentrada}"/></td>
                                                        <c:set var="contadorB" value="1"/>
                                                        <c:forEach var="notas" items="${datos.notas}">
                                                            <c:if test="${notas.nro_nota != nro_nota}">
                                                                <td data-nronota="${contadorB}"
                                                                    data-estado="${notas.permitidomodificar}"
                                                                    data-nota="${notas.nota}"
                                                                    data-status=""
                                                                    data-idestudiante="${datos.id_estudiante}"><c:out
                                                                        value="${notas.nota}"/></td>
                                                                <c:set var="contadorB" value="${contadorB+1}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${!empty datos.notas}">
                                                    <c:set var="contadorA" value="${contadorA+1}"/>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                <input type="hidden" name="id_tipo_nota" id="id_tipo_nota"
                                       value="<c:out value="${parametro.id_tipo_nota_s}"/>">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <form action="<c:url value="/libretas/exportar-excel" />" method="get">
                                    <input type="hidden" name="id_programa"
                                           value="<c:out value="${parametro.id_programa}"/>">
                                    <input type="hidden" name="id_tipo_evaluacion"
                                           value="<c:out value="${parametro.id_tipo_evaluacion}"/>">
                                    <input type="hidden" name="id_grupo" value="<c:out value="${parametro.id_grupo}"/>">
                                    <input type="hidden" name="id_materia"
                                           value="<c:out value="${parametro.id_materia}"/>">
                                    <input type="hidden" name="gestion" value="<c:out value="${parametro.gestion}"/>">
                                    <input type="hidden" name="periodo" value="<c:out value="${parametro.periodo}"/>">
                                    <input type="hidden" name="id_tipo_nota_s"
                                           value="<c:out value="${parametro.id_tipo_nota_s}"/>">
                                    <button class="btn btn-primary btn-sm" style="float: right" type="submit"><i
                                            class="fa fa-file-excel-o" aria-hidden="true"></i> Descargar planilla
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </main>
    <div id="errortoast"></div>
    <div id="errortoasttable"></div>
    <div id="loader"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/tableX.js" />"></script>
<script>
    <c:if test="${id_tipo_nota != null && mensajeerror == null}">
    let table = tableX({
        index_from_left: 3, index_from_right: -1, index_from_top: -1, index_from_bottom: -1
    });
    </c:if>
    let formretorno = new Form(document.getElementById('retornar'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Enviando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });
</script>
<script src="<c:url value="/static/js/app/main/libretas/listarestudiantes.js?v=1" />"></script>
</body>
</html>
