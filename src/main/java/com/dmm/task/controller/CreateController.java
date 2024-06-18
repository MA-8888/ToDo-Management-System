package com.dmm.task.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String post(@Validated TaskForm taskForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		if (bindingResult.hasErrors()) {
			List<Tasks> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			model.addAttribute("tasks", list);
			model.addAttribute("taskForm", taskForm);
			return "create";
		} else {
			if (user == null) {
				throw new IllegalStateException("認証情報が存在しません。");
			}

			Tasks task = new Tasks();
			task.setTitle(taskForm.getTitle());
			task.setText(taskForm.getText());

			// Userから名前を取得して設定
			task.setName(user.getUsername());

			LocalDate date = taskForm.getDate();
			LocalDateTime dateTime = date.atTime(0, 0);
			task.setDate(dateTime);
			task.setDone(taskForm.isDone());

			repo.save(task);

			return "redirect:/main";
		}
	}

	//	@PostMapping("/main/create")
	//	public String post(@Validated TaskForm taskForm, BindingResult bindingResult,
	//			@AuthenticationPrincipal AccountUserDetails user, Model model) {
	//		if (bindingResult.hasErrors()) {
	//			List<Tasks> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
	//			model.addAttribute("tasks", list);
	//			model.addAttribute("taskForm", taskForm);
	//			return "create";
	//		} else {
	//			Tasks task = new Tasks();
	//			task.setTitle(taskForm.getTitle());
	//			task.setText(taskForm.getText());
	//			task.setName(user.getUsername());
	//
	//			LocalDate date = taskForm.getDate();
	//			LocalDateTime dateTime = date.atTime(0, 0);
	//			task.setDate(dateTime);
	//			task.setDone(taskForm.isDone());
	//
	//			repo.save(task);
	//
	//			return "redirect:/main";
	//		}
	//	}
}
