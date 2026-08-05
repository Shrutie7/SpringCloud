package com.shrucode.user_service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity

//without enabling this @PreAuthorize & @PostAuthorize will be ignored
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public SecurityConfig(JWTUtil jwtUtil , UserDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // to load data from db
        provider.setPasswordEncoder(passwordEncoder()); // to hash pwd
        return provider;
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider(){
        return new JWTAuthenticationProvider(jwtUtil,userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider(),jwtAuthenticationProvider()));
    }

    @Autowired
    private CustomOAuth2SuccessHandler successHandler;

    @Autowired
    private OAuthTokenValidatorUtil tokenValidatorUtil;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,AuthenticationManager authenticationManager, JWTUtil jwtUtil) throws Exception {
        //form based
//        http.csrf(csrf->csrf.disable())
//                .headers(headers -> headers.frameOptions(frame -> frame.disable()))//to view h2 db
//                .authorizeHttpRequests(auth->
//                auth.requestMatchers("/api/user-register","/h2-console/**")
//                        .permitAll().anyRequest().authenticated())
//                .sessionManagement(session->
//                        session.sessionCreationPolicy(
//                                SessionCreationPolicy.IF_REQUIRED)
//                                .maximumSessions(1)
//                                .maxSessionsPreventsLogin(true)
//                )
//                .formLogin(form->Customizer.withDefaults());//to redirect to certain page after login form.defaultSuccessUrl("/api/details", true) use this instead of Customizer.withDefaults()

        /*jwt*/
//        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager,jwtUtil);
//
//        JWTValidationFilter jwtValidationFilter = new JWTValidationFilter(authenticationManager);
//
//        JWTRefreshFilter jwtRefreshFilter = new JWTRefreshFilter(authenticationManager,jwtUtil);
//
//        http.authorizeHttpRequests(auth->
//                auth.requestMatchers("/api/user-register")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated()).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrf->csrf.disable())
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(jwtValidationFilter, JWTAuthenticationFilter.class)
//                .addFilterAfter(jwtRefreshFilter,JWTValidationFilter.class);


        //oauth - stateful
//        http.authorizeHttpRequests(auth->auth.requestMatchers("/user-register")
//                .permitAll()
//                .anyRequest()
//                .authenticated())
//                .csrf(csrf->csrf.disable())
//                .oauth2Login(Customizer.withDefaults());

        //oauth - stateless

//        OAuthValidationFilter oAuthValidationFilter = new OAuthValidationFilter(tokenValidatorUtil);
//
//        http.authorizeHttpRequests(auth->auth.requestMatchers("/user-register")
//                .permitAll()
//                .anyRequest()
//                .authenticated())
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrf->csrf.disable())
//                .oauth2Login(oauth->oauth.successHandler(successHandler))
//                .addFilterBefore(oAuthValidationFilter, UsernamePasswordAuthenticationFilter.class);

        //Role based Authorization
        //Add @EnableMethodSecurity at top of this class without enabling this @PreAuthorize & @PostAuthorize will be ignored
//        http.authorizeHttpRequests(auth->auth.requestMatchers("/user-login")
//                .permitAll()
//                .anyRequest()
//                .authenticated())
//                .csrf(csrf->csrf.disable())
//                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(Customizer.withDefaults()); //basic auth for simplicity


        //actuator
        http.authorizeHttpRequests(auth->auth
                .requestMatchers("/manage/health","/manage/info","/user-login")//everything else requires login
                .permitAll()
                .anyRequest()
                .authenticated())
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults()); //basic auth for simplicity



        return http.build();
    }
}