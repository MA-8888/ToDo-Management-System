package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.entity.Create;
import com.dmm.task.service.CreateService;

@Controller
public class CreateController {
    @Autowired
    private CreateService service;

    @GetMapping("/create/@{date}")
    public String showCreateForm(Model model) {
        model.addAttribute("create", new Create());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Create create) {
        service.save(create);
        return "redirect:/main";
    }
}
