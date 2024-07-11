package com.fredex.quizzapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // You can add more fields as needed

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    // Constructors, getters, and setters
}
