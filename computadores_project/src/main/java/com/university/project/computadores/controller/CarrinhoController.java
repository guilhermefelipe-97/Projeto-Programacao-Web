package com.university.project.computadores.controller;

import com.university.project.computadores.model.Computador;
import com.university.project.computadores.repository.ComputadorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador responsável pelas operações do carrinho de compras.
 * Implementa as rotas para adicionar, visualizar e finalizar compras.
 */
@Controller
public class CarrinhoController {

    @Autowired
    private ComputadorRepository computadorRepository;
    
    /**
     * Adiciona um computador ao carrinho de compras.
     * Rota: /adicionarCarrinho (GET)
     */
    @GetMapping("/adicionarCarrinho")
    public String adicionarAoCarrinho(@RequestParam Long id, 
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        // Busca o computador pelo ID
        Computador computador = computadorRepository.findById(id)
                .orElse(null);
        
        // Se o computador não existir ou estiver deletado, redireciona para a página index com mensagem de erro
        if (computador == null || computador.getIsDeleted() != null) {
            redirectAttributes.addFlashAttribute("erro", "Computador não encontrado ou indisponível.");
            return "redirect:/index";
        }
        
        // Obtém o carrinho da sessão ou cria um novo se não existir
        List<Computador> carrinho = (List<Computador>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }
        
        // Adiciona o computador ao carrinho
        carrinho.add(computador);
        
        // Atualiza o carrinho na sessão
        session.setAttribute("carrinho", carrinho);
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Computador adicionado ao carrinho com sucesso!");
        
        return "redirect:/index";
    }
    
    /**
     * Exibe os itens do carrinho de compras.
     * Rota: /verCarrinho (GET)
     */
    @GetMapping("/verCarrinho")
    public String verCarrinho(HttpSession session, 
                             Model model,
                             RedirectAttributes redirectAttributes) {
        // Obtém o carrinho da sessão
        List<Computador> carrinho = (List<Computador>) session.getAttribute("carrinho");
        
        // Se o carrinho estiver vazio, redireciona para a página index com mensagem
        if (carrinho == null || carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Não existem itens no carrinho.");
            return "redirect:/index";
        }
        
        // Calcula o valor total do carrinho
        double valorTotal = carrinho.stream()
                .mapToDouble(c -> c.getPreco().doubleValue())
                .sum();
        
        // Adiciona o carrinho e o valor total ao modelo
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("valorTotal", valorTotal);
        
        return "carrinho";
    }
    
    /**
     * Finaliza a compra, invalidando a sessão.
     * Rota: /finalizarCompra (GET)
     */
    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session, 
                                 RedirectAttributes redirectAttributes) {
        // Invalida a sessão
        session.invalidate();
        
        // Adiciona mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Compra finalizada com sucesso! Obrigado por comprar conosco.");
        
        return "redirect:/index";
    }
    
    /**
     * Remove um item específico do carrinho.
     * Rota: /removerDoCarrinho (GET)
     */
    @GetMapping("/removerDoCarrinho")
    public String removerDoCarrinho(@RequestParam int index, 
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        // Obtém o carrinho da sessão
        List<Computador> carrinho = (List<Computador>) session.getAttribute("carrinho");
        
        // Verifica se o carrinho existe e se o índice é válido
        if (carrinho != null && index >= 0 && index < carrinho.size()) {
            // Remove o item do carrinho
            carrinho.remove(index);
            
            // Atualiza o carrinho na sessão
            session.setAttribute("carrinho", carrinho);
            
            // Adiciona mensagem de sucesso
            redirectAttributes.addFlashAttribute("mensagem", "Item removido do carrinho com sucesso!");
        } else {
            // Adiciona mensagem de erro
            redirectAttributes.addFlashAttribute("erro", "Não foi possível remover o item do carrinho.");
        }
        
        return "redirect:/verCarrinho";
    }
}
