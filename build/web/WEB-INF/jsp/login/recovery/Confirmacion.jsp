<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="imagetoolbar" content="no">
        <META HTTP-EQUIV="Cache-Control" CONTENT="max-age='0'" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <META http-equiv="expires" content="0" />
        <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT" />
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
        <title>Sistema Integrado - Moxos</title>
        <link rel="stylesheet" href="<c:url value='/Public/Css/bootstrap.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/Public/Css/FontAwesome/css/fontawesome-all.css'/>">
        <link rel="stylesheet" href="<c:url value='/Public/Css/signin.css'/>" type="text/css">
        <link rel="stylesheet" type="text/css" href="<c:url value='/Public/Css/Card.css'/>"> 
    </head>
    <body>
        <div class="container">
            <br>
            <div class="Card-Informacion">
                <div class="card border-secondary mb-3" style="max-width: 18rem;">
                    <div class="card-header"><i class="fas fa-info-circle"></i>&nbsp;&nbsp;AVISO</div>
                    <div class="card-body text-secondary">  
                        <p class="card-text"> 
                            <c:out value="${mensaje}"/><br/>
                        </p>
                        <div class="text-center"><a href="." class="btn btn-primary"> Aceptar </a></div>
                    </div>
                </div>
            </div>
        </div> 
        <script src="<c:url value='/Public/Js/jquery/jquery-3.3.1.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/jquery/popper.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/bootstrap.min.js'/>"></script>
    </body>
</html>