package com.finki.journalingapplication.web.controllers;

import com.finki.journalingapplication.model.User;
import com.finki.journalingapplication.model.exceptionPassword.InvalidPasswordException;
import com.finki.journalingapplication.model.exceptionPassword.types.PasswordNotMatchException;
import com.finki.journalingapplication.model.exceptionPassword.types.Username0rPasswordDoesntMatchException;
import com.finki.journalingapplication.model.exceptionPassword.types.UsernameExistsException;
import com.finki.journalingapplication.model.exceptionPassword.types.UsernameInPasswordException;
import com.finki.journalingapplication.repository.UserRepository;
import com.finki.journalingapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    private String login() {
        return "login";
    }
    @GetMapping("/register")
    private String register() {
        return "register";
    }
    @PostMapping("/register")
    private String registerAccount(Model model, @RequestParam String name, @RequestParam String surname, @RequestParam String email,@RequestParam String username, @RequestParam String password, @RequestParam String rpassword) {
        try {
            userService.registerAccount(name, surname,email, username, password, rpassword);
        } catch (PasswordNotMatchException | UsernameExistsException | InvalidPasswordException |
                 UsernameInPasswordException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
    @PostMapping("/login")
    private String loginAccount(Model model, HttpSession session, @RequestParam String username, @RequestParam String password) {
        try {
            User currentUser = userService.loginAccount(username, password);
            session.setAttribute("User", currentUser);
        } catch (Username0rPasswordDoesntMatchException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
        return "redirect:/diaries";
    }
    @PostMapping("/auth-status")
    private String authStatus(HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        if (currentUser != null) {
            return "redirect:/diaries";
        }
        return "redirect:/login";
    }
    @GetMapping("/logout")
    private String logoutAccount(HttpSession session) {
        session.invalidate();
        return "redirect:/homepage";
    }
    @PostMapping("/changePass")
    private String changePass(@RequestParam String currentPassword,
                              @RequestParam String newPassword,
                              @RequestParam String confirmPassword, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        currentUser = userRepository.findById(currentUser.getId()).get();
        if (!(passwordEncoder.matches(currentPassword, currentUser.getPassword()))) {
            String exception = "Your current password is incorrect";
            String currentPasswordIncorrect = "true";
            String changePass = "true";

            return "redirect:/profile?currentPasswordIncorrect=" + currentPasswordIncorrect + "&changePass=" + changePass + "&messageException=" + exception;
        }
        if (!(newPassword.equals(confirmPassword))) {
            String exception = "Your passwords doesn't match";
            String passwordsDontMatch = "true";
            String changePass = "true";

            return "redirect:/profile?passwordsDontMatch=" + passwordsDontMatch + "&changePass=" + changePass + "&messageException=" + exception;
        }
        try {
            userService.updatePassword(currentUser.getUsername(), newPassword);

        } catch (InvalidPasswordException e) {
            String exception = e.getMessage();
            String passwordsDontMatch = "true";
            String changePass = "true";
            return "redirect:/profile?passwordsDontMatch=" + passwordsDontMatch + "&changePass=" + changePass + "&messageException=" + exception;

        }
        String successfullyChanged = "true";

        return "redirect:/profile?successfullyChanged=" + successfullyChanged;
    }
    @GetMapping("/changePass")
    private String changePass(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("User");
        model.addAttribute("user", userRepository.findById(currentUser.getId()).get());
        String changePass = "true";
        return "redirect:/profile?changePass=" + changePass;
    }
    @GetMapping("/profile")
    private String profile(Model model, HttpSession session,
                           @RequestParam(required = false) String changePass,
                           @RequestParam(required = false) String successfullyChanged,
                           @RequestParam(required = false) String passwordsDontMatch,
                           @RequestParam(required = false) String messageException,
                           @RequestParam(required = false) String currentPasswordIncorrect) {
        User currentUser = (User) session.getAttribute("User");
        model.addAttribute("user", userRepository.findById(currentUser.getId()).get());
        model.addAttribute("changePass", changePass);
        model.addAttribute("successfullyChanged", successfullyChanged);
        model.addAttribute("message", messageException);
        model.addAttribute("passwordsDontMatch", passwordsDontMatch);
        model.addAttribute("currentPasswordIncorrect", currentPasswordIncorrect);
        return "profile";
    }

}
