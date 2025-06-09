package com.university.project.computadores.controller;

import com.university.project.computadores.model.Computador;
import com.university.project.computadores.repository.ComputadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Controlador responsável pelas operações CRUD de Computador.
 * Implementa as rotas para listagem, cadastro, edição, exclusão e restauração.
 */
@Controller
public class ComputadorController {

    @Autowired
    private ComputadorRepository computadorRepository;
    
    /**
     * Lista todos os computadores não deletados (soft delete).
     * Rota: /index (GET)
     */
    @GetMapping("/index")
    public String listarComputadoresAtivos(Model model) {
        // Busca todos os computadores não deletados
        List<Computador> computadores = computadorRepository.findByIsDeletedIsNull();
        model.addAttribute("computadores", computadores);
        return "index";
    }
    
    /**
     * Lista todos os computadores, incluindo os deletados logicamente.
     * Rota: /admin (GET)
     */
    @GetMapping("/admin")
    public String listarTodosComputadores(Model model) {
        // Busca todos os computadores, incluindo os deletados
        List<Computador> computadores = computadorRepository.findAll();
        model.addAttribute("computadores", computadores);
        return "admin";
    }
    
    /**
     * Exibe o formulário de cadastro de computador.
     * Rota: /cadastro (GET)
     */
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("computador", new Computador());
        return "cadastro";
    }
    
    /**
     * Exibe o formulário de edição de computador.
     * Rota: /editar (GET)
     */
    @GetMapping("/editar")
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
        return "cadastro"; // Reutiliza o mesmo template de cadastro
    }
    
    /**
     * Salva ou atualiza um computador.
     * Rota: /salvar (POST)
     */
    @PostMapping("/salvar")
    public String salvarComputador(@Valid @ModelAttribute("computador") Computador computador, 
                                  BindingResult result, 
                                  RedirectAttributes redirectAttributes) {
        // Verifica se há erros de validação
        if (result.hasErrors()) {
            return "cadastro"; // Retorna ao formulário com os erros
        }
        
        // Se for um novo computador (sem ID), seleciona uma imagem aleatória
        if (computador.getId() == null) {
            computador.setImageUrl(selecionarImagemAleatoria());
        }
        
        // Salva o computador
        computadorRepository.save(computador);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Computador salvo com sucesso!");
        
        return "redirect:/admin";
    }
    
    /**
     * Realiza o soft delete de um computador.
     * Rota: /deletar (GET)
     */
    @GetMapping("/deletar")
    public String deletarComputador(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        // Busca o computador pelo ID
        Computador computador = computadorRepository.findById(id)
                .orElse(null);
        
        // Se o computador não existir, redireciona para a página admin com mensagem de erro
        if (computador == null) {
            redirectAttributes.addFlashAttribute("erro", "Computador não encontrado.");
            return "redirect:/admin";
        }
        
        // Realiza o soft delete (define a data atual como valor para isDeleted)
        computador.setIsDeleted(new Date().getTime());
        computadorRepository.save(computador);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Computador removido com sucesso!");
        
        return "redirect:/admin";
    }
    
    /**
     * Restaura um computador deletado logicamente.
     * Rota: /restaurar (GET)
     */
    @GetMapping("/restaurar")
    public String restaurarComputador(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        // Busca o computador pelo ID
        Computador computador = computadorRepository.findById(id)
                .orElse(null);
        
        // Se o computador não existir, redireciona para a página admin com mensagem de erro
        if (computador == null) {
            redirectAttributes.addFlashAttribute("erro", "Computador não encontrado.");
            return "redirect:/admin";
        }
        
        // Restaura o computador (define isDeleted como null)
        computador.setIsDeleted(null);
        computadorRepository.save(computador);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Computador restaurado com sucesso!");
        
        return "redirect:/admin";
    }
    
    /**
     * Seleciona uma imagem aleatória da pasta static/images.
     * @return Caminho da imagem selecionada
     */
    private String selecionarImagemAleatoria() {
        // Lista de imagens disponíveis (em um cenário real, estas imagens estariam na pasta static/images)
        List<String> imagens = new ArrayList<>();
        imagens.add("/images/computador1.jpg");
        imagens.add("/images/computador2.jpg");
        imagens.add("/images/computador3.jpg");
        imagens.add("/images/computador4.jpg");
        imagens.add("/images/computador5.jpg");
        
        // Seleciona uma imagem aleatória
        Random random = new Random();
        int indice = random.nextInt(imagens.size());
        
        return imagens.get(indice);
    }
}
