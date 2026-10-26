package ma.ensaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Encodeur de mots de passe (BCrypt) : utilisé par InitData et par Spring Security
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration des accès
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                // Ressources publiques
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                // Administration : ADMIN uniquement
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Gestion globale des médicaments et des stocks : ADMIN uniquement
                .requestMatchers("/medicaments/gerer/**").hasRole("ADMIN")

             // Modification des stocks et vente : PHARMACIEN et ADMIN
                .requestMatchers("/medicaments/action/**").hasAnyRole("PHARMACIEN", "ADMIN")

                // Enregistrement simple des ventes : CAISSIER et ADMIN
                .requestMatchers("/ventes/enregistrer/**").hasAnyRole("CAISSIER", "ADMIN")

                // Consultation des médicaments (lecture seule) : tous les rôles
                .requestMatchers("/medicaments/liste")
                    .hasAnyRole("ADMIN", "PHARMACIEN", "CAISSIER", "CLIENT")

                // Toute autre requête : authentification requise
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")                 // page personnalisée
                .defaultSuccessUrl("/home", true)    // redirection forcée après succès
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")   // redirection après déconnexion
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")  // page d'accès refusé
            )
            .csrf(csrf -> csrf.disable());           // désactivé pour les tests : à réactiver en production !

        return http.build();
    }
}