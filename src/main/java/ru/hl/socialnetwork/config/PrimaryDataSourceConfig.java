package ru.hl.socialnetwork.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

import static ru.hl.socialnetwork.util.LiquibaseConfigUtil.getSpringLiquibase;

@Configuration
public class PrimaryDataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.primary.master")
  public DataSourceProperties masterDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.primary.master.configuration")
  public DataSource masterDataSource(DataSourceProperties masterDataSourceProperties) {
    return masterDataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.primary.slave")
  public DataSourceProperties slaveDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.primary.slave.configuration")
  public DataSource slaveDataSource(DataSourceProperties slaveDataSourceProperties) {
    return slaveDataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class).build();
  }

  @Bean
  @Primary
  public DataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource) {

    Map<Object, Object> targetDataSources = new LinkedHashMap<>();
    RoutingDataSourceConfiguration routingDataSourceConfiguration = new RoutingDataSourceConfiguration();
    targetDataSources.put(DataSourceTypes.MASTER, masterDataSource);
    targetDataSources.put(DataSourceTypes.SLAVE, slaveDataSource);
    routingDataSourceConfiguration.setTargetDataSources(targetDataSources);
    routingDataSourceConfiguration.setDefaultTargetDataSource(masterDataSource);
    return routingDataSourceConfiguration;
  }

  @Bean
  public DataSource primaryDataSource(DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.primary.liquibase")
  public LiquibaseProperties primaryLiquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean
  public SpringLiquibase primaryLiquibase(DataSource primaryDataSource, LiquibaseProperties primaryLiquibaseProperties) {
    return getSpringLiquibase(primaryDataSource, primaryLiquibaseProperties);
  }

  @Primary
  @Bean(name = "readerJdbcTemplate")
  public NamedParameterJdbcTemplate getReaderJdbcTemplate(DataSource slaveDataSource) {
    return new NamedParameterJdbcTemplate(slaveDataSource);
  }

  @Bean(name = "writerJdbcTemplate")
  public NamedParameterJdbcTemplate getWriterJdbcTemplate(DataSource masterDataSource) {
    return new NamedParameterJdbcTemplate(masterDataSource);
  }

  private static class RoutingDataSourceConfiguration extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
      return DataSourceTypeContextHolder.getDataSourceType();
    }
  }

  public enum DataSourceTypes {
    MASTER, SLAVE
  }

  public static class DataSourceTypeContextHolder {

    private static final ThreadLocal<DataSourceTypes> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceTypes dataSourceType) {
      if(dataSourceType == null){
        throw new NullPointerException();
      }
      contextHolder.set(dataSourceType);
    }

    public static DataSourceTypes getDataSourceType() {
      return contextHolder.get();
    }

    public static void clearDataSourceType() {
      contextHolder.remove();
    }
  }
}