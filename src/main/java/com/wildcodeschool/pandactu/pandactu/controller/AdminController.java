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

    @GetMapping("/connection")
    public String connection(Model out) {
        Admin admin = new Admin();
        out.addAttribute("admin", admin);
        return "connection";
    }

    @PostMapping("/connection")
    public String getConnection(@ModelAttribute Admin admin, HttpSession session) {
        String encryptedPassword = Hashing.sha256()
                .hashString("!p4nda" + admin.getPassword(), StandardCharsets.UTF_8)
                .toString();
        admin.setPassword(encryptedPassword);
        Optional<Admin> optionalAdmin = adminRepository.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
        if (optionalAdmin.isPresent()) {
            admin = optionalAdmin.get();
            session.setAttribute("session", admin);
            if (admin.isConnected()) {
                return "redirect:/accueil";
            } else {
                return "redirect:/connection";
            }
        }
        return "redirect:/connection";
    }

    @GetMapping("/deconnection")
    public String deconnection() {
        return "home";
    }

    @GetMapping("/accueil")
    public String accueil(Model out) {
        out.addAttribute("articles", articleRepository.findAll());
        return "home";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/administration")
    public String administration() {
        return "administration";
    }

    @PostMapping("/admin/create")
    public String createAdmin(@ModelAttribute Admin admin) {
        adminRepository.save(admin);
        return "administration";
    }
}
