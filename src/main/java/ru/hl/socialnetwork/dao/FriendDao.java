package ru.hl.socialnetwork.dao;

import lombok.Data;

@Data
public class FriendDao {
  private int id;
  private int senderId;
  private int receiverId;
  private boolean isApproved;
}
