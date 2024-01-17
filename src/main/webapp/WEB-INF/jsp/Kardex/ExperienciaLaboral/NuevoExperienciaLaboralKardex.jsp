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
                    <div class="row mb-3">
                        <label class="control-label col-md-3">Tipo de experiencia laboral<span
                                class="text-danger font-weight-bold">*</span></label>
                        <div class="col-md-9">
                            <select class="form-select" id="tipoexperiencia" onchange="cargarformulario(this.value);">
                                <optgroup label="Seleccionar experiencia laboral">
                                    <c:forEach var="item" items="${detalleexperiencia}">
                                        <option value="${item.id}"
                                                <c:if test="${item.id eq parametro.tipo_experiencia_laboral}">selected</c:if> >${item.value}</option>
                                    </c:forEach>
                                </optgroup>
                            </select>
                            <input type="hidden" id="id_experiencia_laboral"
                                   value="${parametro.id_experiencia_laboral}"/>
                            <input type="hidden" id="id_persona" value="${parametro.id_persona}"/>
                            <input type="hidden" id="tipo_experiencia_laboral"
                                   value="${parametro.tipo_experiencia_laboral}"/>
                            <input type="hidden" id="root" value="${parametro.root}"/>
                            <input type="hidden" id="id_persona_kardex" value="${parametro.id_persona_kardex}"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col" id="formulario">
                            <c:if test="${parametro.tipo_experiencia_laboral eq ''}">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm-6 mx-auto">
                                            <div class="card border-secondary mb-3">
                                                <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                                                    !!
                                                </div>
                                                <div class="card-body text-secondary">
                                                    <c:out value="${mensaje}"/><br/>
                                                </div>
                                                <div class="card-footer text-muted text-center">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${parametro.tipo_experiencia_laboral eq 'EXPERIENCIA PROFESIONAL/LABORAL'}">
                                <br>
                                <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                                           action="${pageContext.request.contextPath}/${accion}"
                                           enctype="multipart/form-data">
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
                                        <label class="control-label col-md-3">Universidad/Institucion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="institucion"
                                                        aria-describedby="institucionHelp"></form:input>
                                            <form:errors path="institucion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="institucionHelp">Univerisdad o
                                                Institucion donde trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Cargo o Funcion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="cargoocupado"
                                                        aria-describedby="cargoocupadoHelp"></form:input>
                                            <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="cargoocupadoHelp">Cargo o funcion
                                                que realizaba.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Descripcion del cargo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:textarea cssClass="form-control" path="detalle" rows="3"
                                                           aria-describedby="detalleHelp"></form:textarea>
                                            <form:errors path="detalle" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="detalleelp">Descripcion del cargo o
                                                detallar la designacion.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Referencias<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="refrencia"
                                                        aria-describedby="refrenciaHelp"></form:input>
                                            <form:errors path="refrencia" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="refrenciaHelp">Detallar referencias
                                                laborales.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de inicio de trabajo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control date" path="text_inicio" type="date"
                                                        aria-describedby="inicioHelp"></form:input>
                                            <form:errors path="text_inicio" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="inicioHelp">Detallar fecha de inicio
                                                laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de fin de trabajo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control date" path="text_fin" type="date"
                                                        aria-describedby="finHelp"></form:input>
                                            <form:errors path="text_fin" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="finHelp">Detallar fecha de fin
                                                laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Documento original del certificado de
                                            trabajo o memorandun de designacion<span
                                                    class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file"

                                                   aria-describedby="url_experienciaHelp"
                                                   accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <small class="form-text text-muted" id="url_experienciaHelp">Cargar imagen
                                                del documento original del certificado de trabajo o memorandun de
                                                designacion</small>
                                            <form:errors path="url_experiencia" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                            curriculum?</label>
                                        <div class="col-md-9">
                                            <div class="toggle-flip">
                                                <label>
                                                    <input type="checkbox" name="text_mostrar" <c:out
                                                            value="${model.mostrar ? 'checked': ''}"/>><span
                                                        class="flip-indecator" data-toggle-on="SI"
                                                        data-toggle-off="NO"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tile-footer">
                                        <button class="btn btn-primary" type="submit"><i
                                                class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                                        </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                                      href="${pageContext.request.contextPath}/ListarExperienciaLaboral?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                            class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                    </div>
                                </form:form>
                            </c:if>
                            <c:if test="${parametro.tipo_experiencia_laboral eq 'EXPERIENCIA ACADEMICA-DOCENCIA'}">
                                <br>
                                <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                                           action="${pageContext.request.contextPath}/${accion}"
                                           enctype="multipart/form-data">
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
                                        <label class="control-label col-md-3">Instituci�n<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="institucion"
                                                        aria-describedby="institucionHelp"></form:input>
                                            <form:errors path="institucion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="institucionHelp">Univerisdad o
                                                Institucion donde trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Asignatura / funci�n<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="cargoocupado"
                                                        aria-describedby="cargoocupadoHelp"></form:input>
                                            <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="cargoocupadoHelp">Asignatura /
                                                funci�n que impartia.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Descripcion de la materia<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:textarea cssClass="form-control" path="detalle" rows="3"
                                                           aria-describedby="detalleHelp"></form:textarea>
                                            <form:errors path="detalle" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="detalleelp">Descripcion de la
                                                materia o detallar la designacion.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Referencias<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="refrencia"
                                                        aria-describedby="refrenciaHelp"></form:input>
                                            <form:errors path="refrencia" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="refrenciaHelp">Detallar referencias
                                                laborales.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de inicio de designacion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control date" path="text_inicio" type="date"
                                                        aria-describedby="inicioHelp"></form:input>
                                            <form:errors path="text_inicio" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="inicioHelp">Detallar fecha de inicio
                                                laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de fin de designacion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control date" path="text_fin" type="date"
                                                        aria-describedby="finHelp"></form:input>
                                            <form:errors path="text_fin" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="finHelp">Detallar fecha de fin
                                                laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Documento original del certificado de
                                            trabajo o memorandun de designacion<span
                                                    class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file"

                                                   aria-describedby="url_experienciaHelp"
                                                   accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <small class="form-text text-muted" id="url_experienciaHelp">Cargar imagen
                                                del documento original del certificado de trabajo o memorandun de
                                                designacion</small>
                                            <form:errors path="url_experiencia" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                            curriculum?</label>
                                        <div class="col-md-9">
                                            <div class="toggle-flip">
                                                <label>
                                                    <input type="checkbox" name="text_mostrar" <c:out
                                                            value="${model.mostrar ? 'checked': ''}"/>><span
                                                        class="flip-indecator" data-toggle-on="SI"
                                                        data-toggle-off="NO"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tile-footer">
                                        <button class="btn btn-primary" type="submit"><i
                                                class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                                        </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                                      href="${pageContext.request.contextPath}/ListarExperienciaLaboral?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                            class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                    </div>
                                </form:form>
                            </c:if>
                            <c:if test="${parametro.tipo_experiencia_laboral eq 'EXPERIENCIA TRIBUNAL EN EVENTOS ACADEMICOS'}">
                                <br>
                                <p>
                                    <strong>Nota: </strong> Tribunal de internado rotatorio, defensas de trabajos
                                    cientificos, examen de mesa, examen de grado, asesor o tutor y/o otros
                                </p>
                                <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                                           action="${pageContext.request.contextPath}/${accion}"
                                           enctype="multipart/form-data">
                                    <form:hidden path="id_persona_kardex"/>
                                    <form:hidden path="id_persona"/>
                                    <form:hidden path="UUID"/>
                                    <form:hidden path="id_experiencia_laboral"/>
                                    <form:hidden path="url_experiencia"/>
                                    <form:hidden path="root"/>
                                    <form:hidden path="refrencia"/>
                                    <form:hidden path="calificacion"/>
                                    <form:hidden path="text_fin"/>
                                    <form:hidden path="text_inicio"/>
                                    <form:hidden path="tipo_experiencia_laboral"/>
                                    <form:errors path="tipo_experiencia_laboral" cssClass="invalid"></form:errors>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Institucion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="institucion"
                                                        aria-describedby="institucionHelp"></form:input>
                                            <form:errors path="institucion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="institucionHelp">Univerisdad o
                                                Institucion donde trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Cartera o Funcion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="cargoocupado"
                                                        aria-describedby="cargoocupadoHelp"></form:input>
                                            <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="cargoocupadoHelp">Cargo o funcion
                                                que realizaba.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Descripcion de la cartera o Funcion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:textarea cssClass="form-control" path="detalle" rows="3"
                                                           aria-describedby="detalleHelp"></form:textarea>
                                            <form:errors path="detalle" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="detalleelp">Descripcion de la
                                                cartera o funcion que realizaba.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Gestion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="gestion" type="number"
                                                        aria-describedby="gestionHelp"></form:input>
                                            <form:errors path="gestion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="gestionHelp">Gestion en que inicio
                                                el trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Documento original del certificado de
                                            trabajo o memorandun de designacion<span
                                                    class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file"

                                                   aria-describedby="url_experienciaHelp"
                                                   accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <small class="form-text text-muted" id="url_experienciaHelp">Cargar imagen
                                                del documento original del certificado de trabajo o memorandun de
                                                designacion</small>
                                            <form:errors path="url_experiencia" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                            curriculum?</label>
                                        <div class="col-md-9">
                                            <div class="toggle-flip">
                                                <label>
                                                    <input type="checkbox" name="text_mostrar" <c:out
                                                            value="${model.mostrar ? 'checked': ''}"/>><span
                                                        class="flip-indecator" data-toggle-on="SI"
                                                        data-toggle-off="NO"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tile-footer">
                                        <button class="btn btn-primary" type="submit"><i
                                                class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                                        </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                                      href="${pageContext.request.contextPath}/ListarExperienciaLaboral?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                            class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                    </div>
                                </form:form>
                            </c:if>
                            <c:if test="${parametro.tipo_experiencia_laboral eq 'EXPERIENCIA CARGOS JERARQUICOS UNIVERSITARIOS'}">
                                <br>
                                <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                                           action="${pageContext.request.contextPath}/${accion}"
                                           enctype="multipart/form-data">
                                    <form:hidden path="id_persona_kardex"/>
                                    <form:hidden path="id_persona"/>
                                    <form:hidden path="UUID"/>
                                    <form:hidden path="id_experiencia_laboral"/>
                                    <form:hidden path="url_experiencia"/>
                                    <form:hidden path="root"/>
                                    <form:hidden path="refrencia"/>
                                    <form:hidden path="calificacion"/>
                                    <form:hidden path="text_fin"/>
                                    <form:hidden path="text_inicio"/>
                                    <form:hidden path="tipo_experiencia_laboral"/>
                                    <form:errors path="tipo_experiencia_laboral" cssClass="invalid"></form:errors>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Institucion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="institucion"
                                                        aria-describedby="institucionHelp"></form:input>
                                            <form:errors path="institucion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="institucionHelp">Univerisdad o
                                                Institucion donde trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Cargo o Funcion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="cargoocupado"
                                                        aria-describedby="cargoocupadoHelp"></form:input>
                                            <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="cargoocupadoHelp">Cargo o funcion
                                                que realizaba.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Descripcion del cargo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:textarea cssClass="form-control" path="detalle" rows="3"
                                                           aria-describedby="detalleHelp"></form:textarea>
                                            <form:errors path="detalle" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="detalleelp">Descripcion del cargo o
                                                detallar la designacion.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Gestion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="gestion" type="number"
                                                        aria-describedby="gestionHelp"></form:input>
                                            <form:errors path="gestion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="gestionHelp">Gestion en que inicio
                                                el trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Documento original del certificado de
                                            trabajo o memorandun de designacion<span
                                                    class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file"

                                                   aria-describedby="url_experienciaHelp"
                                                   accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <small class="form-text text-muted" id="url_experienciaHelp">Cargar imagen
                                                del documento original del certificado de trabajo o memorandun de
                                                designacion</small>
                                            <form:errors path="url_experiencia" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                            curriculum?</label>
                                        <div class="col-md-9">
                                            <div class="toggle-flip">
                                                <label>
                                                    <input type="checkbox" name="text_mostrar" <c:out
                                                            value="${model.mostrar ? 'checked': ''}"/>><span
                                                        class="flip-indecator" data-toggle-on="SI"
                                                        data-toggle-off="NO"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tile-footer">
                                        <button class="btn btn-primary" type="submit"><i
                                                class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                                        </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                                      href="${pageContext.request.contextPath}/ListarExperienciaLaboral?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                            class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                    </div>
                                </form:form>
                            </c:if>
                            <c:if test="${parametro.tipo_experiencia_laboral eq 'EXPERIENCIA ADMINISTRATIVAS UNIVERSITARIA'}">
                                <br>
                                <form:form cssClass="form-horizontal" modelAttribute="model" method="POST"
                                           action="${pageContext.request.contextPath}/${accion}"
                                           enctype="multipart/form-data">
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
                                        <label class="control-label col-md-3">Universidad<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="institucion" readonly="true"
                                                        aria-describedby="institucionHelp"></form:input>
                                            <form:errors path="institucion" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="institucionHelp">Univerisdad o
                                                Institucion donde trabajo.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Cargo o Funcion<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="cargoocupado"
                                                        aria-describedby="cargoocupadoHelp"></form:input>
                                            <form:errors path="cargoocupado" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="cargoocupadoHelp">Cargo o funcion
                                                que realizaba.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Descripcion del cargo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:textarea cssClass="form-control" path="detalle" rows="3"
                                                           aria-describedby="detalleHelp"></form:textarea>
                                            <form:errors path="detalle" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="detalleelp">Descripcion del cargo o
                                                detallar la designacion.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Referencias<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control" path="refrencia"
                                                        aria-describedby="refrenciaHelp"></form:input>
                                            <form:errors path="refrencia" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="refrenciaHelp">Detallar referencias
                                                laborales.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de inicio de trabajo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control date" path="text_inicio" type="date"
                                                        aria-describedby="inicioHelp"></form:input>
                                            <form:errors path="text_inicio" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="inicioHelp">Detallar fecha de inicio
                                                laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Fecha de fin de trabajo<span
                                                class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <form:input cssClass="form-control date" path="text_fin" type="date"
                                                        aria-describedby="finHelp"></form:input>
                                            <form:errors path="text_fin" cssClass="invalid"></form:errors>
                                            <small class="form-text text-muted" id="finHelp">Detallar fecha de fin
                                                laboral.</small>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Documento original del certificado de
                                            trabajo o memorandun de designacion<span
                                                    class="text-danger font-weight-bold">*</span></label>
                                        <div class="col-md-9">
                                            <input class="form-control-file" name="Image" id="Image" type="file"

                                                   aria-describedby="url_experienciaHelp"
                                                   accept="image/x-png,image/gif,image/jpeg,application/pdf">
                                            <small class="form-text text-muted" id="url_experienciaHelp">Cargar imagen
                                                del documento original del certificado de trabajo o memorandun de
                                                designacion</small>
                                            <form:errors path="url_experiencia" cssClass="invalid"></form:errors>
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label class="control-label col-md-3">Desea mostrar esta informacion en su
                                            curriculum?</label>
                                        <div class="col-md-9">
                                            <div class="toggle-flip">
                                                <label>
                                                    <input type="checkbox" name="text_mostrar" <c:out
                                                            value="${model.mostrar ? 'checked': ''}"/>><span
                                                        class="flip-indecator" data-toggle-on="SI"
                                                        data-toggle-off="NO"></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tile-footer">
                                        <button class="btn btn-primary" type="submit"><i
                                                class="fa fa-fw fa-lg fa-check-circle"></i>Registrar
                                        </button>&nbsp;&nbsp;&nbsp;<a class="btn btn-secondary"
                                                                      href="${pageContext.request.contextPath}/ListarExperienciaLaboral?id_persona_kardex=${model.id_persona_kardex}&id_persona=${model.id_persona}"><i
                                            class="fa fa-fw fa-lg fa-times-circle"></i>Cancelar</a>
                                    </div>
                                </form:form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <div id="loader"></div>
    <div id="errortoast"></div>
