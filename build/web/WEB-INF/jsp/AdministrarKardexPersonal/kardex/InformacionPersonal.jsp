<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
        <main class="app-content3">
            <div class="app-title">
                <div>
                    <h1><i class="far fa-address-card"></i>&nbsp; Informacion personal</h1> 
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
                        <form:form method="post" cssClass="form-horizontal" modelAttribute="model" action="${pageContext.request.contextPath}/kardex/RegistrarInformacionPersonal.fautapo">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona"/>
                            <form:hidden path="id_persona_kardex"/>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Nombre</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="nombre" cssClass="form-control" placeholder="Nombre"></form:input>
                                    <form:errors path="nombre" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Segundo nombre</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="segundonombre" cssClass="form-control" placeholder="Segundo nombre"></form:input>
                                    <form:errors path="segundonombre" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Apellido paterno</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="apellidopaterno" cssClass="form-control" placeholder="Apellido paterno"></form:input>
                                    <form:errors path="apellidopaterno" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Apellido materno</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="apellidomaterno" cssClass="form-control" placeholder="Apellido materno"></form:input>
                                    <form:errors path="apellidomaterno" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Lugar de nacimiento</label>
                                    <div class="col-md-9">
                                    <form:select path="id_localidad" cssClass="form-control">
                                        <form:options items="${localidades}" itemLabel="value" itemValue="id" />
                                    </form:select>
                                    <form:errors path="id_localidad" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="localidadHelp">Si no encuentra la localidad comunicar con DTIC para registrarlo.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha nacimiento</label>
                                    <div class="col-md-9">
                                    <form:input readonly="true" path="text_fechanacimiento" cssClass="form-control calendar" placeholder="Fecha nacimiento"></form:input>
                                    <form:errors path="text_fechanacimiento" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Estado Civil</label>
                                    <div class="col-md-9">
                                    <form:select path="estadocivil" cssClass="form-control">
                                        <form:options items="${estadocivil}" itemLabel="value" itemValue="id" />
                                    </form:select>
                                    <form:errors path="estadocivil" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Sexo</label>
                                    <div class="col-md-9"> 
                                        <div class="toggle-flip">
                                            <label>
                                                <input type="checkbox" name="text_sexo" <c:out value="${model.sexo ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="HOMBRE" data-toggle-off="MUJER"></span>
                                        </label>
                                    </div>
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
            $('select').select2();
        </script>
    </body>
</html>