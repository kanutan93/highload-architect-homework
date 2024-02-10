package ru.hl.dialogservice.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.dialog")
  public DataSourceProperties dialogDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "dialogDataSource")
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
  public SpringLiquibase dialogLiquibase(@Qualifier("dialogDataSource") DataSource dialogDataSource, LiquibaseProperties dialogLiquibaseProperties) {
    return getSpringLiquibase(dialogDataSource, dialogLiquibaseProperties);
  }

  private static SpringLiquibase getSpringLiquibase(DataSource dataSource, LiquibaseProperties properties) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(properties.getChangeLog());
    liquibase.setContexts(properties.getContexts());
    liquibase.setDefaultSchema(properties.getDefaultSchema());
    liquibase.setDropFirst(properties.isDropFirst());
    liquibase.setShouldRun(properties.isEnabled());
    liquibase.setLabels(properties.getLabels());
    liquibase.setChangeLogParameters(properties.getParameters());
    liquibase.setRollbackFile(properties.getRollbackFile());
    return liquibase;
  }
}