</c:if>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/formload.js" />"></script>
<script>
    let toast = new ToastModal(document.getElementById('errortoast'), {});
    let enviar = new Form(document.getElementById('model'), {
        target: document.getElementById('loader'),
        text: 'Enviando..',
        url: '<c:url value="/static/image/logominiatura.png" />'
    });
    function cargarformulario(value) {
        let data = {
            id_experiencia_laboral: document.getElementById('id_experiencia_laboral').value,
            id_persona: document.getElementById('id_persona').value,
            root: document.getElementById('root').value,
            id_persona_kardex: document.getElementById('id_persona_kardex').value,
            tipo_experiencia_laboral: value
        };
        Get("<c:url value="/SeleccionarPersonaExperienciaKardex"/>", data)
            .then(function (data) {
                document.getElementById('formulario').innerHTML = data;
            })
            .catch(function (error) {
                toast.show({
                    classNameToast: 'danger',
                    textBody: "Problemas con el servidor",
                    titleText: "Aviso",
                    subtitleText: ""
                });
            });
    }

    function validarSubida() {
        let file = document.querySelector('.form-control-file');
        if ((file = this.files[0])) {
            let sizeByte = this.files[0].size;
            let sizekiloBytes = parseInt(sizeByte / 1024);
            if (sizekiloBytes > document.querySelector('.form-control-file').getAttribute('size')) {
                toast.show({
                    classNameToast: 'danger',
                    textBody: 'El tamaño supera el limite permitido',
                    titleText: "Aviso",
                    subtitleText: ""
                });
                this.value = '';
            }
        }
    }
</script>
</body>
</html>