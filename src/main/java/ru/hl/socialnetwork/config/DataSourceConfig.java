package ru.hl.socialnetwork.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.master.datasource")
  public DataSourceProperties masterDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.master.datasource.configuration")
  public DataSource masterDataSource(DataSourceProperties masterDataSourceProperties) {
    return masterDataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @ConfigurationProperties("spring.slave.datasource")
  public DataSourceProperties slaveDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.slave.datasource.configuration")
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
  public DataSource dataSource(DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
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