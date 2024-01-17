package com.moxos.uab.controller.menu;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.util.Utils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private MiFacade mi;
    @Value("${app.upload.path}")
    private String path;
    @Autowired
    private HttpServletRequest request;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/menu")
    public String Menu(Model modelo) {
        List<TreeMenuEntity> enlaces = new ArrayList<TreeMenuEntity>();
        Clientes cliente = this.getUsuario();
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
        List<Enlaces> listaEnlaces = this.mi.getListarEnlaces(enlace);
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
        modelo.addAttribute("fil", getMenus(TreeMenuEntity.GenerarMenu(enlaces)));
        modelo.addAttribute("listaEnlazces", String.valueOf(fil - 1));
        modelo.addAttribute("correo", cliente.getCorreo());
        modelo.addAttribute("celular", cliente.getCelular());
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("snombre", cliente.getNombres().substring(0, 10) + "..");
        return "Menu/Menu";
    }

    private String getMenus(List<MenuEntity> menu) {
        String li = "";
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getNivel() == 0 && menu.get(i).getMenus().isEmpty()) {
                li += "<li> <a class='app-menu__item active' href='#'> <i class='app-menu__icon fas fa-chart-network'></i><span class='app-menu__label'>" + menu.get(i).getModule() + "</span>";
            } else {
                if (menu.get(i).getNivel() == 0 && !menu.get(i).getMenus().isEmpty()) {
                    li += "<li class='treeview'> <a class='app-menu__item'  href='#' data-toggle='treeview' > <i class='app-menu__icon fa fa-wpforms'></i><span class='app-menu__label'>" + menu.get(i).getModule() + "</span><i class='treeview-indicator fa fa-angle-right'></i>";
                }
            }
            if (menu.get(i).getNivel() != 0 && !menu.get(i).getMenus().isEmpty()) {
                li += "<li> <a class='treeview-item'  href='#' data-url='." + menu.get(i).getController().replace(".fautapo", "") + "'> <i class='icon fa fa-circle-o'></i>" + menu.get(i).getModule();
            } else {
                if (menu.get(i).getNivel() != 0 && menu.get(i).getMenus().isEmpty()) {
                    li += "<li> <a class='treeview-item' href='#' data-url='." + menu.get(i).getController().replace(".fautapo", "") + "'> <i class='icon fa fa-circle-o'></i>" + menu.get(i).getModule();
                }
            }
            if (!menu.get(i).getMenus().isEmpty()) {
                li += "</a>";
                li += " <ul class='treeview-menu'>";
                li += getMenus(menu.get(i).getMenus()) + "</ul></li>";
            } else {
                li += "</a></li>";
            }
        }
        return li.replaceAll(".NINGUNO", "");
    }

    @RequestMapping("/main")
    public String main(Model modelo) throws IOException {
        modelo.addAttribute("snombre", this.getUsuario().getNombres());
        modelo.addAttribute("simagen", getImagen(this.getUsuario().getId_usuario()));
        return "Menu/Main";
    }

    @RequestMapping(value = "/upload-image", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> CargarImagenes(@ModelAttribute("filedocente") MultipartFile filedocente) throws IOException {
        MessageResult message = new MessageResult();
        Integer iResultado = 0;
        Docentes docente = new Docentes();
        docente.setId_docente(this.getUsuario().getId_usuario());
        List<Docentes> docentes = this.mi.getDetallefotoadjunto(docente);
        Docentes datosDocente = new Docentes();
        datosDocente.setId_docente(this.getUsuario().getId_usuario());
        datosDocente.setAdjunto(filedocente.getContentType());
        datosDocente.setNombre_archivo(String.format("%010d", this.getUsuario().getId_usuario()) + filedocente.getContentType().replaceAll("image/", "."));
        datosDocente.setId_estado("I");
        datosDocente.setId_rol(this.getUsuario().getId_rol());
        datosDocente.setUlt_usuario(this.getUsuario().getId_usuario());
        if (docentes.isEmpty()) {
            iResultado = this.mi.setRegistrarDocenteAdjuntos(datosDocente);
        }
        if (!docentes.isEmpty()) {
            datosDocente.setId_doc_adjunto(docentes.stream().findFirst().get().getId_doc_adjunto());
            iResultado = this.mi.setActualizarDocenteAdjuntos(datosDocente);
        }
        if (iResultado == 1) {
            this.getUsuario().setImagen(updateImageDocente(filedocente, this.getUsuario().getId_usuario()));
            request.getSession().setAttribute("__sess_cliente", this.getUsuario());
            message.setMessage(this.getUsuario().getImagen());
            message.setStatus("OK");
        } else {
            message.setMessage("No se pudo grabar la infomacion");
            message.setStatus("Error");
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private String updateImageDocente(MultipartFile filedocente, Integer idDocente) throws IOException {
        String rootPath = path + File.separator + "docadjuntos" + File.separator + String.format("%010d", idDocente) + filedocente.getContentType().replaceAll("image/", ".");
        byte[] bytes = filedocente.getBytes();
        Path path = Paths.get(rootPath);

        Files.write(path, bytes);
        String contentType = filedocente.getContentType(), imagen = "";
        File fnew = new File(rootPath);
        BufferedImage originalImage = ImageIO.read(fnew);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage imagerecortada = Utils.resize(originalImage, 70, 70);
        ImageIO.write(imagerecortada, "png", baos);
        byte[] imageInByte = baos.toByteArray();
        imagen = "data:" + contentType + ";base64," + Base64.encodeBase64String(imageInByte);
        return imagen;
    }
    private String getImagen(Integer id) throws IOException {
        String imagen;

        String scontentype = "image/png";
        String rootPath = path + File.separator + "docadjuntos" + File.separator + "nulo.png";
        Docentes docente = new Docentes();
        docente.setId_docente(id);
        List<Docentes> docentes = this.mi.getDetallefotoadjunto(docente);
        if (!docentes.isEmpty()) {
            Docentes fotoadjunto = docentes.stream().findFirst().get();
            scontentype = fotoadjunto.getAdjunto();
            rootPath = path + File.separator + "docadjuntos" + File.separator + fotoadjunto.getNombre_archivo();
        }
        File fnew = new File(rootPath);
        if (!fnew.exists()){
            fnew = new File(path + File.separator + "docadjuntos" + File.separator + "nulo.png");
        }
        BufferedImage originalImage = ImageIO.read(fnew);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        byte[] imageInByte = baos.toByteArray();
        imagen = "data:" + scontentype + ";base64," + Base64.encodeBase64String(imageInByte);
        return imagen;
    }
}
