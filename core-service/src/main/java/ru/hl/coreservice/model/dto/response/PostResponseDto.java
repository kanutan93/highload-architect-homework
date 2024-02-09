package ru.hl.coreservice.model.dto.response;

import lombok.Data;

@Data
public class PostResponseDto {
    private Integer id;
    private String text;
    private Integer authorUserId;
}
