package com.university.project.computadores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    /**
     * Exibe a página "Sobre" com a descrição da loja.
     */
    @GetMapping("/sobre")
    public String sobre(Model model) {
        model.addAttribute("pageTitle", "Sobre Nós");
        model.addAttribute("descricao", "TechStore é a sua loja especializada em computadores e tecnologia, oferecendo os melhores produtos com preços competitivos.");
        return "sobre";
    }
    
    /**
     * Exibe a página de contato com endereço, telefone, e-mail e horário de funcionamento.
     */
    @GetMapping("/contato")
    public String contato(Model model) {
        model.addAttribute("pageTitle", "Contato");
        model.addAttribute("endereco", "EAJ/UFRN - Esc. Agrícola de Jundiaí - Ens. Téc. e Sup. - Rodovia RN 160, Km 03 s/n Distrito de, Macaíba - RN, 59280-000");
        model.addAttribute("telefone", "(11) 99999-9999");
        model.addAttribute("email", "contato@lojacomputadores.com");
        model.addAttribute("horario", "Seg - Sex: 9:00 - 18:00");
        return "contato";
    }
} 