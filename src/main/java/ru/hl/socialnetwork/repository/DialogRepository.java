package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.MessageDao;

import java.util.List;

public interface DialogRepository {

    Integer getDialogId(Integer currentUserId, Integer userId);

    Integer createDialog(Integer currentUserId, Integer userId);

    List<MessageDao> getMessages(Integer currentUserId, Integer userId);

    void createMessage(MessageDao dialogDao);
}
