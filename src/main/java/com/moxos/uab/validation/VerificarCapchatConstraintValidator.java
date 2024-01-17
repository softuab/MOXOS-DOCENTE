package com.moxos.uab.validation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class VerificarCapchatConstraintValidator implements ConstraintValidator<VerificarCapchat, String> {

    private Boolean isOptional;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(VerificarCapchat a) {
        this.isOptional = a.optional();
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        String captcha = (String) request.getSession().getAttribute("CAPTCHA");
        return t.equals(captcha);
    }

}
