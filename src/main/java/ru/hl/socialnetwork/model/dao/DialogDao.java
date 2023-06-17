package ru.hl.socialnetwork.model.dao;

import lombok.Data;

@Data
public class DialogDao {
  private int id;
  private int from;
  private int to;
  private String text;
}
