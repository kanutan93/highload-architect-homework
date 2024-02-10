package ru.hl.dialogservice.repository;


import ru.hl.dialogservice.model.dao.MessageDao;

import java.util.List;

public interface DialogRepository {

    Integer getDialogId(Integer currentUserId, Integer userId);

    Integer createDialog(Integer currentUserId, Integer userId);

    List<MessageDao> getMessages(Integer dialogId);

    void createMessage(MessageDao dialogDao);
}
