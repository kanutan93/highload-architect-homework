package ru.hl.primaryservice.model.dao;

import lombok.Data;

@Data
public class PostDao {
  private Integer id;
  private String text;
  private Integer authorUserId;
}
