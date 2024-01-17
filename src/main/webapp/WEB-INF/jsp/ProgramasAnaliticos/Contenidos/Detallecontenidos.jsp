<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="row mb-3">
    <div class="col">
        <a class="btn btn-success"
           href="<c:url value="/programaanaanalitico/contenidos/agregar"><c:param name = "id_dct_programa_analitico" value = "${id_dct_programa_analitico}"/><c:param name = "gestion" value = "${gestion}"/><c:param name = "periodo" value = "${periodo}"/><c:param name = "grupo" value = "${grupo}"/><c:param name = "materia" value = "${materia}"/></c:url> ">Registrar
            contenidos</a>
    </div>
</div>
<div class="row">
    <div class="col">
        <span class="d-block p-2 bg-primary text-white"><i class="fa fa-address-card"></i> contenido </span>
        <c:forEach var="contenidos" items="${contenidos}" varStatus="contador">
            <div id="li${contenidos.id_prg_a_contenido}" class="card item">
                <div class="card-header">
                    <div class="d-flex">
                        <div class="flex-shrink-0">
                        </div>
                        <div class="flex-grow-1 ms-3">
                            UNIDAD TEMATICA ${contador.count}: ${contenidos.contenido}
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <h6 class="mt-0 mb-1 bold">OBJETIVOS INSTRUCTIVOS</h6>
                        ${contenidos.objetivo_instructivo}
                    <h6 class="mt-0 mb-1 bold">SISTEMA DE CONOCIMIENTOS</h6>
                        ${contenidos.conocimientos}
                    <h6 class="mt-0 mb-1 bold">SISTEMA DE HABILIDADES</h6>
                        ${contenidos.habilidades}
                    <h6 class="mt-0 mb-1 bold">SISTEMA DE VALORES</h6>
                        ${contenidos.valores}
                </div>
                <div class="card-footer text-muted text-end">
                    <a class="btn btn-primary"
                       href="<c:url value="/programaanaanalitico/contenidos/editar"><c:param name = "id_prg_a_contenido" value = "${contenidos.id_prg_a_contenido}"/><c:param name = "gestion" value = "${gestion}"/><c:param name = "periodo" value = "${periodo}"/><c:param name = "grupo" value = "${grupo}"/><c:param name = "materia" value = "${materia}"/></c:url>"><i
                            class="fa fa-edit"></i></a>
                    <button type="button" class="btn btn-primary"
                            data-idpgrprograma="${id_dct_programa_analitico}"
                            data-unidad="${contenidos.contenido}"
                            data-url="<c:url value="/programaanaanalitico/contenidos/eliminar"/>"
                            data-delete="${contenidos.id_prg_a_contenido}"
                            data-parent="li${contenidos.id_prg_a_contenido}" onclick="eliminarElemento(this)">
                        <i class="fa fa-trash"></i>
                    </button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>