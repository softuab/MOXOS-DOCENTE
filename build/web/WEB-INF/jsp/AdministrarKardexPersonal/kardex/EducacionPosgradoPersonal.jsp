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
                    <h1><i class="far fa-address-card"></i>&nbsp; Información academica posgrado en Educacion superior como requisito institucional</h1> 
                </div>
            </div>
            <div class="row"> 
                <div class="col-sm-12">
                    <div class="tile">
                        <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/kardex/RegistrarEducacionPosgradoPersonal.fautapo" enctype="multipart/form-data">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona_kardex"/>
                            <form:hidden path="id_persona"/>
                            <form:hidden path="imagetituloposgrado"/>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Nivel estudio:</label>
                                <div class="col-md-9">
                                    <form:select path="id_nivelestudio_posgrado" cssClass="form-control">
                                        <optgroup label="Seleccionar nivel estudio">
                                            <form:options items="${nivelestudio}" itemLabel="value" itemValue="id" />
                                        </optgroup> 
                                    </form:select>
                                    <form:errors path="id_nivelestudio_posgrado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Numero de titulo:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="tituloposgrado" cssClass="form-control" placeholder="Numero de titulo"></form:input>
                                    <form:errors path="tituloposgrado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Universidad:</label>
                                    <div class="col-md-9">
                                    <form:select path="emisiontituloposgrado" cssClass="form-control" aria-describedby="universidadHelp">
                                        <optgroup label="Seleccionar universidad">
                                            <form:options items="${universidades}" itemLabel="value" itemValue="id" />
                                        </optgroup> 
                                    </form:select>
                                    <small class="form-text text-muted" id="universidadHelp">Universidad donde se emitio el titulo provision nacional que pertenezcan al sistema universitario.</small>
                                    <form:errors path="emisiontituloposgrado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha de emision de titulo:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="text_fechaemisionposgrado" cssClass="form-control  calendar" placeholder="Seleccionar fecha" aria-describedby="fechatituloprofesionHelp" readonly="true"></form:input>
                                        <small class="form-text text-muted" id="fechatituloprofesionHelp">La fecha de emision del titulo posgradual.</small>
                                    <form:errors path="text_fechaemisionposgrado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="file" class="control-label col-md-3">Fotocopia legalizada de provicion nacional</label>
                                    <div class="col-md-9">
                                        <input class="form-control-file" name="file" id="file" type="file" aria-describedby="fileHelp" size="51200?"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                        <small class="form-text text-muted" id="fileHelp">Cargar imagen de la fotocopia legalizado del titulo posgradual</small>
                                    <form:errors path="imagetituloposgrado" cssClass="invalid"></form:errors>
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