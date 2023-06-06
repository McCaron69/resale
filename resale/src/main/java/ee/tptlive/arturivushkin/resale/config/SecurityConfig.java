package ee.tptlive.arturivushkin.resale.config;

import ee.tptlive.arturivushkin.resale.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity httpSecurity
      ,UserDetailsService userDetailsService) throws Exception {
    return httpSecurity
        .getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(encoder())
        .and().build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
                                         JwtFilter jwtFilter) throws Exception {
      return http
          .cors().disable()
          .csrf().disable()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authorizeHttpRequests()
          .requestMatchers(HttpMethod.OPTIONS).permitAll()
          .requestMatchers(HttpMethod.GET).permitAll()
          .requestMatchers("/","/resources/**","/auth","/register","/images/**").permitAll()
          .requestMatchers("/admin").hasRole("admin")
          .anyRequest().authenticated()
          .and()
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
  }

  @Bean
  public PasswordEncoder encoder(){
    return new BCryptPasswordEncoder();
  }
}
