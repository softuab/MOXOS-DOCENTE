/* global toastr */

function ValidarSinumero(obj, input, namebutton, ri, rs) {
    var value = obj;
    if (!/^([0-9])*$/.test(value)) {
        input.classList.remove("checkinput");
        input.classList.add("errorinput");
        document.getElementById(namebutton).disabled = true;
        jQuery(function () {
            $.notify({
                title: "Numero invalido : ",
                message: "Debe ingresar valor numerico!",
                icon: 'fas fa-exclamation-triangle'}, {
                allow_dismiss: false,
                placement: {
                    from: "top",
                    align: "right"
                }
            });
        });
        (jQuery);
    } else
    {
        if ((value < ri) || (value > rs)) {
            input.classList.remove("checkinput");
            input.classList.add("errorinput");
            document.getElementById(namebutton).disabled = true;
            jQuery(function () {
                $.notify({
                    title: "Numero invalido : ",
                    message: "Ocurrio un error el numero : " + value + "  fuera de rango, debe estar entre : " + ri + " y " + rs,
                    icon: 'fas fa-exclamation-triangle'
                }, {
                    allow_dismiss: false,
                    placement: {
                        from: "top",
                        align: "right"
                    }
                });
            });
            (jQuery);
        } else
        {
            document.getElementById(namebutton).disabled = false;
            input.classList.remove("errorinput");
            input.classList.add("checkinput");
        }

    }
}

function validarNota(obj, ri, rs) {
    res = validar(obj, "9");
    if (res == true) {
        valor = parseInt(obj.value);
        if ((valor < ri) || (valor > rs)) {
            toastr.error("Ocurrio un error", "el numero : " + obj.value + " fuera de rango, debe estar entre : " + ri + " y " + rs);
            return false;
        }
    }
    return true;
}
function validar(obj, tipo) {
    dato = obj.value;
    cad = dato.split("");
    if (obj.value == "") {
        return false;
    } else {
        switch (tipo) {
            case '9' :
                for (i = 0; i < cad.length; i++) {
                    if ((cad[i] > '9') || (cad[i] < '0')) {
                        toastr.error("el dato : '" + dato + "'  no es un numero valido");
                        return false;
                    }
                }
                break;
            case 'A' :
                for (i = 0; i < cad.length; i++) {
                    if (!((cad[i] == ' ') || ((cad[i] >= 'a') && (cad[i] <= 'z')) || ((cad[i] >= 'A') && (cad[i] <= 'Z')) || (cad[i] == '.'))) {
                        toastr.error("el dato : '" + dato + "'  no es parte del alfabeto valido");
                        return false;
                    }
                }
                break;
            case 'A9':
                for (i = 0; i < cad.length; i++) {
                    if (!((cad[i] == '%') || (cad[i] == ' ') || ((cad[i] >= '0') && (cad[i] <= '9')) || ((cad[i] >= 'a') && (cad[i] <= 'z')) || ((cad[i] >= 'A') && (cad[i] <= 'Z')))) {
                        toastr.error("el dato : '" + dato + "'  contiene caracteres especiales no validos");
                        return false;
                    }
                }
                break;
            case '9.0':
                for (i = 0; i < cad.length; i++) {
                    if (!(((cad[i] >= '0') && (cad[i] <= '9')) || ((cad[i] == ',') || (cad[i] == '.')))) {
                        toastr.error("el dato : '" + dato + "'  contiene caracteres especiales no validos");
                        return false;
                    }
                }
                break;

        }
    }
    return true;
}

