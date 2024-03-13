package ru.hl.dialogservice.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnreadMessageCounterPayload {

  private Integer senderUserId;
  private Integer receiverUserId;
}
