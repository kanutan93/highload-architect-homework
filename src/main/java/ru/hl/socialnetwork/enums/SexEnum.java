package ru.hl.socialnetwork.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SexEnum {
  M("M"),
  W("W");

  private String value;

  SexEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @JsonCreator
  public static SexEnum fromValue(String value) {
    for (SexEnum b : SexEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
