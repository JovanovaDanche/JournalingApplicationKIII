package com.finki.journalingapplication.web.controllers;

import com.finki.journalingapplication.model.Diary;
import com.finki.journalingapplication.repository.UserRepository;
import com.finki.journalingapplication.service.DiaryService;
import com.finki.journalingapplication.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping
public class DiaryPageController {

    private final DiaryService diaryService;
    private final UserRepository userRepository;




   @GetMapping("/diaries")
    private String getDiaryPage(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate, Model model, HttpSession session) {

        User sessionUser = (User) session.getAttribute("User");
        LocalDate currentDate = LocalDate.now();


        if (selectedDate != null) {
            List<Diary> userDiaries = diaryService.findByUserIdAndDate(sessionUser.getId(), selectedDate);
            model.addAttribute("selectedDate", selectedDate);
            model.addAttribute("diaries", userDiaries);
        } else {
            List<Diary> userDiaries = diaryService.findByUserIdAndDate(sessionUser.getId(), currentDate);
            model.addAttribute("selectedDate", currentDate);
            model.addAttribute("diaries", userDiaries);
        }
        model.addAttribute("user", sessionUser);

        return "diaries";
    }


    @GetMapping("/diaries/add")
    private String addDiary(HttpSession session,Model model) {

        return "add_diary";
    }@PostMapping("/diaries")
    public String create(@RequestParam String content,
                         @RequestParam String title,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                         HttpSession session) {

        User sessionUser = (User) session.getAttribute("User");

        diaryService.create(content,title,date,sessionUser.getId());

        return "redirect:/diaries";
    }



    @GetMapping("/diaries/edit/{id}")
    private String editDiary(@PathVariable Long id,
                             HttpSession session,
                             Model model) {

        Diary diary = this.diaryService.findById(id);


        model.addAttribute("diary", diary);
        return "add_diary";
    }

    @PostMapping("/diaries/{id}")
    private String update(@PathVariable Long id,
                          @RequestParam String content,
                          @RequestParam String title,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                          HttpSession session) {

        User sessionUser = (User) session.getAttribute("User");
        diaryService.edit(id,content,title,sessionUser.getId(),date);
        return "redirect:/diaries";
    }

    @PostMapping("/diaries/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.diaryService.delete(id);
        return "redirect:/diaries";
    }



}
