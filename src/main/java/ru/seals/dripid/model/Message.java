package ru.seals.dripid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.seals.dripid.model.enums.MessageStatus;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Person admin;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    private String content;
    private LocalDateTime createdAt;
}
