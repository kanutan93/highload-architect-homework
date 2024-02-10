package ru.hl.primaryservice.model.dto.request.post;

import lombok.Data;

@Data
public class UpdatePostRequestDto {
  private Integer id;
  private String text;
}
