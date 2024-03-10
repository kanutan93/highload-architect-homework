package ru.hl.counterservice.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Data
@AllArgsConstructor
@Tuple("counter")
public class CounterDao {

  @Id
  @Field("id")
  private Integer id;

  @Field("current_user_id")
  private Integer currentUserId;

  @Field("user_id")
  private Integer userId;

  @Field("count")
  private Integer count;
}
