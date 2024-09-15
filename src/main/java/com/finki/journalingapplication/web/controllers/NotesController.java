package com.finki.journalingapplication.web.controllers;

import com.finki.journalingapplication.model.Notes;
import com.finki.journalingapplication.model.User;
import com.finki.journalingapplication.repository.UserRepository;
import com.finki.journalingapplication.service.NotesService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping
public class NotesController {
    private final UserRepository userRepository;
    private final NotesService notesService;
    @GetMapping("/notes")
    private String getNotes(Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("User");
        model.addAttribute("user", userRepository.findById(currentUser.getId()).get());
        model.addAttribute("notes", notesService.getAllNotes(currentUser));
        return "notes.html";
    }
    @GetMapping("/add-notes")
    public String addNotes(Model model,HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        model.addAttribute("user", userRepository.findById(currentUser.getId()).get());
        return "add-notes";
    }
    @GetMapping("/notes/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        Optional<Notes> noteOptional = notesService.findById(id);
        Notes note = noteOptional.orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + id));
        model.addAttribute("note", note);
        return "add-notes";
    }
    @PostMapping("/notes")
    public String saveNote(@RequestParam String title ,
                           @RequestParam String description,HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        this.notesService.save(title,description,currentUser);
        return "redirect:/notes";
    }
    @PostMapping("/notes/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam String description,HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        this.notesService.edit(id, title, description,currentUser);
        return "redirect:/notes";
    }
    @PostMapping("/notes/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.notesService.deleteNotes(id);
        return "redirect:/notes";
    }

    @GetMapping("/notes/suggestions")
    @ResponseBody
    public List<String> getSuggestions(@RequestParam String query, HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        return notesService.getNoteSuggestions(query, currentUser);
    }


}
