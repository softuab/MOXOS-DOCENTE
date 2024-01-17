package com.moxos.uab.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.moxos.uab.util.Utils;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author FNZABALETAA
 */
public class CheckDetailValidConstraintValidator implements ConstraintValidator<CheckDetailValid, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final CheckDetailValid constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            if (firstObj != null) {
                if (!Utils.isNullOrBlank(firstObj.toString())) {
                    if (firstObj.toString().equals("on")) {
                        if (secondObj == null) {
                            if (Utils.isNullOrBlank(secondObj.toString())) {
                                valid = false;
                            } else {
                                valid = true;
                            }
                        } else {
                            valid = true;
                        }
                    } else {
                        valid = true;
                    }
                } else {
                    valid = true;
                }
            } else {
                valid = true;
            }
        } catch (final Exception ignore) {
            // ignore
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
