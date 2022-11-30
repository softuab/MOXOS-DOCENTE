<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="es"> 
    <body class="app sidebar-mini rtl"> 
        <div class="row">  
            <div class="col"> 
                <div class="mb-3">
                    <label for="username">Usuario</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">@</span>
                        </div>
                        <input   id="usernamemoodle" type="text" value="${user.username}" readonly class="form-control" >
                        <div class="invalid-feedback">
                            <span id="erroruser">Necesita definir un usuario.</span>
                        </div>   
                    </div>
                </div>
                <input type="hidden" id="idnumber" value="${user.idnumber}"> 
                <input type="hidden" id="firstname" value="${user.firstname}"> 
                <input type="hidden" id="lastname" value="${user.lastname}"> 
                <input type="hidden" id="email" value="${user.email}"> 
                <input type="hidden" id="iduser" value="${user.id}"> 
                <input type="hidden" id="indice" value="${indice}"> 
            </div> 
        </div>
        <div class="row">
            <div class="col">
                <label for="contrasenia">Contraseña</label>
                <input type="password" class="form-control" id="contrasenia">
                <div id="errorpassword" ></div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label for="repetircontrasenia">Repetir contraseña</label>
                <input type="password" class="form-control" id="repetircontrasenia" >
                <div id="errorrevalidate" ></div>
            </div>
        </div> 
    </body>
</html>