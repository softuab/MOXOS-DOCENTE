package com.moxos.uab.model.models.login;

import lombok.Data;


@Data
public class LoginRequest {
    private String apodo;
    private String clave;
    private String captcha;
    private Integer error;
    private String ubicacion;
    private boolean twofactor;
}
