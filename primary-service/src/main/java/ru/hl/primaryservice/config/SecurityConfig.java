package ru.hl.primaryservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String USERS_QUERY = "SELECT email AS username, password, true AS enabled FROM users WHERE email = ?";
  private static final String AUTHORITY_QUERY = "SELECT email AS username, 'USER_ROLE' AS authority FROM users WHERE email = ?";

  @Bean
  public static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  @ConditionalOnProperty(value = "spring.datasource.primary.slave.readonly-slave-enabled", havingValue = "false")
  public UserDetailsService jdbcUserDetailsServiceMaster(@Qualifier("masterDataSource") DataSource masterDataSource) {
    return jdbcUserDetailsServiceInternal(masterDataSource);
  }

  @Bean
  @ConditionalOnProperty(value = "spring.datasource.primary.slave.readonly-slave-enabled", havingValue = "true")
  public UserDetailsService jdbcUserDetailsServiceSlave(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
    return jdbcUserDetailsServiceInternal(slaveDataSource);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .and()
        .authorizeRequests().antMatchers("/api/auth/register/**", "/actuator/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http
        .csrf().disable();
  }

  private UserDetailsService jdbcUserDetailsServiceInternal(DataSource dataSource) {
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

    users.setUsersByUsernameQuery(USERS_QUERY);
    users.setAuthoritiesByUsernameQuery(AUTHORITY_QUERY);

    return users;
  }
}
