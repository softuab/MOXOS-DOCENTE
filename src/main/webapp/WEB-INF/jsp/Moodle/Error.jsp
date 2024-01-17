<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
<main class="app-content3">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-address-card"></i>&nbsp; ${nombres}</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 mx-auto">
                        <div class="card border-secondary mb-3">
                            <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;AVISO
                                !!
                            </div>
                            <div class="card-body text-secondary">
                                <p class="card-text"> ${mensaje} </p>
                            </div>
                            <div class="card-footer text-muted text-center">
                                <form id="retornar" method="post"
                                      action="<c:url value="/regresarListarAsignacionesAdministrarCursos" />">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="gestion" value="${parametros.gestion}">
                                    <input type="hidden" name="periodo" value="${parametros.periodo}">
                                    <button class="btn btn-primary btn-sm" type="submit"><i
                                            class="fa fa-home fa-lg"></i> Retornar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="loader"></div>
    <script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/public/js/popper.min.js" />"></script>
    <script src="<c:url value="/static/js/loader.js?v=3" />"></script>
    <script src="<c:url value="/static/js/formload.js" />"></script>
    <script>
        let formretorno = new Form(document.getElementById('retornar'), {
            target: document.getElementById('loader'),
            text: 'Enviando..',
            url: '<c:url value="/static/image/logominiatura.png" />'
        });
    </script>
</main>
</body>
</html>