<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="row">
    <div class="col text-center">
        <h4>FORMAS DE ORGANIZACIÓN</h4>
    </div>
</div>
<div class="row">
    <div class="col">
        <table class="table">
            <thead class="text-center">
            <tr>
                <th rowspan="2">Nro</th>
                <th rowspan="2">Nombre de la Unidad</th>
                <c:if test="${formasorganizacion.formasdistribucion.size() > 0}">
                    <th colspan="${formasorganizacion.formasdistribucion.size()}">
                        Tipos de clases a utilizar en la <br> asignatura
                    </th>
                </c:if>
                <th rowspan="2">Total<br>de horas</th>
            </tr>
            <tr>
                <c:if test="${formasorganizacion.formasdistribucion.size() > 0}">
                    <c:forEach var="item" items="${formasorganizacion.formasdistribucion}" varStatus="contador">
                        <th>${item.sigla_formas}</th>
                    </c:forEach>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:set var="suma" value="0"/>
            <c:set var="total" value="0"/>
            <c:set var="hora" value="-1"/>
            <c:set var="id_prg_a_distribucion" value="-1"/>
            <c:forEach var="item" items="${formasorganizacion.distribucion}" varStatus="contador">
                <c:set var="suma" value="0"/>
                <tr>
                    <td>
                        <button data-contenidoid="${item.id_prg_a_contenido}"
                                data-programaanaiticoid="${formasorganizacion.id_dct_programa_analitico}"
                                data-id="${contador.count}"
                                data-totalhoras="${formasorganizacion.totalHoras}"
                                onclick="agregarFormasDeOrganizacion(this)"
                                type="button" class="btn btn-primary"><i class="fa fa-plus"></i></button>
                    </td>
                    <td>${item.contenido}</td>
                    <c:if test="${formasorganizacion.formasdistribucion.size() > 0}">
                        <c:forEach var="forma" items="${formasorganizacion.formasdistribucion}" varStatus="contador">
                            <c:forEach var="formadistribucion" items="${item.distribuciones}">
                                <c:if test="${formadistribucion.id_prg_a_formas==forma.id_prg_a_formas}">
                                    <c:set var="hora" value="${formadistribucion.horas}"/>
                                    <c:set var="id_prg_a_distribucion"
                                           value="${formadistribucion.id_prg_a_distribucion}"/>
                                    <c:set var="suma" value="${suma+formadistribucion.horas}"/>
                                    <c:set var="total" value="${total+formadistribucion.horas}"/>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${hora == -1}">
                                    <td class="text-center">0</td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-center">
                                        <button data-url="<c:url value="/programaanaanalitico/formasorganizacion/eliminar"></c:url>"
                                                data-option="Eliminar|Editar" data-text="Eliminar|Editar"
                                                data-icons="fa fa-trash|fa fa-pencil" type="button"
                                                data-id="${id_prg_a_distribucion}"
                                                data-idpgranalitico="${formasorganizacion.id_dct_programa_analitico}"
                                                data-hora="${hora}"
                                                data-horaAnterior="${hora}"
                                                data-totalhoras="${formasorganizacion.totalHoras}"
                                                onclick="editarFormasDeOrganizacion(this)"
                                                class="btn btn-default rounded text-black organizacion">${hora}</button>
                                    </td>
                                    <c:set var="hora" value="-1"/>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <td class="text-center"><c:out value="${suma}"/></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="${formasorganizacion.formasdistribucion.size()+2}">TOTAL</td>
                <td class="text-center">${total}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
