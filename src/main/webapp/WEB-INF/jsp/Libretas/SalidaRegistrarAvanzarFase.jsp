<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
<body>
<main class="app-content3">

    <div class="app-title">
        <div>
            <h1><i class="far fa-address-card"></i>&nbsp; ${nombres}</h1>
            <p>${parametros.sigla} - ${parametros.materia} GRUPO ${parametros.grupo} </p>
            <p><c:out value="${parametros.programa}"/> </p>
            <p>Gestion: <c:out value="${parametros.gestion}"/> Periodo: <c:out value="${parametros.periodo}"/>  </p>
        </div>
        <ul class="app-breadcrumb breadcrumb">
            <li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
            <li class="breadcrumb-item">${parametros.fase}</li>
        </ul>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="tile">
                <div class="row">
                    <div class="col">
                        <div class="container">
                            <c:if test="${mensaje != null}">
                                <div class="row">
                                    <div class="col-sm-6 mx-auto">
                                        <div class="card border-secondary mb-3">
                                            <div class="card-header"><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;ATENCION
                                                !!
                                            </div>
                                            <div class="card-body text-secondary">
                                                <c:out value="${mensaje}"/><br/>
                                            </div>
                                            <div class="card-footer text-center">
                                                <div>
                                                    <form name="formretorno" method="post" cssClass="unlock-form"
                                                          action="<c:url value="/libretas/retornorListarAsignaciones" />">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                        <input type="hidden" name="gestion" value="${parametros.gestion}">
                                                        <input type="hidden" name="periodo" value="${parametros.periodo}">
                                                    </form>
                                                    <a class="btn btn-primary" href="javascript:enviarsolicitud(document.formretorno);"><< Volver</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
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
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script src="<c:url value="/static/js/loader.js?v=3" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), {
        textAction: 'Enviando...',
        UrlImage: '<c:url value="/static/image/logominiatura.png" />'
    });

    function enviarsolicitud(form) {
        loadercontent.show();
        form.submit();
    }
</script>
</body>
</html>
