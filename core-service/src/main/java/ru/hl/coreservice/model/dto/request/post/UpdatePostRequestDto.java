package ru.hl.coreservice.model.dto.request.post;

import lombok.Data;

@Data
public class UpdatePostRequestDto {
  private Integer id;
  private String text;
}
