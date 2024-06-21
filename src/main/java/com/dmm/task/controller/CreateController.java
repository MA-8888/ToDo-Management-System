package com.dmm.task.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.TaskForm;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class CreateController {
	@Autowired
	private TasksRepository repo;

	@GetMapping("/main/create/{date}")
	public String showCreateForm(@PathVariable String date, Model model, @Validated TaskForm taskForm) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedDate = LocalDate.parse(date, formatter);
		LocalDateTime dateTime = parsedDate.atTime(0, 0);

		model.addAttribute("date", dateTime);
		model.addAttribute("taskForm", new TaskForm());

		return "create";
	}

	@PostMapping("/main/create")
	public String post(@Validated TaskForm taskForm,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {

		model.addAttribute("taskForm", taskForm);

		LocalDate date = taskForm.getDate();
		LocalDateTime dateTime = date.atTime(0, 0);

		Tasks task = new Tasks();
		task.setTitle(taskForm.getTitle());
		task.setText(taskForm.getText());
		task.setName(user.getName());
		task.setDate(dateTime);

		repo.save(task);

		return "redirect:/main";
	}
}
