package ru.hl.primaryservice.model.dao;

import lombok.Data;
import ru.hl.primaryservice.model.enums.SexEnum;

@Data
public class UserDao {
  private int id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private short age;
  private SexEnum sex;
  private String aboutInfo;
  private String city;
  private Boolean isApproved;
}
