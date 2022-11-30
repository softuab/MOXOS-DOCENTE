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
                            <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/RegistrarPersonaModalidadIngreso.fautapo" enctype="multipart/form-data">
                                <form:hidden path="id_persona_kardex"/>
                                <form:hidden path="id_persona"/>
                                <form:hidden path="UUID"/>
                                <form:hidden path="id_modalidadingreso"/>
                                <form:hidden path="url_modalidadingreso"/>
                                <form:hidden path="root"/>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Carrera</label>
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control" path="id_programa" items="${programas}" itemLabel="value" itemValue="id"  aria-describedby="id_programaHelp"/>
                                        <form:errors path="id_programa" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="id_programaHelp">Seleccionar la carrera.</small>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="control-label col-md-3">Modalidad de ingreso</label>
                                    <div class="col-md-9">
                                        <form:select cssClass="form-control" path="modalidadingreso" items="${modalidadingreso}" itemLabel="value" itemValue="id"  aria-describedby="modalidadingresoHelp"/>
                                        <form:errors path="modalidadingreso" cssClass="invalid"></form:errors>
                                        <small class="form-text text-muted" id="modalidadingresoHelp">Seleccionar la modalidad de ingreso.</small>
                                   </div>
                                </div>
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Fecha ingreso<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                        <form:input readonly="true" cssClass="form-control calendar" path="text_fechaingreso" ></form:input>
                                        <form:errors path="text_fechaingreso" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>   
                                    <div class="form-group row">
                                        <label class="control-label col-md-3">Documento original del documento<span  class="text-danger font-weight-bold">*</span></label> 
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file" aria-describedby="url_modalidadingresoHelp"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <form:errors path="url_modalidadingreso" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="url_modalidadingresoHelp">Cargar imagen del documento original del memorandum o resolucion rectoral</small>
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
                                    <button class="btn btn-primary" type="submit"><i class="fa fa-fw fa-lg fa-check-circle"></i>Registrar</button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary" href="${pageContext.request.contextPath}/ListaModalidadIngreso.fautapo?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
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
        <script>
            $('.calendar').datepicker({
                format: "dd/mm/yyyy",
                autoclose: true,
                locale: 'es',
                todayHighlight: true
            });
            $('#id_programa').select2();
            function recargarprograma() {
                var select = document.getElementById("id_facultad");
                var data = {
                    id_facultad: select.value
                };
                console.log(data);
                var res = new Promise(function (resolve, reject) {
                    $.ajax({
                        type: "GET",
                        url: "./ListarProgramasFacultad.fautapo",
                        data: data,
                        success: function (data) {
                            var s = document.getElementById('id_programa_group');
                            s.innerHTML = data.programas;
                            resolve(data);
                        },
                        error: function (err) {
                            reject(err);
                        }
                    });
                });
                console.log(res);
                console.log(res.PromiseValue);
            }
        </script>
    </body>
</html>