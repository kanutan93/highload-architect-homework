package ru.hl.dialogservice.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.tarantool.repository.Query;
import ru.hl.dialogservice.model.dao.MessageDao;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageDao, Integer> {

  @Query(function = "get_messages")
  List<MessageDao> getMessages(Integer dialogId);

  @Query(function = "create_message")
  MessageDao createMessage(MessageDao dialogDao);
}
