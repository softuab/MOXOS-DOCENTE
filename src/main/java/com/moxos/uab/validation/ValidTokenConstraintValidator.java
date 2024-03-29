package com.moxos.uab.validation;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.moxos.uab.mybatis.entity.Tokens;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidTokenConstraintValidator implements ConstraintValidator<ValidToken, Object> {

    @Autowired
    private MiFacade mi;

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final ValidToken constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        List<Tokens> listtokens = new ArrayList<>();
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            Tokens token = new Tokens();
            token.setId_docente(Integer.valueOf((String) firstObj));
            token.setToken(secondObj.toString());
            listtokens = mi.getListartokendocente(token);
        } catch (final Exception ignore) {
            // ignore
        }
        if (listtokens.isEmpty()) {
            valid = false;
        } else {
            List<Tokens> TokensValidos = listtokens.stream().filter(p -> !p.isExpired()).collect(Collectors.toList());
            valid = !TokensValidos.isEmpty();
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }

}
