package ru.hl.socialnetwork.kafka.payload;

import lombok.Data;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

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
