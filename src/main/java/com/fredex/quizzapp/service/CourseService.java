package com.fredex.quizzapp.service;


import com.fredex.quizzapp.model.Category;
import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public void updateCourse(Course updatedCourse) {
        courseRepository.save(updatedCourse);
    }

    public List<Course> getCoursesByCategory(Optional<Category> category) {

        return courseRepository.findCourseByCategory(category);
    }
}

