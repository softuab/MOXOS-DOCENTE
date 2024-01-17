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
    <link href="<c:url value="/static/css/introjs.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/choices.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/contextmenu.css?"/>" rel="stylesheet">
    <style>
        .item {
            opacity: 1;
            transition: opacity 0.5s;
        }

        .eliminado {
            opacity: 0;
        }
    </style>
    <script type="text/javascript" src="<c:url value='/static/js/tinymce/tinymce.min.js?v=1'/>"></script>
    <script>
        tinymce.init({
            selector: '.textarea',
            height: 500,
            language: 'es_MX',
            fontsize_formats: '8pt 10pt 12pt 14pt 18pt 24pt 36pt',
            image_advtab: true,
            content_css: 'writer',
            plugins: [
                'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
                'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                'insertdatetime', 'media', 'table', 'help', 'wordcount'
            ],
            toolbar: 'fontfamily | fontsize | undo redo | blocks | ' +
                'bold italic backcolor | alignleft aligncenter ' +
                'alignright alignjustify | table tableinsertdialog tablecellprops tableprops advtablerownumbering | bullist numlist outdent indent | ' +
                'removeformat | help ',
            content_style: 'body{max-width:700px; padding:30px; margin:auto;font-size:16px;font-family:Arial,"Helvetica Neue",Helvetica,Arial,sans-serif; line-height:1.3; letter-spacing: -0.03em;color:#222} h1,h2,h3,h4,h5,h6 {font-weight:400;margin-top:1.2em} h1 {} h2{} .tiny-table {width:100%; border-collapse: collapse;} .tiny-table td, th {border: 1px solid #555D66; padding:10px; text-align:left;font-size:16px;font-family:Lato,"Helvetica Neue",Helvetica,Arial,sans-serif; line-height:1.6;} .tiny-table th {background-color:#E2E4E7}'
        });
    </script>
