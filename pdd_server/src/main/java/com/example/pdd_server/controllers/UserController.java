package com.example.pdd_server.controllers;

import com.example.pdd_server.models.User;
import com.example.pdd_server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static class RegisterRequest {
        public String name;
        public String phone;
        public String iin;
        public String password;
        public String about;
    }

    public static class LoginRequest {
        public String phone;
        public String password;
    }

    @PostMapping("/register")
    public Object registerUser(@RequestBody RegisterRequest req) {
        if (req.phone == null || req.phone.isBlank() || req.password == null || req.password.isBlank()) {
            return "Телефон и пароль обязательны";
        }
        if (userRepository.existsByPhone(req.phone)) {
            return "Пользователь с таким телефоном уже существует";
        }

        User u = new User();
        u.setName(req.name);
        u.setPhone(req.phone);
        u.setIin(req.iin);
        u.setAbout(req.about);
        u.setRole("USER");
        u.setPasswordHash(passwordEncoder.encode(req.password));

        userRepository.save(u);
        return "OK";
    }

    @PostMapping("/login")
    public Object loginUser(@RequestBody LoginRequest req) {
        var existingOpt = userRepository.findByPhone(req.phone);
        if (existingOpt.isEmpty()) return "Пользователь не найден";

        User existing = existingOpt.get();
        if (!passwordEncoder.matches(req.password, existing.getPasswordHash())) {
            return "Неверный пароль";
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", existing.getId());
        data.put("name", existing.getName());
        data.put("iin", existing.getIin());
        data.put("phone", existing.getPhone());
        data.put("about", existing.getAbout());
        data.put("role", existing.getRole());
        return data;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/users/id/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) return "Пользователь не найден";
        userRepository.deleteById(id);
        return "OK";
    }

    @DeleteMapping("/users/phone/{phone}")
    public String deleteUserByPhone(@PathVariable String phone) {
        var userOpt = userRepository.findByPhone(phone);
        if (userOpt.isEmpty()) return "Пользователь не найден";
        userRepository.delete(userOpt.get());
        return "OK";
    }
}


