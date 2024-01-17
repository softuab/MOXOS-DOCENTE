<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<br>
<form:form cssClass="form-horizontal" modelAttribute="model" method="POST" action="${pageContext.request.contextPath}/${accion}" enctype="multipart/form-data">
    <form:hidden path="id_persona_kardex"/>
    <form:hidden path="id_persona"/>
    <form:hidden path="UUID"/>
    <form:hidden path="id_experiencia_laboral"/>
    <form:hidden path="url_experiencia"/>
    <form:hidden path="root"/>
    <form:hidden path="gestion"/>
    <form:hidden path="calificacion"/>
    <form:hidden path="tipo_experiencia_laboral"/>
    <form:errors path="tipo_experiencia_laboral" cssClass="invalid"></form:errors>  
    <div class="mb-3 row">
        <label class="control-label col-md-3">Institucion<span  class="text-danger font-weight-bold">*</span></label>
        <div class="col-md-9">
            <form:input cssClass="form-control" path="institucion" aria-describedby="institucionHelp"></form:input>
            <form:errors path="institucion" cssClass="invalid"></form:errors>
                <small class="form-text text-muted" id="institucionHelp">Univerisdad o Institucion donde trabajo.</small>
            </div>
        </div>      
        <div class="mb-3 row">
            <label class="control-label col-md-3">Asignatura / funcion<span  class="text-danger font-weight-bold">*</span></label>
            <div class="col-md-9">
            <form:input cssClass="form-control" path="cargoocupado" aria-describedby="cargoocupadoHelp"></form:input>
            <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                <small class="form-text text-muted" id="cargoocupadoHelp">Asignatura / función que impartia.</small>
            </div>
        </div> 
        <div class="mb-3 row">
            <label class="control-label col-md-3">Descripcion de la materia<span  class="text-danger font-weight-bold">*</span></label> 
            <div class="col-md-9">
            <form:textarea cssClass="form-control" path="detalle" rows="3" aria-describedby="detalleHelp"></form:textarea>
            <form:errors path="detalle" cssClass="invalid"></form:errors>
                <small class="form-text text-muted" id="detalleelp">Descripcion de la materia o detallar la designacion.</small>
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
            <label class="control-label col-md-3">Fecha de inicio de designacion<span  class="text-danger font-weight-bold">*</span></label> 
            <div class="col-md-9">
            <form:input cssClass="form-control date" path="text_inicio" type="date" aria-describedby="inicioHelp"></form:input>
            <form:errors path="text_inicio" cssClass="invalid"></form:errors>
                <small class="form-text text-muted" id="inicioHelp">Detallar fecha de inicio laboral.</small>
            </div>
        </div> 
        <div class="mb-3 row">
            <label class="control-label col-md-3">Fecha de fin de designacion<span  class="text-danger font-weight-bold">*</span></label> 
            <div class="col-md-9">
            <form:input cssClass="form-control date" path="text_fin" type="date" aria-describedby="finHelp"></form:input>
            <form:errors path="text_fin" cssClass="invalid"></form:errors>
                <small class="form-text text-muted" id="finHelp">Detallar fecha de fin laboral.</small>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="control-label col-md-3">Documento original del certificado de trabajo o memorandun de designacion<span  class="text-danger font-weight-bold">*</span></label> 
            <div class="col-md-9">
                <input class="form-control-file" name="Image" id="Image" type="file"  aria-describedby="url_experienciaHelp"  accept="image/x-png,image/gif,image/jpeg,application/pdf">
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