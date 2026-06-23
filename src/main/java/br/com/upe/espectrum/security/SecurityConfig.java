package br.com.upe.espectrum.security;

import br.com.upe.espectrum.entities.enums.Perfil;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,  "/cadastro/admin").permitAll()
                        //cadastros Terapeuta
                        .requestMatchers(HttpMethod.POST, "/cadastro/cadastro-admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cadastro/auto-cadastro").permitAll()

                        .requestMatchers(HttpMethod.POST,  "/cadastro//paciente-responsavel").hasAnyRole("TERAPEUTA", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/terapeuta/{idTerapeuta}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/pendentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/terapeuta/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/terapeuta/{id}/reativar").hasRole("ADMIN")

                        //buscando pacientes
                        .requestMatchers(HttpMethod.GET, "/pacientes").authenticated()

                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
