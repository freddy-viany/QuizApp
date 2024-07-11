package com.fredex.quizzapp.controller;


import com.fredex.quizzapp.model.Category;
import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Quiz;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.service.CategoryService;
import com.fredex.quizzapp.service.CourseService;
import com.fredex.quizzapp.service.QuizService;
import com.fredex.quizzapp.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    private final CategoryService categoryService;

    private final TopicService topicService;

    public CourseController(CourseService courseService, CategoryService categoryService, TopicService topicService) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.topicService = topicService;
    }


    @GetMapping("/list")
    public String getAllCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/category/{categoryId}")
    public String getCoursesByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            List<Course> courses = courseService.getCoursesByCategory(category);
            model.addAttribute("category", category);
            model.addAttribute("courses", courses);
            return "courses_by_category";
        } else {
            // Handle category not found scenario
            return "redirect:/courses/list"; // Redirect to list all courses
        }
    }

    @GetMapping("/{courseId}")
    public String getCourseDetails(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            List<Topic> topics = topicService.getTopicsByCourse(Optional.of(course));
            model.addAttribute("course", course);
            model.addAttribute("topics", topics);
            return "course-details";
        } else {
            // Handle course not found scenario
            return "redirect:/courses/list"; // Redirect to list all courses
        }
    }


    @GetMapping("/add")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-course";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("course") Course course, @RequestParam("categoryId") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId).orElse(null);
        course.setCategory(category);
        courseService.addCourse(course);
        return "redirect:/courses/list";
    }

    @GetMapping("/edit/{courseId}")
    public String showEditCourseForm(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories",categories);
        courseOptional.ifPresent(course -> model.addAttribute("course", course));
        return "edit-course";
    }

    @PostMapping("/edit/{courseId}")
    public String editCourse(@PathVariable("courseId") Long courseId, @ModelAttribute("course") Course updatedCourse) {
        updatedCourse.setId(courseId); // Ensure the correct ID is set
        courseService.updateCourse(updatedCourse);
        return "redirect:/courses/list";
    }

    @GetMapping("/delete/{courseId}")
    public String deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/courses/list";
    }

}
