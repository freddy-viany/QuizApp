package com.fredex.quizzapp.controller;


import com.fredex.quizzapp.model.Category;
import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Question;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.service.CategoryService;
import com.fredex.quizzapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final CourseService courseService;

    @Autowired
    public CategoryController(CategoryService categoryService, CourseService courseService) {
        this.categoryService = categoryService;
        this.courseService = courseService;
    }

    @GetMapping("/categories/list")
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "list-categories";
    }


    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "add-category";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/categories/list";
    }

    @GetMapping("/categories/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("categoryId") Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("category", category);
        return "category-edit-form";
    }

    @PostMapping("/categories/updateCategory")
    public String updateCategory(@ModelAttribute("category") Category category) {
        categoryService.saveOrUpdateCategory(category);
        return "redirect:/categories/list"; // Redirect to the category list page after updating
    }

    @GetMapping("/categories/showListCourses")
    public String showListCourses(@RequestParam("categoryId") Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        List<Course> courses = courseService.getCoursesByCategory(Optional.ofNullable(category));
        model.addAttribute("category", category);
        model.addAttribute("courses", courses);
        //return "courses";
        return "courses_by_category";
    }



    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("categoryId") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/list";
    }
}
