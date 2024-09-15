package com.finki.journalingapplication.service;

import com.finki.journalingapplication.model.Notes;
import com.finki.journalingapplication.model.User;

import java.util.List;
import java.util.Optional;

public interface NotesService {
    List<Notes>getAllNotes(User user);
    Optional<Notes> findById(Long id);
    Optional<Notes> save(String title, String description,User user);
    Optional<Notes> edit(Long id, String title, String description,User user);
    void deleteNotes(Long id);
    public List<String> getNoteSuggestions(String query, User user);

}
