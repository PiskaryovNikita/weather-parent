package com.nixsolutions.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nixsolutions.model.Todo;
import com.nixsolutions.service.TodoService;

@RestController
@RequestMapping(path = "/api/todos", consumes = { "application/json", "*/*" }, produces = "application/json")
public class TodoController {
	@Autowired
	private TodoService todoService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Todo create(@RequestBody Todo entity) {
		return todoService.save(entity);
	}

	@GetMapping
	public List<Todo> getAll() {
		List<Todo> list = new ArrayList<>();
		Iterable<Todo> iterable = todoService.findAll();
		for (Todo todo : iterable) {
			list.add(todo);
		}
		return list;
	}
	
	@GetMapping(path="/{name}")
	public Todo get(@PathVariable("name") String name) {
		return todoService.findByName(name);
	}
	
	@PutMapping(path="/{name}")
	public Todo update(@PathVariable("name") String name, @RequestBody Todo entity) {
		Todo todo = todoService.findByName(name);
		entity.setId(todo.getId());
		return todoService.update(entity);
	}
	
	@DeleteMapping(path="/{name}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("name") String name) {
		todoService.delete(todoService.findByName(name));
	}
}
