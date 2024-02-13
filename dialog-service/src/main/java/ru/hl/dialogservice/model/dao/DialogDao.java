package ru.hl.dialogservice.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Tuple("dialog")
@Data
public class DialogDao {
  @Id
  private Integer id;
  @Field("user1_id")
  private Integer user1Id;
  @Field("user2_id")
  private Integer user2Id;
}
