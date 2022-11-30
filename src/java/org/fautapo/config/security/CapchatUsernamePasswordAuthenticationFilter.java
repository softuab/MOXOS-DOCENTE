/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.config.security;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author FNZABALETAA
 */
public class CapchatUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String valor = request.getParameter("captcha");
        String captcha = (String) request.getSession().getAttribute("CAPTCHA");

        if (valor == null || !valor.equals(captcha)) {
            this.reCaptchaError(request, response, "ReCaptcha failed");
            return null;
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    private void reCaptchaError(HttpServletRequest request, HttpServletResponse response, String errorMsg) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.fautapo?error=2");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new AuthenticationServiceException("Captcha failed : " + errorMsg);
        } catch (IOException e) {
            throw new AuthenticationServiceException("captcha failed : " + errorMsg);
        }
    }
}
