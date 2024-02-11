package ru.hl.dialogservice.config;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolClientConfig;
import io.tarantool.driver.api.TarantoolClusterAddressProvider;
import io.tarantool.driver.api.TarantoolServerAddress;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import io.tarantool.driver.auth.TarantoolCredentials;
import io.tarantool.driver.core.ProxyTarantoolTupleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.config.AbstractTarantoolDataConfiguration;
import org.springframework.data.tarantool.repository.config.EnableTarantoolRepositories;
import ru.hl.dialogservice.repository.DialogRepository;
import ru.hl.dialogservice.repository.MessageRepository;


@Configuration
@EnableTarantoolRepositories(basePackageClasses = {DialogRepository.class, MessageRepository.class})
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

  @Override
  public TarantoolClient tarantoolClient(TarantoolClientConfig tarantoolClientConfig,
                                         TarantoolClusterAddressProvider tarantoolClusterAddressProvider) {
    return new ProxyTarantoolTupleClient(super.tarantoolClient(tarantoolClientConfig, tarantoolClusterAddressProvider));
  }
}