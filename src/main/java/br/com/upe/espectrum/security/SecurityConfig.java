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
                        .requestMatchers(HttpMethod.GET, "/auth/verificar-email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/recuperar-senha").permitAll()
                        .requestMatchers(HttpMethod.POST,  "/cadastro/terapeuta").permitAll()
                        .requestMatchers(HttpMethod.POST,  "/cadastro/admin").permitAll()
                        //cadastros Terapeuta
                        .requestMatchers(HttpMethod.POST, "/cadastro/cadastro-admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cadastro/auto-cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST,  "/cadastro/paciente-responsavel").hasAnyRole("TERAPEUTA", "ADMIN")
                        .requestMatchers(HttpMethod.POST,  "/cadastro/professor").hasAnyRole("TERAPEUTA", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/terapeuta/{idTerapeuta}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/iniciar/paciente/{pacienteId}/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA")
                        .requestMatchers(HttpMethod.PUT,"/{sessaoId}/finalizar/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA")
                        .requestMatchers(HttpMethod.GET,"/{sessaoId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/paciente/{pacienteId}").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/{sessaoId}/finalizar/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA")
                        .requestMatchers(HttpMethod.POST,"/{sessaoId}/salvar/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        .requestMatchers(HttpMethod.PUT,"/{atividadeId}/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        .requestMatchers(HttpMethod.GET,"/{atividadeId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categoria/{categoriaSessaoId}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/sessao/{sessaoId}/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA")
                        .requestMatchers(HttpMethod.POST,"/categoria/{categoriaSessaoId}/usuario/{usuarioId}").hasAnyRole("ADMIN","TERAPEUTA")
                        .requestMatchers(HttpMethod.GET,"/sessao/{sessaoId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categoria/{categoriaSessaoId}").permitAll()











                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/pendentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/resumo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/todos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/terapeuta/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/terapeuta/{id}/reativar").hasRole("ADMIN")

                        //vinculação de pacientes a terapeutas
                        .requestMatchers(HttpMethod.GET, "/vinculos/terapeuta/{idTerapeuta}/pacientes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vinculos/terapeuta/{idTerapeuta}/pacientes-disponiveis").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vinculos/meus-pacientes").hasRole("TERAPEUTA")
                        .requestMatchers(HttpMethod.DELETE, "/vinculos/escolares/{idVinculo}").hasAnyRole("TERAPEUTA", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vinculos/{idVinculo}").hasRole("ADMIN")

                        //dashboard admin
                        .requestMatchers(HttpMethod.GET, "/admin/dashboard").hasRole("ADMIN")

                        //buscando pacientes
                        .requestMatchers(HttpMethod.GET, "/pacientes").authenticated()

                        //buscando professores
                        .requestMatchers(HttpMethod.GET, "/professores").hasAnyRole("TERAPEUTA", "ADMIN")

                        //suporte
                        .requestMatchers(HttpMethod.POST, "/suporte").authenticated()

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
