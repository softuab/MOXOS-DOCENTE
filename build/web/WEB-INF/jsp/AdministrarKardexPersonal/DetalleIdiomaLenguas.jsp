<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<ul class="list-unstyled" id="listidioma">
    <c:forEach var="detalle" items="${idiomas}"> 
        <li class="media">
            <img src="<c:url value="/imagenes/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle"  onclick="vistapreviadocumentos(this)" data-id="${detalle.id_idioma}" data-tabla="idioma" onMouseOver="this.style.cursor = 'pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.descripcion_idioma}</h5>
                <p>
                    <b>Lengua:</b>&nbsp;${detalle.tipo_idioma}<br>
                    <b>Lee el idioma?</b>&nbsp;${detalle.lee ? 'SI' : 'NO'}<br>
                    <b>Escribe el idioma?</b>&nbsp;${detalle.escribe ? 'SI' : 'NO'}<br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                <form name="forma" class="unlock-form" action="<c:url value="/FileDownload.fautapo"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.url_idioma}' /> 
                    <input type="hidden" name="directorio" value='idiomas' /> 
                    <button class="btn btn-primary" type="submit"><i class="fas fa-download"></i></button>
                </form>
                </p>
            </div>
        </li>   
    </c:forEach> 
</ul>