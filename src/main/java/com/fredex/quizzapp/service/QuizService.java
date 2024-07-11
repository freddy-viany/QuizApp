package com.fredex.quizzapp.service;

import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Question;
import com.fredex.quizzapp.model.Quiz;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.repository.QuestionRepository;
import com.fredex.quizzapp.repository.QuizRepository;
import com.fredex.quizzapp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }


    public Optional<Topic> getTopicById(Long topicId) {
        return topicRepository.findById(topicId);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, Quiz quiz) {
        if (quizRepository.existsById(id)) {
            quiz.setId(id);
            return quizRepository.save(quiz);
        }
        return null; // Handle not found case
    }

    public void deleteQuiz(Long id) {

        quizRepository.deleteById(id);

    }


    public boolean checkAnswer(Long questionId, String userAnswer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // Retrieve correct answer from the question entity
        String correctAnswer = question.getAnswer();

        // Compare user's answer with the correct answer (case-sensitive)
        return userAnswer != null && userAnswer.equals(correctAnswer);
    }



}
