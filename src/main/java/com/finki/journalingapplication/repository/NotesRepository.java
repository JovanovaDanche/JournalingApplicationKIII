package com.finki.journalingapplication.repository;

import com.finki.journalingapplication.model.Notes;
import com.finki.journalingapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Long> {
    List<Notes> findByUser(User user);
    @Query("SELECT n.description FROM Notes n WHERE n.user = :user")
    List<String> findDescriptionsByUser(@Param("user") User user);
}
