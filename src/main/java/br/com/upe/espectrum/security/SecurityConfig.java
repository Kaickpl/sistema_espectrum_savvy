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
                        .requestMatchers(HttpMethod.POST, "/cadastro/cadastro-admin").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.POST, "/cadastro/auto-cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST,  "/cadastro/paciente-responsavel").hasAnyRole("TERAPEUTA", "SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.POST,  "/cadastro/professor").hasAnyRole("TERAPEUTA", "SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.PUT, "/admin/terapeuta/{idTerapeuta}").hasRole("SUPERVISOR_ESTAGIO")
                        // aplicação do protocolo: terapeuta, supervisor, professor e responsável podem aplicar
                        .requestMatchers(HttpMethod.POST,"/api/sessao/iniciar/paciente/{pacienteId}").hasAnyRole("SUPERVISOR_ESTAGIO","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        .requestMatchers(HttpMethod.POST,"/api/sessao/{sessaoId}/salvar").hasAnyRole("SUPERVISOR_ESTAGIO","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        .requestMatchers(HttpMethod.PUT,"/api/atividade/{atividadeId}").hasAnyRole("SUPERVISOR_ESTAGIO","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        // finalização do protocolo: apenas terapeuta e supervisor, professor/responsável NÃO podem finalizar
                        .requestMatchers(HttpMethod.PUT,"/api/sessao/{sessaoId}/finalizar").hasAnyRole("SUPERVISOR_ESTAGIO","TERAPEUTA")
                        .requestMatchers(HttpMethod.GET,"/api/sessao/{sessaoId}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/sessao/paciente/{pacienteId}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/atividade/{atividadeId}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/atividade/categoria/{categoriaSessaoId}").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/comentario/sessao/{sessaoId}").hasAnyRole("SUPERVISOR_ESTAGIO","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        .requestMatchers(HttpMethod.POST,"/api/comentario/categoria/{categoriaSessaoId}").hasAnyRole("SUPERVISOR_ESTAGIO","TERAPEUTA","PROFESSOR","RESPONSAVEL")
                        .requestMatchers(HttpMethod.GET,"/api/comentario/sessao/{sessaoId}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/comentario/categoria/{categoriaSessaoId}").authenticated()










                        .requestMatchers(HttpMethod.GET, "/vinculos").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/pendentes").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/resumo").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.GET, "/terapeuta/admin/todos").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.DELETE, "/terapeuta/{id}").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.PATCH, "/terapeuta/{id}/reativar").hasRole("SUPERVISOR_ESTAGIO")

                        //vinculação de pacientes a terapeutas
                        .requestMatchers(HttpMethod.GET, "/vinculos/terapeuta/{idTerapeuta}/pacientes").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.GET, "/vinculos/terapeuta/{idTerapeuta}/pacientes-disponiveis").hasRole("SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.GET, "/vinculos/meus-pacientes").hasAnyRole("TERAPEUTA", "PROFESSOR", "RESPONSAVEL")
                        .requestMatchers(HttpMethod.DELETE, "/vinculos/escolares/{idVinculo}").hasAnyRole("TERAPEUTA", "SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.DELETE, "/vinculos/{idVinculo}").hasRole("SUPERVISOR_ESTAGIO")

                        //dashboard admin
                        .requestMatchers(HttpMethod.GET, "/admin/dashboard").hasRole("SUPERVISOR_ESTAGIO")

                        //buscando pacientes
                        .requestMatchers(HttpMethod.GET, "/pacientes").authenticated()
                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/pacientes/{id}").hasAnyRole("TERAPEUTA", "SUPERVISOR_ESTAGIO")
                        .requestMatchers(HttpMethod.DELETE, "/pacientes/{id}").hasAnyRole("TERAPEUTA", "SUPERVISOR_ESTAGIO")

                        //buscando professores
                        .requestMatchers(HttpMethod.GET, "/professores").hasAnyRole("TERAPEUTA", "SUPERVISOR_ESTAGIO")

                        //suporte
                        .requestMatchers(HttpMethod.POST, "/suporte").authenticated()

                        // TrocarSenha
                        .requestMatchers(HttpMethod.POST, "/trocarSenha/**").permitAll()



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
