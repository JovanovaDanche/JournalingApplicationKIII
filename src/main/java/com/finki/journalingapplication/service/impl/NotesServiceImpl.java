package com.finki.journalingapplication.service.impl;

import com.finki.journalingapplication.model.Notes;
import com.finki.journalingapplication.model.User;
import com.finki.journalingapplication.repository.DiaryRepository;
import com.finki.journalingapplication.repository.NotesRepository;
import com.finki.journalingapplication.repository.ToDoRepository;
import com.finki.journalingapplication.service.NotesService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotesServiceImpl implements NotesService {
    private final NotesRepository notesRepository;
    private final DiaryRepository diaryRepository;
    private final ToDoRepository toDoRepository;

    public NotesServiceImpl(NotesRepository notesRepository, DiaryRepository diaryRepository, ToDoRepository toDoRepository) {
        this.notesRepository = notesRepository;
        this.diaryRepository = diaryRepository;
        this.toDoRepository = toDoRepository;
    }

    @Override
    public void deleteNotes(Long noteId) {
        this.notesRepository.deleteById(noteId);
    }


    @Override
    public List<Notes> getAllNotes(User user) {
        return notesRepository.findByUser(user);
    }

    @Override
    public Optional<Notes> findById(Long id) {
        return this.notesRepository.findById(id);
    }

    @Override
    public Optional<Notes> save(String title, String description, User user) {
        return Optional.of(this.notesRepository.save(new Notes(title, description, user)));
    }

    @Override
    public Optional<Notes> edit(Long id, String title, String description, User user) {
        Notes n = notesRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        n.setTitle(title);
        n.setDescription(description);
        LocalDateTime existingCreatedAt = n.getCreatedAt();
        n.setCreatedAt(existingCreatedAt);
        n.setUser(user);
        Notes updatedNote = notesRepository.save(n);
        return Optional.of(updatedNote);
    }
    public List<String> getNoteSuggestions(String query, User user) {
        List<String> notesDescriptions = notesRepository.findDescriptionsByUser(user);
        List<String> diaryDescriptions = diaryRepository.findDescriptionsByUser(user);
        List<String> toDoDescriptions = toDoRepository.findDescriptionsByUser(user);
        List<String> allWords = notesDescriptions.stream()
                .flatMap(description -> Arrays.stream(description.split("\\s+")))
                .collect(Collectors.toList());

        allWords.addAll(diaryDescriptions.stream()
                .flatMap(description -> Arrays.stream(description.split("\\s+")))
                .collect(Collectors.toList()));

        allWords.addAll(toDoDescriptions.stream()
                .flatMap(description -> Arrays.stream(description.split("\\s+")))
                .collect(Collectors.toList()));

        return allWords.stream()
                .map(word -> word.replaceAll("[^a-zA-Z0-9]", ""))  // remove punctuation
                .filter(word -> word.toLowerCase().startsWith(query.toLowerCase()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
