<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <META HTTP-EQUIV="Cache-Control" CONTENT="max-age='0'" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <META http-equiv="expires" content="0" />
        <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT" />
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        <title>Sistema Integrado -  Moxos</title>
        <link rel="stylesheet" href="<c:url value='/Public/Css/main.css'/>" type="text/css">
        <link rel="stylesheet" href="<c:url value='/Public/Css/FontAwesome/css/fontawesome-all.css'/>"> 
    </head>
    <body class="app sidebar-mini rtl">
        <c:if test="${!empty id_rol}">
            <main class="app-content3">
                <div class="app-title">
                    <div>
                        <h1><i class="far fa-address-card"></i>&nbsp; ${nombres}</h1> 
                    </div>
                </div>
                <div class="row"> 
                    <div class="col-sm-12">
                        <div class="tile">
                            <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/RegistrarPersonaFormacionAcademicaKardex.fautapo" enctype="multipart/form-data">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <form:hidden path="id_persona_kardex"/>
                                <form:hidden path="id_persona"/>
                                <form:hidden path="UUID"/>
                                <form:hidden path="id_formacion"/>
                                <form:hidden path="root"/>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Tipo de titulo emitido<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select path="tipotitulo" cssClass="form-control">
                                            <optgroup label="Seleccionar">
                                                <form:options items="${detalletipotitulo}" itemLabel="value" itemValue="id" />
                                            </optgroup> 
                                        </form:select>
                                        <form:errors path="tipotitulo" cssClass="invalid"></form:errors>
                                        </div>
                                    </div> 
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Nivel estudio<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:select path="id_nivelestudio" cssClass="form-control">
                                            <optgroup label="Seleccionar nivel estudio">
                                                <form:options items="${nivelestudio}" itemLabel="value" itemValue="id" />
                                            </optgroup> 
                                        </form:select>
                                        <form:errors path="id_nivelestudio" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Universidad/Institucion educativa<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:select path="universidad" cssClass="form-control">
                                            <optgroup label="Seleccionar Universidad/Institucion educativa">
                                                <form:options items="${universidades}" itemLabel="value" itemValue="id" />
                                            </optgroup> 
                                        </form:select>
                                        <form:errors path="universidad" cssClass="invalid"></form:errors>
                                        </div>
                                    </div> 
                                <c:if test="${readonly}">
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Descripcion<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="expedido" aria-describedby="expedidoHelp"  readonly="true"></form:input>
                                            <form:errors path="expedido" cssClass="invalid"></form:errors>
                                                <small class="form-text text-muted" id="expedidoHelp">Universidad o Institucion donde se emitio el titulo.</small>
                                            </div>
                                        </div> 
                                </c:if>
                                <c:if test="${!readonly}">
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Descripcion<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="expedido" aria-describedby="expedidoHelp"></form:input>
                                            <form:errors path="expedido" cssClass="invalid"></form:errors>
                                                <small class="form-text text-muted" id="expedidoHelp">Universidad o Institucion donde se emitio el titulo.</small>
                                            </div>
                                        </div> 
                                </c:if>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Descripcion del titulo<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="descripcion" aria-describedby="descripcionHelp"></form:input>
                                        <form:errors path="descripcion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="descripcionHelp">Descripcion del titulo academico obtenido.</small>
                                        </div>
                                    </div> 
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Numero de titulo y/o certificado.<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control" path="numerotitulo" aria-describedby="numerotituloHelp"></form:input>
                                        <form:errors path="numerotitulo" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="numerotituloHelp">Numero del titulo academico.</small>
                                        </div>
                                    </div> 
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Fecha de emision del titulo<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input  cssClass="form-control calendar" path="text_fechaemision" aria-describedby="fechaemisionHelp" readonly="true"></form:input>
                                        <form:errors path="text_fechaemision" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="fechaemisionHelp">Fecha del titulo academico en la que fue emitido.</small>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Documentos asociados a:<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-8">
                                        <form:select cssClass="form-control" multiple="multiple" path="id_programas">
                                            <c:forEach var="item" items="${programas}">
                                                <c:if test="${item.selected}"> <option selected value="${item.id}">${item.value}</option></c:if>
                                                <c:if test="${!item.selected}"> <option value="${item.id}">${item.value}</option></c:if>
                                            </c:forEach>
                                        </form:select>
                                        <form:errors path="id_programas" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="fechaemisionHelp">Documento en carrera que sera presentada.</small>
                                        </div>
                                    </div> 
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Es un titulo de formaciona academica en Educacion superior?<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                            <div class="toggle-flip"> 
                                                <label>
                                                    <input type="checkbox" name="text_eseducacionsuperor" <c:out value="${model.eseducacionsuperor ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span>
                                            </label>
                                        </div> 
                                    </div>
                                </div> 
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Documento original del titulo academico<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <input class="form-control-file" name="Image" id="Image" type="file" aria-describedby="url_formacionHelp"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                        <form:errors path="url_formacion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="url_formacionHelp">Cargar imagen del documento original de del titulo academico</small>
                                        </div>
                                    </div>  
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Desea mostrar esta informacion en su curriculum?</label> 
                                        <div class="col-md-9">
                                            <div class="toggle-flip">
                                                <label>
                                                    <input type="checkbox" name="text_mostrar" <c:out value="${model.mostrar ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span>
                                            </label>
                                        </div> 
                                    </div>
                                </div>  
                                <div class="tile-footer">
                                    <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/ListarFormacionAcademica.fautapo?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </main>
        </c:if>
        <script src="<c:url value='/Public/Js/jquery/jquery-3.3.1.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/jquery/popper.min.js'/>" ></script>
        <script src="<c:url value='/Public/Js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/main.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/pace.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/select2.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/bootstrap-datepicker.min.js'/>"></script>
        <script src="<c:url value="/Public/Js/sweetalert.min.js"/>"></script>
        <script>
            $('.calendar').datepicker({
                format: "dd/mm/yyyy",
                autoclose: true,
                locale: 'es',
                todayHighlight: true
            });
            $('#tipotitulo').change(function (evt) {
                var data = {tipotitulo: $('#tipotitulo').val()};
                $.get('./GetNivelEstudio.fautapo', data, function (response)
                {
                    var options = "";
                    $.each(response, function (i, elem) {
                        options += '<option value="' + elem.id + '">' + elem.value + '</option>';
                    });
                    $('#id_nivelestudio').html('<optgroup label="Seleccionar nivel estudio">' + options + '</optgroup>')
                });
            });
            $(".form-control-file").change(function (e) {
                var file;
                if ((file = this.files[0])) {
                    var sizeByte = this.files[0].size;
                    var sizekiloBytes = parseInt(sizeByte / 1024);
                    if (sizekiloBytes > $('.form-control-file').attr('size')) {
                        swal("Oops", 'El tamaño supera el limite permitido!', "error");
                        $(this).val('');
                    }
                }
            });
            $('#id_programas').select2();
            $('#universidad').change(function (evt) {
                if ($('#universidad').val() === 'OTRO') {
                    $('#expedido').attr('readonly', false);
                    $('#expedido').val('');
                } else {
                    $('#expedido').attr('readonly', true);
                    $('#expedido').val($('#universidad').val());
                }
            });
        </script>
    </body>
</html>