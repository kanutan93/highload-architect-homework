package ru.hl.socialnetwork.kafka.payload;

import lombok.Data;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

@Data
public class PostPayload {

  private Integer receiverUserId;
  Action action;
  private PostResponseDto post;

  public enum Action {
    CREATE,
    UPDATE,
    DELETE
  }
}
