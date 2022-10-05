package ru.hl.socialnetwork.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OtherProfileResponseDto extends ProfileResponseDto {
  private boolean isFriend;
}
