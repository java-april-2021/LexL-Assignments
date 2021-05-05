package com.lex.dogs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lex.dogs.models.Dog;
import com.lex.dogs.models.Tag;
import com.lex.dogs.services.DogService;
import com.lex.dogs.services.TagService;

@Controller
public class DogController {
	@Autowired
	private DogService dService;
	@Autowired
	private TagService tService;
	
	@GetMapping("/")
	public String index(Model viewModel) {
		List<Dog> allDogs = this.dService.getAllDogs();
		viewModel.addAttribute("dogs", allDogs);
		return "index.jsp";
	}
	@GetMapping("/add")
	public String addPet(@ModelAttribute("dog")Dog dog) {
		return "new.jsp";
	}
	@PostMapping("/add")
	public String newPet(@Valid @ModelAttribute("dog")Dog dog, BindingResult result) {
		if (result.hasErrors()) {
			return"new.jsp";
		}
		this.dService.createDog(dog);
		return "redirect:/";
	}
	
	@PostMapping("/addTag/{id}")
	public String addTag(@Valid @ModelAttribute("tag")Tag tag, BindingResult result, @PathVariable("id")Long id) {
		Dog dogToAdd = this.dService.getSingleDog(id);
		tag.setDog(dogToAdd);
		this.tService.createTag(tag);
		return "redirect:/{id}";
	}
	
	@GetMapping("/{id}")
	public String showDog(@PathVariable("id")Long id, Model viewModel, @ModelAttribute("tag") Tag tag) {
		Dog dogToShowcase = this.dService.getSingleDog(id);
		viewModel.addAttribute("dog",dogToShowcase);
		return "show.jsp";
	}
	
	@PostMapping("/addHTMLway")
	public String newPetHTML(@RequestParam("name") String name, @ RequestParam("breed") String breed, @RequestParam("age") int age, RedirectAttributes redirectAttr) {
		ArrayList<String> errors = new ArrayList<String>();
		if(name.equals("")) {
			errors.add("Please add name");
		}
		if (errors.size() > 0) {
			for(String e : errors) {
				redirectAttr.addFlashAttribute("errors", e);
				
			}
			return "redirect:/add";
			
		}
		this.dService.createDogHTML(name, breed, age);
		return "redirect:/";
	}
}