<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 

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
        <main class="app-content3">
            <div class="app-title">
                <div>
                    <h1><i class="far fa-address-card"></i>&nbsp; Información identificación personal</h1> 
                </div>
            </div>
            <div class="row"> 
                <div class="col-sm-12">
                    <div class="tile">
                        <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/kardex/RegistrarIdentificacionPersonal.fautapo" enctype="multipart/form-data">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona_kardex"/>
                            <form:hidden path="id_persona"/>
                            <form:hidden path="imagecarnetidentidad"/>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Tipo documento</label>
                                <div class="col-md-9">
                                    <form:select path="tipodocumento" cssClass="form-control">
                                        <form:options items="${tipodocumento}" itemLabel="value" itemValue="id" />
                                    </form:select>
                                    <form:errors path="tipodocumento" cssClass="invalid"></form:errors> 
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Numero de documento</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="numerodocumento" cssClass="form-control" placeholder="Numero del documento"></form:input>
                                    <form:errors path="numerodocumento" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Emision</label>
                                    <div class="col-md-9">
                                    <form:select path="emision_documento" cssClass="form-control">
                                        <form:options items="${ciudad}" itemLabel="value" itemValue="id" />
                                    </form:select>
                                    <form:errors path="emision_documento" cssClass="invalid"></form:errors> 
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha de Expiración de documento</label>
                                    <div class="col-md-9">
                                    <form:input readonly="true" path="text_fechaexpiracioncarnet" cssClass="form-control calendar" placeholder="Fecha de Expiración de documento"></form:input>
                                    <form:errors path="text_fechaexpiracioncarnet" cssClass="invalid"></form:errors>
                                    </div>
                                </div> 
                                <div class="form-group row">
                                    <label for="Image" class="control-label col-md-3">Fotocopia de carnet identidad</label>
                                    <div class="col-md-9">
                                        <input class="form-control-file" name="Image" id="Image" type="file" aria-describedby="ImageHelp" size="51200?"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                        <small class="form-text text-muted" id="ImageHelp">Cargar imagen de fotocopia del documento identificacion</small>
                                    <form:errors path="imagecarnetidentidad" cssClass="invalid"></form:errors>
                                    </div>
                                </div> 
                                <div class="tile-footer">
                                    <div class="row">
                                        <div class="col-md-8 col-md-offset-3">
                                            <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/IndexKardexPersonal.fautapo?id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                        </div>
                                    </div>
                                </div>
                        </form:form> 
                    </div>
                </div>
            </div> 
        </main>
        <script src="<c:url value='/Public/Js/jquery/jquery-3.3.1.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/jquery/popper.min.js'/>" ></script>
        <script src="<c:url value='/Public/Js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/main.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/pace.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/bootstrap-datepicker.min.js'/>"></script> 
        <script src="<c:url value='/Public/Js/plugins/select2.min.js'/>"></script>
        <script>
            $('.calendar').datepicker({
                format: "dd/mm/yyyy",
                autoclose: true,
                locale: 'es',
                todayHighlight: true
            });
        </script>
    </body>
</html>