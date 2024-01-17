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
    <link href="<c:url value="/static/css/choices.min.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<c:if test="${!empty id_rol}">
    <main class="app-content3">
        <div class="app-title">
            <div>
                <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="tile">
                    <div class="row">
                        <div class="col">
                            <c:if test="${error.count > 0}">
                                <div class="alert alert-danger" role="alert">
                                    <ol>
                                        <c:forEach var="value" items="${error.error}">
                                            <li>${value}</li>
                                        </c:forEach>
                                    </ol>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                               action="${pageContext.request.contextPath}/RegistrarModificarFormacionAcademica"
                               enctype="multipart/form-data">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <form:hidden path="id_persona_kardex"/>
                        <form:hidden path="id_persona"/>
                        <form:hidden path="UUID"/>
                        <form:hidden path="id_formacion"/>
                        <form:hidden path="url_formacion"/>
                        <form:hidden path="root"/>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Tipo de titulo emitido<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <form:select path="tipotitulo" cssClass="form-select">
                                    <optgroup label="Seleccionar">
                                        <form:options items="${detalletipotitulo}" itemLabel="value" itemValue="id"/>
                                    </optgroup>
                                </form:select>
                                <form:errors path="tipotitulo" cssClass="invalid"></form:errors>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Nivel estudio<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <form:select path="id_nivelestudio" cssClass="form-select">
                                    <optgroup label="Seleccionar nivel estudio">
                                        <form:options items="${nivelestudio}" itemLabel="value" itemValue="id"/>
                                    </optgroup>
                                </form:select>
                                <form:errors path="id_nivelestudio" cssClass="invalid"></form:errors>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Universidad/Institucion educativa<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <form:select path="universidad" cssClass="form-select">
                                    <optgroup label="Seleccionar Universidad/Institucion educativa">
                                        <form:options items="${universidades}" itemLabel="value" itemValue="id"/>
                                    </optgroup>
                                </form:select>
                                <form:errors path="universidad" cssClass="invalid"></form:errors>
                            </div>
                        </div>
                        <c:if test="${readonly}">
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Descripcion<span
                                        class="text-danger font-weight-bold">*</span></label>
                                <div class="col-md-9">
                                    <form:input cssClass="form-control" path="expedido" aria-describedby="expedidoHelp"
                                                readonly="true"></form:input>
                                    <form:errors path="expedido" cssClass="invalid"></form:errors>
                                    <small class="form-text text-muted" id="expedidoHelp">Universidad o Institucion
                                        donde se emitio el titulo.</small>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${!readonly}">
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Descripcion<span
                                        class="text-danger font-weight-bold">*</span></label>
                                <div class="col-md-9">
                                    <form:input cssClass="form-control" path="expedido"
                                                aria-describedby="expedidoHelp"></form:input>
                                    <form:errors path="expedido" cssClass="invalid"></form:errors>
                                    <small class="form-text text-muted" id="expedidoHelp">Universidad o Institucion
                                        donde se emitio el titulo.</small>
                                </div>
                            </div>
                        </c:if>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Descripcion del titulo<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <form:input cssClass="form-control" path="descripcion"
                                            aria-describedby="descripcionHelp"></form:input>
                                <form:errors path="descripcion" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="descripcionHelp">Descripcion del titulo
                                    academico obtenido.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Numero de titulo y/o certificado.<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <form:input cssClass="form-control" path="numerotitulo"
                                            aria-describedby="numerotituloHelp"></form:input>
                                <form:errors path="numerotitulo" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="numerotituloHelp">Numero del titulo
                                    academico.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Fecha de emision del titulo<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <form:input type="date" cssClass="form-control date" path="text_fechaemision"
                                            aria-describedby="fechaemisionHelp"></form:input>
                                <form:errors path="text_fechaemision" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="fechaemisionHelp">Fecha del titulo academico en
                                    la que fue emitido.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Documentos asociados a:<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-8">
                                <form:select cssClass="form-select select" multiple="multiple" path="id_programas">
                                    <c:forEach var="item" items="${programas}">
                                        <c:if test="${item.selected}">
                                            <option selected value="${item.id}">${item.value}</option>
                                        </c:if>
                                        <c:if test="${!item.selected}">
                                            <option value="${item.id}">${item.value}</option>
                                        </c:if>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="id_programas" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="fechaemisionHelp">Documento en carrera que sera
                                    presentada.</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Es un titulo de formaciona academica en Educacion
                                superior?<span class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <div class="toggle-flip">
                                    <label>
                                        <input type="checkbox" name="text_eseducacionsuperor" <c:out
                                                value="${model.eseducacionsuperor ? 'checked': ''}"/>><span
                                            class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Documento original del titulo academico<span
                                    class="text-danger font-weight-bold">*</span></label>
                            <div class="col-md-9">
                                <input class="form-control-file" name="Image" id="Image" type="file"
                                       aria-describedby="url_formacionHelp"
                                       accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                <form:errors path="url_formacion" cssClass="invalid"></form:errors>
                                <small class="form-text text-muted" id="url_formacionHelp">Cargar imagen del documento
                                    original de del titulo academico</small>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                curriculum?</label>
                            <div class="col-md-9">
                                <div class="toggle-flip">
                                    <label>
                                        <input type="checkbox" name="text_mostrar" <c:out
                                                value="${model.mostrar ? 'checked': ''}"/>><span class="flip-indecator"
                                                                                                 data-toggle-on="SI"
                                                                                                 data-toggle-off="NO"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="tile-footer">
                            <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                            </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                          href="${pageContext.request.contextPath}/ListarFormacionAcademica?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </main>
    <div id="errortoast"></div>
    <div id="loader"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/choices.min.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let toast = new ToastModal(document.getElementById('errortoast'), {});
    const choices = new Choices('.select', {
        allowHTML: true,
        placeholderValue: '',
        searchPlaceholderValue: 'Buscar y seleccionar elementos',
        itemSelectText: 'Seleccionar elemento'
    });
    document.getElementById('tipotitulo').addEventListener("change", function () {
        let data = {tipotitulo: document.getElementById('tipotitulo').value}
        Get("<c:url value="/GetNivelEstudio"/>", data)
            .then(function (data) {
                let json = JSON.parse(data);
                let options = "";
                json.forEach((elem) => {
                    options += '<option value="' + elem.id + '">' + elem.value + '</option>';
                });
                document.getElementById('id_nivelestudio').innerHTML = '<optgroup label="Seleccionar nivel estudio">' + options + '</optgroup>';
            })
            .catch(function (error) {
                toast.show({
                    classNameToast: 'danger',
                    textBody: "Problemas con el servidor",
                    titleText: "Aviso",
                    subtitleText: ""
                });
            });
    })
    document.getElementById('universidad').addEventListener("change", function () {
        if (document.getElementById('universidad').value === "OTRO") {
            document.getElementById('expedido').setAttribute('readonly', false);
            document.getElementById('expedido').value = "";
        } else {
            document.getElementById('expedido').setAttribute('readonly', true);
            document.getElementById('expedido').value = document.getElementById('universidad').value;
        }
    });
    let enviar = new Form(document.getElementById('model'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
</script>
</body>
</html>