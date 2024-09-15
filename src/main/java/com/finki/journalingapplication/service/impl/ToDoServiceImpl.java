package com.finki.journalingapplication.service.impl;

import com.finki.journalingapplication.model.ToDo;
import com.finki.journalingapplication.model.User;
import com.finki.journalingapplication.repository.ToDoRepository;
import com.finki.journalingapplication.service.ToDoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public List<ToDo> getAllTodos(User user) {
        return toDoRepository.findByUser(user);
    }

    @Override
    public ToDo save(String title,LocalDateTime dueDateTime, User user) {
        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setCompleted(false);
        toDo.setDueDateTime(dueDateTime);
        toDo.setUser(user);

        return toDoRepository.save(toDo);
    }

    @Override
    public void markCompleted(Long id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ToDo id: " + id));
        toDo.setCompleted(!toDo.isCompleted()); // Toggle completion status
        toDoRepository.save(toDo);
    }

    @Override
    public ToDo findById(Long id) {
        return this.toDoRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void deleteCompletedTodos(User user) {
        List<ToDo> completedTodos = toDoRepository.findByUserAndCompleted(user, true);
        toDoRepository.deleteAll(completedTodos);
    }
}
