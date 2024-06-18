package com.dmm.task.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	public String showEditForm(@PathVariable("id") Integer id, Model model, TaskForm taskForm) {
		Tasks task = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
		model.addAttribute("task", task);
		model.addAttribute("taskForm", new TaskForm());
		return "edit";
	}

	@PostMapping("/main/edit/{id}")
	public String updateTask(@PathVariable("id") Integer id, @Validated TaskForm taskForm) {
		Tasks task = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
		task.setTitle(taskForm.getTitle());
		task.setText(taskForm.getText());

		
		LocalDate date =taskForm.getDate();
		LocalDateTime dateTime = date.atTime(0,0);
		task.setDate(dateTime);
		task.setDone(taskForm.isDone());
		repo.save(task);

		return "redirect:/main";
	}

	@PostMapping("/main/delete/{id}")
	public String deleteTask(@PathVariable("id") Integer id) {
		Tasks task = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
		repo.delete(task);

		return "redirect:/main";
	}
}
