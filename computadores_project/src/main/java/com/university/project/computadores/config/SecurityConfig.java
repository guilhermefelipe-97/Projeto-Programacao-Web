package com.university.project.computadores.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuração do Spring Security para autenticação e autorização.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Configura o encoder de senha para usar BCrypt.
     * Tornar este método static resolve a dependência circular.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() { // <--- ADICIONADO 'static'
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o filtro de segurança HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Páginas públicas
                        .requestMatchers("/", "/index", "/login", "/cadusuario", "/salvarusuario", "/css/**", "/js/**", "/images/**").permitAll()

                        // Páginas restritas a administradores
                        .requestMatchers("/admin", "/cadastro", "/salvar", "/editar", "/deletar", "/restaurar").hasRole("ADMIN")

                        // Páginas restritas a usuários comuns
                        .requestMatchers("/verCarrinho", "/adicionarCarrinho", "/removerDoCarrinho", "/finalizarCompra").hasRole("USER")

                        // Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Configura o AuthenticationManager para usar nosso UserDetailsService e PasswordEncoder.
     * Este método agora pode usar o PasswordEncoder estático sem problemas.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()); // Chama o método estático
    }
}