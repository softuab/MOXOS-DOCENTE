/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.fautapo.domain.logic.MiFacade;
import java.security.Principal;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import org.apache.tomcat.util.codec.binary.Base64;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Usuarios;
import org.fautapo.util.RemoteIpHostHelper;
import static org.fautapo.util.Util.resize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    @Qualifier("mi")
    private MiFacade mi;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String userName = "";
        String ip = null; // IP del cliente
        String host = null; // Host del cliente
        if (authentication.getPrincipal() instanceof Principal) {
            userName = ((Principal) authentication.getPrincipal()).getName();
        } else {
            User userSpringSecu = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userName = userSpringSecu.getUsername();
        }
        host = request.getRemoteHost();
        Usuarios usuario = new Usuarios();
        usuario.setApodo(userName);
        Clientes cliente = this.mi.getBuscarConexion(usuario);
        cliente.setImagen(getImagen(cliente.getId_usuario()));
        Cookie c = new Cookie("userid", String.valueOf(cliente.getId_usuario()));
        ip = RemoteIpHostHelper.getRemoteIpFrom(request);
        String UserAgent = RemoteIpHostHelper.getUserAgent(request);
        String ubicacion = request.getParameter("ubicacion");
        request.getSession().setAttribute("_ubicacion", ubicacion);
        request.getSession().setAttribute("_tipo_usuario", "Docente");
        request.getSession().setAttribute("_so_browser", UserAgent);
        request.getSession().setAttribute("_ip_address", ip);
        request.getSession().setAttribute("userName", cliente.getNombres());
        request.getSession().setAttribute("__sess_cliente", cliente); // Subimos 'cliente' a la sesion 
        response.addCookie(c);
        response.sendRedirect(request.getContextPath() + "/menu.fautapo");
    }

    private String getImagen(Integer id) throws IOException {
        String imagen = "";

        String scontentype = "image/png";
        String rootPath = System.getProperty("catalina.home") + File.separator + "docadjuntos" + File.separator + "nulo.png";
        Docentes docente = new Docentes();
        docente.setId_docente(id);
        List<Docentes> docentes = this.mi.getDetallefotoadjunto(docente);
        if (!docentes.isEmpty()) {
            Docentes fotoadjunto = docentes.stream().findFirst().get();
            scontentype = fotoadjunto.getAdjunto();
            rootPath = System.getProperty("catalina.home") + File.separator + "docadjuntos" + File.separator + fotoadjunto.getNombre_archivo();
        }
        File fnew = new File(rootPath);
        BufferedImage originalImage = ImageIO.read(fnew);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resize(originalImage, 70, 70), "png", baos);
        byte[] imageInByte = baos.toByteArray();
        imagen = "data:" + scontentype + ";base64," + Base64.encodeBase64String(imageInByte);
        return imagen;
    }
}
