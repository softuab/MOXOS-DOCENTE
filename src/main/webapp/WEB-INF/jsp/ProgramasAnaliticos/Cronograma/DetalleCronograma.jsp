<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="row mb-3">
    <div class="col">
        <button type="button" data-id="${id_dct_programa_analitico}" onclick="agregarFormularioCronograma(this);"
                class="btn btn-success"><i class="fa fa-plus"></i> Registrar cronograma
        </button>
    </div>
</div>
<div class="row mb-3">
    <div class="col">
        <table class="table">
            <thead>
            <tr>
                <th>DÍA Y FECHA</th>
                <th>TIPO DE CLASE</th>
                <th>TÍTULO DE LA CLASE (ASUNTO)</th>
                <th>TIEMPO A EMPLEAR</th>
                <th>OBSERVACIONES Y CUMPLIMIENTO</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cronograma}">
                <tr id="li${item.id_prg_a_cronograma}" class="item">
                    <td><fmt:formatDate value="${item.fecha}" pattern="E dd/MM/yyyy"/></td>
                    <td>${item.tipo_de_clase}</td>
                    <td>${item.titulo_de_clase}</td>
                    <td>${item.tiempo_a_emplear}</td>
                    <td>${item.observaciones}</td>
                    <td>
                        <button data-id="${item.id_prg_a_cronograma}" onclick="editarFormularioCronograma(this)" type="button" class="btn btn-warning rounded-0 text-black"><i class="fa fa-edit"></i></button>
                        <button data-id="${item.id_prg_a_cronograma}"
                                data-parent="li${item.id_prg_a_cronograma}"
                                data-url="<c:url value="/programaanaanalitico/cronograma/eliminar"></c:url>"
                                onclick="eliminarElementoCronograma(this)" type="button" class="btn btn-danger rounded-0 text-black"><i class="fa fa-trash"></i></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>