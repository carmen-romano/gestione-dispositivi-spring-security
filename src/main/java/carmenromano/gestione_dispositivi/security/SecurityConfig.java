package carmenromano.gestione_dispositivi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean    //Questo bean ci servirà per poter configurare la SecurityFilterChain
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable());  // Disabilita il form di login
        httpSecurity.csrf(http -> http.disable()); //disabilita la protezione CSRF(Cross-Site Request Forgery), non utile per il nostro progetto poiché
        // porterebbe ad una serie di problematiche aggiuntive, da utilizzare in app complesse
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //disabilita le sessioni (con JWT non si utilizzano)
        httpSecurity.authorizeHttpRequests(http -> http.requestMatchers("/**").permitAll()); // Permette tutte le richieste senza autenticazione
        return httpSecurity.build(); // Costruisce e restituisce l'oggetto SecurityFilterChain configurato
    }
}
