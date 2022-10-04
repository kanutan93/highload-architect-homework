package ru.hl.socialnetwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String USERS_QUERY = "SELECT email AS username, password, true FROM users WHERE username = ?";

  @Autowired
  private DataSource dataSource;

  @Bean
  public static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
        .usersByUsernameQuery(USERS_QUERY)
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .and()
        .authorizeRequests().antMatchers("/api/auth/register/**").permitAll()
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http
        .csrf().disable();
  }
}
