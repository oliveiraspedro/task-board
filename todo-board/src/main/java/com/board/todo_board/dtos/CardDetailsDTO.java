package com.board.todo_board.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String formatTime(LocalDateTime createAt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createAt.format(formatter);
    }
}
