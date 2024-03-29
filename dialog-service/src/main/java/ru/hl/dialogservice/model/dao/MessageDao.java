package ru.hl.dialogservice.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Tuple("message")
@Data
public class MessageDao {
  @Id
  private int id;
  @Field("from_id")
  private int from;
  @Field("to_id")
  private int to;
  @Field("dialog_id")
  private int dialogId;
  private String text;
}
