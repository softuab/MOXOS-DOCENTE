<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Programa Analitico</title>
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
    <style>
        @page {
            size: letter;
            margin: 2cm 2.5cm 2cm 2cm;
            @bottom-center {
                content: 'Página ' counter(page);
            }
            @top-center {
                content: element(header);
                padding-top: 3cm; /* Ajusta este valor según el espacio deseado en cada página */
            }
        }

        .button-58 {
            align-items: center;
            background-color: #06f;
            border: 2px solid #06f;
            box-sizing: border-box;
            color: #fff;
            cursor: pointer;
            display: inline-flex;
            fill: #000;
            font-family: Inter, sans-serif;
            font-size: 16px;
            font-weight: 600;
            height: 48px;
            justify-content: center;
            letter-spacing: -.8px;
            line-height: 24px;
            min-width: 140px;
            outline: 0;
            padding: 0 17px;
            text-align: center;
            text-decoration: none;
            transition: all .3s;
            user-select: none;
            -webkit-user-select: none;
            touch-action: manipulation;
        }

        .button-58:focus {
            color: #171e29;
        }

        .button-58:hover {
            background-color: #3385ff;
            border-color: #3385ff;
            fill: #06f;
        }

        .button-58:active {
            background-color: #3385ff;
            border-color: #3385ff;
            fill: #06f;
        }

        @media (min-width: 768px) {
            .button-58 {
                min-width: 170px;
            }
        }

        .page {
            width: 8.5in;
            height: 11in;
            margin: 0 auto;
        }

        .imprimir {
            width: 100%;
            padding: 50px;
            text-align: center;
        }

        body {
            font-family: 'Arial', serif;
            font-size: 12pt;
            line-height: 1.5;
            margin: 0;
            padding: 0;
            text-align-all: justify;
        }

        header {
            display: grid;
            gap: 10px;
        }

        .contenedor {
            width: 100%;
            display: grid;
            grid-template-columns: 1fr 5fr 1fr; /* Divide el contenedor en tres fracciones iguales */
        }

        .firmas {
            width: 100%;
            padding-top: 100px;
            text-align: center;
            display: grid;
            grid-template-columns: 1fr 1fr;
        }

        footer {
            display: none;
            background-color: lightgray;
            color: black;
            padding: 10px;
            text-align: center;
            font-size: 14px;
        }


        .content {
            min-height: 8in;
            margin-top: 0.5in;
            margin-bottom: 0.5in;
        }


        .content h1 {
            font-size: 12pt;
            line-height: 150%;
            font-family: 'Arial', 'SansSerif';
        }

        .content li {
            text-align: justify;
            font-size: 12pt;
        }

        .content p {
            font-family: 'Arial', serif;
            font-size: 12pt;
            margin: 0;
            line-height: 150%;
            text-align: justify;
            padding: 0 0 10px;
        }

        .content table {
            width: 90%;
        }

        .content span {
            font-family: 'Arial', serif;
            margin: 0;
            padding: 0;
            line-height: 150%;
            text-align: justify;
            padding-bottom: 10px;
        }

        .lista {
            list-style: decimal;
        }

        .numero {
            counter-reset: numero;
        }

        .numero::before {
            counter-increment: numero;
            content: counter(numero);
        }

        .numero::before {
            content: "1";
        }

        .title-materias {
            text-align: center;
            background: #D9D9D9;
            font-size: 10pt;
            height: 50px;
            font-weight: bold;
        }

        .body-materias {
            font-size: 10pt;
            font-family: Arial, SansSerif;
        }

        .body-materias td {
            border: 1px solid black;
            padding: 5px;
        }

        .text-center {
            text-align: center;
        }

        .text-start {
            text-align: left;
        }

        .title-materias td {
            border: 1px solid black;
            padding: 5px;
        }

        .texto-vertical {
            writing-mode: horizontal-tb;
            transform: rotate(90deg);
        }

        @media print {
            body {
                font-family: 'Arial', serif;
                font-size: 18pt;
                line-height: 1.5;
                margin: 0;
                padding: 0;
                text-align-all: justify;
            }

            .content {
                position: running(header);
                margin-right: 0pt;
                margin-left: 0pt;
            }

            .page {
                margin: 0 auto;
                padding: 0;
                page-break-after: always;
            }

            .content h1 {
                font-size: 18pt;
                line-height: 150%;
                font-family: 'Arial', 'SansSerif';
            }

            .content li {
                text-align: justify;
                font-size: 18pt;
            }

            .content p {
                font-family: 'Arial', serif;
                font-size: 18pt;
                margin: 0;
                line-height: 150%;
                text-align: justify;
                padding: 0 0 10px;
            }

            .content table {
                font-family: 'Arial', serif;
                font-size: 18pt;
            }

            .content span {
                font-size: 18pt;
                font-family: 'Arial', serif;
                margin: 0;
                line-height: 150%;
                text-align: justify;
                padding: 0 0 10px;
            }

            .imprimir {
                display: none;
            }
        }
    </style>
