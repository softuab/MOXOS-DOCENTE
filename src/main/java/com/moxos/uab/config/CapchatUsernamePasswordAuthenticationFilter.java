package com.moxos.uab.config;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moxos.uab.util.Convert;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CapchatUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String valor = request.getParameter("captcha");
        boolean twofctor = Convert.ToBoolean(request, "twofactor");
        String captcha = (String) request.getSession().getAttribute("CAPTCHA");

        if (twofctor) {
            return super.attemptAuthentication(request, response);
        }

        if (valor == null || !valor.equals(captcha)) {
            this.reCaptchaError(request, response, "ReCaptcha failed");
            return null;
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    private void reCaptchaError(HttpServletRequest request, HttpServletResponse response, String errorMsg) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login?error=2");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new AuthenticationServiceException("Captcha failed : " + errorMsg);
        } catch (IOException e) {
            throw new AuthenticationServiceException("captcha failed : " + errorMsg);
        }
    }
}
