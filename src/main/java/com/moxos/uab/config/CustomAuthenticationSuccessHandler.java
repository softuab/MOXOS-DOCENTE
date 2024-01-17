package com.moxos.uab.config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moxos.uab.mybatis.entity.Auditoria;
import com.moxos.uab.mybatis.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.context.SecurityContextHolder;

import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.util.*;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MiFacade mi;

    @Value("${app.upload.path}")
    private String path;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Clientes cliente;
        if (authentication.getPrincipal() instanceof Clientes) {
            cliente = ((Clientes) authentication.getPrincipal());
        } else {
            User userSpringSecu = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userSpringSecu.getUsername();
            Usuarios usuario = new Usuarios();
            usuario.setApodo(userName);
            cliente = this.mi.getBuscarConexion(usuario);
        }
        cliente.setImagen(getImagen(cliente.getId_usuario()));
        request.getSession().setAttribute("userName", cliente.getNombres());
        request.getSession().setAttribute("__sess_cliente", cliente);
        registrarEvento(cliente.getId_usuario(), request);
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private void registrarEvento(Integer id, HttpServletRequest request) throws SocketException, UnknownHostException {
        HttpSession session = request.getSession();
        String UserAgent = RemoteIpHostHelper.getUserAgent(request);
        String ubicacion = request.getParameter("ubicacion");
        String idaddres = RemoteIpHostHelper.getRemoteIpFrom(request);
        String macaddres = RemoteIpHostHelper.getMacAddress();
        Auditoria auditoria = new Auditoria();
        auditoria.setAccion(AccionEvento.INICIO_SESION.getValue());
        auditoria.setApp_name(AppName.DOCENTE.getValue());
        auditoria.setId_session(session.getId());
        auditoria.setIpaddres(idaddres);
        auditoria.setId_usuario(id);
        auditoria.setUbicacion(ubicacion);
        auditoria.setTipo_usuario("DOCENTE");
        auditoria.setUltima_session(new Date());
        auditoria.setUseragent(UserAgent);
        auditoria.setMacaddres(macaddres);
        mi.guardarEvento(auditoria);
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
        ImageIO.write(Utils.resize(originalImage, 70, 70), "png", baos);
        byte[] imageInByte = baos.toByteArray();
        imagen = "data:" + scontentype + ";base64," + Base64.encodeBase64String(imageInByte);
        return imagen;
    }
}
