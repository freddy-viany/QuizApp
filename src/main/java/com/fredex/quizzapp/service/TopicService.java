package com.fredex.quizzapp.service;

import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Topic;
import com.fredex.quizzapp.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;


    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic updateTopic(Long id, Topic topic) {
        if (topicRepository.existsById(id)) {
            topic.setId(id);
            return topicRepository.save(topic);
        }
        return null; // Handle not found case
    }

    public boolean deleteTopic(Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return true;
        }
        return false; // Handle not found case
    }

    public List<Topic> getTopicsByCourse(Optional<Course> course) {
        return topicRepository.findByCourse(course);
    }



}
