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
                    <h1><i class="fa fa-address-card"></i>&nbsp; Informacion academica posgrado en Educacion superior como requisito institucional</h1>
                </div>
            </div>
            <div class="row"> 
                <div class="col-sm-12">
                    <div class="tile">
                        <form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/kardex/RegistrarEducacionPosgradoPersonal" enctype="multipart/form-data">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <form:hidden path="id_persona_kardex"/>
                            <form:hidden path="id_persona"/>
                            <form:hidden path="imagetituloposgrado"/>
                            <div class="mb-3 row">
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
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Numero de titulo:</label>
                                    <div class="col-md-9">
                                    <form:input type="text" path="tituloposgrado" cssClass="form-control" placeholder="Numero de titulo"></form:input>
                                    <form:errors path="tituloposgrado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
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
                                <div class="mb-3 row">
                                    <label class="control-label col-md-3">Fecha de emision de titulo:</label>
                                    <div class="col-md-9">
                                    <form:input type="date" path="text_fechaemisionposgrado" cssClass="form-control  date" placeholder="Seleccionar fecha" aria-describedby="fechatituloprofesionHelp"  ></form:input>
                                        <small class="form-text text-muted" id="fechatituloprofesionHelp">La fecha de emision del titulo posgradual.</small>
                                    <form:errors path="text_fechaemisionposgrado" cssClass="invalid"></form:errors>
                                    </div>
                                </div>
                                <div class="mb-3 row">
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