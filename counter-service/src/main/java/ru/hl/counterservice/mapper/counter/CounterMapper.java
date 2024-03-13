package ru.hl.counterservice.mapper.counter;

import org.mapstruct.Mapper;
import ru.hl.counterservice.model.dao.CounterDao;
import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;

@Mapper
public interface CounterMapper {

  UnreadMessageCounterResponseDto toUnreadMessageCounterResponseDto(CounterDao counterDao);
}
