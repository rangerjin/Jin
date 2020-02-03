package com.poeking.main.restfull.toDoItem;

import java.util.List;

import com.poeking.main.restfull.ApiResponse;

import lombok.Builder;

public class ToDoItemResponse extends ApiResponse<ToDoItem> {

	@Builder
	public ToDoItemResponse(final ToDoItem toDoItem, final List<String> errors) {
		super(toDoItem);
		this.setErrors(errors);
	}
}
