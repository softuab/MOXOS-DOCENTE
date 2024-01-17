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
    <link href="<c:url value="/static/css/bootstrap-duallistbox.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<main id="contenedor" class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p> ${parametros.materia} </p>
            <p><c:out value="${parametros.programa}"/></p>
        </div>
        <ul class="app-breadcrumb breadcrumb">
            <li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
            <li class="breadcrumb-item">${datosAsignacion.fase}</li>
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
                <div id="contenido" class="row">
                    <div class="col">
                        <form id="demoform">
                            <select multiple="multiple" size="30" id="listEstudiantes"
                                    title="duallistbox_demo1[]">
                                <c:set var="contadorA" value="1"/>
                                <c:forEach var="datos" items="${listaNotas.pageList}" varStatus="contador">
                                    <option value='${datos.ru}-${datos.nombres}-${datos.usuario}-${datos.id_persona}-${datos.registrado?1:0}-${datos.registradomoodle?1:0}'>${datos.nombres_completos}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" id="periodo" value="<c:out value="${parametros.periodo}"/>">
                            <input type="hidden" id="gestion" value="<c:out value="${parametros.gestion}"/>">
                            <input type="hidden" id="id_programa" value="<c:out value="${parametros.id_programa}"/>">
                            <input type="hidden" id="materia" value="<c:out value="${parametros.materia}"/>">
                            <input type="hidden" id="idcurso" value="<c:out value="${idcurso}"/>">
                            <input type="hidden" id="idcursomoodle" value="<c:out value="${idcursomoodle}"/>">
                            <input type="hidden" id="id_grupo" value="<c:out value="${parametros.id_grupo}"/>">
                            <input type="hidden" id="id_materia" value="<c:out value="${parametros.id_materia}"/>">
                            <button id="enviarregistro" onclick="enviar(this)"
                                    data-loading="<i class='fa fa-spinner fa-spin'></i> Matriculando" type="button"
                                    class="btn btn-primary btn-block">Matricular
                            </button>
                        </form>
                        <form id="idlistaestudiantes"
                              action="<c:url value='/listarEstudiantesMatriculados'/>" method='post'>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="fase" value="<c:out value="${datosAsignacion.fase}"/>">
                            <input type="hidden" name="nombres" value="<c:out value="${nombres}"/>">
                            <input type="hidden" name="programa" value="<c:out value="${parametros.programa}"/>">
                            <input type="hidden" name="periodo" value="<c:out value="${parametros.periodo}"/>">
                            <input type="hidden" name="gestion" value="<c:out value="${parametros.gestion}"/>">
                            <input type="hidden" name="id_programa" value="<c:out value="${parametros.id_programa}"/>">
                            <input type="hidden" name="materia" value="<c:out value="${parametros.materia}"/>">
                            <input type="hidden" name="idcurso" value="<c:out value="${idcurso}"/>">
                            <input type="hidden" name="idcursomoodle" value="<c:out value="${idcursomoodle}"/>">
                            <input type="hidden" name="id_grupo" value="<c:out value="${parametros.id_grupo}"/>">
                            <input type="hidden" name="id_materia" value="<c:out value="${parametros.id_materia}"/>">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="loader"></div>
<div id="errortoast"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script src="<c:url value="/static/js/duallistbox.js" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script>
    let toast = new ToastModal(document.getElementById('errortoast'), {});
    let formretorno = new Form(document.getElementById('retornar'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Enviando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });
    let listBox = new ListBox(document.getElementById('listEstudiantes'), {
        nonSelectedListLabel: 'No Matriculados',
        selectedListLabel: 'Matriculados',
        preserveSelectionOnMove: 'moved',
        moveOnSelect: false
    });
    let enviar = (input) => {
        loadercontent.show();
        let value = getSelectedOptions(document.getElementById('listEstudiantes'));
        let valores = value.toString().split(",");
        let jsonArray = [];
        for (i = 0; i < valores.length; i++) {
            let json = valores[i] === '' ? null : valores[i].split("-");
            if (json !== null) {
                let nombres = json[1].split('!');
                let data = {
                    username: json[0],
                    firstname: nombres[0],
                    lastname: nombres[1],
                    email: json[2],
                    idnumber: json[3],
                    registrado_academico: json[4] === '1' ? true : false,
                    registrado_moodle: json[5] === '1' ? true : false,
                    id: 0
                };
                jsonArray.push(data);
            }
        }
        let formdata = {
            seleccionados: jsonArray,
            idcursomoodle: document.getElementById('idcursomoodle').value,
            idcurso: document.getElementById('idcurso').value,
            id_programa: document.getElementById('id_programa').value,
            materia: document.getElementById('materia').value,
            gestion: document.getElementById('gestion').value,
            periodo: document.getElementById('periodo').value,
            id_grupo: document.getElementById('id_grupo').value,
            id_materia: document.getElementById('id_materia').value
        };
        Post('<c:url value="/matricularEstudianteMateria" />', formdata)
            .then(function (data) {
                if (data.status == "OK") {
                    document.getElementById('idlistaestudiantes').submit();
                } else {
                    loadercontent.hide();
                    toast.show({
                        classNameToast: 'danger',
                        textBody: data.mensaje,
                        titleText: "Aviso",
                        subtitleText: ""
                    });
                }
            })
            .catch(function (error) {
            });
    }

    function getSelectedOptions(sel) {
        let opts = [], opt;
        let len = sel.options.length;
        for (let i = 0; i < len; i++) {
            opt = sel.options[i];
            if (opt.selected) {
                opts.push(opt.value);
            }
        }
        return opts;
    }
</script>
</body>
</html>

