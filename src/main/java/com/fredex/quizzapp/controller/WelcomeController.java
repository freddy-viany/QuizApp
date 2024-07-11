package com.fredex.quizzapp.controller;


import com.fredex.quizzapp.model.Category;
import com.fredex.quizzapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// WelcomeController.java
@Controller
public class WelcomeController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String welcome(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "index";
    }
}

