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
                    <h1><i class="far fa-address-card"></i>&nbsp; Información laboral</h1> 
                </div>
            </div>
            <div class="row"> 
                <div class="col-sm-12">
                    <div class="tile">
                        <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/kardex/RegistrarInformacionLaboralPersonal.fautapo">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona_kardex"/>
                            <form:hidden path="id_persona"/>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Tipo de NUA:</label>
                                <div class="col-md-9">
                                    <form:select path="tiponua" cssClass="form-control">
                                        <optgroup label="Seleccionar tipo NUA">
                                            <form:options items="${tiponua}" itemLabel="value" itemValue="id" />
                                        </optgroup> 
                                    </form:select>
                                    <form:errors path="tiponua" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">NUA:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="nua" cssClass="form-control" placeholder="NUA"></form:input>
                                    <form:errors path="nua" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Entidad Financiera:</label>
                                    <div class="col-md-9">
                                    <form:select path="id_banco" cssClass="form-control">
                                        <optgroup label="Seleccionar Bancos">
                                            <form:options items="${bancos}" itemLabel="value" itemValue="id" />
                                        </optgroup> 
                                    </form:select>
                                    <form:errors path="id_banco" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Numero de cuenta:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="cuentacorriente" cssClass="form-control" placeholder="Numero de cuenta"></form:input>
                                    <form:errors path="cuentacorriente" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Jubilado:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_sindicato" <c:out value="${model.sindicato ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Numero de REN:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="ren" cssClass="form-control" placeholder="Numero de REN"></form:input>
                                    <form:errors path="ren" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Capacidades diferentes</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_discapacidad" <c:out value="${model.discapacidad ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Numero de CODEPEDI:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="nrodiscpacitado" cssClass="form-control" placeholder="Numero de CODEPEDI"></form:input>
                                    <form:errors path="nrodiscpacitado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">1178:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_ley1178" <c:out value="${model.ley1178 ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Numero de certificado 1178:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="nrotitulo" cssClass="form-control" placeholder="Numero de certificado 1178"></form:input>
                                    <form:errors path="nrotitulo" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Promedio de 1178:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="promedio" cssClass="form-control" placeholder="Promedio de 1178"></form:input>
                                    <form:errors path="promedio" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha de emision de 1178:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="text_fechacurso1178" cssClass="form-control calendar" placeholder="Seleccionar fecha" readonly="true"></form:input>
                                    <form:errors path="text_fechacurso1178" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Detalle de idioma Nativo:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="idiomanativo" cssClass="form-control" placeholder="Detalle de idioma Nativo"></form:input>
                                    <form:errors path="idiomanativo" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Fecha de emision idioma Nativo:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="text_fechaemision" cssClass="form-control calendar" placeholder="Seleccionar fecha" readonly="true"></form:input>
                                    <form:errors path="text_fechaemision" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Certificación del Sippase:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_sippase" <c:out value="${model.sippase ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="form-group row">
                                <label class="control-label col-md-3">Fecha de emision de Sippase:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="text_fechaemisionsippase" cssClass="form-control calendar" placeholder="Seleccionar fecha" readonly="true"></form:input>
                                    <form:errors path="text_fechaemisionsippase" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Numero de SSU:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="numerodeseguro" cssClass="form-control" placeholder="Numero de SSU"></form:input>
                                    <form:errors path="numerodeseguro" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Sindicato:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_sindicato" <c:out value="${model.sindicato ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
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