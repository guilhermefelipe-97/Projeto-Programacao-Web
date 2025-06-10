package com.university.project.computadores.controller;

import com.university.project.computadores.model.Computador;
import com.university.project.computadores.repository.ComputadorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Controlador responsável pelas operações CRUD de Computador.
 * Implementa as rotas para listagem, cadastro, edição, exclusão e restauração.
 */
@Controller
public class ComputadorController {

    @Autowired
    private ComputadorRepository computadorRepository;
    
    @GetMapping("/")
    public String redirecionarParaIndex() {
        return "redirect:/index";
    }
    
    @GetMapping("/computadores")
    public String redirecionarParaComputadores() {
        return "redirect:/index";
    }
    
    /**
     * Lista todos os computadores não deletados (soft delete).
     * Rota: /index (GET)
     */
    @GetMapping("/index")
    public String listarComputadoresAtivos(Model model) {
        // Busca todos os computadores não deletados
        List<Computador> computadores = computadorRepository.findByIsDeletedIsNull();
        
        // Debug: Verifica os computadores encontrados
        System.out.println("Computadores encontrados: " + computadores.size());
        for (Computador c : computadores) {
            System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome());
        }
        
        model.addAttribute("computadores", computadores);
        model.addAttribute("pageTitle", "Página Inicial");
        return "index";
    }
    
    /**
     * Lista todos os computadores, incluindo os deletados logicamente.
     * Rota: /admin (GET)
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String listarTodosComputadores(Model model) {
        // Busca todos os computadores, incluindo os deletados
        List<Computador> computadores = computadorRepository.findAll();
        model.addAttribute("computadores", computadores);
        model.addAttribute("pageTitle", "Área Administrativa");
        return "admin";
    }
    
    /**
     * Exibe o formulário de cadastro de computador.
     * Rota: /cadastro (GET)
     */
    @GetMapping("/cadastro")
    @PreAuthorize("hasRole('ADMIN')")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("computador", new Computador());
        model.addAttribute("pageTitle", "Cadastro de Computador");
        return "cadastro";
    }
    
    /**
     * Exibe o formulário de edição de computador.
     * Rota: /editar (GET)
     */
    @GetMapping("/editar")
    @PreAuthorize("hasRole('ADMIN')")
    public String exibirFormularioEdicao(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes) {
        // Busca o computador pelo ID
        Computador computador = computadorRepository.findById(id)
                .orElse(null);
        
        // Se o computador não existir, redireciona para a página admin com mensagem de erro
        if (computador == null) {
            redirectAttributes.addFlashAttribute("erro", "Computador não encontrado.");
            return "redirect:/admin";
        }
        
        model.addAttribute("computador", computador);
        model.addAttribute("pageTitle", "Edição de Computador");
        return "cadastro"; // Reutiliza o mesmo template de cadastro
    }
    
    /**
     * Salva ou atualiza um computador.
     * Rota: /salvar (POST)
     */
    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvarComputador(@ModelAttribute("computador") Computador computador, 
                                  BindingResult result, 
                                  RedirectAttributes redirectAttributes) {
        try {
            System.out.println("=== INÍCIO DO PROCESSO DE SALVAMENTO ===");
            
            // Define a imagem antes da validação
            if (computador.getId() == null || computador.getImageUrl() == null || computador.getImageUrl().isEmpty()) {
                String imagemUrl = selecionarImagemAleatoria();
                System.out.println("Nova imagem selecionada: " + imagemUrl);
                computador.setImageUrl(imagemUrl);
            }
            
            // Valida o objeto após definir a imagem
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Computador>> violations = validator.validate(computador);
            
            if (!violations.isEmpty()) {
                System.out.println("=== ERROS DE VALIDAÇÃO ENCONTRADOS ===");
                violations.forEach(violation -> {
                    System.out.println("Campo: " + violation.getPropertyPath());
                    System.out.println("Mensagem: " + violation.getMessage());
                    result.rejectValue(violation.getPropertyPath().toString(), "", violation.getMessage());
                });
                return "cadastro";
            }
            
            System.out.println("Dados recebidos do formulário:");
            System.out.println("ID: " + computador.getId());
            System.out.println("Nome: " + computador.getNome());
            System.out.println("Marca: " + computador.getMarca());
            System.out.println("Processador: " + computador.getProcessador());
            System.out.println("Memória RAM: " + computador.getMemoriaRam());
            System.out.println("Armazenamento: " + computador.getArmazenamento());
            System.out.println("Preço: " + computador.getPreco());
            System.out.println("Descrição: " + computador.getDescricao());
            System.out.println("Imagem: " + computador.getImageUrl());
            
            System.out.println("Tentando salvar no banco de dados...");
            computador = computadorRepository.save(computador);
            System.out.println("Computador salvo com sucesso! ID: " + computador.getId());
            
            redirectAttributes.addFlashAttribute("mensagem", "Computador salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
            
            System.out.println("=== FIM DO PROCESSO DE SALVAMENTO ===");
            return "redirect:/admin";
        } catch (Exception e) {
            System.out.println("=== ERRO AO SALVAR COMPUTADOR ===");
            System.out.println("Tipo do erro: " + e.getClass().getName());
            System.out.println("Mensagem do erro: " + e.getMessage());
            e.printStackTrace();
            
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar computador: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "cadastro";
        }
    }
    
    /**
     * Realiza o soft delete de um computador.
     * Rota: /deletar (GET)
     */
    @GetMapping("/deletar")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletarComputador(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        // Busca o computador pelo ID
        Computador computador = computadorRepository.findById(id)
                .orElse(null);
        
        // Se o computador não existir, redireciona para a página index com mensagem de erro
        if (computador == null) {
            redirectAttributes.addFlashAttribute("erro", "Computador não encontrado.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/index";
        }
        
        // Realiza o soft delete (define a data atual como valor para isDeleted)
        computador.setIsDeleted(LocalDateTime.now());
        computadorRepository.save(computador);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Computador removido com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        
        return "redirect:/index";
    }
    
    /**
     * Restaura um computador deletado logicamente.
     * Rota: /restaurar (GET)
     */
    @GetMapping("/restaurar")
    @PreAuthorize("hasRole('ADMIN')")
    public String restaurarComputador(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        // Busca o computador pelo ID
        Computador computador = computadorRepository.findById(id)
                .orElse(null);
        
        // Se o computador não existir, redireciona para a página index com mensagem de erro
        if (computador == null) {
            redirectAttributes.addFlashAttribute("erro", "Computador não encontrado.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/index";
        }
        
        // Restaura o computador (define isDeleted como null)
        computador.setIsDeleted(null);
        computadorRepository.save(computador);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Computador restaurado com sucesso!");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        
        return "redirect:/index";
    }
    
    /**
     * Seleciona uma imagem aleatória da pasta static/images.
     * @return Caminho da imagem selecionada
     */
    private String selecionarImagemAleatoria() {
        // Lista de imagens disponíveis
        List<String> imagens = new ArrayList<>();
        imagens.add("/images/pc_gamer.jpg");
        imagens.add("/images/laptop_moderno.jpg");
        imagens.add("/images/desktop_office.jpg");
        imagens.add("/images/notebook_ultrabook.jpg");
        imagens.add("/images/workstation.jpg");
        imagens.add("/images/all_in_one.jpg");
        
        // Seleciona uma imagem aleatória
        Random random = new Random();
        int indice = random.nextInt(imagens.size());
        
        return imagens.get(indice);
    }
}
