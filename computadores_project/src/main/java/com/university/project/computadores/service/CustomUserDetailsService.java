package com.university.project.computadores.service;

import com.university.project.computadores.model.Usuario;
import com.university.project.computadores.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por carregar os detalhes do usuário para autenticação.
 * Implementa UserDetailsService do Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carrega um usuário pelo nome de usuário (username).
     * Método exigido pela interface UserDetailsService.
     * 
     * @param username Nome de usuário a ser buscado
     * @return UserDetails do usuário encontrado
     * @throws UsernameNotFoundException Se o usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo username
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        
        // Retorna o próprio objeto Usuario, que já implementa UserDetails
        return usuario;
    }
}
