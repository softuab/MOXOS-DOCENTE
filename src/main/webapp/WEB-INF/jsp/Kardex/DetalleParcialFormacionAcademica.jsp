<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<ul class="list-unstyled" id="listformacion">
    <c:forEach var="detalle" items="${formacion}"> 
        <li class="media">
            <img src="<c:url value="/static/image/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)"
                 data-url="<c:url value="/VistapreviaimagenDocumentos" />" data-id="${detalle.id_formacion}" data-tabla="formacionacademica" onMouseOver="this.style.cursor='pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.descripcion}</h5>
                <p>
                    <b>Tipo de titulo emitido:</b>&nbsp;${detalle.tipotitulo}<br>
                    <b>Nivel Academico:</b>&nbsp;${detalle.nivelestudio}<br>
                    <b>Area de conocimiento:</b>&nbsp;${detalle.detalle_profesion}<br>
                    <b>Universidad/Institucion:</b>&nbsp;${detalle.expedido}<br>
                    <b>Numero Titulo:</b>&nbsp;${detalle.numerotitulo}<br>
                    <b>Fecha Emision:</b>&nbsp;<fmt:formatDate pattern = "dd/MM/yyyy" value = "${detalle.fechaemision}" /><br>
                    <b>Es un titulo de formacion academica en Educacion superior?</b>&nbsp;${detalle.eseducacionsuperor ? 'SI' : 'NO'}<br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                <form name="formacion${detalle.id_formacion}" action="<c:url value="./FileDownload"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.url_formacion}' /> 
                    <input type="hidden" name="directorio" value='formacion' /> 
                    <button class="btn btn-primary" type="submit"><i class="fa fa-download"></i></button>
                </form>
                </p>
            </div>
        </li>   
    </c:forEach> 
</ul>