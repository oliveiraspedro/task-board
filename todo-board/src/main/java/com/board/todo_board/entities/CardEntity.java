package com.board.todo_board.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@Setter
public class CardEntity {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "createat")
    private LocalDateTime createAt;

    @Column(name = "column_id")
    private Long columnId;

    @OneToOne
    @JoinColumn(name = "blocked_cards_id", referencedColumnName = "blocked_cards_id")
    private BlockedCardEntity blockedCard;
}
