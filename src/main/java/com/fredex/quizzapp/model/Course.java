package com.fredex.quizzapp.model;


import jakarta.persistence.*;


@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    //private String level;  //beginner, middle, advanced
   // private String status; // e.g., popular, new
   // private double price; // If applicable

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Getters and setters
    // Constructors
    // Other fields and methods as needed

    // Constructor
    public Course() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}



