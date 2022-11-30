package org.fautapo.web.cuenta;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Enlaces;
import org.fautapo.domain.MenuEntity;
import org.fautapo.domain.ParametrosBusqueda;
import org.fautapo.domain.Tableros;
import org.fautapo.domain.TreeMenuEntity;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.MessageResult;
import org.fautapo.model.ResultFileBase64;
import static org.fautapo.util.Util.resize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MenuController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping(value = "/CargarImagenes.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> CargarImagenes(@ModelAttribute("filedocente") MultipartFile filedocente) {
        Clientes cliente = this.GetUsuario();
        MessageResult Respuesta = new MessageResult();
        ResultFileBase64 respuestabase64 = new ResultFileBase64();
        int iResultado = 0;
        int iId_docente = cliente.getId_usuario();
        String sNombre = String.format("%010d", iId_docente) + filedocente.getContentType().replaceAll("image/", ".");
        String sAdjunto = filedocente.getContentType();
        Docentes docente = new Docentes();
        docente.setId_docente(iId_docente);
        List<Docentes> docentes = this.mi.getDetallefotoadjunto(docente);
        if (docentes.isEmpty()) {
            Docentes datosAdjunto = new Docentes();
            datosAdjunto.setId_docente(iId_docente);
            datosAdjunto.setAdjunto(sAdjunto);
            datosAdjunto.setNombre_archivo(sNombre);
            datosAdjunto.setId_estado("I");
            datosAdjunto.setId_rol(cliente.getId_rol());
            datosAdjunto.setUlt_usuario(cliente.getId_usuario());
            iResultado = this.mi.setRegistrarDocenteAdjuntos(datosAdjunto);
            if (iResultado == 1) {
                try {
                    String rootPath = System.getProperty("catalina.home") + File.separator + "docadjuntos" + File.separator + String.format("%010d", iId_docente) + filedocente.getContentType().replaceAll("image/", ".");
                    byte[] bytes = filedocente.getBytes();
                    Path path = Paths.get(rootPath);

                    Files.write(path, bytes);
                    String contentType = filedocente.getContentType(), imagen = "";
                    File fnew = new File(rootPath);
                    BufferedImage originalImage = ImageIO.read(fnew);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resize(originalImage, 70, 70), contentType.replaceAll("image/", "").replace("application/", ""), baos);
                    byte[] imageInByte = baos.toByteArray();
                    imagen = "data:" + contentType + ";base64," + Base64.encodeBase64String(imageInByte);
                    respuestabase64.setBase64(imagen);
                    respuestabase64.setContentType(contentType);
                    Respuesta.setMessage("");
                    Respuesta.setStatus("OK");
                    cliente.setImagen(respuestabase64.getBase64());
                    request.getSession().setAttribute("__sess_cliente", cliente);
                } catch (Exception ex) {
                    Respuesta.setMessage("No se pudo grabar la infomacion");
                    Respuesta.setStatus("Error");
                }
            } else {
                Respuesta.setMessage("No se pudo grabar la infomacion");
                Respuesta.setStatus("Error");
            }
        } else {
            try {
                Docentes datosAdjunto = docentes.stream().findFirst().get();
                datosAdjunto.setId_docente(iId_docente);
                datosAdjunto.setAdjunto(sAdjunto);
                datosAdjunto.setNombre_archivo(sNombre);
                datosAdjunto.setId_estado("I");
                datosAdjunto.setId_rol(cliente.getId_rol());
                datosAdjunto.setUlt_usuario(cliente.getId_usuario());
                iResultado = this.mi.setActualizarDocenteAdjuntos(datosAdjunto);
                if (iResultado == 1) {
                    String rootPath = System.getProperty("catalina.home") + File.separator + "docadjuntos" + File.separator + String.format("%010d", iId_docente) + filedocente.getContentType().replaceAll("image/", ".");
                    byte[] bytes = filedocente.getBytes();
                    Path path = Paths.get(rootPath);
                    Files.write(path, bytes);
                    String contentType = filedocente.getContentType(), imagen = "";
                    File fnew = new File(rootPath);
                    BufferedImage originalImage = ImageIO.read(fnew);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resize(originalImage, 70, 70), contentType.replaceAll("image/", "").replace("application/", ""), baos);
                    byte[] imageInByte = baos.toByteArray();
                    imagen = "data:" + contentType + ";base64," + Base64.encodeBase64String(imageInByte);
                    respuestabase64.setBase64(imagen);
                    respuestabase64.setContentType(contentType);
                    Respuesta.setMessage("");
                    Respuesta.setStatus("OK");
                    cliente.setImagen(respuestabase64.getBase64());
                    request.getSession().setAttribute("__sess_cliente", cliente);

                } else {
                    Respuesta.setMessage("No se pudo grabar la infomacion");
                    Respuesta.setStatus("Error");
                }

            } catch (Exception ex) {
                Respuesta.setMessage("No se pudo grabar la infomacion");
                Respuesta.setStatus("Error");
            }
        }
        return new ResponseEntity<>(Respuesta, HttpStatus.OK);
    }

    @RequestMapping("/menu.fautapo")
    public String Menu(Model modelo) {
        List<TreeMenuEntity> enlaces = new ArrayList<TreeMenuEntity>();
        Clientes cliente = this.GetUsuario();
        modelo.addAttribute("simagen", cliente.getImagen());
        ParametrosBusqueda Buscar = new ParametrosBusqueda();
        Buscar.setId_docente(cliente.getId_usuario());
        Buscar.setGestion(cliente.getGestion());
        List<Docentes> listarnotificaciones = mi.GetListaNotificacionDocente(Buscar);
        int cantidad = 0;
        if (!listarnotificaciones.isEmpty()) {
            cantidad = listarnotificaciones.size();
            modelo.addAttribute("cantidadnotificacion", cantidad);
            modelo.addAttribute("notificaciones", listarnotificaciones);
        } else {
            modelo.addAttribute("cantidadnotificacion", cantidad);
            modelo.addAttribute("notificaciones", null);
        }
        Enlaces enlace = new Enlaces();
        enlace.setId_rol(cliente.getId_rol());
        List listaEnlaces = this.mi.getListarEnlaces(enlace);
        int fil = listaEnlaces.size();
        for (int i = 0; i < listaEnlaces.size(); i++) {
            TreeMenuEntity menues = new TreeMenuEntity();
            Enlaces aux = (Enlaces) listaEnlaces.get(i);
            menues.setIdObject(aux.getId_enlace());
            menues.setIdNivel(aux.getNivel());
            menues.setIdParent(aux.getId_enlace_padre());
            menues.setController(aux.getRuta());
            menues.setAction(aux.getImagen());
            menues.setNameModule(aux.getEnlace());
            enlaces.add(menues);
        }
        modelo.addAttribute("fil", GetMenus(TreeMenuEntity.GenerarMenu(enlaces)));
        modelo.addAttribute("listaEnlazces", String.valueOf(fil - 1));
        modelo.addAttribute("correo", cliente.getCorreo());
        modelo.addAttribute("celular", cliente.getCelular());
        modelo.addAttribute("cliente", cliente);
        return "menu/Menu";
    }

    @RequestMapping("/VerCuerpo.fautapo")
    public String Intro(Model modelo) {
        String sId_rol, sVisita;
        String sEntrada = request.getParameter("entrada");
        String sBandera;
        Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
        sId_rol = Integer.toString(cliente.getId_rol());
        modelo.addAttribute("snombre", cliente.getNombres());
        modelo.addAttribute("simagen", cliente.getImagen());
        if (sId_rol == null) {
            sVisita = "Si";
        } else {
            sVisita = "No";
        }
        modelo.addAttribute("sVisita", sVisita);

        //Listar Tableros 
        List lRolesNoticias = this.mi.getListarRolesNoticias();
        modelo.addAttribute("lRolesNoticias", lRolesNoticias);
        if (sEntrada != null) {
            Tableros tablero = new Tableros();
            tablero.setId_rol(Integer.parseInt(sEntrada));
            List lNoticiasRol = this.mi.getListarNoticiasRol(tablero);
            modelo.addAttribute("lNoticiasRol", lNoticiasRol);
            modelo.addAttribute("bandera", sEntrada);
        }

        List lNoticias = this.mi.getListarNoticias();
        modelo.addAttribute("lNoticias", lNoticias);

        return "menu/VerCuerpo";

    }

    private String GetMenus(List<MenuEntity> menu) {
        String li = "";
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getNivel() == 0 && menu.get(i).getMenus().isEmpty()) {
                li += "<li> <a class='app-menu__item active' href='#'> <i class='app-menu__icon fas fa-chart-network'></i><span class='app-menu__label'>" + menu.get(i).getModule() + "</span>";
            } else {
                if (menu.get(i).getNivel() == 0 && !menu.get(i).getMenus().isEmpty()) {
                    li += "<li class='treeview'> <a class='app-menu__item'  href='#' data-toggle='treeview' > <i class='app-menu__icon fab fa-wpforms'></i><span class='app-menu__label'>" + menu.get(i).getModule() + "</span><i class='treeview-indicator fa fa-angle-right'></i>";
                }
            }
            if (menu.get(i).getNivel() != 0 && !menu.get(i).getMenus().isEmpty()) {
                li += "<li> <a class='treeview-item'  href='#' data-url='." + menu.get(i).getController() + "'> <i class='icon fas fa-chevron-circle-right'></i>" + menu.get(i).getModule();
            } else {
                if (menu.get(i).getNivel() != 0 && menu.get(i).getMenus().isEmpty()) {
                    li += "<li> <a class='treeview-item' href='#' data-url='." + menu.get(i).getController() + "'> <i class='icon fas fa-chevron-circle-right'></i>" + menu.get(i).getModule();
                }
            }
            if (!menu.get(i).getMenus().isEmpty()) {
                li += "</a>";
                li += " <ul class='treeview-menu'>";
                li += GetMenus(menu.get(i).getMenus()) + "</ul></li>";
            } else {
                li += "</a></li>";
            }
        }
        return li.replaceAll(".NINGUNO", "");
    }

}
