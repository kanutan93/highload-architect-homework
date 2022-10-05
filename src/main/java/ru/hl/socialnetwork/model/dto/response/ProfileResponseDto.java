package ru.hl.socialnetwork.model.dto.response;

import lombok.Data;
import ru.hl.socialnetwork.model.enums.SexEnum;

@Data
public class ProfileResponseDto {
  private Integer id;
  private String email;
  private String firstName;
  private String lastName;
  private short age;
  private SexEnum sex;
  private String aboutInfo;
  private String city;
  private Boolean isFriend;
}
