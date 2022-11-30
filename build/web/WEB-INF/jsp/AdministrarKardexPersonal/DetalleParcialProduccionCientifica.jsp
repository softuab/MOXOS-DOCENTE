<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<ul class="list-unstyled" id="listcursos">
    <c:forEach var="detalle" items="${detalles}"> 
        <li class="media">
            <img src="<c:url value="/imagenes/Document.png" />"  height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)" data-id="${detalle.id_produccion_cientifica}" data-tabla="produccioncientifica" onMouseOver="this.style.cursor='pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.nombre_producto}</h5>
                <p>
                    <b>Categoria:</b>&nbsp;${detalle.categoria}<br>
                    <b>Fecha de certificacion:</b>&nbsp;<fmt:formatDate pattern = "dd/MM/yyyy" value = "${detalle.fecha_certificacion}" /><br>
                    <b>Tipo de produccion:</b>&nbsp;${detalle.tipo_produccion}<br>
                    <b>Publicado:</b>&nbsp;${detalle.publicado ? 'SI' : 'NO'}<br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                <form name="formacion${detalle.id_produccion_cientifica}" action="<c:url value="./FileDownload.fautapo"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.url_portada_libro}' /> 
                    <input type="hidden" name="directorio" value='produccion' /> 
                    <button class="btn btn-primary" type="submit"><i class="fas fa-download"></i></button>
                </form>
                </p>
            </div>
        </li>   
    </c:forEach> 
</ul>