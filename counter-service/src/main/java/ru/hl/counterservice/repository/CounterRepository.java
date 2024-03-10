package ru.hl.counterservice.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.tarantool.repository.Query;
import ru.hl.counterservice.model.dao.CounterDao;

import java.util.List;

public interface CounterRepository extends CrudRepository<CounterDao, Integer> {

  @Query(function = "get_counters")
  List<CounterDao> getCounters(Integer currentUserId);

  @Query(function = "get_counter")
  CounterDao getCounter(Integer currentUserId, Integer userId);

  @Query(function = "create_counter")
  CounterDao createCounter(Integer currentUserId, Integer userId, Integer count);

  @Query(function = "update_counter")
  CounterDao updateCounter(Integer id, Integer currentUserId, Integer userId, Integer count);

  @Query(function = "delete_counter")
  void deleteCounter(Integer id);
}
