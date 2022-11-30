/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.service;

import java.util.ArrayList;
import java.util.List;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("mi")
    private MiFacade mi;

    public void setMi(MiFacade mi) {
        this.mi = mi;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        Usuarios usuario = new Usuarios();
        usuario.setApodo(username);
        Clientes cliente = this.mi.getBuscarConexion(usuario);
        if (cliente == null) {
            throw new UsernameNotFoundException("No se econtro al usuario: " + username);
        } else {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(cliente.getRol()));
            return new User(cliente.getCorreo(), cliente.getClave(), roles);
        }
    }
}
