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
        System.out.println("=== INÍCIO DO PROCESSO DE CADASTRO ===");
        System.out.println("Dados recebidos:");
        System.out.println("- Username: " + usuario.getUsername());
        System.out.println("- É admin? " + usuario.getIsAdmin());
        
        // Verifica se há erros de validação
        if (result.hasErrors()) {
            System.out.println("ERRO: Validação falhou");
            result.getAllErrors().forEach(error -> System.out.println("- " + error.getDefaultMessage()));
            return "cadusuario";
        }
        
        // Verifica se o username já existe
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            System.out.println("ERRO: Username já existe");
            result.rejectValue("username", "error.usuario", "Nome de usuário já existe");
            return "cadusuario";
        }
        
        // Codifica a senha usando BCrypt
        String senhaOriginal = usuario.getPassword();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        System.out.println("Senha codificada: " + usuario.getPassword());
        
        // Salva o usuário
        try {
            usuarioRepository.save(usuario);
            System.out.println("SUCESSO: Usuário salvo com ID: " + usuario.getId());
            
            // Adiciona mensagem de sucesso
            redirectAttributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
            
            System.out.println("=== FIM DO PROCESSO DE CADASTRO (SUCESSO) ===");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao salvar usuário");
            System.out.println("- Tipo: " + e.getClass().getName());
            System.out.println("- Mensagem: " + e.getMessage());
            System.out.println("=== FIM DO PROCESSO DE CADASTRO (ERRO) ===");
            
            result.rejectValue("username", "error.usuario", "Erro ao salvar usuário: " + e.getMessage());
            return "cadusuario";
        }
    }
}
