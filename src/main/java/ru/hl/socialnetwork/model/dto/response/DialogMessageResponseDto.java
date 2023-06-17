package ru.hl.socialnetwork.model.dto.response;

import lombok.Data;

@Data
public class DialogMessageResponseDto {
  private int from;
  private int to;
  private String text;
}
