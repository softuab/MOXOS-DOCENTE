/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatConstraintValidator implements ConstraintValidator<DateFormat, String> {

    private String format;
    private String message;

    @Override
    public void initialize(DateFormat dateformat) {
        format = dateformat.format();
        message = dateformat.message();
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        boolean correcto = false;
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat(format);
            formatoFecha.setLenient(false);
            //Comprobación de la fecha
            formatoFecha.parse(t);
            correcto = true;
        } catch (ParseException e) {
            correcto = false;
        }
        return correcto;
    }

}
