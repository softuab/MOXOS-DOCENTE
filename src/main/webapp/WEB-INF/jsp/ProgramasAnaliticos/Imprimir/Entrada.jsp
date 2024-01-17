<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <title>Sistema - Moxos</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<main id="main" class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
            <h5> PROGRAMA ANALITICO DE ${model.materia} GRUPO ${moel.grupo} ${model.periodo}/${model.gestion} </h5>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="container">
                    <div class="row">
                        <div class="col">
                                    <span class="d-block p-2 bg-secondary text-white"><i class="fa fa-address-card"></i>
                                        Imprimir caratula de Programa Analitico
                                    </span>
                            <form method='get' action="<c:url value='/programaanaanalitico/impresion/caratula'/>">
                                <input type=hidden name="id"
                                       value="<c:out value="${model.id_dct_programa_analitico}"/>">
                                <button type="submit" class="btn btn-primary btn-lg btn-block"><i
                                        class="fa fa-print"></i> Imprimir caratula
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                    <span class="d-block p-2 bg-secondary text-white"><i class="fa fa-address-card"></i>
                                        Imprimir documento Programa Analitico
                                    </span>
                            <form method='get' target="_blank" id="imprimirContenido"
                                  action="<c:url value='/programaanaanalitico/impresion/vistaprevia'/>">
                                <input type=hidden name="id"
                                       value="<c:out value="${model.id_dct_programa_analitico}"/>">
                                <input type=hidden name="id_tipo_grado" value="<c:out value="${model.id_tipo_grado}"/>">
                                <input type=hidden name="id_programa" value="<c:out value="${model.id_programa}"/>">
                                <button type="submit"
                                        class="btn btn-primary btn-lg btn-block"><i class="fa fa-print"></i> Imprimir
                                    programa analitico
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="loader"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
</body>
</html>

