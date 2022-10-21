package com.felipe.todolist.domain.persistence;

import com.felipe.todolist.domain.model.ToDoList;

public interface ListRepository {
    ToDoList save(ToDoList toDoList);
}
