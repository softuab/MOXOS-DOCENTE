<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <META HTTP-EQUIV="Cache-Control" CONTENT="max-age='0'" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <META http-equiv="expires" content="0" />
        <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT" />
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        <title>Sistema Integrado -  Moxos</title>
        <link rel="stylesheet" href="<c:url value='/Public/Css/main.css'/>" type="text/css">
        <link rel="stylesheet" href="<c:url value='/Public/Css/FontAwesome/css/fontawesome-all.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/Public/Css/TableResponsive.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/Public/Css/CardProfile.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/Public/Css/Card.css'/>">
    </head>
    <body class="app sidebar-mini rtl"> 
        <main class="app-content3">
            <div class="app-title">
                <div>
                    <h1><i class="far fa-address-card"></i>&nbsp; ${nombres}</h1>
                    <p>Lista de materias para elaborar programa analitico ${periodo}/${gestion} </p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <form name='formaEvaluaciones' method='post' action="${pageContext.request.contextPath}/InicioElaboracionProgramaAnalitico.fautapo">
                                        <input type=hidden name="id_asignacion"      value="<c:out value="${model.id_asignacion}"/>"> 
                                        <input type=hidden name="id_materia"         value="<c:out value="${model.id_materia}"/>"> 
                                        <input type=hidden name="materia"            value="<c:out value="${model.materia}"/>">
                                        <input type=hidden name="id_grupo"           value="<c:out value="${model.id_grupo}"/>">
                                        <input type=hidden name="grupo"              value="<c:out value="${model.grupo}"/>">    
                                        <input type=hidden name="id_programa"        value="<c:out value="${model.id_programa}"/>">
                                        <input type=hidden name="id_fase"            value="<c:out value="${model.id_fase}"/>">
                                        <input type=hidden name="id_tipo_evaluacion" value="<c:out value="${model.id_tipo_evaluacion}"/>">
                                        <input type=hidden name="tipo_evaluacion"    value="<c:out value="${model.tipo_evaluacion}"/>">
                                        <input type=hidden name="gestion"            value="<c:out value="${model.gestion}"/>">
                                        <input type=hidden name="periodo"            value="<c:out value="${model.periodo}"/>">
                                        <input type=hidden name="id_docente"         value="<c:out value="${model.id_docente}"/>">
                                        <input type=hidden name="id_plan"   value="<c:out value="${model.id_plan}"/>">
                                        <input type=hidden name="id_departamento"    value="<c:out value="${model.id_departamento}"/>">
                                        <input type=hidden name="id_mencion"    value="<c:out value="${model.id_mencion}"/>">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-success">
                                            <i class="fas fa-plus"></i> Crear nuevo programa analitico
                                        </button>
                                    </form>
                                </div>
                            </div> 
                            <hr>
                            <form:form cssClass="row" method="post" modelAttribute="model" action="${pageContext.request.contextPath}/ImportarProgramaAnalitico.fautapo" >
                                <form:hidden path="id_asignacion"/>
                                <form:hidden path="id_materia"/>
                                <form:hidden path="materia"/>
                                <form:hidden path="id_grupo"/>
                                <form:hidden path="grupo"/>
                                <form:hidden path="id_programa"/>
                                <form:hidden path="id_fase"/>
                                <form:hidden path="id_tipo_evaluacion"/>
                                <form:hidden path="tipo_evaluacion"/>
                                <form:hidden path="gestion"/>
                                <form:hidden path="periodo"/>
                                <form:hidden path="id_docente"/>
                                <form:hidden path="id_plan"/>
                                <form:hidden path="id_departamento"/>
                                <form:hidden path="id_mencion"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group col-8"> 
                                    <form:select cssClass="form-control"  path="id_dct_programa_analitico" items="${listarpgranalitico}" itemLabel="value" itemValue="id"/>
                                    <small class="form-text text-muted" id="id_dct_programa_analiticoHelp">Seleccionar el programa analitico a importar.</small>
                                </div>
                                <div class="form-group col-4">
                                    <button class="btn btn-primary btn-block" type="button"><i class="fa fa-fw fa-lg fa-check-circle"></i>Importar Programa Analitico</button>
                                </div>
                            </form:form> 
                        </div>
                    </div>
                </div>
            </div>
        </main> 
        <script src="<c:url value='/Public/Js/jquery/jquery-3.3.1.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/jquery/popper.min.js'/>" ></script>
        <script src="<c:url value='/Public/Js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/Public/Js/main.js'/>"></script>
        <script src="<c:url value='/Public/Js/plugins/pace.min.js'/>"></script>
    </body>
</html>