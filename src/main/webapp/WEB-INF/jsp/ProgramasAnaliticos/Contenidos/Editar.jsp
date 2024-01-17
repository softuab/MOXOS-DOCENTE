<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <h5> CONTENIDOS DE ${model.materia} GRUPO ${model.grupo} ${model.periodo}/${model.gestion} </h5>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="row">
                    <div class="col">
                        <form:form name="forma" method="post" modelAttribute="model"
                                   action="${pageContext.request.contextPath}/programaanaanalitico/contenidos/editar">
                            <form:hidden path="id_prg_a_contenido"/>
                            <form:hidden path="id_dct_programa_analitico"/>
                            <form:hidden path="id_estado"/>
                            <form:hidden path="gestion"/>
                            <form:hidden path="periodo"/>
                            <form:hidden path="materia"/>
                            <form:hidden path="grupo"/>
                            <form:hidden path="mapa"/>
                            <div class="mb-3">
                                <label for="contenido">Contenido:</label>
                                <form:input path="contenido" cssClass="form-control" placeholder="Contenido"/>
                                <small class="form-text text-muted" id="contenidoHelp">Definir el contenido del unidad
                                    tematica.</small>
                                <form:errors path="contenido" cssClass="invalid"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="objetivo_instructivo">Objetivo Instructivo:</label>
                                <form:textarea path="objetivo_instructivo" cssClass="form-control textarea" rows="5"/>
                                <small class="form-text text-muted" id="objetivo_instructivoHelp">Definir el objetivo
                                    instructivo.</small>
                                <form:errors path="objetivo_instructivo" cssClass="invalid"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="objetivo_instructivo">Conocimientos:</label>
                                <form:textarea path="conocimientos" cssClass="form-control textarea" rows="5"/>
                                <small class="form-text text-muted" id="conocimientosHelp">Definir los
                                    conocimientos.</small>
                                <form:errors path="conocimientos" cssClass="invalid"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="habilidades">Habilidades:</label>
                                <form:textarea path="habilidades" cssClass="form-control textarea" rows="5"/>
                                <small class="form-text text-muted" id="habilidadesHelp">Definir los
                                    conocimientos.</small>
                                <form:errors path="habilidades" cssClass="invalid"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="habilidades">valores:</label>
                                <form:textarea path="valores" cssClass="form-control textarea" rows="5"/>
                                <small class="form-text text-muted" id="valoresHelp">Definir los valores.</small>
                                <form:errors path="valores" cssClass="invalid"></form:errors>
                            </div>
                        </form:form>
                    </div>
                </div>
                <div class="tile-footer">
                    <button class="btn btn-primary" type="button" onclick="registrarFormulario()"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                    </button>&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-secondary"
                       href="${pageContext.request.contextPath}/detalleProgramaAnalitico?id_dct_programa_analitico=${model.id_dct_programa_analitico}&gestion=${model.gestion}&periodo=${model.periodo}&materia=${model.materia}&grupo=${model.grupo}&tab=2"><i
                            class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                </div>
            </div>
        </div>
    </div>
    </div>
</main>
<div id="loader"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Cargando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });
    function  registrarFormulario(){
        loadercontent.show();
        document.getElementById('model').submit();
    }
    document.addEventListener("DOMContentLoaded", function () {
        loadercontent.show();
        setTimeout(function () {
            loadercontent.hide();
        }, 2700);
    });
    window.onload = function () {
        document.querySelectorAll('.tox-promotion').forEach(function (item) {
            item.style.display = 'none';
        });
    }
</script>
</body>
</html>

