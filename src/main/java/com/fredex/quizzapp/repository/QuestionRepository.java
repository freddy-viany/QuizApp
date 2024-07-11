package com.fredex.quizzapp.repository;

import com.fredex.quizzapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTopicId(Long questionId);

    @Query(value = "SELECT * FROM question q WHERE q.topic_id = :topicId ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Question> findRandomQuestionsByTopicId(@Param("topicId") Long topicId, @Param("count") int count);


    // You can add custom queries if needed
}
