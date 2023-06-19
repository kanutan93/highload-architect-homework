package ru.hl.socialnetwork.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static ru.hl.socialnetwork.util.LiquibaseConfigUtil.getSpringLiquibase;

@Configuration
public class DialogDataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.dialog")
  public DataSourceProperties dialogDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.dialog.configuration")
  public DataSource dialogDataSource(DataSourceProperties dialogDataSourceProperties) {
    return dialogDataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.dialog.liquibase")
  public LiquibaseProperties dialogLiquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean
  public SpringLiquibase dialogLiquibase(DataSource dialogDataSource, LiquibaseProperties dialogLiquibaseProperties) {
    return getSpringLiquibase(dialogDataSource, dialogLiquibaseProperties);
  }
}