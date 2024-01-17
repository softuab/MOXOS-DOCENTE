package com.moxos.uab.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;


public class DocumentoValidoValidator implements ConstraintValidator<DocumentoValido, Object> {

    private String tipodocumento;
    private String dip;
    private String message;

    @Override
    public void initialize(final DocumentoValido constraintAnnotation) {
        tipodocumento = constraintAnnotation.tipodocumento();
        dip = constraintAnnotation.dip();
        message = constraintAnnotation.message();
    }

    private boolean EsValidoFormato(String tipodocumento, String dip) {
        boolean status = false;
        switch (tipodocumento) {
            case "CARNET IDENTIDAD":
                if (dip.contains("-")) {
                    String[] data = dip.split("-");
                    if (data.length <= 1) {
                        status = false;
                    } else {
                        status = data[0].matches("[0-9]*") && data[1].length() == 2;
                    }

                } else {
                    status = dip.matches("[0-9]*");
                }
                break;
            case "PASAPORTE":
                status = true;
                break;
            default:
                status = false;
        }
        return status;
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object firstObj = BeanUtils.getProperty(value, tipodocumento);
            final Object secondObj = BeanUtils.getProperty(value, dip);

            valid = EsValidoFormato(firstObj.toString(), secondObj.toString());
        } catch (final Exception ignore) {
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(dip)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
