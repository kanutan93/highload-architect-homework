package ru.hl.counterservice.config;

import io.tarantool.driver.api.TarantoolServerAddress;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import io.tarantool.driver.auth.TarantoolCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.config.AbstractTarantoolDataConfiguration;
import org.springframework.data.tarantool.repository.config.EnableTarantoolRepositories;
import ru.hl.counterservice.repository.CounterRepository;


@Configuration
@EnableTarantoolRepositories(basePackageClasses = {CounterRepository.class})
class DataSourceConfig extends AbstractTarantoolDataConfiguration {

  @Value("${tarantool.host}")
  protected String host;
  @Value("${tarantool.port}")
  protected int port;
  @Value("${tarantool.username}")
  protected String username;
  @Value("${tarantool.password}")
  protected String password;

  @Override
  protected TarantoolServerAddress tarantoolServerAddress() {
    return new TarantoolServerAddress(host, port);
  }

  @Override
  public TarantoolCredentials tarantoolCredentials() {
    return new SimpleTarantoolCredentials(username, password);
  }
}