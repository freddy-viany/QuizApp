package com.fredex.quizzapp.controller;


import com.fredex.quizzapp.model.Question;
import com.fredex.quizzapp.model.Quiz;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.repository.QuizRepository;
import com.fredex.quizzapp.service.QuestionService;
import com.fredex.quizzapp.service.QuizService;
import com.fredex.quizzapp.service.TopicService;
import com.fredex.quizzapp.util.UserAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicService topicService ;


    private Map<Long, List<Question>> quizQuestionsMap = new HashMap<>();



    @GetMapping("/list")
    public String getAllQuizzes(Model model) {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "quiz-list"; // Thymeleaf template name for quiz list
    }

    @GetMapping("/{id}")
    public String getQuizById(@PathVariable("id") Long id, Model model) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        quiz.ifPresent(value -> model.addAttribute("quiz", value));
        return "quiz-details"; // Thymeleaf template name for quiz details
    }

    @GetMapping("/new")
    public String showQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("topics", topicService.getAllTopics());
        return "quiz-form"; // Thymeleaf template name for creating new quiz
    }

    @PostMapping("/new")
    public String createQuiz(@ModelAttribute("quiz") Quiz quiz) {
        quizService.createQuiz(quiz);
        return "redirect:/quizzes/list"; // Redirect to list of quizzes
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        quiz.ifPresent(value -> model.addAttribute("quiz", value));
        model.addAttribute("topics", topicService.getAllTopics());
        return "quiz-form"; // Thymeleaf template name for editing quiz
    }

    @PostMapping("/{id}/edit")
    public String updateQuiz(@PathVariable("id") Long id, @ModelAttribute("quiz") Quiz quiz) {
        quiz.setId(id);
        quizService.updateQuiz(id, quiz);
        return "quiz-list"; // Redirect to list of quizzes
    }

    @GetMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable("id") Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quizzes/list"; // Redirect to list of quizzes
    }



  /*  @GetMapping("/{quizId}/start")
    public String startQuiz(@PathVariable("quizId") Long quizId, Model model) {
        Optional<Quiz> quiz = quizService.getQuizById(quizId);
        if (quiz.isPresent()) {
            List<Question> questions = questionService.getRandomQuestionsForQuiz(quizId, 10); // Adjust number of questions as needed
            model.addAttribute("quiz", quiz.get());
            model.addAttribute("questions", questions);
            return "quiz-start"; // Thymeleaf template for starting the quiz
        } else {
            // Handle quiz not found
            return "quiz-list"; // Redirect to quiz list or error page
        }
    }*/


    @GetMapping("/{topicId}/start")
    public String startQuiz(@PathVariable("topicId") Long topicId, Model model) {
        Optional<Topic> topic = quizService.getTopicById(topicId);
        if (topic.isPresent()) {
            List<Question> questions = questionService.getRandomQuestionsForTopic(topicId, 10);
            quizQuestionsMap.put(topicId, questions); // Store questions in map for this quiz
            model.addAttribute("topic", topic.get());
            model.addAttribute("questions", questions);

            // Initialize results list (size should match the number of questions)
            model.addAttribute("results", new String[questions.size()]);

            model.addAttribute("userAnswers", new UserAnswers());
            return "quiz-start"; // Thymeleaf template for starting the quiz
        } else {
            // Handle topic not found
            return "redirect:/topics"; // Redirect to topic list or error page
        }
    }


    @PostMapping("/{quizId}/submit")
    public String submitQuiz(@PathVariable("quizId") Long quizId, @ModelAttribute UserAnswers userAnswers, Model model) {
        List<Question> questions = quizQuestionsMap.get(quizId);
        String[] results = new String[questions.size()]; // Array to store results for each question


        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers.getAnswers().get(question.getId());
            boolean isCorrect = quizService.checkAnswer(question.getId(), userAnswer);
            results[i] = isCorrect ? "correct" : "incorrect";
        }

        model.addAttribute("topic", questions.get(0).getTopic()); // Assuming all questions are for the same topic
        model.addAttribute("questions", questions);
        model.addAttribute("results", results);

        return "quiz-result"; // Thymeleaf template to display quiz results
    }


    private int calculateScore(List<Question> questions, List<Long> answerIds) {
        int score = 0;
        for (Question question : questions) {
            // Compare user's answer ID with the correct answer's ID
            if (answerIds.contains(question.getAnswer())) {
                score++;
            }
        }
        return score;
    }



}
