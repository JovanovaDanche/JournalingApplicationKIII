package com.finki.journalingapplication.repository;

import com.finki.journalingapplication.model.ToDo;
import com.finki.journalingapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    List<ToDo> findByUser(User user);
    List<ToDo> findByUserAndCompleted(User user,boolean completed);
    @Query("SELECT t.title FROM ToDo t WHERE t.user = :user")
    List<String> findDescriptionsByUser(@Param("user") User user);
}
