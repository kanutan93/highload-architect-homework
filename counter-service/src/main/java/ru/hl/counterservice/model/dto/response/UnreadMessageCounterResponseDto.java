package ru.hl.counterservice.model.dto.response;

import lombok.Data;

@Data
public class UnreadMessageCounterResponseDto {
  private Integer userId;
  private Integer count;
}
