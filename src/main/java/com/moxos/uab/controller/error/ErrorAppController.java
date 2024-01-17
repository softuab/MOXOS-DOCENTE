package com.moxos.uab.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorAppController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model modelo) {
        // Obtiene el código de estado del error
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        modelo.addAttribute("mensaje", "Ocurrio un error interno con el servidor");
        // Personaliza el manejo de errores según el código de estado
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // Manejo de error 404 (Recurso no encontrado)
                return "Error/Error";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // Manejo de error 500 (Error interno del servidor)
                return "Error/Error";
            }
        }

        // Manejo de otros errores
        return "Error/Error";
    }
}
