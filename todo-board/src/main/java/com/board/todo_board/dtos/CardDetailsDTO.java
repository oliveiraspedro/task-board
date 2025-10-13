package com.board.todo_board.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createAt;

    private boolean isBlocked;
    private LocalDateTime blockedIn;
    private String blockCause;
    private LocalDateTime unblockedIn;
    private String unblockCause;
}
