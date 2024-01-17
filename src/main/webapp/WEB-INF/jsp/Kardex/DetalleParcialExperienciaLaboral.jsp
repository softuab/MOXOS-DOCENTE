<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<ul class="list-unstyled" id="listformacion">
    <c:forEach var="detalle" items="${detalles}"> 
        <li class="media">
            <img src="<c:url value="/static/image/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)"
                 data-url="<c:url value="/VistapreviaimagenDocumentos" />" data-id="${detalle.id_experiencia_laboral}" data-tabla="experiencialaboral" onMouseOver="this.style.cursor = 'pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.detalle}</h5>
                <p>
                    <b>Institucion:</b>&nbsp;${detalle.institucion}<br>
                    <b>Esperiencia laboral como:</b>&nbsp;${detalle.tipo_experiencia_laboral}<br>
                    <b>Descripcion de la funcion:</b>&nbsp;${detalle.cargoocupado}<br>
                    <b>Referencia profesional:</b>&nbsp;${detalle.refrencia}<br>
                    <b>Calificacion años de servicio:</b>&nbsp;${detalle.calificacion}<br>
                    <b>Periodo de trabajo:</b>&nbsp; De <fmt:formatDate pattern = "dd/MM/yyyy" value = "${detalle.inicio}" /> A <fmt:formatDate pattern = "dd/MM/yyyy" value = "${detalle.fin}" /><br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                <form name="formacion${detalle.id_experiencia_laboral}" action="<c:url value="./FileDownload"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.url_experiencia}' /> 
                    <input type="hidden" name="directorio" value='experiencia' /> 
                    <button class="btn btn-primary" type="submit"><i class="fa fa-download"></i></button>
                </form>
                </p>
            </div>
        </li>   
    </c:forEach> 
</ul>