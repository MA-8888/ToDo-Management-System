package com.dmm.task.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.entity.Create;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;

@Controller
public class CreateController {
	@Autowired
	private TasksRepository repo;

	@GetMapping("/main/create/{date}")
	public String showCreateForm(Model model) {
		model.addAttribute("create", new Create());
		return "create";
	}

	@PostMapping("/main/create/")
	public String create(@RequestParam("title") String title, @RequestParam("date")CharSequence dateString,
			@RequestParam("text") String text) {
		LocalDate date = LocalDate.parse(dateString);

		Tasks task = new Tasks(title, date, text);
		repo.save(task);

		return "redirect:/main";
	}

}
