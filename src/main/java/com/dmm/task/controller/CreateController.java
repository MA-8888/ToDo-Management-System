package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.entity.Create;
import com.dmm.task.entity.Tasks;
import com.dmm.task.service.CreateService;

@Controller
public class CreateController {
	@Autowired
	private CreateService service;

	@GetMapping("/main/create/{date}")
	public String showCreateForm(Model model) {
		model.addAttribute("create", new Create());
		return "create";
	}

	@PostMapping("/main/create/")
	public String create(@ModelAttribute Create create) {
		Tasks task = new Tasks();
		service.save(create);
		return "redirect:/main";
	}

}
