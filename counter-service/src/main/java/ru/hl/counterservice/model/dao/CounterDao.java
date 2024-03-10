package ru.hl.counterservice.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RedisHash(value = "counter")
public class CounterDao implements Serializable {

  @Id
  @Indexed
  private Integer userId;
  private Integer count;
}
