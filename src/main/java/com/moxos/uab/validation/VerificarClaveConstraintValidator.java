package com.moxos.uab.validation;

import com.moxos.uab.mybatis.entity.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerificarClaveConstraintValidator implements ConstraintValidator<VerificarClave, String> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    private Boolean isOptional;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @Override
    public void initialize(VerificarClave a) {
        this.isOptional = a.optional();
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        boolean isPasswordMatches = bcryptEncoder.matches(t, getUsuario().getClave());
        return isPasswordMatches;
    }

}
