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
		LocalDateTime dateTime = parsedDate.atStartOfDay();
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
			Tasks task = new Tasks();
			task.setTitle(taskForm.getTitle());
			task.setText(taskForm.getText());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime date = LocalDateTime.parse(taskForm.getDate(), formatter);
			task.setDate(date);
			System.out.println("Parsed date: " + date);

			repo.save(task);

			return "redirect:/main";
		}
	}

}
//dateをStringからLocalDateに変えて保存する
//カレンダーで月の終わりの日で週の表示を終わらせる
//editの方もやる
