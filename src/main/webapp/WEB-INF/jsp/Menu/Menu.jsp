<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sistema - Moxos</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="theme-color" content="#305496">
    <link rel="icon" type="image/jpg" href="<c:url value="/public/image/logo.ico" />"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl">
<header class="app-header"><a class="app-header__logo" href=".">moxos</a>
    <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
    <ul class="app-nav">
        <li class="dropdown">
            <a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Show notifications">
                <span id="status" class="text-success"><i class="fa fa-circle" aria-hidden="true"></i></span>
            </a>
        </li>
        <li class="dropdown">
            <a class="app-nav__item" href="#" data-bs-toggle="dropdown" aria-expanded="false"
               aria-label="Show notifications">
                <i class="fa fa-bell-o fa-lg"></i>
            </a>
            <ul class="app-notification dropdown-menu dropdown-menu-right">
                <li class="app-notification__title">Usted tiene ${cantidadnotificacion} </li>
                <div class="app-notification__content">
                    <c:forEach var="notificacion" items="${notificaciones}" varStatus="contador">
                        <li>
                            <a class="app-notification__item" href="${notificacion.url}" target="_blank"><span
                                    class="app-notification__icon">
                                            <span class="fa-stack fa-lg"><i
                                                    class="fa fa-circle fa-stack-2x text-primary"></i><i
                                                    class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
                                <div>
                                    <p class="app-notification__message">${notificacion.contenido}</p>
                                    <p class="app-notification__message">${notificacion.departamento}</p>
                                    <p class="app-notification__meta"><fmt:formatDate
                                            value="${notificacion.fecha_publicacion}" pattern="dd, MMMM yyyy"/></p>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </div>
                <li class="app-notification__footer"><a href="http://uabjb.edu.bo/comunicados">Ver todas las
                    Notificaciones.</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="app-nav__item" href="#" data-bs-toggle="dropdown" aria-expanded="false"
               aria-label="Open Profile Menu"><i
                    class="fa fa-user fa-lg"></i></a>
            <ul class="dropdown-menu settings-menu dropdown-menu-right">
                <li><a class="dropdown-item" href="#" onclick="IrPerfil()"><i class="fa fa-user fa-lg"></i> Perfil</a>
                </li>
                <li>
                    <form id="logout" action="<c:url value="/logout" />" method="post"><input type="hidden"
                                                                                              name="${_csrf.parameterName}"
                                                                                              value="${_csrf.token}"/>
                    </form>
                    <a class="dropdown-item" href="javascript:document.getElementById('logout').submit();"
                       target="_top"><i class="fa fa-sign-out" aria-hidden="true"></i> Salir</a></li>
            </ul>
        </li>
    </ul>
</header>
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user"><img id="idimagenpersona" class="app-sidebar__user-avatar rounded-circle"
                                        src="${simagen}" alt="Docente">
        <div>
            <p class="app-sidebar__user-name" style="">${snombre}</p>
            <p class="app-sidebar__user-designation">Docente</p>
        </div>
    </div>
    <ul class="app-menu">
        <li>
            <a class="app-menu__item active" href=".">
                <i class="app-menu__icon fa fa-dashboard"></i><span class="app-menu__label">Administracion</span>
            </a>
        </li>
        ${fil}
    </ul>
</aside>
<main class="app-content2">
    <div class="frame">
        <iframe id="marco" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen>
            Contenido de IFRAME
        </iframe>
    </div>
    <div id="toast"></div>
</main>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/public/js/main.js" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/menu.js?v=2" />"></script>
</body>
</html>
