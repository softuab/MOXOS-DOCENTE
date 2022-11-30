<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<c:url value='/Public/Css/TableResponsive.css'/>">
        <link href="<c:url value='/Public/Css/fileupdateloader.css'/>" rel="stylesheet"/>
    </head>
    <body> 
        <br/>
        <div id="contenidocontenido">
            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#idModalcontenido">
                <i class="fas fa-plus"></i> Registrar contenidos
            </button>
            <br/><br/>
            <span class="d-block p-2 bg-primary text-white"><i class="fas fa-address-card"></i>&nbsp;contenido</span>

            <ul id="listcontenidos" class="list-unstyled">
                <c:forEach var="contenidos" items="${contenidos}" varStatus="contador">
                    <li id="li${contenidos.id_prg_a_contenido}" class="media">
                        <c:if test="${contenidos.imagenestableciada}">
                            <div class="btn-group">
                                <button type="button" class="btn btn-outline-light dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <img id="imagecontenido${contenidos.id_prg_a_contenido}" src="<c:url value='/imagenes/mapa.png'/>" width="48" height="48" class="align-self-start mr-3 rounded-circle">
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" onclick="modificarcontenido(this)" data-id="${contenidos.id_prg_a_contenido}"  data-url="<c:url value='/UploadContenido.fautapo'/>" href="#">Modificar mapa</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" onclick="vercontenido(this)" data-id="${contenidos.id_prg_a_contenido}" href="#">Ver mapa de contenido</a>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${!contenidos.imagenestableciada}">
                            <div class="btn-group">
                                <button type="button" class="btn btn-outline-light dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <img id="imagecontenido${contenidos.id_prg_a_contenido}" src="<c:url value='/imagenes/none.png'/>" width="48" height="48" class="align-self-start mr-3 rounded-circle">
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" onclick="modificarcontenido(this)" data-id="${contenidos.id_prg_a_contenido}"  data-url="<c:url value='/UploadContenido.fautapo'/>" href="#">Modificar mapa</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" onclick="vercontenido(this)" data-id="${contenidos.id_prg_a_contenido}" href="#">Ver mapa de contenido</a>
                                </div>
                            </div>
                        </c:if>
                        <div class="media-body">
                            <h5 class="mt-0 mb-1" id="title${contenidos.id_prg_a_contenido}">${contenidos.contenido}</h5>
                            <p id="content${contenidos.id_prg_a_contenido}">
                                <b>Objetivo instuctivo:</b>${contenidos.text_objetivo_instructivo}<br>
                                <b>Conocimientos:</b>${contenidos.text_conocimientos}<br>
                                <b>Habilidades:</b>${contenidos.text_habilidades}<br>
                                <b>Valores:</b>${contenidos.text_valores}<br>
                            </p>
                            <div class="text-right">
                                <button type="button" class="btn btn-primary" data-contenido="detail" data-edit="${contenidos.id_prg_a_contenido}">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button type="button" class="btn btn-primary" data-contenido="delete" data-delete="${contenidos.id_prg_a_contenido}">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>


            <div class="modal fade" id="idModalcontenido" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">DATOS CONTENIDOS</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" id="id_contenido_dct_programa_analitico" value="${id_dct_programa_analitico}"/>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <a class="btn btn-primary" onclick="paso10.show()" href="#"><i class="fa fa-question-circle"></i> Ayuda</a>
                                            <input type="text" class="form-control" id="contenido" placeholder="Contenido">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="objetivo_instructivo">Objetivo Instructivo:</label>
                                            <textarea  class="form-control" rows="5" id="objetivo_instructivo"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="conocimientos">Conocimientos:</label>
                                            <textarea  class="form-control" rows="5" id="conocimientos"></textarea> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="habilidades">Habilidades:</label>
                                            <textarea  class="form-control" rows="5" id="habilidades"></textarea> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row"> 
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="valores">valores:</label>
                                            <textarea  class="form-control" rows="5" id="valores"></textarea>  
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Salir</button>
                            <button id="guardarcontenido" class="btn btn-primary" type="button" data-contenido="insert">
                                <span id="statusinsertstart" class="visible">Guardar Cambios</span>
                                <span id="statusinsert" class="spinner-border spinner-border-sm invisible" role="status" aria-hidden="true"></span>
                                <span id="statusinserttext" class="invisible">Enviando...</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="myModal_contenido">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">DATOS CONTENIDO</h4>
                            <button type="button" class="close" data-dismiss="modal">×</button>
                        </div>
                        <div id="content_contenido" class="modal-body">
                            <input type="hidden" id="id_prg_a_contenido_edit"/>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <a class="btn btn-primary" onclick="paso10.show()" href="#"><i class="fa fa-question-circle"></i> Ayuda</a>
                                            <input type="text" class="form-control" id="contenido_edit" placeholder="Contenido">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="objetivo_instructivo">Objetivo Instructivo:</label>
                                            <textarea  class="form-control" rows="5" id="objetivo_instructivo_edit"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="conocimientos">Conocimientos:</label>
                                            <textarea  class="form-control" rows="5" id="conocimientos_edit"></textarea> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="habilidades">Habilidades:</label>
                                            <textarea  class="form-control" rows="5" id="habilidades_edit"></textarea> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row"> 
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="valores">valores:</label>
                                            <textarea  class="form-control" rows="5" id="valores_edit"></textarea>  
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal footer -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Salir</button>
                            <button id="guardardatoseditadoscontenido" class="btn btn-primary" type="button" data-contenido="update">
                                <span id="statusupdatestart" class="visible">Guardar Cambios</span>
                                <span id="statusupdate" class="spinner-border spinner-border-sm invisible" role="status" aria-hidden="true"></span>
                                <span id="statusupdatetext" class="invisible">Enviando...</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="uploadmodal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                            <button type="button" class="close" onclick="cerrarmodal()" >
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div id="drop-area">
                                <form class="my-form">
                                    <p>Cargue varios archivos con el cuadro de diálogo de archivo o arrastrando y soltando imágenes en la región discontinua</p>
                                    <input type="hidden" id="idcontenido" name="idcontenido" value="">
                                    <input type="file" id="fileElem" name="fileElem"  accept=".png, .jpg, .jpeg" onchange="handleFiles(this.files)">
                                    <label class="button" for="fileElem">Selecciona archivos</label>
                                </form>
                                <progress id="progress-bar" max=100 value=0></progress>
                                <div id="gallery"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="vistaprevia" tabindex="-1" role="dialog" aria-labelledby="examplevistaprevia" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <img src="" id="imgvistaprevia" class="img-fluid" alt="Responsive image">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Entiendo</button> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>