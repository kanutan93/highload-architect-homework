package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.DialogDao;

import java.util.List;

public interface DialogRepository {

    List<DialogDao> getDialogMessages(Integer currentUserId, Integer userId);

    void createDialogMessage(DialogDao dialogDao);
}
