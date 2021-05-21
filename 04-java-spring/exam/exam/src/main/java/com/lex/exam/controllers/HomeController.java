package com.lex.exam.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lex.exam.models.User;
import com.lex.exam.services.UserService;
import com.lex.exam.validators.UserValidator;

@Controller
public class HomeController {
	@Autowired
	private UserService uService;
	@Autowired
	private UserValidator validator;
	
	
	
	
	@GetMapping("/")
	public String baseRoute(Model viewModel, @ModelAttribute("user")User user) {
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user")User user, BindingResult result, HttpSession session) {
		validator.validate(user, result);
		if(result.hasErrors()) {
			return "index.jsp";
		}
		
		User newUser = this.uService.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/dashboard";
		
	}
	
	
	@GetMapping("/thoughts")
	public String index(Model viewModel, HttpSession session) {
		Long userId = (Long)session.getAttribute("user_id");
		//System.out.println((Long)session.getAttribute("user_id"));
		if(userId == null) {
			return"redirect:/";
		}
		
		
		System.out.println(userId);
		User user = this.uService.find(userId);
		//List<Dog> allDogs = this.dService.getAllDogs();
		//viewModel.addAttribute("dogs", allDogs);
		viewModel.addAttribute("user", user);
		return "index.jsp";
	}
	
	
	
	
	@PostMapping("/login")
	public String login(@RequestParam("loginEmail") String email, @RequestParam("loginPass") String password, RedirectAttributes redirectAttr, HttpSession session) {
		if(!this.uService.authenticateUser(email, password)) {
			redirectAttr.addFlashAttribute("loginerror","Invalid Credentials");
			return "redirect:/";
		}
		User user = this.uService.getByEmail(email);
		//System.out.println(user.getId());
		session.setAttribute("user_id", user.getId());
		//System.out.println((Long)session.getAttribute("user_id"));

		return "redirect:/dashboard";
		
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@GetMapping("/dashboard")
	private String dashboard(Model viewModel, HttpSession session) {
		Long userId =(Long)session.getAttribute("user_id");
		//viewModel.addAttribute("user", this.uService.find(userId));
		//viewModel.addAttribute("thoughts", this.tService.getThoughts());
		return "dashboard.jsp";
	}
}
