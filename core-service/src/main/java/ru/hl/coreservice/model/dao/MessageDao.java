package ru.hl.coreservice.model.dao;

import lombok.Data;

@Data
public class MessageDao {
  private int id;
  private int from;
  private int to;
  private int dialogId;
  private String text;
}