</head>
<body>
<div class='page'>
    <header>
        <div class="contenedor">
            <div style="display: flex;flex-direction: column;justify-content: center;align-items: center;">
                <img style="width: 96px" src="${pageContext.request.contextPath}/static/image/logominiatura.png">
            </div>
            <div style="background: #fdfdfe; text-align: center; font-weight: bold; font-family: Arial, SansSerif; font-size: 16pt; line-height: 20px">
                <p>UNIVERSIDAD AUTONOMA DEL BENI<br>
                    “JOSE BALLIVIAN”<br>
                    VICERRECTORADO DE PREGRADO</p>
                <p>
                    PROGRAMA ANALÍTICO
                </p>
            </div>
            <div style="display: flex;flex-direction: column;justify-content: center;align-items: center;">
                <img style="width: 96px" src="${QR}">
            </div>
        </div>
    </header>
    <div id="content" class='content' contenteditable='true'>
        <h1>2. IDENTIFICACIÓN DE LA ASIGNATURA</h1>
        <div style="width: 100%">
            <strong>FACULTAD:</strong> ${facultad}<br>
            <strong>GESTIÓN ACADÉMICA:</strong> ${gestionacademica}<br>
            <strong>CARRERA:</strong> ${carrera}<br>
            <strong>ASIGNATURA:</strong> ${asignatura}<br>
        </div>
        <p style="padding-top: 25px;"><strong>DATOS DE LA MATERIA</strong></p>
        <div style="width: 100%; padding-top: 25px;">
            <table data-size="" style='border: 1px solid black; width: 100%; border-collapse: collapse;'>
                <tbody>
                <tr class="title-materias">
                    <td>Área</td>
                    <td>Sigla</td>
                    <td class="texto-vertical">Nivel</td>
                    <td class="texto-vertical">Ciclo</td>
                    <td>Sistema</td>
                    <td>Prerrequisitos</td>
                    <td>Clasificación<br> de la Asignatura</td>
                </tr>
                <tr class="body-materias">
                    <td>${areaambito}</td>
                    <td>${sigla}</td>
                    <td>${aniosemestre}</td>
                    <td class="text-center">${ciclo}</td>
                    <td>${sistema}</td>
                    <td>${preresquisito}</td>
                    <td>${clasificacion}</td>
                </tr>
                </tbody>
            </table>
            <table data-size="" style='border: 1px solid black; width: 100%; border-collapse: collapse;'>
                <tbody>
                <tr class="title-materias">
                    <td>Horas Acad.<br>Teóricas/Semana</td>
                    <td>Horas Acad.<br>Prácticas/Semana</td>
                    <td>Total Horas<br>Acad./Semana</td>
                    <td>Total Horas<br>Acad./Semestre</td>
                </tr>
                <tr class="body-materias">
                    <td class="text-center">${horasteorica}</td>
                    <td class="text-center">${horaspracticas}</td>
                    <td class="text-center">${totalhoraacademica}</td>
                    <td class="text-center">${totalhoraacademicasemestre}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <p style="padding-top: 25px;"><strong>DATOS DEL DOCENTE</strong></p>
        <div style='display: flex; justify-content: center; padding-top: 25px; padding-bottom: 25px;  align-items: center;'>
            <table data-size="" id="datosdocente"
                   style='border: 1px solid black; width: 80%; border-collapse: collapse;'>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; font-weight: bold; text-align: center'
                        colspan='2'>
                        DATOS DEL DOCENTE
                    </td>
                </tr>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; padding: 5px; width: 50%; font-family: Arial, SansSerif; font-size: 12pt; font-weight: bold'>
                        Nombres y Apellido:
                    </td>
                    <td style='border: 1px solid black; background: #FFFFFF; padding: 5px; width: 50%;'>${nombredocente}</td>
                </tr>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; padding: 5px; width: 50%; font-family: Arial, SansSerif; font-size: 12pt; font-weight: bold'>
                        Categoría y Tiempo de Dedicación:
                    </td>
                    <td style='border: 1px solid black; background: #FFFFFF; padding: 5px; width: 50%;'>${categoriadocente}</td>
                </tr>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; padding: 5px; width: 50%; font-family: Arial, SansSerif; font-size: 12pt; font-weight: bold'>
                        Modalidad de Ingreso:
                    </td>
                    <td style='border: 1px solid black; background: #FFFFFF; padding: 5px; width: 50%;'>${modalidaddeingreso}</td>
                </tr>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; padding: 5px; width: 50%; font-family: Arial, SansSerif; font-size: 12pt; font-weight: bold'>
                        Teléfono:
                    </td>
                    <td style='border: 1px solid black; background: #FFFFFF; padding: 5px; width: 50%;'>${telefono}</td>
                </tr>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; padding: 5px; width: 50%; font-family: Arial, SansSerif; font-size: 12pt; font-weight: bold'>
                        Correo electrónico:
                    </td>
                    <td style='border: 1px solid black; background: #FFFFFF; padding: 5px; width: 50%;'>${correoelectronico}</td>
                </tr>
                <tr>
                    <td style='border: 1px solid black; background: #D9D9D9; padding: 5px; width: 50%; font-family: Arial, SansSerif; font-size: 12pt; font-weight: bold'>
                        Exigencia del Docente que imparte la asignatura:
                    </td>
                    <td style='border: 1px solid black; background: #FFFFFF; padding: 5px; width: 50%;'>${observacion}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <h1>3. MARCO REFERENCIAL</h1>
        ${marcoreferencial}
        <h1>4. JUSTIFICACIÓN</h1>
        ${justificaciones}
        <h1>5. PROPÓSITOS</h1>
        ${propositos}
        <h1>6. SISTEMA DE OBJETIVOS</h1>
        <h1>6.1 OBJETIVO GENERAL DE LA ASIGNATURA</h1>
        ${sistemasobeticosgeneral}
        <h1>6.2 OBJETIVO EDUCATIVO</h1>
        ${sistemasobeticoseducativo}
        <h1>6.3 OBJETIVOS INSTRUCTIVOS</h1>
        ${sistemasobeticosinstructivo}
        <h1>7. CONTENIDOS</h1>
        <table id="contenidos" data-size="" style='border: 1px solid black; width: 100%; border-collapse: collapse;'>
            <tbody>
            <c:forEach var="item" items="${contenidosanaliticos}" varStatus="contador">
                <tr>
                    <td colspan="3"
                        style="border: 1px solid black; font-weight: bold; font-size: 12pt; padding: 10px; text-align: center">
                        UNIDAD TEMÁTICA ${contador.count}: ${item.contenido} </td>
                </tr>
                <tr>
                    <td style="border: 1px solid black; width: 33.33%; background: #D9D9D9; font-size: 12pt; padding: 5px; font-weight: bold;  text-align: center">
                        SISTEMA DE CONOCIMIENTOS
                    </td>
                    <td style="border: 1px solid black; width: 33.33%; background: #D9D9D9; font-size: 12pt; padding: 5px; font-weight: bold;  text-align: center">
                        SISTEMA DE HABILIDADES
                    </td>
                    <td style="border: 1px solid black; width: 33.33%; background: #D9D9D9; font-size: 12pt; padding: 5px; font-weight: bold;  text-align: center">
                        SISTEMA DE VALORES
                    </td>
                </tr>
                <tr>
                    <td style="border: 1px solid black;width: 33.33%; font-size: 12pt; padding: 5px;">${item.conocimientos}</td>
                    <td style="border: 1px solid black;width: 33.33%; font-size: 12pt; padding: 5px;">${item.habilidades}</td>
                    <td style="border: 1px solid black;width: 33.33%; font-size: 12pt; padding: 5px;">${item.valores}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h1>8. MODALIDAD Y METODOLOGÍA A UTILIZAR EN EL PEA</h1>
        ${metodologia}
        <h1>9. FORMAS DE ORGANIZACIÓN</h1>
        <p>Tipología de clases a utilizar dentro del aula:</p>
        <div style='display: flex; justify-content: center;  align-items: center;'>
            <table data-size="" id="formas" style='border: 1px solid black; width: 60%; border-collapse: collapse;'>
                <tr>
                    <td style="border: 1px solid black; font-weight: bold; font-size: 12pt; padding: 10px; text-align: center">
                        PRESENCIAL
                    </td>
                    <td style="border: 1px solid black; font-weight: bold; font-size: 12pt; padding: 10px; text-align: center">
                        VIRTUAL - DISTANCIA
                    </td>
                </tr>
                <tr>
                    <td style="border: 1px solid black; font-size: 12pt; padding: 10px; text-align: left">
                        <c:set var="aux" value=""/>
                        <c:forEach var="item" items="${precensial}">
                            <c:choose>
                                <c:when test="${aux!=item.grupo_forma}">
                                    <br>
                                    <strong>${item.grupo_forma}</strong><br>
                                    <span style="font-size: 12pt;">${item.sigla_formas} - ${item.formas}</span><br>
                                    <c:set var="aux" value="${item.grupo_forma}"/>
                                </c:when>
                                <c:otherwise>
                                    <span style="font-size: 12pt;">${item.sigla_formas} - ${item.formas}</span><br>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td style="border: 1px solid black; font-size: 12pt; padding: 10px; text-align: left">
                        <c:set var="aux" value=""/>
                        <c:forEach var="item" items="${virtual}">
                            <c:choose>
                                <c:when test="${aux!=item.grupo_forma}">
                                    <br>
                                    <strong>${item.grupo_forma}</strong><br>
                                    <span style="font-size: 12pt;">${item.sigla_formas} - ${item.formas}</span><br>
                                    <c:set var="aux" value="${item.grupo_forma}"/>
                                </c:when>
                                <c:otherwise>
                                    <span style="font-size: 12pt;">${item.sigla_formas} - ${item.formas}</span><br>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </div>
        <div style='display: flex; justify-content: center;  align-items: center; padding-top: 30px'>
            <table data-size="" id="formasorganizacion"
                   style='border: 1px solid black; width: 100%; border-collapse: collapse;'>
                <thead>
                <tr>
                    <th rowspan="2" style="border: 1px solid black;background: #D9D9D9; width: 50px;">Nro</th>
                    <th rowspan="2" style="border: 1px solid black;background: #D9D9D9; width: 30%;">Nombre de la
                        Unidad
                    </th>
                    <c:if test="${formasorganizacion.formasdistribucion.size() > 0}">
                        <th colspan="${formasorganizacion.formasdistribucion.size()}"
                            style="border: 1px solid black;background: #D9D9D9;">
                            Tipos de clases a utilizar en la <br> asignatura
                        </th>
                    </c:if>
                    <th rowspan="2" style="border: 1px solid black;background: #D9D9D9; width: 10%;">Total<br>de horas
                    </th>
                </tr>
                <tr>
                    <c:if test="${formasorganizacion.formasdistribucion.size() > 0}">
                        <c:forEach var="item" items="${formasorganizacion.formasdistribucion}" varStatus="contador">
                            <th style="border: 1px solid black;">${item.sigla_formas}</th>
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
                        <td style="border: 1px solid black; text-align: center; font-size: 12pt; padding: 5px; width: 50px;">
                                ${contador.count}
                        </td>
                        <td style="border: 1px solid black; text-align: left; font-size: 12pt; padding: 5px; width: 30%;">${item.contenido}</td>
                        <c:if test="${formasorganizacion.formasdistribucion.size() > 0}">
                            <c:forEach var="forma" items="${formasorganizacion.formasdistribucion}"
                                       varStatus="contador">
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
                                        <td style="border: 1px solid black; text-align: center; font-size: 12pt; padding: 5px;">
                                            0
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="border: 1px solid black; text-align: center; font-size: 12pt; padding: 5px;">${hora}</td>
                                        <c:set var="hora" value="-1"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                        <td style="border: 1px solid black; text-align: center; font-size: 12pt; padding: 5px;"><c:out
                                value="${suma}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="${formasorganizacion.formasdistribucion.size()+2}"
                        style="border: 1px solid black; background: #D9D9D9; font-weight: bold; text-align: left; font-size: 12pt; padding: 5px;">
                        TOTAL
                    </td>
                    <td style="border: 1px solid black; background: #D9D9D9; text-align: center; font-size: 12pt; padding: 5px; font-weight: bold;">${total}</td>
                </tr>
                </tbody>
            </table>
        </div>
        ${formasdeorganizacion}
        <h1>10. RECURSOS EDUCATIVOS Y TECNOLÓGICOS</h1>
        ${recursoseducativos}
        <h1>11. SISTEMA DE EVALUACIÓN</h1>
        <h1>11.1. COMPONENTES DEL SISTEMA DE EVALUACIÓN</h1>
        ${sistemasevaluacion}
        <h1>11.2. CRITERIOS DE EVALUACIÓN (REQUISITOS MÍNIMOS DE APROBACIÓN)</h1>
        ${sistemasevaluacioncriterio}
        <h1>12. BIBLIOGRAFÍA</h1>
        <div style="width: 100%">
            <h1>Básica</h1>
            <ul class="lista">
                <c:forEach var="item" items="${basica}" varStatus="contador">
                    <c:choose>
                        <c:when test="${item.tipo_referncia == 1}">
                            <li style="padding-top: 25px;">
                                    ${item.autor}, (${item.anio}),<strong> ${item.titulo}</strong>. (${item.edicion}
                                ed.), ${item.lugar}, Editorial ${item.editorial}
                                <strong>Ubicación:</strong> ${item.ubicacion}
                            </li>
                        </c:when>
                        <c:when test="${item.tipo_referncia == 2}">
                            <li style="padding-top: 25px;"> ${item.autor}, (<fmt:formatDate pattern="yyyy"
                                                                                            value="${item.fecha_publicacion}"/>), ${item.titulo}, ${item.titulo_documento}, ${item.volumen}(${item.numero}), ${item.paginas}
                            </li>
                        </c:when>
                        <c:when test="${item.tipo_referncia == 3}">
                            <li style="padding-top: 25px;">
                                    ${item.autor}, (<fmt:formatDate pattern="yyyy"
                                                                    value="${item.fecha_publicacion}"/>), ${item.titulo_documento}.
                                Recuperado el <fmt:formatDate pattern="dd" value="${item.fecha_consulta}"/> de
                                <fmt:formatDate
                                        pattern="MMMM" value="${item.fecha_consulta}"/>
                                de <fmt:formatDate pattern="yyyy" value="${item.fecha_consulta}"/>, de ${item.url}
                                <strong>Ubicación:</strong> ${item.ubicacion}
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </ul>
            <h2>Complementaria</h2>
            <ul class="lista">
                <c:forEach var="item" items="${complementaria}" varStatus="contador">
                    <c:choose>
                        <c:when test="${item.tipo_referncia == 1}">
                            <li style="padding-top: 25px;">
                                    ${item.autor}, (${item.anio}),<strong> ${item.titulo}</strong>. (${item.edicion}
                                ed.), ${item.lugar}, Editorial ${item.editorial}
                                <strong>Ubicacion:</strong> ${item.ubicacion}
                            </li>
                        </c:when>
                        <c:when test="${item.tipo_referncia == 2}">
                            <li style="padding-top: 25px;"> ${item.autor}, (<fmt:formatDate pattern="yyyy"
                                                                                            value="${item.fecha_publicacion}"/>), ${item.titulo}, ${item.titulo_documento}, ${item.volumen}(${item.numero}), ${item.paginas}
                            </li>
                        </c:when>
                        <c:when test="${item.tipo_referncia == 3}">
                            <li style="padding-top: 25px;">
                                    ${item.autor}, (<fmt:formatDate pattern="yyyy"
                                                                    value="${item.fecha_publicacion}"/>), ${item.titulo_documento}.
                                Recuperado el <fmt:formatDate pattern="dd" value="${item.fecha_consulta}"/> de
                                <fmt:formatDate
                                        pattern="MMMM" value="${item.fecha_consulta}"/>
                                de <fmt:formatDate pattern="yyyy" value="${item.fecha_consulta}"/>, de ${item.url}
                                <strong>Ubicacion:</strong> ${item.ubicacion}
                            </li>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
        <h1>13. CRONOGRAMA DE ACTIVIDADES</h1>
        <div style="width: 100%">
            <table data-size="" id="cronogramas"
                   style='border: 1px solid black; width: 100%; border-collapse: collapse;'>
                <thead>
                <tr>
                    <th style="padding: 5px; background: #D9D9D9; text-align: center; font-weight: bold; border: 1px solid black;">
                        Nº
                    </th>
                    <th style="padding: 5px; background: #D9D9D9; text-align: center; font-weight: bold; border: 1px solid black;">
                        DÍA Y FECHA
                    </th>
                    <th style="padding: 5px; background: #D9D9D9; text-align: center; font-weight: bold; border: 1px solid black;">
                        TIPO DE CLASE
                    </th>
                    <th style="padding: 5px; background: #D9D9D9; text-align: center; font-weight: bold; border: 1px solid black;">
                        TÍTULO DE LA CLASE (ASUNTO)
                    </th>
                    <th style="padding: 5px; background: #D9D9D9; text-align: center; font-weight: bold; border: 1px solid black;">
                        TIEMPO A EMPLEAR
                    </th>
                    <th style="padding: 5px; background: #D9D9D9; text-align: center; font-weight: bold; border: 1px solid black;">
                        OBSERVACIONES Y CUMPLIMIENTO
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cronograma}" varStatus="contador">
                    <tr>
                        <td style="padding: 5px; text-align: center; border: 1px solid black;">${contador.count}</td>
                        <td style="padding: 5px; text-align: left; width: 10%; border: 1px solid black;"><fmt:formatDate
                                value="${item.fecha}" pattern="E dd/MM/yyyy"/></td>
                        <td style="padding: 5px; text-align: left; border: 1px solid black;">${item.tipo_de_clase}</td>
                        <td style="padding: 5px; text-align: left; width: 30%; border: 1px solid black;">${item.titulo_de_clase}</td>
                        <td style="padding: 5px; text-align: left; border: 1px solid black;">${item.tiempo_a_emplear}</td>
                        <td style="padding: 5px; text-align: left; border: 1px solid black;">${item.observaciones}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div style="width: 100%; padding-top: 100px; text-align: right">
            <c:set var="fechaActual" value="<%= new java.util.Date() %>"/>
            <fmt:formatDate value="${fechaActual}" pattern="dd"/> de <fmt:formatDate value="${fechaActual}"
                                                                                     pattern="MMMM"/> de <fmt:formatDate
                value="${fechaActual}" pattern="yyyy"/>
        </div>
        <div class="firmas">
            <div>
                ---------------------------------------<br>
                ${nro_resolucion} ${nombredocente}<br>
                <strong>DOCENTE</strong>
            </div>
            <div>
                ---------------------------------------<br>
                FIRMA Y SELLO<br>
                <strong>JEFE DE ESTUDIO</strong>
            </div>
        </div>
    </div>
    <div class="imprimir">
        <button class="button-58" type="button" onclick="imprimirAPDF()"><i class="fa fa-print"></i> &nbsp;&nbsp;Imprimir
        </button>
    </div>
    <footer>
    </footer>
