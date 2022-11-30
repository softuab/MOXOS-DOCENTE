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
                            <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/RegistrarModificarCursos.fautapo" enctype="multipart/form-data">
                                <form:hidden path="id_persona_kardex"/>
                                <form:hidden path="id_persona"/>
                                <form:hidden path="UUID"/>
                                <form:hidden path="id_cursos_realizados"/>
                                <form:hidden path="url_cursos"/>
                                <form:hidden path="root"/>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Tipo de asistencia<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control"  path="tipoorganizacion" items="${detalleorganizacion}" itemLabel="value" itemValue="id" aria-describedby="tipoorganizacionHelp"/>
                                        <form:errors path="tipoorganizacion" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="tipoorganizacionHelp">Tipo de evento a la que se asistio.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Tipo de evento<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control"  path="tipo_eventos" items="${detalleevento}" itemLabel="value" itemValue="id" aria-describedby="tipo_eventosHelp"/>
                                        <form:errors path="tipo_eventos" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="tipo_eventosHelp">Seleccionar el tipo evento como participo.</small>
                                    </div>
                                </div>   
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Universidad/Institucion educativa<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="expedido_institucion" aria-describedby="expedido_institucionHelp"></form:input>
                                        <form:errors path="expedido_institucion" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="expedido_institucionHelp">Univerisdad o Institucion donde trabajo.</small>
                                    </div>
                                </div>      
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Descripcion del curso o evento<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="detalle" aria-describedby="detalleHelp"></form:input>
                                        <form:errors path="detalle" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="detalleHelp">Descripcion del evento o cursos.</small>
                                    </div>
                                </div> 
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Numero del titulo<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="nrotitulo" aria-describedby="nrotituloHelp"></form:input>
                                        <form:errors path="nrotitulo" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="nrotituloHelp">Numero del titulo del curso o evento.</small>
                                    </div>
                                </div> 
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Horas academicas</label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="horas_academicas" aria-describedby="horas_academicasHelp"></form:input>
                                        <form:errors path="horas_academicas" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="horas_academicasHelp">Detallar las horas academicas del curso.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Duracion</label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="duracion" aria-describedby="duracionHelp"></form:input>
                                        <form:errors path="duracion" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="duracionHelp">Duracion del curso.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha de evento<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control calendar" readonly="true" path="text_fechapresentacion" aria-describedby="fechapresentacionHelp"></form:input>
                                        <form:errors path="text_fechapresentacion" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="fechapresentacionHelp">Fecha en la se realizo el evento.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Objetivo del curso<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control"  path="objetivo_curso" items="${objetivo_curso}" itemLabel="value" itemValue="id" aria-describedby="tipo_eventosHelp"/>
                                        <form:errors path="objetivo_curso" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="tipo_eventosHelp">Seleccionar el objetivo del curso.</small>
                                    </div>
                                </div>  
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Documento original del certificado de del evento<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <input class="form-control-file" name="Image" id="Image" type="file" aria-describedby="url_cursosHelp"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                        <form:errors path="url_cursos" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="url_cursosHelp">Cargar imagen del documento original del certificado de del evento</small>
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
                                    <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/ListarCursosRealizados.fautapo?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
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
        <script src="<c:url value='/Public/Js/plugins/bootstrap-datepicker.min.js'/>"></script>
        <script>
            $('.calendar').datepicker({
                format: "dd/mm/yyyy",
                autoclose: true,
                locale: 'es',
                todayHighlight: true
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
        </script>
    </body>
</html>