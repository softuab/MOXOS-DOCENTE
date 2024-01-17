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
        <main class="app-content3">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-address-card"></i>&nbsp; Informacion laboral</h1> 
                </div>
            </div>
            <div class="row"> 
                <div class="col-sm-12">
                    <div class="tile">
                        <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/kardex/RegistrarInformacionLaboralPersonal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona_kardex"/>
                            <form:hidden path="id_persona"/>
                            <div class="mb-3 row">
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
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">NUA:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="nua" cssClass="form-control" placeholder="NUA"></form:input>
                                    <form:errors path="nua" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
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
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Numero de cuenta:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="cuentacorriente" cssClass="form-control" placeholder="Numero de cuenta"></form:input>
                                    <form:errors path="cuentacorriente" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Jubilado:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_sindicato" <c:out value="${model.sindicato ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Numero de REN:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="ren" cssClass="form-control" placeholder="Numero de REN"></form:input>
                                    <form:errors path="ren" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Capacidades diferentes</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_discapacidad" <c:out value="${model.discapacidad ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Numero de CODEPEDI:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="nrodiscpacitado" cssClass="form-control" placeholder="Numero de CODEPEDI"></form:input>
                                    <form:errors path="nrodiscpacitado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">1178:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_ley1178" <c:out value="${model.ley1178 ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Numero de certificado 1178:</label>
                                <div class="col-md-9">
                                    <form:input type="text" path="nrotitulo" cssClass="form-control" placeholder="Numero de certificado 1178"></form:input>
                                    <form:errors path="nrotitulo" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Promedio de 1178:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="promedio" cssClass="form-control" placeholder="Promedio de 1178"></form:input>
                                    <form:errors path="promedio" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Fecha de emision de 1178:</label>
                                    <div class="col-md-9">
                                    <form:input type="date" path="text_fechacurso1178" cssClass="form-control date" placeholder="Seleccionar fecha"></form:input>
                                    <form:errors path="text_fechacurso1178" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Detalle de idioma Nativo:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="idiomanativo" cssClass="form-control" placeholder="Detalle de idioma Nativo"></form:input>
                                    <form:errors path="idiomanativo" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Fecha de emision idioma Nativo:</label>
                                    <div class="col-md-9">
                                    <form:input type="date" path="text_fechaemision" cssClass="form-control date" placeholder="Seleccionar fecha"></form:input>
                                    <form:errors path="text_fechaemision" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Certificacion del Sippase:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_sippase" <c:out value="${model.sippase ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="mb-3 row">
                                <label class="control-label col-md-3">Fecha de emision de Sippase:</label>
                                <div class="col-md-9">
                                    <form:input type="date" path="text_fechaemisionsippase" cssClass="form-control date" placeholder="Seleccionar fecha"></form:input>
                                    <form:errors path="text_fechaemisionsippase" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Numero de SSU:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="numerodeseguro" cssClass="form-control" placeholder="Numero de SSU"></form:input>
                                    <form:errors path="numerodeseguro" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Sindicato:</label>
                                    <div class="col-md-9"><div class="toggle-flip"><label><input type="checkbox" name="text_sindicato" <c:out value="${model.sindicato ? 'checked': ''}"/>><span class="flip-indecator" data-toggle-on="SI" data-toggle-off="NO"></span></label></div></div>
                            </div>
                            <div class="tile-footer">
                                <div class="row">
                                    <div class="col-md-8 col-md-offset-3">
                                        <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/IndexKardexPersonal?id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                    </div>
                                </div>
                            </div>
                        </form:form> 
                    </div>
                </div>
            </div> 
        </main>
        <script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
        <script src="<c:url value="/public/js/popper.min.js" />"></script>
    </body>
</html>