package com.fredex.quizzapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Topic() {
    }

    public Topic(String name, Course course) {
        this.name = name;
        this.course = course;
    }

    // Constructors, getters, and setters
}
