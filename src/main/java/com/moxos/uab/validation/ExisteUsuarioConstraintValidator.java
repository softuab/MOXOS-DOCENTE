package com.moxos.uab.validation;


import com.moxos.uab.mybatis.logic.MiFacade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExisteUsuarioConstraintValidator implements ConstraintValidator<ExisteUsuario, String> {

    @Autowired
    private MiFacade mi;

    public void setMi(MiFacade mi) {
        this.mi = mi;
    }
    private Boolean isOptional;

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        return mi.existeUsuario(t);
    }

    @Override
    public void initialize(ExisteUsuario a) {
        this.isOptional = a.optional();
    }

}
