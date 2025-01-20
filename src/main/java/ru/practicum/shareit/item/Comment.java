package ru.practicum.shareit.item;

import jakarta.persistence.*;
import ru.practicum.shareit.user.User;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
