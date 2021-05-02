package com.lex.dogs.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lex.dogs.models.Dog;
import com.lex.dogs.repository.DogRepository;

@Service
public class DogService{
	// dependency inject our repository so this service has access to all methods in the repository interface
	private DogRepository dRepo;
	
	public DogService(DogRepository repo) {
		this.dRepo = repo;
	}
	// get all dogs
	public List<Dog> getAllDogs(){
		return this.dRepo.findAll();
	}
	// get one dog
	public Dog getSingleDog(Long id) {
		return this.dRepo.findById(id).orElse(null);
	}
	// Create a Dog
	public Dog createDog(Dog newDog) {
		return this.dRepo.save(newDog);
	}
	// update a dog
	public Dog updateDog(Dog dog) {
		return this.dRepo.save(dog);
	}
	// delete a dog
	public void deleteDog(Long id) {
		this.dRepo.deleteById(id);
	}
	// create Dog with HTML forms
	public Dog createDogHTML(String name, String breed, int age) {
		Dog newDog = new Dog(name, breed, age);
		return this.dRepo.save(newDog);
	}
	
	
}