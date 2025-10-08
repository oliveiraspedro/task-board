package com.board.todo_board.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_cards")
@Getter
@Setter
public class BlockedCardEntity {

    @Id
    @Column(name = "blocked_cards_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "blockcause")
    private String blockCause;

    @Column(name = "unblockcause")
    private String unblockCause;

    @Column(name = "blockedin")
    private LocalDateTime blockedIn;

    @Column(name = "unblockedin")
    private LocalDateTime unblockedIn;
}
