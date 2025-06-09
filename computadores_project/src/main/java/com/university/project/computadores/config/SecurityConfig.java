package com.university.project.computadores.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o provedor de autenticação.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura o gerenciador de autenticação.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    /**
     * Configura o filtro de segurança HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Páginas públicas
                        .requestMatchers("/", "/index", "/login", "/cadusuario", "/salvarusuario", 
                                       "/css/**", "/js/**", "/images/**", "/h2-console/**", "/error", "/.well-known/**").permitAll()

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
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .requireCsrfProtectionMatcher(request -> {
                            String method = request.getMethod();
                            return !method.equals("GET") && !method.equals("HEAD") && !method.equals("TRACE") && !method.equals("OPTIONS");
                        })
                )
                .headers(headers -> headers
                        .frameOptions().sameOrigin()
                );

        return http.build();
    }
}