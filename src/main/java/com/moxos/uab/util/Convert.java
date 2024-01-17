package com.moxos.uab.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class Convert {

    public static Boolean ToBoolean(HttpServletRequest request, String value) {
        String variable = request.getParameter(value);
        if (null == variable) {
            variable = "";
        }
        return ToBoolean(variable);
    }

    public static String ToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String dateToString = df.format(date);
        return (dateToString);
    }

    public static Date ToDate(String value, String format) {
        String variable = value;
        Date date = null;
        if (null == variable) {
            variable = "";
            date = new Date();
        } else {
            try {
                date = new SimpleDateFormat(format).parse(variable);
            } catch (ParseException e) {
                date = new Date();
            }
        }
        return date;
    }

    public static int ToInteger(HttpServletRequest request, String variable) {
        variable = request.getParameter(variable);
        if ((null == variable) || ("".equals(variable))) {
            variable = "0";
        }
        return Integer.parseInt(variable);
    }

    public static Boolean ToBoolean(String value) {
        Boolean valuereturn = Boolean.FALSE;
        switch (value) {
            case "on":
                valuereturn = Boolean.TRUE;
                break;
            case "off":
                valuereturn = Boolean.FALSE;
                break;
            case "false":
                valuereturn = Boolean.FALSE;
                break;
            case "true":
                valuereturn = Boolean.TRUE;
                break;
            default:
                valuereturn = Boolean.FALSE;
                break;
        }
        return valuereturn;
    }

    public static String ToString(HttpServletRequest request, String variable) {
        variable = request.getParameter(variable);
        if (null == variable) {
            variable = "";
        }
        return variable;
    }

    public static String ToString(Object variable) {
        return String.valueOf(variable);
    }
}
