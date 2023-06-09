package ru.hl.socialnetwork.model.dto.response;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long id;
    private String text;
    private Long authorUserId;
}
