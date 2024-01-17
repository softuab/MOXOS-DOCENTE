package com.moxos.uab.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.moxos.uab.model.models.login.LoginRequest;
import com.moxos.uab.model.models.login.RecoveryPasswordRequest;
import com.moxos.uab.model.models.login.TokenRecoveryRequest;
import com.moxos.uab.mybatis.entity.Docentes;
import com.moxos.uab.mybatis.entity.Tokens;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.service.EmailService;
import com.moxos.uab.util.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private MiFacade mi;

    @RequestMapping("/signin")
    public String signin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/menu";
        }
        return "Login/SigIn";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute("apodo") String apodo, Model modelo) {
        Docentes docentes = mi.getUsuario(apodo.trim());
        if (docentes == null) {
            modelo.addAttribute("authenticationError", "No existe el usuario");
            return "Login/SigIn";
        }
        modelo.addAttribute("login", apodo);
        String params = "?apodo=" + apodo + "&twofactor=" + docentes.isActivar2fa();
        return "redirect:/login" + params;
    }

    @RequestMapping("/login")
    public String loginPage(@ModelAttribute("model") LoginRequest login, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/menu";
        }
        if (login.getError() != null) {
            if (login.getError().equals(1)) {
                model.addAttribute("authenticationError", "contraseña incorrecto");
                model.addAttribute("twofactor", login.isTwofactor());
                model.addAttribute("login", login.getApodo());
                return "Login/Login";
            }
            if (login.getError().equals(2)) {
                model.addAttribute("authenticationError", "Captcha incorrecto");
                model.addAttribute("twofactor", login.isTwofactor());
                model.addAttribute("login", login.getApodo());
                return "Login/Login";
            }
            if (login.getError().equals(3)) {
                model.addAttribute("authenticationError", "No existe el usuario");
                model.addAttribute("twofactor", login.isTwofactor());
                return "redirect:/signin";
            }
            if (login.getError().equals(4)) {
                model.addAttribute("authenticationError", "Se encueantra bloqueado temporalmente por multiples intentos incorrecto al iniciar sesion");
                model.addAttribute("twofactor", login.isTwofactor());
                model.addAttribute("login", "");
                return "Login/Login";
            }
            if (login.getError().equals(5)) {
                model.addAttribute("authenticationError", "Codigo de verificacion incorrecto");
                model.addAttribute("login", login.getApodo());
                model.addAttribute("twofactor", login.isTwofactor());
                return "Login/Login";
            }
        }

        if (login.getApodo() == null)
            return "redirect:/signin";
        if (login.getApodo().isBlank() || login.getApodo().isEmpty())
            return "redirect:/signin";

        model.addAttribute("login", login.getApodo());
        model.addAttribute("twofactor", login.isTwofactor());
        return "Login/Login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "Login/SigIn";
    }
    @GetMapping("/logout")
    public String getlogout(HttpServletRequest requests) {
        HttpSession session = requests.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "Login/SigIn";
    }
    @GetMapping("/expired")
    public String expired() {
        // Lógica para invalidar la sesión anterior si existe
        SecurityContextHolder.getContext().setAuthentication(null);
        return "Login/SigIn";
    }

}
