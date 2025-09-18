package com.board.todo_board.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_cards")
public class BlockedCardEntity {

    @Id
    @Column(name = "blocked_cards_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String blockCause;

    private String unblockCause;

    private LocalDateTime blockedIn;

    private LocalDateTime unblockedIn;
}
