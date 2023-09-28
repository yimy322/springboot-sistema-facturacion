/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccesHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author Yimy
 */
@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig {

    //para el mensaje de usuario logeado
    @Autowired
    private LoginSuccesHandler successHanler;
    
    //Para la conexion a la base de datos
//    @Autowired
//    private DataSource dataSource;
    
    @Autowired
    private JpaUserDetailsService userDetailsService ;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @Bean
//    public UserDetailsService userDetailsService() throws Exception {
//
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("sideral").password(passwordEncoder().encode("user")).roles("USER").build());
////        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN", "USER").build());
////
////        return manager;
//    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests((authz) -> {
            try {
                authz.requestMatchers(mvc.pattern("/"),
                        mvc.pattern("/css/**"),
                        mvc.pattern("/js/**"),
                        mvc.pattern("/images/**"),
                        mvc.pattern("/locale"),
                        mvc.pattern("/api/clientes/**"),
                        mvc.pattern("/listar**")).permitAll()
//                        .requestMatchers(mvc.pattern("/uploads/**")).hasAnyRole("USER")
//                        .requestMatchers(mvc.pattern("/ver/**")).hasRole("USER")
//                        .requestMatchers(mvc.pattern("/factura/**")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern("/form/**")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern("/eliminar/**")).hasRole("ADMIN")
                        .anyRequest().authenticated();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).formLogin(form -> form.loginPage("/login").permitAll().successHandler(successHanler))
        .logout((logout) -> logout.permitAll());

        return http.build();

    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }

}
