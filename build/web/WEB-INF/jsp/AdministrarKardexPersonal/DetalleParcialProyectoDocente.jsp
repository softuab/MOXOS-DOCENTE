<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<ul class="list-unstyled" id="listproyectodocente">
    <c:forEach var="detalle" items="${detalles}"> 
        <li class="media">
            <img src="<c:url value="/imagenes/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)" data-id="${detalle.id_personas_proyecto}" data-tabla="proyectodocente" onMouseOver="this.style.cursor='pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.nombreproyecto}</h5>
                <p>
                    <b>Inversion:</b>&nbsp;${detalle.inversion}<br>
                    <b>Financiador:</b>&nbsp;${detalle.financiador}<br>
                    <b>Origen del proyecto:</b>&nbsp;${detalle.origendelproyectoproyecto}<br>
                    <b>Gestion:</b>&nbsp;${detalle.gestion}<br>
                    <b>Estado:</b>&nbsp;${detalle.estado}<br>
                    <b>Funcion:</b>&nbsp;${detalle.funcion}<br>
                    <b>Modalidad:</b>&nbsp;${detalle.modalidad}<br>
                    <b>Duracion de proyecto:</b>&nbsp;${detalle.duracionproyecto}<br>
                    <b>Tipo de proyecto:</b>&nbsp;${detalle.tipodeproyecto}<br>
                    <b>¿Es un representante sindical?</b>&nbsp;${detalle.representaciondirigencial ? 'SI' : 'NO'}<br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                <form name="proyectodocente${detalle.id_personas_proyecto}" action="<c:url value="./FileDownload.fautapo"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.documento}' /> 
                    <input type="hidden" name="directorio" value='proyectodocente' /> 
                    <button class="btn btn-primary" type="submit"><i class="fas fa-download"></i></button>
                </form>
                </p>
            </div>
        </li>   
    </c:forEach> 
</ul>