package com.nta.learning.controller;

import com.nta.learning.entity.Author;
import com.nta.learning.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final AuthorRepository repository;

    // Constructor injection (best practice)
    @Autowired
    public UserController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/index")
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        final List<Author> authors = repository.findAll();
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        final Page<Author> userPagination = repository.findAll(pageable);
        model.addAttribute("users", userPagination);
        return "index";
    }


    @GetMapping("/addUserForm")
    public String showSignUpForm(Author author) {
        return "add-user-form";
    }

    @PostMapping("/addUser")
    public String addUser(@Validated Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user-form";
        }
        repository.save(author);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        Author author = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        repository.delete(author);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Author author = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        model.addAttribute(author);
        return "update-user-form";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated Author author,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            author.setId(id);
            return "update-user-form";
        }
        repository.save(author);
        return "redirect:/";
    }


}