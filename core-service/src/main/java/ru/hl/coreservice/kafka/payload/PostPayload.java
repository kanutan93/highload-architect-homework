package ru.hl.coreservice.kafka.payload;

import lombok.Data;
import ru.hl.coreservice.model.dto.response.PostResponseDto;

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
