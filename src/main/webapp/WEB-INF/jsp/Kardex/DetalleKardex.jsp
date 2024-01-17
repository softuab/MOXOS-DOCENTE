<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<body>
<main class="app-content3">
    <div class="row user">
        <div class="col-md-12">
            <div class="profile">
                <div class="info"><img class="user-img" src="${simagen}" alt="Docente">
                    <p>Docente</p>
                </div>
                <div class="cover-image"></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="tile p-0">
                <ul class="nav flex-column nav-tabs user-tabs">
                    <li class="nav-item"><a class="nav-link active" href="#user-detalle" data-bs-toggle="pill"
                                            data-bs-target="#user-detalle" role="tab" aria-controls="user-detalle">DATOS
                        PERSONALES</a></li>
                    <li class="nav-item"><a class="nav-link" href="#modalidad-detalle" data-bs-toggle="pill"
                                            data-bs-target="#modalidad-detalle" role="tab"
                                            aria-controls="modalidad-detalle">MODALIDAD DE INGRESO</a></li>
                    <li class="nav-item"><a class="nav-link" href="#idioma-detalle" data-bs-toggle="pill"
                                            data-bs-target="#idioma-detalle" role="tab" aria-controls="idioma-detalle">IDIOMAS
                        Y LENGUAS ORIGINARIAS</a></li>
                    <li class="nav-item"><a class="nav-link" href="#formacion-detalle" data-bs-toggle="pill"
                                            data-bs-target="#formacion-detalle" role="tab"
                                            aria-controls="formacion-detalle">FORMACION ACADEMICA</a></li>
                    <li class="nav-item"><a class="nav-link" href="#experiencia-detalle" data-bs-toggle="pill"
                                            data-bs-target="#experiencia-detalle" role="tab"
                                            aria-controls="experiencia-detalle">EXPERIENCIA PROFESIONAL/LABORAL </a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="#cursos-detalle" data-bs-toggle="pill"
                                            data-bs-target="#cursos-detalle" role="tab" aria-controls="cursos-detalle">CURSOS
                        REALIZADOS</a></li>
                    <li class="nav-item"><a class="nav-link" href="#produccion-detalle" data-bs-toggle="pill"
                                            data-bs-target="#produccion-detalle" role="tab"
                                            aria-controls="produccion-detalle">PRODUCCION CIENTIFICA</a></li>
                    <li class="nav-item"><a class="nav-link" href="#evaluacion-detalle" data-bs-toggle="pill"
                                            data-bs-target="#evaluacion-detalle" role="tab"
                                            aria-controls="evaluacion-detalle">EVALUACION DOCENTE</a></li>
                    <li class="nav-item"><a class="nav-link" href="#actividadesacademicas-detalle" data-bs-toggle="pill"
                                            data-bs-target="#actividadesacademicas-detalle" role="tab"
                                            aria-controls="actividadesacademicas-detalle">ACTIVIDADES ACADEMICAS</a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="#proyectodocente-detalle" data-bs-toggle="pill"
                                            data-bs-target="#proyectodocente-detalle" role="tab"
                                            aria-controls="proyectodocente-detalle">PROYECTOS REALIZADOS</a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-9">
            <div class="tab-content">
                <div class="tab-pane fade show active" role="tabpanel" aria-labelledby="user-detalle" id="user-detalle">
                    <div class="tile">
                        <div class="tile-title-w-btn">
                            <h3 class="title">Datos Personales</h3>
                        </div>
                        <div class="tile-body">
                            <div class="tile-title-w-btn">
                                <h5 class="title">1.- Informacion personal</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarInformacionPersonal"/>" method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Nombre:</b>&nbsp;${kardex.nombre}<br>
                                <b class="text-primary">Segundo nombre:</b>&nbsp;${kardex.segundonombre}<br>
                                <b class="text-primary">Apellido paterno:</b>&nbsp;${kardex.apellidopaterno}<br>
                                <b class="text-primary">Apellido materno:</b>&nbsp;${kardex.apellidomaterno}<br>
                                <b class="text-primary">Fecha de Nacimiento:</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechanacimiento}"/> <br>
                                <b class="text-primary">Lugar de Nacimiento:</b>&nbsp;${kardex.detalle_localidad}<br>
                                <b class="text-primary">Estado civil:</b>&nbsp;${kardex.estadocivil}<br>
                                <b class="text-primary">Sexo:</b>&nbsp;${kardex.detalle_sexo}<br>
                            </p>
                            <hr>
                            <div class="tile-title-w-btn">
                                <h5 class="title">2.- Información identificación personal</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarIdentificacionPersonalPersonal"/>"
                                      method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Tipo documento:</b>&nbsp;${kardex.tipodocumento}<br>
                                <b class="text-primary">Numero de documento:</b>&nbsp;${kardex.numerodocumento}<br>
                                <b class="text-primary">Emision:</b>&nbsp;${kardex.emision_documento}<br>
                                <b class="text-primary">Fecha de Expiración de documento:</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechaexpiracioncarnet}"/> <br>
                                <b class="text-primary">Fotocopia de carnet:</b>&nbsp;<button
                                    class="btn btn-primary btn-sm" id="vercarnet" data-persona="${kardex.id_persona}"
                                    data-tabla="persona" data-columna="carnet" data-url="<c:url value="/Vistapreviaimagenkardex" />" onclick="vistaprevia(this)"><i id="icon1"
                                                                                                              class="fa fa-eye"></i>
                            </button>
                                <br>
                            </p>
                            <hr>
                            <div class="tile-title-w-btn">
                                <h5 class="title">3.- Información de Servicio militar(opcional mujeres)</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarServicioMilitarPersonal"/>"
                                      method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Numero de Libreta:</b>&nbsp;${kardex.numerolibreta}<br>
                                <b class="text-primary">Matricula de Libreta:</b>&nbsp;${kardex.matriculalibreta}<br>
                                <b class="text-primary">Escalon:</b>&nbsp;${kardex.escalon}<br>
                                <b class="text-primary">Año de Servicio:</b>&nbsp;${kardex.aserviciomilitar} <br>
                                <b class="text-primary">Fotocopia de libreta de servicio militar:</b>&nbsp;<button
                                    class="btn btn-primary btn-sm" id="vercarnet" data-persona="${kardex.id_persona}"
                                    data-tabla="persona" data-columna="libreta" data-url="<c:url value="/Vistapreviaimagenkardex" />"  onclick="vistaprevia(this)"><i
                                    id="icon1" class="fa fa-eye"></i></button>
                                <br>
                            </p>
                            <hr>
                            <div class="tile-title-w-btn">
                                <h5 class="title">4.- Información academica pregrado actual</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarEducacionPregradoPersonal"/>"
                                      method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Nivel de estudio:</b>&nbsp;${kardex.detalle_nivelestudio}<br>
                                <b class="text-primary">Profesion:</b>&nbsp;${kardex.detalle_profesion}<br>
                                <b class="text-primary">Universidad:</b>&nbsp;${kardex.universidad}<br>
                                <b class="text-primary">Numero de titulo:</b>&nbsp;${kardex.numerotituloprovision}<br>
                                <b class="text-primary">Fecha de emision de titulo:</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechatituloprofesion}"/><br>
                                <b class="text-primary">Colegio de
                                    profesionales:</b>&nbsp;${kardex.detalle_colegio_profesionales}<br>
                                <b class="text-primary">Numero de registro de colegio
                                    profesional:</b>&nbsp;${kardex.numeroregistroprofesionales} <br>
                                <b class="text-primary">Etiqueta de presentacion
                                    profesional:</b>&nbsp;${kardex.prefijo_profesional} <br>
                                <b class="text-primary">Fotocopia de titulo de provision nacional:</b>&nbsp;<button
                                    class="btn btn-primary btn-sm" id="vercarnet" data-persona="${kardex.id_persona}"
                                    data-tabla="persona" data-columna="titulo"  data-url="<c:url value="/Vistapreviaimagenkardex" />"  onclick="vistaprevia(this)"><i id="icon1"
                                                                                                              class="fa fa-eye"></i>
                            </button>
                                <br>
                            </p>
                            <hr>
                            <div class="tile-title-w-btn">
                                <h5 class="title">5.- Información academica posgrado en Educacion superior como
                                    requisito institucional</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarEducacionPosgradoPersonal"/>"
                                      method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Nivel de estudio
                                    posgrado:</b>&nbsp;${kardex.nivelestudio_posgrado}<br>
                                <b class="text-primary">Detalle del Titulo en educacion
                                    superior:</b>&nbsp;${kardex.tituloposgrado}<br>
                                <b class="text-primary">Universidad:</b>&nbsp;${kardex.emisiontituloposgrado}<br>
                                <b class="text-primary">Fecha de emision de titulo:</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechaemisionposgrado}"/><br>
                                <b class="text-primary">Fotocopia de titulo de educacion superior:</b>&nbsp;<button
                                    class="btn btn-primary btn-sm" id="vercarnet" data-persona="${kardex.id_persona}"
                                    data-tabla="persona" data-columna="posgrado" data-url="<c:url value="/Vistapreviaimagenkardex" />" onclick="vistaprevia(this)"><i
                                    id="icon1" class="fa fa-eye"></i></button>
                                <br>
                            </p>
                            <hr>
                            <div class="tile-title-w-btn">
                                <h5 class="title">6.- Información laboral</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarInformacionLaboralPersonalPersonal"/>"
                                      method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Tipo Nua:</b>&nbsp;${kardex.tiponua}<br>
                                <b class="text-primary">Nua:</b>&nbsp;${kardex.nua}<br>
                                <b class="text-primary">Banco:</b>&nbsp;${kardex.detalle_banco}<br>
                                <b class="text-primary">Numero de cuenta:</b>&nbsp;${kardex.cuentacorriente}<br>
                                <b class="text-primary">Sindicato:</b>&nbsp;${kardex.detalle_sindicato}<br>
                                <b class="text-primary">Jubilado:</b>&nbsp;${kardex.detalle_jubilado}<br>
                                <b class="text-primary">REN:</b>&nbsp;${kardex.ren}<br>
                                <b class="text-primary">Capacidades diferentes:</b>&nbsp;${kardex.detalle_discapacitado}<br>
                                <b class="text-primary">Numero de CODEPEDI:</b>&nbsp;${kardex.nrodiscpacitado}<br>
                                <b class="text-primary">Numero de SSU:</b>&nbsp;${kardex.numerodeseguro}<br>
                                <b class="text-primary">Curso 1178:</b>&nbsp;${kardex.detalle_ley1178}<br>
                                <b class="text-primary">Numero de certificado 1178:</b>&nbsp;${kardex.nrotitulo}<br>
                                <b class="text-primary">Promedio de 1178:</b>&nbsp;${kardex.promedio}<br>
                                <b class="text-primary">Fecha de emision de 1178:</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechacurso1178}"/><br>
                                <b class="text-primary">Certificación del
                                    Sippase:</b>&nbsp;${kardex.detalle_sippase}<br>
                                <b class="text-primary">Fecha de emision de Sippase:</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechaemisionsippase}"/><br>

                                <b class="text-primary">Detalle de idioma Nativo</b>&nbsp;${kardex.idiomanativo}<br>
                                <b class="text-primary">Fecha de emision idioma Nativo</b>&nbsp;<fmt:formatDate
                                    pattern="dd/MM/yyyy" value="${kardex.fechaemision}"/><br>

                            </p>
                            <hr>
                            <div class="tile-title-w-btn">
                                <h5 class="title">7.- Contacto</h5>
                                <form name="forma" class="unlock-form"
                                      action="<c:url value="/kardex/EditarContactoPersonal"/>" method="GET">
                                    <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                    <button type="submit" class="btn btn-primary icon-btn"><i
                                            class="fa fa-pencil"></i></button>
                                </form>
                            </div>
                            <p>
                                <b class="text-primary">Direccion:</b>&nbsp;${kardex.direccion}<br>
                                <b class="text-primary">Telefono/Celular:</b>&nbsp;${kardex.telefonocelular}<br>
                                <b class="text-primary">Correo Electronico:</b>&nbsp;${kardex.correoinsitucional}<br>
                            </p>
                            <hr>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="modalidad-detalle" id="modalidad-detalle">
                    <div class="tile">
                        <h3 class="title">Modalidad de ingreso a la carrera</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetallemodalidad"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetallemodalidad', 'listmodalidad')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroModalidadIngresoKardex"/>"
                                  method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialModalidad">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/ListaModalidadIngreso"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todos las modalidades
                                    de ingreso
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="idioma-detalle" id="idioma-detalle">
                    <div class="tile">
                        <h3 class="title">Idiomas y Lenguas originarias</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleidioma"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetalleidioma', 'listidioma')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroIdiomaKardex"/>" method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialIdiomaLenguas">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" class="unlock-form" action="<c:url value="/ListaIdiomaLenguas"/>"
                                  method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todos los idiomas y
                                    lenguas originarios
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="formacion-detalle" id="formacion-detalle">
                    <div class="tile">
                        <h3 class="title">Formacion academica</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleformacion"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetalleformacion', 'listformacion')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroFormacionKardex"/>" method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialFormacionAcademico">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" action="<c:url value="/ListarFormacionAcademica"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todas las formaciones
                                    academicas realizadas
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="experiencia-detalle"
                     id="experiencia-detalle">
                    <div class="tile">
                        <h3 class="title">Experiencia profesional/laboral</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleexperiencia"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetalleexperiencia', 'listexperiencia')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroExperienciaLaboralKardex"/>"
                                  method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialExperienciaLaboral">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" action="<c:url value="/ListarExperienciaLaboral"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todas los detalles de
                                    la experiencia profecional
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="cursos-detalle" id="cursos-detalle">
                    <div class="tile">
                        <h3 class="title">Cursos realizados</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetallecursos"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetallecursos', 'listcursos')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroCursosRealizadosKardex"/>"
                                  method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialCursosRealizados">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" action="<c:url value="/ListarCursosRealizados"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todos los cursos
                                    realizados
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="produccion-detalle" id="produccion-detalle">
                    <div class="tile">
                        <h3 class="title">Produccion cientifica</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleproduccion"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetalleproduccion', 'listproduccion')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroProduccionKardex"/>" method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialProduccionCientifica">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" action="<c:url value="/ListarProduccionCientifica"/>"
                                  method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todas las producciones
                                    cientificas
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="evaluacion-detalle" id="evaluacion-detalle">
                    <div class="tile">
                        <h3 class="title">Evaluacion docente</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleevaluacion"
                                   placeholder="Buscar titulo"
                                   onkeyup="filtertable('buscardetalleevaluacion', 'listevaluacion')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroEvaluacionKardex"/>" method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialEvaluacionDocente">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" action="<c:url value="/ListarEvaluacionDocente"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todas las
                                    evaluaciones
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="actividadesacademicas-detalle"
                     id="actividadesacademicas-detalle">
                    <div class="tile">
                        <h3 class="title">Actividades Academicas</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleactividadesacademicas"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetalleactividadesacademicas', 'listactividadesacademicas')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroActividadesAcademicasKardex"/>"
                                  method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialActividadesAcademicas">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/ListaActividadesAcademicas"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todos las actividades
                                    academicas
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" role="tabpanel" aria-labelledby="proyectodocente-detalle"
                     id="proyectodocente-detalle">
                    <div class="tile">
                        <h3 class="title">PROYECTO REALIZADOS POR EL DOCENTE</h3>
                        <div class="tile-title-w-btn">
                            <input class="form-control mr-sm-2" type="search" id="buscardetalleproyectodocente"
                                   placeholder="Buscar titulo"
                                   onkeyup="filterlist('buscardetalleproyectodocente', 'listproyectodocente')">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/FormularioRegistroProyectoKardex"/>" method="GET">
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="root" value=''/>
                                <button type="submit" class="btn btn-primary icon-btn"><i class="fa fa-user-plus"></i>
                                </button>
                            </form>
                        </div>
                        <div class="tile-body">
                            <jsp:include page="${request.contextPath}/ListaParcialProyectoDocente">
                                <jsp:param name="id_persona_kardex" value="${kardex.id_persona_kardex}"/>
                                <jsp:param name="number" value="10"/>
                            </jsp:include>
                        </div>
                        <div class="tile-footer">
                            <form name="forma" class="unlock-form"
                                  action="<c:url value="/ListarProyectoDocente"/>" method="GET">
                                <input type="hidden" name="id_persona_kardex" value='${kardex.id_persona_kardex}'/>
                                <input type="hidden" name="id_persona" value='${kardex.id_persona}'/>
                                <button class="btn btn-light  btn-sm btn-block text-black"" type="submit">Ver todos los proyectos
                                    realizados por el docente
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade zoom" id="vistaprevia" data-bs-backdrop="static" data-bs-keyboard="false"
         tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div id="imgvistaprevia" class="modal-body">
                </div>
                <div class="modal-footer">
                    <button type="button" id="btncancelar" class="waves-effect waves-red btn-flat"
                            data-bs-dismiss="modal">Entiendo
                    </button>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="errortoast"></div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/ajax.js?v=7" />"></script>
<script src="<c:url value="/static/js/toast.boostrap.js" />"></script>
<script src="<c:url value="/static/js/app/main/kardex/vistaprevia.js" />"></script>
<script src="<c:url value="/static/js/app/main/kardex/fiter.js" />"></script>
</body>
</html>
