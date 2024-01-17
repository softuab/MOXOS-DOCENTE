<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="row mb-3">
    <div class="col">
        <button type="button" data-id="${id_dct_programa_analitico}" onclick="agregarFormularioBibliografia(this);" class="btn btn-success"><i class="fa fa-plus" ></i> Registrar referencia bibliografica</button>
    </div>
</div>
<div class="row">
    <div class="col">
        <c:forEach var="item" items="${bibliografias}" varStatus="contador">
            <c:choose>
                <c:when test = "${item.tipo_referncia == 1}">
                    <div id="li${item.id_prg_a_bibliografia}" class="d-flex align-items-center  item">
                        <div class="flex-shrink-0"><h1 data-nro="1">${contador.count}</h1></div>
                        <div class="flex-grow-1 ms-3">
                                ${item.autor}, (${item.anio}),<strong> ${item.titulo}</strong>. (${item.edicion} ed.), ${item.lugar}, Editorial ${item.editorial} <strong>Ubicacion:</strong> ${item.ubicacion}
                            <button data-id="${item.id_prg_a_bibliografia}" onclick="editarFormularioBibliografia(this)" type="button" class="btn btn-default rounded-0 text-black"><i class="fa fa-edit"></i></button>
                                    <button data-id="${item.id_prg_a_bibliografia}" data-parent="li${item.id_prg_a_bibliografia}" data-url="<c:url value="/programaanaanalitico/bibliografias/eliminar"></c:url>" onclick="eliminarElementoBibliografia(this)" type="button" class="btn btn-default rounded-0 text-black"><i class="fa fa-trash"></i></button>
                        </div>
                    </div>
                </c:when>
                <c:when test = "${item.tipo_referncia == 2}">
                    <div id="li${item.id_prg_a_bibliografia}" class="d-flex align-items-center  item">
                        <div class="flex-shrink-0"><h1 data-nro="1">${contador.count}</h1></div>
                        <div class="flex-grow-1 ms-3">
                                ${item.autor}, (<fmt:formatDate pattern="yyyy" value="${item.fecha_publicacion}"/>), ${item.titulo}, ${item.titulo_documento}, ${item.volumen}(${item.numero}), ${item.paginas} <strong>Ubicacion:</strong> ${item.ubicacion}
                            <button data-id="${item.id_prg_a_bibliografia}" onclick="editarFormularioBibliografia(this)" type="button" class="btn btn-default rounded-0 text-black"><i class="fa fa-edit"></i></button>
                                    <button data-id="${item.id_prg_a_bibliografia}"  data-parent="li${item.id_prg_a_bibliografia}" data-url="<c:url value="/programaanaanalitico/bibliografias/eliminar"></c:url>" onclick="eliminarElementoBibliografia(this)" type="button" class="btn btn-default rounded-0 text-black"><i class="fa fa-trash"></i></button>
                        </div>
                    </div>
                </c:when>
                <c:when test = "${item.tipo_referncia == 3}">
                    <div id="li${item.id_prg_a_bibliografia}" class="d-flex align-items-center  item">
                        <div class="flex-shrink-0"><h1 data-nro="1">${contador.count}</h1></div>
                        <div class="flex-grow-1 ms-3">
                                ${item.autor}, (<fmt:formatDate pattern="yyyy" value="${item.fecha_publicacion}"/>), ${item.titulo_documento}. Recuperado el <fmt:formatDate pattern="dd" value="${item.fecha_consulta}"/> de  <fmt:formatDate pattern="MMMM" value="${item.fecha_consulta}"/>
                                  de  <fmt:formatDate pattern="yyyy" value="${item.fecha_consulta}"/>, de <a href="${item.url}" target="_blank">${item.url}</a> <strong>Ubicacion:</strong> ${item.ubicacion}
                            <button data-id="${item.id_prg_a_bibliografia}" onclick="editarFormularioBibliografia(this)" type="button" class="btn btn-default rounded-0 text-black"><i class="fa fa-edit"></i></button>
                            <button data-id="${item.id_prg_a_bibliografia}" data-parent="li${item.id_prg_a_bibliografia}" data-url="<c:url value="/programaanaanalitico/bibliografias/eliminar"></c:url>" onclick="eliminarElementoBibliografia(this)" type="button" class="btn btn-default rounded-0 text-black"><i class="fa fa-trash"></i></button>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>
</div>
