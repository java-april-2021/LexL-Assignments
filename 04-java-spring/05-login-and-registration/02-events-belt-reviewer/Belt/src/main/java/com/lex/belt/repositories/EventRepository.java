package com.lex.belt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lex.belt.models.Event;

public interface EventRepository extends CrudRepository<Event, Long>{
	List<Event> findByState(String state);
	List<Event> findByStateIsNot(String state);
}