package ru.hl.primaryservice.model.dto.response;

import lombok.Data;

@Data
public class UnreadMessageCounterResponseDto {
  private Integer userId;
  private Integer count;
}
