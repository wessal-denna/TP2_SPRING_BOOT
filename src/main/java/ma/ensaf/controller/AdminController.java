package ma.ensaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ma.ensaf.repository.UserRepository;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Accessible uniquement au rôle ADMIN (cf. SecurityConfig)
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin";
    }
}