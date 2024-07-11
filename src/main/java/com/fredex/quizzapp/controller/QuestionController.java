package com.fredex.quizzapp.controller;


import com.fredex.quizzapp.model.Question;
import com.fredex.quizzapp.model.Quiz;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.service.QuestionService;
import com.fredex.quizzapp.service.QuizService;
import com.fredex.quizzapp.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/list")
    public String getAllQuestions(Model model) {
        List<Question> questions = questionService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "question-list"; // Thymeleaf template name for question list
    }

    @GetMapping("/{id}")
    public String getQuestionById(@PathVariable("id") Long id, Model model) {
        Optional<Question> question = questionService.getQuestionById(id);
        question.ifPresent(value -> model.addAttribute("question", value));
        return "question-details"; // Thymeleaf template name for question details
    }

    @GetMapping("/new")
    public String showQuestionForm(Model model) {
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        model.addAttribute("question", new Question());
        return "question-form"; // Thymeleaf template name for creating new question
    }

    @PostMapping("/new")
    public String createQuestion(@ModelAttribute("question") Question question) {
        System.out.println(question);
        questionService.createQuestion(question);
        return "redirect:/questions/list"; // Redirect to list of questions
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Question> question = questionService.getQuestionById(id);
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        question.ifPresent(value -> model.addAttribute("question", value));
        return "question-edit-form"; // Thymeleaf template name for editing question
    }

    @PostMapping("/{id}/edit")
    public String updateQuestion(@PathVariable("id") Long id, @ModelAttribute("question") Question question) {
        //question.setId(id);
        questionService.updateQuestion(id, question);
        return "redirect:/questions/list"; // Redirect to list of questions
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/questions/list"; // Redirect to list of questions
    }

}