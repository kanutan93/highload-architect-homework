package ru.hl.primaryservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hl.primaryservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.List;

@FeignClient(name = "counterServiceClient", url = "http://counter-service:8080")
public interface CounterServiceClient {

  @GetMapping("/api/counter/{currentUserId}")
  ResponseEntity<List<UnreadMessageCounterResponseDto>> getUnreadMessageCounters(@PathVariable Integer currentUserId);
}