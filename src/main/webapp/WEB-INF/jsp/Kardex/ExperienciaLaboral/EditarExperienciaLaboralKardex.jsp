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
                                    <c:if test = "${error.count > 0}">
                                        <div class="alert alert-danger" role="alert"> 
                                            <ol>
                                                <c:forEach var="value" items="${error.error}" >
                                                    <li>${value}</li>
                                                    </c:forEach> 
                                            </ol>
                                        </div>
                                    </c:if> 
                                </div>
                            </div>
                            <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/RegistrarModificarExperiencia" enctype="multipart/form-data">
                                <form:hidden path="id_persona_kardex"/>
                                <form:hidden path="id_persona"/>
                                <form:hidden path="UUID"/>
                                <form:hidden path="id_experiencia_laboral"/>
                                <form:hidden path="url_experiencia"/>
                                <form:hidden path="root"/>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Tipo de experiencia laboral<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select path="tipo_experiencia_laboral" cssClass="form-control">
                                            <optgroup label="Seleccionar experiencia laboral">
                                                <form:options items="${detalleexperiencia}" itemLabel="value" itemValue="id" />
                                            </optgroup> 
                                        </form:select>
                                        <form:errors path="tipo_experiencia_laboral" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>   
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Universidad/Institucion educativa<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control" path="institucion" aria-describedby="institucionHelp"></form:input>
                                        <form:errors path="institucion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="institucionHelp">Univerisdad o Institucion donde trabajo.</small>
                                        </div>
                                    </div>      
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Cargo o Funcion<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control" path="cargoocupado" aria-describedby="cargoocupadoHelp"></form:input>
                                        <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="cargoocupadoHelp">Cargo o funcion que realizaba.</small>
                                        </div>
                                    </div> 
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Descripcion del cargo<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:textarea cssClass="form-control" path="detalle" rows="3" aria-describedby="detalleHelp"></form:textarea>
                                        <form:errors path="detalle" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="detalleelp">Descripcion del cargo o detallar la designacion.</small>
                                        </div>
                                    </div> 
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Referencias<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control" path="refrencia" aria-describedby="refrenciaHelp"></form:input>
                                        <form:errors path="refrencia" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="refrenciaHelp">Detallar referencias laborales.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Calificacion de a�os de servicio</label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control" path="calificacion" aria-describedby="calificacionHelp"></form:input>
                                        <form:errors path="calificacion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="calificacionHelp">Detallar calificacion de a�os de servicio.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Gestion<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control" path="gestion" type="number" aria-describedby="gestionHelp"></form:input>
                                        <form:errors path="gestion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="gestionHelp">Gestion en que inicio el trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de inicio de trabajo<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control calendar" path="text_inicio" readonly="true" aria-describedby="inicioHelp"></form:input>
                                        <form:errors path="text_inicio" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="inicioHelp">Detallar fecha de inicio laboral.</small>
                                        </div>
                                    </div> 
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de fin de trabajo<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input cssClass="form-control calendar" path="text_fin" readonly="true" aria-describedby="finHelp"></form:input>
                                        <form:errors path="text_fin" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="finHelp">Detallar fecha de fin laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Documento original del certificado de trabajo o memorandun de designacion<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file" onchange="validarSubida()" aria-describedby="url_experienciaHelp"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <small class="form-text text-muted" id="url_experienciaHelp">Cargar imagen del documento original del certificado de trabajo o memorandun de designacion</small>
                                        <form:errors path="url_experiencia" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>  
                                    <div class="mb-3 row">
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
                                    <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/ListarExperienciaLaboral?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </main>
        </c:if>
        <script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
        <script src="<c:url value="/public/js/popper.min.js" />"></script>
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
                        swal("Oops", 'El tama�o supera el limite permitido!', "error");
                        $(this).val('');
                    }
                }
            });
        </script>
    </body>
</html>