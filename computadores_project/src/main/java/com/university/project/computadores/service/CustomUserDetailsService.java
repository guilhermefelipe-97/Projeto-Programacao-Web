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
        System.out.println("=== INÍCIO DO PROCESSO DE LOGIN ===");
        System.out.println("Tentando carregar usuário: " + username);
        
        try {
            // Busca o usuário pelo username
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        System.out.println("ERRO: Usuário não encontrado: " + username);
                        return new UsernameNotFoundException("Usuário não encontrado: " + username);
                    });
            
            System.out.println("SUCESSO: Usuário encontrado!");
            System.out.println("Detalhes do usuário:");
            System.out.println("- Username: " + usuario.getUsername());
            System.out.println("- É admin? " + usuario.getIsAdmin());
            System.out.println("- Permissões: " + usuario.getAuthorities());
            System.out.println("- Senha (hash): " + usuario.getPassword());
            System.out.println("=== FIM DO PROCESSO DE LOGIN ===");
            
            // Retorna o próprio objeto Usuario, que já implementa UserDetails
            return usuario;
        } catch (Exception e) {
            System.out.println("ERRO: Exceção durante o carregamento do usuário:");
            System.out.println("- Tipo: " + e.getClass().getName());
            System.out.println("- Mensagem: " + e.getMessage());
            System.out.println("=== FIM DO PROCESSO DE LOGIN (COM ERRO) ===");
            throw e;
        }
    }
}
