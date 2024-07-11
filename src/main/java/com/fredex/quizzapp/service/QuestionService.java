package com.fredex.quizzapp.service;

import com.fredex.quizzapp.model.Question;
import com.fredex.quizzapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;



    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question question) {
        Optional<Question> question1 = questionRepository.findById(id);



        if(question1.isPresent()){
            question1.get().setAnswer(question.getAnswer());
            question1.get().setDescription(question.getDescription());
            question1.get().setOptionOne(question.getOptionOne());
            question1.get().setOptionTwo(question.getOptionTwo());
            question1.get().setOptionThree(question.getOptionThree());
            question1.get().setOptionFour(question.getOptionFour());
            question1.get().setTopic(question.getTopic());

             return questionRepository.save(question1.get());
        }



        return null; // Handle not found case
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).get();
        if(question!=null){
            questionRepository.delete(question);
        }

    }

    public List<Question> getQuestionsForQuiz(Long quizId) {
        return questionRepository.findByTopicId(quizId);
    }

    public List<Question> getRandomQuestionsForQuiz(Long quizId, int numberOfQuestions) {
        List<Question> allQuestions = questionRepository.findByTopicId(quizId);
        if (allQuestions.size() <= numberOfQuestions) {
            return allQuestions; // If the number of available questions is less than or equal to requested, return all
        } else {
            Collections.shuffle(allQuestions); // Shuffle the list of questions
            return allQuestions.stream().limit(numberOfQuestions).collect(Collectors.toList()); // Return limited number of random questions
        }
    }


    public List<Question> getRandomQuestionsForTopic(Long topicId, int count) {
        List<Question> allQuestions = questionRepository.findByTopicId(topicId);
        List<Question> uniqueQuestions = new ArrayList<>();

        Random random = new Random();
        while (uniqueQuestions.size() < count && !allQuestions.isEmpty()) {
            int randomIndex = random.nextInt(allQuestions.size());
            Question randomQuestion = allQuestions.remove(randomIndex);
            if (!uniqueQuestions.contains(randomQuestion)) {
                uniqueQuestions.add(randomQuestion);
            }
        }

        return uniqueQuestions;
    }
}
