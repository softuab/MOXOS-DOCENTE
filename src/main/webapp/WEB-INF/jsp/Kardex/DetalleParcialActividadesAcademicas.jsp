<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<ul class="list-unstyled" id="listactividadesacademicas">
    <c:forEach var="detalle" items="${detalles}"> 
        <li class="media">
            <img src="<c:url value="/static/image/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)"
                 data-url="<c:url value="/VistapreviaimagenDocumentos" />" data-id="${detalle.id_activades_academicas}" data-tabla="actividadesacademicas" onMouseOver="this.style.cursor = 'pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.tipo_de_actividad}</h5>
                <p>
                    <b>Descripcion actividad:</b>&nbsp;${detalle.descripcion_actividad}<br>
                    <b>Tipo de documento</b>&nbsp;${detalle.tipo_documento}&nbsp; NRO. ${detalle.detalle_documento}<br>
                    <b>Gestion:</b>&nbsp;${detalle.gestion}<br>
                    <b>Mes:</b>&nbsp;${detalle.mes}<br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                <form name="formacion${detalle.id_activades_academicas}" action="<c:url value="./FileDownload"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.url_actividades_academicas}' /> 
                    <input type="hidden" name="directorio" value='actividadesacademicas' /> 
                    <button class="btn btn-primary" type="submit"><i class="fa fa-download"></i></button>
                </form>
                </p>
            </div>
        </li>   
    </c:forEach> 
</ul>