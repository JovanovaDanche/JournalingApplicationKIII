package com.finki.journalingapplication.service;

import com.finki.journalingapplication.model.ToDo;
import com.finki.journalingapplication.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ToDoService {
    List<ToDo> getAllTodos(User user);
    ToDo save(String title, LocalDateTime dueDateTime, User user);
    void markCompleted(Long id);
    ToDo findById(Long id);
    void deleteCompletedTodos(User user);
}
