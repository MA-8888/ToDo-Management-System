package com.dmm.task.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.TaskForm;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;

@Controller
public class EditController {

	@Autowired
	private TasksRepository repo;

	@GetMapping("/main/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model) {
		Tasks task = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
		model.addAttribute("task", task);
		return "edit";
	}

	@PostMapping("/main/edit")
	public String updateTask(@Validated TaskForm taskForm) {
		Tasks task = new Tasks();
		task.setTitle(taskForm.getTitle());
		task.setText(taskForm.getText());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(taskForm.getDate(), formatter);
		task.setDate(date);
		task.setDone(taskForm.isDone());
		repo.save(task);

		return "redirect:/main";
	}

	@PostMapping("/main/delete")
	public String deleteTask(@PathVariable("id") Integer id) {
		Tasks task = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
		repo.delete(task);

		return "redirect:/main";
	}
}
