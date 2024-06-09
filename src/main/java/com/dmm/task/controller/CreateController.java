package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.TaskForm;
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

	@PostMapping("/main/create")
	public String create(@Validated TaskForm form, Model model) {
//dateをStringからLocalDateに変えて保存する
//カレンダーで月の終わりの日で週の表示を終わらせる
//editの方もやる
		Tasks task = new Tasks();
		task.setTitle(form.getTitle());
		task.setText(form.getText());
		task.setDate(form.getDate());
		
		repo.save(task);

		return "redirect:/main";
	}

}
