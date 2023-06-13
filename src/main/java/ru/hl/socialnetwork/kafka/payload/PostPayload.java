package ru.hl.socialnetwork.kafka.payload;

import lombok.Data;

@Data
public class PostPayload {

  private Integer userId;

  private Long id;
  private String text;
  private Long authorUserId;
}
