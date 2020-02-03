package com.poeking.main.restfull.toDoItem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/todo")
public class ToDoItemController {

	@Autowired
	private ToDoItemService toDoItemService;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody ToDoItemResponse get(@PathVariable(value = "id") Long id) {
		List<String> errors = new ArrayList<>();
		ToDoItem toDoItem = null;
		try {
			toDoItem = toDoItemService.get(id);
		} catch (final Exception e) {
			errors.add(e.getMessage());
		}
		return ToDoItemAdapter.toToDoItemResponse(toDoItem, errors);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ToDoItemResponse> getAll() {
		List<String> errors = new ArrayList<>();
		List<ToDoItem> toDoItems = toDoItemService.getAll();
		List<ToDoItemResponse> toDoItemResponses = new ArrayList<>();
		toDoItems.stream().forEach(toDoItem -> {
			toDoItemResponses.add(ToDoItemAdapter.toToDoItemResponse(toDoItem, errors));
		});
		return toDoItemResponses;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ToDoItemResponse create(@RequestBody final ToDoItemRequest toDoItemRequest) {
		List<String> errors = new ArrayList<>();
		ToDoItem toDoItem = ToDoItemAdapter.toToDoItem(toDoItemRequest);
		try {
			toDoItem = toDoItemService.create(toDoItem);
		} catch (final Exception e) {
			errors.add(e.getMessage());
		}
		return ToDoItemAdapter.toToDoItemResponse(toDoItem, errors);
	}
	/* update */
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody ToDoItemResponse update(@RequestBody final ToDoItemRequest toDoItemRequest) {
		List<String> errors = new ArrayList<>();
		ToDoItem toDoItem = ToDoItemAdapter.toToDoItem(toDoItemRequest);
		try {
			toDoItem = toDoItemService.update(toDoItem);
		} catch (final Exception e) {
			errors.add(e.getMessage());
			e.printStackTrace();
		}
		return ToDoItemAdapter.toToDoItemResponse(toDoItem, errors);
	}
}