</div>
<script>
    window.onload = function () {
        let documento = document.getElementById('content');
        let elementos = documento.getElementsByTagName("*");
        for (let i = 0; i < elementos.length; i++) {
            elementos[i].style.fontSize = "16pt"; // Tamaño de letra deseado
            elementos[i].style.fontFamily = "Arial";
            elementos[i].style.color = "#000000";
        }
        let tds = documento.querySelectorAll("td");
        for (let i = 0; i < tds.length; i++) {
            tds[i].style.fontSize = "12pt";
        }
        let tablas = documento.querySelectorAll("table");
        for (let i = 0; i < tablas.length; i++) {
            tablas[i].style.fontSize = "12pt";
        }
        for (let i = 0; i < tablas.length; i++) {
            if (tablas[i].hasAttribute("data-size")) {
                continue;
            }
            let tdTabla = tablas[i].getElementsByTagName("*");
            for (let j = 0; j < tdTabla.length; j++) {
                tdTabla[j].style.fontSize = "12pt";
                tdTabla[j].style.width = "";
                tdTabla[j].removeAttribute("width");
                tdTabla[j].style.textIndent = "0";
            }
        }
        let ths = documento.querySelectorAll("th");
        for (let i = 0; i < ths.length; i++) {
            ths[i].style.fontSize = "12pt";
        }
        let tdcontenidos = document.getElementById('contenidos').getElementsByTagName("*");
        for (let j = 0; j < tdcontenidos.length; j++) {
            tdcontenidos[j].style.fontSize = "12pt";
            tdcontenidos[j].style.width = "";
            tdcontenidos[j].removeAttribute("width");
            tdcontenidos[j].style.textIndent = "0";
        }
        let tdformas = document.getElementById('formas').getElementsByTagName("*");
        for (let j = 0; j < tdformas.length; j++) {
            tdformas[j].style.fontSize = "12pt";
            tdformas[j].style.width = "";
            tdformas[j].removeAttribute("width");
            tdformas[j].style.textIndent = "0";
        }
        let tddatosdocente = document.getElementById('datosdocente').getElementsByTagName("*");
        for (let j = 0; j < tddatosdocente.length; j++) {
            tddatosdocente[j].style.fontSize = "12pt";
            tddatosdocente[j].style.width = "";
            tddatosdocente[j].removeAttribute("width");
            tddatosdocente[j].style.textIndent = "0";
        }
    }


    function imprimirAPDF() {
        // Obtiene el contenido que deseas imprimir en formato HTML
        window.print();
    }

</script>
</body>
</html>
