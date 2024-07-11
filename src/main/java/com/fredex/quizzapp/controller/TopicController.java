package com.fredex.quizzapp.controller;

import com.fredex.quizzapp.model.Category;
import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.service.CourseService;
import com.fredex.quizzapp.service.TopicService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final CourseService courseService;

    public TopicController(TopicService topicService, CourseService courseService) {
        this.topicService = topicService;
        this.courseService = courseService;
    }

    @GetMapping("/list")
    public String getAllTopics(Model model) {
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "topics";
    }

    @GetMapping("/{id}")
    public String getTopicById(@PathVariable("id") Long topicId, Model model) {
        Optional<Topic> topic = topicService.getTopicById(topicId);
        topic.ifPresent(value -> model.addAttribute("topic", value));
        return "topics-details";
    }


    @GetMapping("/courses/{courseId}")
    public String getTopicsByCourse(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course != null) {
            List<Topic> topics = topicService.getTopicsByCourse(course);
            model.addAttribute("topics", topics);
            return "topics_by_course";
        } else {
            // Handle category not found scenario
            return "redirect:/topics/list"; // Redirect to list all courses
        }
    }




    @GetMapping("/add")
    public String showAddTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        List<Course> courses = courseService.getAllCourses(); // Assuming you have a method in CourseService to get all courses
        model.addAttribute("courses", courses);
        return "add-topic";
    }

    @PostMapping("/add")
    public String createTopic(@ModelAttribute("topic") Topic topic,
                            @RequestParam("courseId") Long courseId) {


        // Set the course for the lesson
        Course course = courseService.getCourseById(courseId).orElse(null);
        topic.setCourse(course);

        topicService.createTopic(topic);
        return "redirect:/topics/list";
    }


    @GetMapping("/edit/{id}")
    public String showEditTopicForm(@PathVariable("id") Long topicId, Model model) {
        Optional<Topic> topicOptional = topicService.getTopicById(topicId);
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses",courses);
        topicOptional.ifPresent(topic -> model.addAttribute("topic", topic));
        return "edit-topic";
    }

    @PostMapping("/edit/{id}")
    public String editTopic(@PathVariable("id") Long topicId, @ModelAttribute("topic") Topic updatedTopic) {
        updatedTopic.setId(topicId); // Ensure the correct ID is set
        topicService.updateTopic(topicId,updatedTopic);
        return "redirect:/topics/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTopic(@PathVariable("id") Long topicId) {
        topicService.deleteTopic(topicId);
        return "redirect:/topics/list";
    }
}
