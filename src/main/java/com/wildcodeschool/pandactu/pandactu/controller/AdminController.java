package com.wildcodeschool.pandactu.pandactu.controller;

import com.google.common.hash.Hashing;
import com.wildcodeschool.pandactu.pandactu.entity.Admin;
import com.wildcodeschool.pandactu.pandactu.repository.AdminRepository;
import com.wildcodeschool.pandactu.pandactu.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ArticleRepository articleRepository;


    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String getConnection(@ModelAttribute Admin admin, HttpSession session) {
        String encryptedPassword = Hashing.sha256()
                .hashString("!p4nda" + admin.getPassword(), StandardCharsets.UTF_8)
                .toString();
        admin.setPassword(encryptedPassword);
        Optional<Admin> optionalAdmin = adminRepository.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
        if (optionalAdmin.isPresent()) {
            admin = optionalAdmin.get();
            session.setAttribute("session", admin);
            return "redirect:/administration";
        }
        return "redirect:/administration";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("session", null);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model out) {
        out.addAttribute("articles", articleRepository.findByOrderByDateDesc());
        return "home";
    }

    @GetMapping("/administration")
    public String administration(Model out, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session");
        out.addAttribute("isAdmin", admin != null);
        out.addAttribute("adminName", admin != null ? admin.getName() : "");
        out.addAttribute("admin", new Admin());
        return "administration";
    }

    @PostMapping("/admin/create")
    public String createAdmin(@ModelAttribute Admin admin, HttpSession session) {
        Admin connectedAdmin = (Admin) session.getAttribute("session");
        if (connectedAdmin == null) {
            //TODO: FORBIDDEN
        }
        String encryptedPassword = Hashing.sha256()
                .hashString("!p4nda" + admin.getPassword(), StandardCharsets.UTF_8)
                .toString();
        admin.setPassword(encryptedPassword);
        adminRepository.save(admin);
        return "redirect:/administration";
    }
}
