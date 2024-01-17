<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<div class="responsive">
    <table class="col-md-12 table table-sm" id="listcursos">
        <thead class="cf">
            <tr>
                <th>GESTION/PERIODO</th>
                <th>ASIGNATURA O AREA</th>
                <th>PUNTAJE</th>
                <th>DESCARGAR</th>
                <th>ESTADO</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="detalle" items="${detalles}">
                <tr>
                    <td data-label="GESTION/PERIODO">
                        <c:out value="${detalle.gestion}"/>  / <c:out value="${detalle.periodo}"/> 
                    </td>
                    <td data-label="ASIGNATURA O AREA">
                        <c:out value="${detalle.asignatura}"/>
                    </td>
                    <td data-label="PUNTAJE">
                        <c:out value="${detalle.puntaje}"/>
                    </td>
                    <td data-label="DESCARGAR">
                        <form name="formacion${detalle.id_evaluacion_docente}" action="<c:url value="./FileDownload"/>" method="GET">
                            <input type="hidden" name="name" value='${detalle.url_certificado_evaluacion}' /> 
                            <input type="hidden" name="directorio" value='evaluacion' /> 
                            <button class="btn btn-primary" type="submit"><i class="fa fa-download"></i></button>
                        </form>
                    </td>
                    <td data-label="ESTADO">
                        <c:if test="${detalle.aprobado}">
                            <span class="badge badge-success">Aprobado</span>
                        </c:if>
                        <c:if test="${!detalle.aprobado}">
                            <span class="badge badge-danger">Sin aprobar</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>