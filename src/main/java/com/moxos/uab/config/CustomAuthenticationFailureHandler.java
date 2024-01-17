package com.moxos.uab.config;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Usuarios;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private MiFacade mi;
    @Value("${app-numero-intentos}")
    private Integer intentos;
    @Value("${app-minutos-bloqueo}")
    private Integer minutos;
    private final Map<String, Integer> attempts = new HashMap<>();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = exception.getMessage();
        String username = request.getParameter("apodo");
        if (message.contains("{5}:")) {
            request.getRequestDispatcher("/login?error=5").forward(request, response);
            return;
        }
        if (message.contains("{4}:"))
            request.getRequestDispatcher("/login?error=4").forward(request, response);
        else if (attempts.containsKey(username)) {
            int count = attempts.get(username);
            count++;
            attempts.put(username, count);
            int maxAttempts = intentos;
            if (count >= maxAttempts) {
                // Bloquear la cuenta del usuario por 1 minuto
                Usuarios usuario = new Usuarios();
                usuario.setApodo(username);
                Clientes user = mi.getBuscarConexion(usuario);
                Date newDate = addMinutos(minutos);
                user.setBloqueado(true);
                user.setFecha_bloqueado(newDate);
                mi.bloquearUsuario(user);
                request.getRequestDispatcher("/login?error=4").forward(request, response);
            } else {
                request.getRequestDispatcher("/login?error=1").forward(request, response);
            }
        } else {
            attempts.put(username, 1);
            if (message.contains("{3}:")) {
                request.getRequestDispatcher("/login?error=3").forward(request, response);
            } else if (message.contains("{4}:")) {
                request.getRequestDispatcher("/login?error=4").forward(request, response);
            } else {
                request.getRequestDispatcher("/login?error=1").forward(request, response);
            }
        }
    }

    private Date addMinutos(int minutos) {
        Date fechaActual = new Date();
        long horaActualMillis = fechaActual.getTime();
        long nuevaHoraMillis = horaActualMillis + (minutos * 60000);
        return new Date(nuevaHoraMillis);
    }
}
