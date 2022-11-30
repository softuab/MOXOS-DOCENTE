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
                            <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/RegistrarModificarProduccion.fautapo" enctype="multipart/form-data">
                                <form:hidden path="id_persona_kardex"/>
                                <form:hidden path="id_persona"/>
                                <form:hidden path="UUID"/>
                                <form:hidden path="id_produccion_cientifica"/>
                                <form:hidden path="url_portada_libro"/>
                                <form:hidden path="root"/>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Categoria<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control"  path="categoria" items="${detallecategoria}" itemLabel="value" itemValue="id"/>
                                        <form:errors path="categoria" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Tipo de produccion cientifica<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control"  path="tipo_produccion" items="${detalleproduccion}" itemLabel="value" itemValue="id"/>
                                        <form:errors path="tipo_produccion" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Objetivo de la produccion cientifica<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control"  path="tipo_de_recurso" items="${detalleobjetivo}" itemLabel="value" itemValue="id"/>
                                        <form:errors path="tipo_de_recurso" cssClass="invalid"></form:errors>
                                    </div>
                                </div>  
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Nombre del producto<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control" path="nombre_producto" aria-describedby="nombre_productoHelp"></form:input>
                                        <form:errors path="nombre_producto" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="nombre_productoHelp">Detalle del nombre dek producto.</small>
                                    </div>
                                </div>      
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha de publicacion o elaboracion<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <form:input cssClass="form-control calendar" readonly="true" path="text_fecha_certificacion" aria-describedby="fecha_certificacionHelp"></form:input>
                                        <form:errors path="text_fecha_certificacion" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="fecha_certificacionHelp">Fecha de publicacion o elaboracion del producto cientifico.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Documentos asociados<span  class="text-danger font-weight-bold">*</span></label> 
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
                                    <label class="control-label col-md-3">Documento original del la portado del producto<span  class="text-danger font-weight-bold">*</span></label> 
                                    <div class="col-md-9">
                                        <input class="form-control-file" name="Image" id="Image" type="file" aria-describedby="url_portada_libroHelp"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                        <form:errors path="url_portada_libro" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="url_portada_libroHelp">Cargar imagen del documento original de la portado del producto</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Se encuentra publicado?</label> 
                                    <div class="col-md-9">
                                        <div class="toggle-flip">
                                            <label>
                                                <input type="checkbox" name="text_publicado" <c:out value="${model.publicado ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span>
                                            </label>
                                        </div> 
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
                                    <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/ListarProduccionCientifica.fautapo?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
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
            $('#id_programas').select2();
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
