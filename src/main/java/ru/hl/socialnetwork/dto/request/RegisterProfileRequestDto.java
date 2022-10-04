package ru.hl.socialnetwork.dto.request;

import lombok.Data;
import ru.hl.socialnetwork.enums.SexEnum;

@Data
public class RegisterProfileRequestDto {
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private short age;
  private SexEnum sex;
  private String aboutInfo;
  private String city;
}