</head>
<body class="app sidebar-mini rtl">
<main id="main" class="app-content3 invisible">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <h5> PROGRAMA ANALITICO DE ${materia} GRUPO ${grupo} ${periodo}/${gestion} </h5>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <form name="formaguardar" method='post' action="<c:url value='EditarProgramaAnalitico'/>">
                    <div class="row mb-3">
                        <div class="col text-end">
                            <a class="btn btn-primary" onclick="iniciartutorial()" href="#"><i
                                    class="fa fa-question-circle"></i> Instructivo de llenado</a>
                            <input type=hidden id="id_dct_programa_analitico"
                                   value="<c:out value="${evaluarprograma.id_dct_programa_analitico}"/>">
                            <input type="hidden" id="gestion" value="${gestion}"/>
                            <input type="hidden" id="periodo" value="${periodo}"/>
                            <input type=hidden id="id_estado" value="<c:out value="${evaluarprograma.id_estado}"/>">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-2">
                            <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist"
                                 aria-orientation="vertical">
                                <a data-step="1"
                                   data-intro="<strong>MARCO REFERENCIAL</strong><br>Determinaciones existentes en el diseño curricular de la carrera que resultan significativas para el diseño del plan global de la asignatura."
                                   class="nav-link  <c:if test="${tab eq '1'}">active</c:if>" id="marco_referencial"
                                   data-bs-toggle="pill"
                                   href="#v-pills-marco_referencial" data-bs-target="#v-pills-marco_referencial"
                                   role="tab" aria-controls="v-pills-marco_referencial" aria-selected="true">1.-
                                    Marco Referencial</a>
                                <a data-step="2"
                                   data-intro="<strong>JUSTIFICACIÓN</strong><br>Debe contener una explicación clara y precisa del contenido teórico abordar y su vínculo con el perfil profesional. Así mismo debe valorarse lo apropiado de la ubicación de la signatura describiendo las relaciones con otras asignaturas y su nivel de profundidad. Se exige que la justificación esté relacionada con los objetivos de la asignatura y con el perfil profesional. Se debe dar respuesta a las siguientes preguntas ¿Qué problemas profesionales ayuda a resolver la asignatura? ¿Por qué resulta necesario su estudio?."
                                   class="nav-link" id="justificacion" data-bs-toggle="pill"
                                   href="#v-pills-justificacion" data-bs-target="#v-pills-justificacion"
                                   role="tab" aria-controls="v-pills-profile" aria-selected="false">2.-
                                    Justificacion</a>
                                <a data-step="3"
                                   data-intro="<strong>PROPÓSITOS</strong><br>Se expresará de manera concreta la intención que se persigue al impartir la asignatura."
                                   class="nav-link" id="propositos" data-bs-toggle="pill"
                                   href="#v-pills-propositos" role="tab" aria-controls="#v-pills-propositos"
                                   aria-selected="false">3.- Propósitos</a>
                                <a data-step="4"
                                   data-intro="<strong>OBJETIVO GENERAL DE LA ASIGNATURA</strong><br> Destaca la capacidad que se quiere lograr en los alumnos y los conocimientos esenciales en que se sustenta dicha capacidad."
                                   class="nav-link" id="objetivo_desarrollador" data-bs-toggle="pill"
                                   href="#v-pills-objetivo_desarrollador"
                                   data-bs-target="#v-pills-objetivo_desarrollador" role="tab"
                                   aria-controls="v-pills-objetivo_desarrollador" aria-selected="false">4.-
                                    Objetivo general</a>
                                <a data-step="5"
                                   data-intro="<strong>OBJETIVO EDUCATIVO</strong><br>Destaca la convicción a formar en el estudiante."
                                   class="nav-link" id="objetivo_educativo" data-bs-toggle="pill"
                                   href="#v-pills-objetivo_educativo"
                                   data-bs-target="#v-pills-objetivo_educativo" role="tab"
                                   aria-controls="v-pills-objetivo_educativo" aria-selected="false">5.- Objetivo
                                    educativo</a>
                                <a data-step="6"
                                   data-intro="<strong>CONTENIDOS</strong><br>Para cada tema o Unidad Temática. <br> Los contenidos de las asignaturas deben estar actualizados y reflejar los conocimientos científicos y conocimientos académicos. "
                                   class="nav-link <c:if test="${tab eq '2'}">active</c:if>" id="contenidos"
                                   data-bs-toggle="pill"
                                   href="#v-pills-contenidos" data-bs-target="#v-pills-contenidos" role="tab"
                                   aria-controls="v-pills-contenidos" aria-selected="false">6.- Contenidos</a>
                                <a data-step="7"
                                   data-intro="<strong>MODALIDAD Y METODOLOGÍA A UTILIZAR EN EL PEA</strong><br>Se exige que la metodología propuesta se adecue a las condiciones de los estudiantes y del contexto, se fundamente en concepciones pedagógicas modernas, el uso de las TICs y en particular permitir el desarrollo de habilidades y destrezas en los estudiantes."
                                   class="nav-link" id="metodos_estrategias" data-bs-toggle="pill"
                                   href="#v-pills-metodos_estrategias"
                                   data-bs-target="#v-pills-metodos_estrategias" role="tab"
                                   aria-controls="v-pills-metodos_estrategias" aria-selected="false">7.-
                                    Metodología</a>
                                <a data-step="8"
                                   data-intro=""
                                   class="nav-link <c:if test="${tab eq '3'}">active</c:if>" id="distribucion_de_tiempo"
                                   data-bs-toggle="pill"
                                   href="#v-pills-distribucion_de_tiempo"
                                   data-bs-target="#v-pills-distribucion_de_tiempo" role="tab"
                                   aria-controls="v-pills-distribucion_de_tiempo" aria-selected="false">8.-
                                    Formas de organización</a>
                                <a data-step="9"
                                   data-intro="<strong>RECURSOS EDUCATIVOS Y TECNOLÓGICOS</strong><br>Se debe proponer el uso de recursos didácticos adecuados a los contenidos de la asignatura.Especímenes, modelos, esquemas, láminas, CD, documentos compartidos, así como, materiales, instrumentos, insumos, maquinarias, computadoras, programas de software, internet y equipos que se requieren para el desarrollo de la asignatura. Son los medios de enseñanzas."
                                   class="nav-link" id="recursos" data-bs-toggle="pill" href="#v-pills-recursos"
                                   data-bs-target="#v-pills-recursos" role="tab"
                                   aria-controls="v-pills-recursos" aria-selected="false">9.- Recursos</a>
                                <a data-step="10"
                                   data-intro="<strong>CRITERIOS DE EVALUACIÓN (REQUISITOS MÍNIMOS DE APROBACIÓN </strong><br>Al menos, debe describirse cualitativamente los conocimientos, habilidades y valores indispensables para considerar a un alumno aprobado.<br> <strong>Nota:</strong> Obtener  51 puntos en la calificación final de la asignatura, no es un criterio de evaluación. Los criterios de evaluación se adecuan a las exigencias de los objetivos de la asignatura."
                                   class="nav-link" id="sistema_evaluacion" data-bs-toggle="pill"
                                   href="#v-pills-sistema_evaluacion"
                                   data-bs-target="#v-pills-sistema_evaluacion" role="tab"
                                   aria-controls="v-pills-sistema_evaluacion" aria-selected="false">10.-
                                    Componentes de Evaluacion</a>
                                <a data-step="11"
                                   data-intro="<strong>COMPONENTES DEL SISTEMA DE EVALUACIÓN </strong><br>La cantidad y tipo de evaluaciones a realizar (parcial, practica, prueba final, etc,), los productos terminales a presentar por los estudiantes en cada evaluación, el o los productos terminales a presentar de cada asignatura, los procedimientos de evaluación a utilizar y la ponderación de los diferentes tipos de exámenes o evaluación."
                                   class="nav-link" id="sistema_evaluacion_criterio" data-bs-toggle="pill"
                                   href="#v-pills-sistema_evaluacion_criterio"
                                   data-bs-target="#v-pills-sistema_evaluacion_criterio" role="tab"
                                   aria-controls="v-pills-sistema_evaluacion_criterio" aria-selected="false">11.-
                                    Criterios de Evaluacion</a>
                                <a data-step="12"
                                   data-intro="Ingresar las referencias bibliograficas de acuerdo a las normas APA"
                                   class="nav-link <c:if test="${tab eq '4'}">active</c:if>" id="bibliografia"
                                   data-bs-toggle="pill"
                                   href="#v-pills-bibliografia" data-bs-target="#v-pills-bibliografia"
                                   role="tab" aria-controls="v-pills-bibliografia" aria-selected="false">12.-
                                    Bibliografía</a>
                                <a data-step="13"
                                   data-intro="Ingresar el cronograma de actividades por clase en el semestro o año"
                                   class="nav-link <c:if test="${tab eq '5'}">active</c:if>" id="cronograma"
                                   data-bs-toggle="pill"
                                   href="#v-pills-cronograma" data-bs-target="#v-pills-cronograma" role="tab"
                                   aria-controls="v-pills-cronograma" aria-selected="false">13.- Cronograma de
                                    actividades</a>
                                <a data-step="14"
                                   data-intro="Mayor grado obtenido y descripcion del perfil academico laboral como por ejemplo magister en educacion superior y licenciado en ciencias de la educacion"
                                   class="nav-link" id="adicional" data-bs-toggle="pill"
                                   href="#v-pills-adicional" data-bs-target="#v-pills-adicional" role="tab"
                                   aria-controls="v-pills-adicional" aria-selected="false">14.- Datos
                                    adicionales</a>
                            </div>
                        </div>
                        <div class="col-10">
                            <div class="tab-content" id="v-pills-tabContent">
                                <div class="tab-pane fade <c:if test="${tab eq '1'}">show active</c:if>"
                                     id="v-pills-marco_referencial"
                                     role="tabpanel" aria-labelledby="marco_referencial">
                                    <div class="mb-3">
                                                <textarea class="form-control textarea" id="txtmarco_referencial"><c:out
                                                        value="${evaluarprograma.marco_referencial}"/></textarea>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="v-pills-justificacion" role="tabpanel"
                                     aria-labelledby="justificacion">
                                    <div class="mb-3">
                                                <textarea class="form-control textarea" id="txtjustificacion"><c:out
                                                        value="${evaluarprograma.justificacion}"/></textarea>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="v-pills-propositos" role="tabpanel"
                                     aria-labelledby="propositos">
                                            <textarea class="form-control textarea" id="txtpropositos"><c:out
                                                    value="${evaluarprograma.propositos}"/></textarea>
                                </div>
                                <div class="tab-pane fade" id="v-pills-objetivo_desarrollador" role="tabpanel"
                                     aria-labelledby="objetivo_desarrollador">
                                            <textarea class="form-control textarea"
                                                      id="txtobjetivo_desarrollador"><c:out
                                                    value="${evaluarprograma.objetivo_desarrollador}"/></textarea>
                                </div>
                                <div class="tab-pane fade" id="v-pills-objetivo_educativo" role="tabpanel"
                                     aria-labelledby="objetivo_educativo">
                                            <textarea class="form-control textarea" id="txtobjetivo_educativo"><c:out
                                                    value="${evaluarprograma.objetivo_educativo}"/></textarea>
                                </div>
                                <div class="tab-pane fade <c:if test="${tab eq '2'}">show active</c:if>"
                                     id="v-pills-contenidos" role="tabpanel"
                                     aria-labelledby="contenidos">
                                    <jsp:include
                                            page="${request.contextPath}/programaanaanalitico/listarContenidos">
                                        <jsp:param name="id_dct_programa_analitico"
                                                   value="${evaluarprograma.id_dct_programa_analitico}"/>
                                    </jsp:include>
                                </div>
                                <div class="tab-pane fade" id="v-pills-metodos_estrategias" role="tabpanel"
                                     aria-labelledby="metodos_estrategias">
                                    <textarea class="form-control textarea" id="txtmetodos_estrategias"><c:out
                                            value="${evaluarprograma.metodos_estrategias}"/></textarea>
                                </div>
                                <div class="tab-pane fade <c:if test="${tab eq '3'}">show active</c:if>"
                                     id="v-pills-distribucion_de_tiempo" role="tabpanel"
                                     aria-labelledby="distribucion_de_tiempo">
                                    <jsp:include
                                            page="${request.contextPath}/programaanaanalitico/formasorganizacion/listarDistribucionTiempos">
                                        <jsp:param name="id_dct_programa_analitico"
                                                   value="${evaluarprograma.id_dct_programa_analitico}"/>
                                    </jsp:include>
                                </div>
                                <div class="tab-pane fade" id="v-pills-recursos" role="tabpanel"
                                     aria-labelledby="recursos">
                                            <textarea class="form-control textarea" id="txtrecursos"
                                                      name="recursos"><c:out
                                                    value="${evaluarprograma.recursos}"/></textarea>
                                </div>
                                <div class="tab-pane fade" id="v-pills-sistema_evaluacion" role="tabpanel"
                                     aria-labelledby="sistema_evaluacion">
                                            <textarea class="form-control textarea" id="txtsistema_evaluacion"><c:out
                                                    value="${evaluarprograma.sistema_evaluacion}"/></textarea>
                                </div>
                                <div class="tab-pane fade" id="v-pills-sistema_evaluacion_criterio"
                                     role="tabpanel" aria-labelledby="sistema_evaluacion_criterio">
                                            <textarea class="form-control textarea"
                                                      id="txtsistema_evaluacion_criterios"><c:out
                                                    value="${evaluarprograma.sistema_evaluacion_criterios}"/></textarea>
                                </div>
                                <div class="tab-pane fade <c:if test="${tab eq '4'}">show active</c:if>"
                                     id="v-pills-bibliografia" role="tabpanel"
                                     aria-labelledby="v-bibliografia">
                                    <jsp:include
                                            page="${request.contextPath}/programaanaanalitico/listarreferenciasbibliograficas">
                                        <jsp:param name="id_dct_programa_analitico"
                                                   value="${evaluarprograma.id_dct_programa_analitico}"/>
                                    </jsp:include>
                                </div>
                                <div class="tab-pane fade <c:if test="${tab eq '5'}">show active</c:if>"
                                     id="v-pills-cronograma" role="tabpanel"
                                     aria-labelledby="cronograma">
                                    <jsp:include page="${request.contextPath}/programaanaanalitico/listarCronograma">
                                        <jsp:param name="id_dct_programa_analitico"
                                                   value="${evaluarprograma.id_dct_programa_analitico}"/>
                                    </jsp:include>
                                </div>
                                <div class="tab-pane fade" id="v-pills-adicional" role="tabpanel"
                                     aria-labelledby="adicional">
                                    <div class="mb-3">
                                        <label for="nro_resolucion">Detalle grado academico(M. Sc.
                                            Ing.)</label>
                                        <input type="text" class="form-control" id="nro_resolucion"
                                               placeholder="Detalle de la sigla del grado academico"
                                               value="<c:out value="${evaluarprograma.nro_resolucion}"/>">
                                    </div>
                                    <div class="mb-3">
                                        <label for="observacion">Perfil Académico y
                                            Laboral.</label>
                                        <textarea class="form-control textarea" id="observacion"
                                                  rows="3"><c:out
                                                value="${evaluarprograma.observacion}"/></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="row">
                    <div class="col">
                        <div class="text-end">
                            <form name="formretornar" method='get'
                                  action="<c:url value='/retornarListaAsignacionesProgramaAnalitico'/>">
                                <input type="hidden" name="periodo" value="${periodo}"/>
                                <input type="hidden" name="gestion" value="${gestion}"/>
                            </form>
                            <a class="btn btn-primary" href='javascript:guardarDocumento(document.formaguardar);'> <i
                                    class="fa fa-file-pdf"> &nbsp;</i>Guardar</a>
                            <a class="btn btn-danger" href='javascript:document.formretornar.submit();'> <i
                                    class="fa fa-print"></i> Cancelar</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade zoom" id="formCRUD" data-bs-backdrop="static" data-bs-keyboard="false"
             tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div id="formulario" class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btncancelar" class="waves-effect waves-red btn-flat"
                                onclick="cerrarFormulario()">CANCELAR
                        </button>
                        <button type="button" id="btnAceptarFormas" onclick="abrirFormulario('model',this)"
                                class="waves-effect waves-red btn-flat">
                            ACEPTAR
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade zoom" id="formCRUDBibliografia" data-bs-backdrop="static" data-bs-keyboard="false"
             tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div id="formularioBibligrafia" class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btnCancelarBibliografia" class="waves-effect waves-red btn-flat"
                                onclick="cerrarFormularioBibliografia()">CANCELAR
                        </button>
                        <button type="button" id="btnAceptarBibliografia"
                                onclick="abrirFormularioBibliografia('model',this)"
                                class="waves-effect waves-red btn-flat">
                            ACEPTAR
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade zoom" id="formCRUDCronograma" data-bs-backdrop="static" data-bs-keyboard="false"
             tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div id="formularioCronograma" class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btnCancelarCronograma" class="waves-effect waves-red btn-flat"
                                onclick="cerrarFormularioCronograma()">CANCELAR
                        </button>
                        <button type="button" id="btnAceptarCronograma"
                                onclick="abrirFormularioCronograma('model',this)"
                                class="waves-effect waves-red btn-flat">
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
<div id="contextMenu" class="offcanvas offcanvas-bottom" tabindex="-1" id="offcanvasBottom"
     aria-labelledby="offcanvasBottomLabel"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/intro.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js" />"></script>
