<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<ul class="list-unstyled" id="listcursos">
    <c:forEach var="detalle" items="${detalles}"> 
        <li class="media">
            <img src="<c:url value="/imagenes/Document.png" />" height="50px" width="50px" class="align-self-center mr-3 rounded-circle" onclick="vistapreviadocumentos(this)" data-id="${detalle.id_cursos_realizados}" data-tabla="cursosrealizados" onMouseOver="this.style.cursor='pointer'">
            <div class="media-body">
                <h5 class="mt-0 mb-1">${detalle.detalle}</h5>
                <p>
                    <b>Tipo de asistencia:</b>&nbsp;${detalle.tipoorganizacion}<br>
                    <b>Tipo de Evento:</b>&nbsp;${detalle.tipo_eventos}<br>
                    <b>Universidad/Institucion:</b>&nbsp;${detalle.expedido_institucion}<br>
                    <b>Numero Titulo:</b>&nbsp;${detalle.nrotitulo}<br>
                    <b>Horas Academicas:</b>&nbsp;${detalle.horas_academicas}<br>
                    <b>Fecha Emision:</b>&nbsp;<fmt:formatDate pattern = "dd/MM/yyyy" value = "${detalle.fechapresentacion}" /><br>
                    <b>Duracion:</b>&nbsp;${detalle.duracion}<br>
                    <b>Objetivo del curso:</b>&nbsp;${detalle.objetivo_curso}<br>
                    <c:if test="${detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-success">Aprobado</span><br>
                    </c:if>
                    <c:if test="${!detalle.aprobado}">
                        <b>El documento se encuentro?</b>&nbsp;<span class="badge badge-danger">Sin aprobar</span><br>
                    </c:if>
                </p>
                <form name="formacion${detalle.id_cursos_realizados}" action="<c:url value="./FileDownload.fautapo"/>" method="GET">
                    <input type="hidden" name="name" value='${detalle.url_cursos}' /> 
                    <input type="hidden" name="directorio" value='cursos' /> 
                    <button class="btn btn-primary" type="submit"><i class="fas fa-download"></i></button>
                </form>
            </div>
        </li>   
    </c:forEach> 
</ul>