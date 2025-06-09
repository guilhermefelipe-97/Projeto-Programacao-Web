package com.university.project.computadores.controller;

import com.university.project.computadores.model.Usuario;
import com.university.project.computadores.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador responsável pelas operações relacionadas a usuários.
 * Implementa as rotas para login, cadastro e salvamento de usuários.
 */
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Exibe a página de login personalizada.
     * Rota: /login (GET)
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * Exibe o formulário de cadastro de usuário.
     * Rota: /cadusuario (GET)
     */
    @GetMapping("/cadusuario")
    public String exibirFormularioCadastroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadusuario";
    }
    
    /**
     * Salva um novo usuário.
     * Rota: /salvarusuario (POST)
     */
    @PostMapping("/salvarusuario")
    public String salvarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        // Verifica se há erros de validação
        if (result.hasErrors()) {
            return "cadusuario";
        }
        
        // Verifica se o username já existe
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            result.rejectValue("username", "error.usuario", "Nome de usuário já existe");
            return "cadusuario";
        }
        
        // Codifica a senha usando BCrypt
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Salva o usuário
        usuarioRepository.save(usuario);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
        
        return "redirect:/login";
    }
}
