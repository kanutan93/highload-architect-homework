package ru.hl.socialnetwork.model.dto.request;

import lombok.Data;
import ru.hl.socialnetwork.model.enums.SexEnum;

@Data
public class UpdateProfileRequestDto {
  private String firstName;
  private String lastName;
  private short age;
  private SexEnum sex;
  private String aboutInfo;
  private String city;
}
