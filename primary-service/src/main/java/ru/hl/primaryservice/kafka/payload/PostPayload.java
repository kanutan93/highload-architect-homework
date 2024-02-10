package ru.hl.primaryservice.kafka.payload;

import lombok.Data;
import ru.hl.primaryservice.model.dto.response.PostResponseDto;

@Data
public class PostPayload {

  private Integer receiverUserId;
  private Action action;
  private PostResponseDto postResponseDto;

  public enum Action {
    CREATE,
    UPDATE,
    DELETE
  }
}
