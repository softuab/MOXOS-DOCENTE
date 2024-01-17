package com.moxos.uab.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.entity.Usuarios;
import com.moxos.uab.mybatis.logic.MiFacade;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MiFacade mi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = new Usuarios();
        usuario.setApodo(username);
        Clientes cliente = this.mi.getBuscarConexion(usuario);
        if (cliente == null) {
            throw new BadCredentialsException("{3}:" + username);
        } else {
            if (cliente.isBloqueado()) {
                if (EstaLiberado(cliente.getFecha_bloqueado(), new Date())) {
                    cliente.setBloqueado(false);
                    mi.bloquearUsuario(cliente);
                    List<GrantedAuthority> roles = new ArrayList<>();
                    roles.add(new SimpleGrantedAuthority(cliente.getRol()));
                    return new User(cliente.getCorreo(), cliente.getClave(), roles);
                } else
                    throw new BadCredentialsException("{4}:" + username);
            } else {
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(cliente.getRol()));
                return new User(cliente.getCorreo(), cliente.getClave(), roles);
            }
        }
    }

    private boolean EstaLiberado(Date inicio, Date fin) {
        return fin.after(inicio);
    }
}
