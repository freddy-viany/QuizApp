package com.fredex.quizzapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
