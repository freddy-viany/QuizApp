package com.fredex.quizzapp.repository;

import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Quiz;
import com.fredex.quizzapp.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByCourse(Optional<Course> course);
    // Add custom query methods if needed
}
