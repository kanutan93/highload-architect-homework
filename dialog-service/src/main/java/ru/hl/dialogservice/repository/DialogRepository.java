package ru.hl.dialogservice.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.tarantool.repository.Query;
import ru.hl.dialogservice.model.dao.DialogDao;

import java.util.List;

public interface DialogRepository extends CrudRepository<DialogDao, Integer> {

  @Query(function = "get_dialog_id")
  List<DialogDao> getDialogId(Integer currentUserId, Integer userId);

  @Query(function = "create_dialog")
  DialogDao createDialog(Integer currentUserId, Integer userId);
}
