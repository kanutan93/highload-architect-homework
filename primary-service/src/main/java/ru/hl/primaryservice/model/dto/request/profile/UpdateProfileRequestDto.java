package ru.hl.primaryservice.model.dto.request.profile;

import lombok.Data;
import ru.hl.primaryservice.model.enums.SexEnum;

@Data
public class UpdateProfileRequestDto {
  private String firstName;
  private String lastName;
  private short age;
  private SexEnum sex;
  private String aboutInfo;
  private String city;
}