<script src="<c:url value="/static/js/sweetalert.min.js" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/choices.min.js" />"></script>
<script src="<c:url value="/static/js/contexttoglemenu.js?v=3"/>"></script>
<script>
    let toast = new ToastModal(document.getElementById('errortoast'), {});
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Cargando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });
    let modal = new bootstrap.Modal(document.getElementById('formCRUD'));
    let modalBibliografia = new bootstrap.Modal(document.getElementById('formCRUDBibliografia'));
    let modalCronograma = new bootstrap.Modal(document.getElementById('formCRUDCronograma'));

    document.addEventListener("DOMContentLoaded", function () {
        loadercontent.show();
        setTimeout(function () {
            loadercontent.hide();
            document.getElementById('main').classList.remove('invisible');
        }, 2700);
    });
    window.onload = function () {
        document.querySelectorAll('.tox-promotion').forEach(function (item) {
            item.style.display = 'none';
        });
    }

    function open(name) {
        alert(tinymce.get(name).getContent())
    }

    function iniciartutorial() {
        introJs().setOptions({
            nextLabel: "Siguiente",
            prevLabel: "Atras",
            skipLabel: "Salir",
            doneLabel: "Gracias",
            tooltipClass: "",
            exitOnOverlayClick: false,
            showStepNumbers: true
        }).start();
    }

    function guardarDocumento(form) {
        let data = {
            id_dct_programa_analitico: document.getElementById('id_dct_programa_analitico').value,
            marco_referencial: tinymce.get('txtmarco_referencial').getContent(),
            justificacion: tinymce.get('txtjustificacion').getContent(),
            propositos: tinymce.get('txtpropositos').getContent(),
            objetivo_desarrollador: tinymce.get('txtobjetivo_desarrollador').getContent(),
            objetivo_educativo: tinymce.get('txtobjetivo_educativo').getContent(),
            metodos_estrategias: tinymce.get('txtmetodos_estrategias').getContent(),
            recursos: tinymce.get('txtrecursos').getContent(),
            sistema_evaluacion: tinymce.get('txtsistema_evaluacion').getContent(),
            id_estado: document.getElementById('id_estado').value,
            nro_resolucion: document.getElementById('nro_resolucion').value,
            observacion: tinymce.get('observacion').getContent(),
            sistema_evaluacion_criterios: tinymce.get('txtsistema_evaluacion_criterios').getContent(),
            gestion: document.getElementById('gestion').value,
            periodo: document.getElementById('periodo').value
        }
        Post('<c:url value="/editarProgramaAnalitico" />', data)
            .then(function (data) {
                if (data.status === "OK") {
                    toast.show({
                        classNameToast: 'success',
                        textBody: data.message,
                        titleText: "Aviso",
                        subtitleText: ""
                    });
                } else {
                    toast.show({
                        classNameToast: 'danger',
                        textBody: data.message,
                        titleText: "Aviso",
                        subtitleText: ""
                    });
                }
            })
            .catch(function (error) {
                toast.show({
                    classNameToast: 'danger',
                    textBody: error,
                    titleText: "Aviso",
                    subtitleText: ""
                });
            });
    }

    function abrirFormulario(name, button) {
        swichButton(button, true);
        if (document.getElementById(name) !== null) {
            submitForm(name, false)
                .then(function (data) {
                    if (!data.includes('id="model"')) {
                        document.getElementById('v-pills-distribucion_de_tiempo').innerHTML = data;
                        document.getElementById('formulario').innerHTML = '';
                        modal.hide();
                        cargarMenuContextualFormarOrganizacion();
                    } else {
                        document.getElementById('formulario').innerHTML = data;
                    }
                    swichButton(button, false);
                })
                .catch(function (error) {
                    swichButton(button, false);
                    document.getElementById('formulario').innerHTML = '';
                    console.log("Error en la solicitud AJAX");
                    console.log(error);
                });
        }
    }

    function eliminarElementoFormasOrganizacion(input) {
        swal({
            title: "Desea eliminar?",
            text: "Una vez eliminado, no podrá recuperar este registro! " + input.dataset.unidad,
            icon: "warning",
            className: "sweet-alert",
            buttons: true,
            dangerMode: true,
            buttons: {
                cancel: 'Cancelar',
                aceptar: {
                    text: "Aceptar",
                    value: true,
                }
            },
        })
            .then((willDelete) => {
                if (willDelete) {
                    let data = {
                        id_prg_a_distribucion: input.dataset.id,
                        id_dct_programa_analitico: input.dataset.idpgranalitico
                    }
                    Get(input.dataset.url, data)
                        .then(function (data) {
                            document.getElementById('v-pills-distribucion_de_tiempo').innerHTML = data;
                            cargarMenuContextualFormarOrganizacion();
                        })
                        .catch(function (error) {
                            toast.show({
                                classNameToast: 'danger',
                                textBody: error,
                                titleText: "Aviso",
                                subtitleText: ""
                            });
                        });
                }
            });
    }

    function swichButton(input, disabled) {
        input.disabled = disabled;
    }

    function cerrarFormulario() {
        swichButton(document.getElementById('btnAceptarFormas'), false);
        modal.hide();
        document.getElementById('formulario').innerHTML = '';
    }

    function abrirFormularioCronograma(name, button) {
        swichButton(button, true);
        if (document.getElementById(name) !== null) {
            submitForm(name, false)
                .then(function (data) {
                    if (!data.includes('id="model"')) {
                        document.getElementById('v-pills-cronograma').innerHTML = data;
                        document.getElementById('formularioCronograma').innerHTML = '';
                        modalCronograma.hide();
                    } else {
                        document.getElementById('formularioCronograma').innerHTML = data;
                        let select = document.getElementById('tipos_de_clases');
                        const choices = new Choices(select, {
                            allowHTML: true,
                            placeholderValue: '',
                            searchPlaceholderValue: 'Buscar y seleccionar elementos',
                            itemSelectText: 'Seleccionar elemento'
                        });
                    }
                    swichButton(button, false);
                })
                .catch(function (error) {
                    swichButton(button, false);
                    document.getElementById('formularioCronograma').innerHTML = '';
                    console.log("Error en la solicitud AJAX");
                    console.log(error);
                });
        }
    }

    function cerrarFormularioCronograma() {
        swichButton(document.getElementById('btnAceptarCronograma'), false);
        modalCronograma.hide();
        document.getElementById('formularioCronograma').innerHTML = '';
    }

    function abrirFormularioBibliografia(name, button) {
        swichButton(button, true);
        if (document.getElementById(name) !== null) {
            submitForm(name, false)
                .then(function (data) {
                    if (!data.includes('id="model"')) {
                        document.getElementById('v-pills-bibliografia').innerHTML = data;
                        document.getElementById('formularioBibligrafia').innerHTML = '';
                        modalBibliografia.hide();
                    } else {
                        document.getElementById('divtiporeferenciabibliografica').innerHTML = data;
                    }
                    swichButton(button, false);
                })
                .catch(function (error) {
                    swichButton(button, false);
                    document.getElementById('formularioBibligrafia').innerHTML = '';
                    console.log("Error en la solicitud AJAX");
                    console.log(error);
                });
        }
    }

    function cerrarFormularioBibliografia() {
        swichButton(document.getElementById('btnAceptarBibliografia'), false);
        modalBibliografia.hide();
        document.getElementById('formularioBibligrafia').innerHTML = '';
    }

    function eliminarElementoBibliografia(input) {
        swal({
            title: "Desea eliminar?",
            text: "Una vez eliminado, no podrá recuperar este registro! " + input.dataset.unidad,
            icon: "warning",
            className: "sweet-alert",
            buttons: true,
            dangerMode: true,
            buttons: {
                cancel: 'Cancelar',
                aceptar: {
                    text: "Aceptar",
                    value: true,
                }
            },
        })
            .then((willDelete) => {
                if (willDelete) {
                    let data = {
                        id_prg_a_bibliografia: input.dataset.id
                    }
                    Post(input.dataset.url, data)
                        .then(function (data) {
                            if (data.status === "OK") {
                                let elemento = document.getElementById(input.dataset.parent);
                                elemento.classList.add('eliminado');
                                setTimeout(function () {
                                    elemento.remove();
                                    let i = 1;
                                    let numbers = document.getElementById('v-pills-bibliografia').querySelectorAll('[data-nro="1"]');
                                    numbers.forEach(function (item) {
                                        item.innerHTML = i.toString();
                                        i++;
                                    })
                                }, 500);
                            } else {
                                toast.show({
                                    classNameToast: 'danger',
                                    textBody: data.message,
                                    titleText: "Aviso",
                                    subtitleText: ""
                                });
                            }
                        })
                        .catch(function (error) {
                            toast.show({
                                classNameToast: 'danger',
                                textBody: error,
                                titleText: "Aviso",
                                subtitleText: ""
                            });
                        });
                }
            });
    }

    function agregarFormularioBibliografia(input) {
        let data = {
            id_dct_programa_analitico: input.dataset.id
        }
        Get('./programaanaanalitico/bibliografias/agregar', data)
            .then(function (data) {
                document.getElementById('formularioBibligrafia').innerHTML = data;
                modalBibliografia.show();
            })
            .catch(function (error) {
                console.log("Error en la solicitud AJAX");
                console.log(error);
            });
    }

    function editarFormularioBibliografia(input) {
        let data = {
            id_prg_a_bibliografia: input.dataset.id
        }
        Get('./programaanaanalitico/bibliografias/editar', data)
            .then(function (data) {
                document.getElementById('formularioBibligrafia').innerHTML = data;
                modalBibliografia.show();
            })
            .catch(function (error) {
                console.log("Error en la solicitud AJAX");
                console.log(error);
            });
    }

    function agregarFormularioCronograma(input) {
        let data = {
            id_dct_programa_analitico: input.dataset.id
        }
        Get('./programaanaanalitico/cronograma/agregar', data)
            .then(function (data) {
                document.getElementById('formularioCronograma').innerHTML = data;
                let select = document.getElementById('tipos_de_clases');
                const choices = new Choices(select, {
                    allowHTML: true,
                    placeholderValue: '',
                    searchPlaceholderValue: 'Buscar y seleccionar elementos',
                    itemSelectText: 'Seleccionar elemento'
                });
                modalCronograma.show();
            })
            .catch(function (error) {
                console.log("Error en la solicitud AJAX");
                console.log(error);
            });
    }

    function editarFormularioCronograma(input) {
        let data = {
            id_prg_a_cronograma: input.dataset.id
        }
        Get('./programaanaanalitico/cronograma/editar', data)
            .then(function (data) {
                document.getElementById('formularioCronograma').innerHTML = data;
                let select = document.getElementById('tipos_de_clases');
                const choices = new Choices(select, {
                    allowHTML: true,
                    placeholderValue: '',
                    searchPlaceholderValue: 'Buscar y seleccionar elementos',
                    itemSelectText: 'Seleccionar elemento'
                });
                modalCronograma.show();
            })
            .catch(function (error) {
                console.log("Error en la solicitud AJAX");
                console.log(error);
            });
    }

    function eliminarElementoCronograma(input) {
        swal({
            title: "Desea eliminar?",
            text: "Una vez eliminado, no podrá recuperar este registro! " + input.dataset.unidad,
            icon: "warning",
            className: "sweet-alert",
            buttons: true,
            dangerMode: true,
            buttons: {
                cancel: 'Cancelar',
                aceptar: {
                    text: "Aceptar",
                    value: true,
                }
            },
        })
            .then((willDelete) => {
                if (willDelete) {
                    let data = {
                        id_prg_a_cronograma: input.dataset.id
                    }
                    Post(input.dataset.url, data)
                        .then(function (data) {
                            if (data.status === "OK") {
                                let elemento = document.getElementById(input.dataset.parent);
                                elemento.classList.add('eliminado');
                                setTimeout(function () {
                                    elemento.remove();
                                }, 500);
                            } else {
                                toast.show({
                                    classNameToast: 'danger',
                                    textBody: data.message,
                                    titleText: "Aviso",
                                    subtitleText: ""
                                });
                            }
                        })
                        .catch(function (error) {
                            toast.show({
                                classNameToast: 'danger',
                                textBody: error,
                                titleText: "Aviso",
                                subtitleText: ""
                            });
                        });
                }
            });
    }

    function agregarFormasDeOrganizacion(input) {
        let data = {
            id_dct_programa_analitico: input.dataset.programaanaiticoid,
            id_prg_a_contenido: input.dataset.contenidoid,
            totalHoras: input.dataset.totalhoras
        }
        Get('./programaanaanalitico/formasorganizacion/agregar', data)
            .then(function (data) {
                document.getElementById('formulario').innerHTML = data;
                modal.show();
            })
            .catch(function (error) {
                console.log("Error en la solicitud AJAX");
                console.log(error);
            });
    }

    function editarFormasDeOrganizacion(input) {
        let data = {
            id_prg_a_distribucion: input.dataset.id,
            horas: input.dataset.hora,
            totalHoras: input.dataset.totalhoras,
            horasAnterior: input.dataset.horaanterior
        }
        Get('./programaanaanalitico/formasorganizacion/editar', data)
            .then(function (data) {
                document.getElementById('formulario').innerHTML = data;
                modal.show();
            })
            .catch(function (error) {
                console.log("Error en la solicitud AJAX");
                console.log(error);
            });
    }

    function eliminarElemento(input) {
        swal({
            title: "Desea eliminar?",
            text: "Una vez eliminado, no podrá recuperar este registro! " + input.dataset.unidad,
            icon: "warning",
            className: "sweet-alert",
            buttons: true,
            dangerMode: true,
            buttons: {
                cancel: 'Cancelar',
                aceptar: {
                    text: "Aceptar",
                    value: true,
                }
            },
        })
            .then((willDelete) => {
                if (willDelete) {
                    let data = {
                        id_prg_a_contenido: input.dataset.delete,
                        id_dct_programa_analitico: input.dataset.idpgrprograma,
                        unidad: input.dataset.unidad
                    }
                    Get(input.dataset.url, data)
                        .then(function (data) {
                            let elemento = document.getElementById(input.dataset.parent);
                            elemento.classList.add('eliminado');
                            setTimeout(function () {
                                elemento.remove();
                            }, 500);
                            document.getElementById('v-pills-distribucion_de_tiempo').innerHTML = data;
                            cargarMenuContextualFormarOrganizacion();
                        })
                        .catch(function (error) {
                            toast.show({
                                classNameToast: 'danger',
                                textBody: error,
                                titleText: "Aviso",
                                subtitleText: ""
                            });
                        });
                }
            });
    }

    function cambiarTipoReferencia(parent, div) {
        let data = {
            tipo_referncia: parent.value,
            id_dct_programa_analitico: parent.dataset.id
        };
        div.innerHTML = "";
        Get('./programaanaanalitico/bibliografias/agregartipo', data)
            .then(function (data) {
                div.innerHTML = data;
            })
            .catch(function (error) {
            });
    }

    function llenarLista(parent, input) {
        let data = {tipo: parent.value};
        Get('./programaanaanalitico/formasorganizacion/formas', data)
            .then(function (data) {
                let values = JSON.parse(data);
                input.innerHTML = "";
                let options = "";
                values.forEach(function (item) {
                    options += '<optgroup label="' + item.group + '">';
                    item.items.forEach(function (value) {
                        if (value.selected)
                            options += '<option value="' + value.id + '" selected>' + value.value + '</option>';
                        else
                            options += '<option value="' + value.id + '">' + value.value + '</option>';
                    });
                    options += '</optgroup>';
                });
                input.innerHTML = options;
            })
            .catch(function (error) {
            });
    }

    function obtenerSeleccion(select) {
        let input = document.getElementById("tipo_de_clase");
        let opcionesSeleccionadas = select.selectedOptions;
        let valoresSeleccionados = [];
        for (let i = 0; i < opcionesSeleccionadas.length; i++) {
            valoresSeleccionados.push(opcionesSeleccionadas[i].value);
        }
        input.value = valoresSeleccionados.join(",");
    }

    cargarMenuContextualFormarOrganizacion();

    function cargarMenuContextualFormarOrganizacion() {
        let organizacion = document.querySelectorAll('.organizacion');
        for (let i = 0; i < organizacion.length; i++) {
            let menu = new ContextMenuToggle(organizacion[i], {
                contextmenuname: 'contextMenu', eventContext: function (event, option) {
                    switch (option) {
                        case "Editar":
                            editarFormasDeOrganizacion(event);
                            break;
                        case "Eliminar":
                            eliminarElementoFormasOrganizacion(event);
                            break;
                        default:
                    }
                }
            });
        }
    }
</script>
</body>
</html>

