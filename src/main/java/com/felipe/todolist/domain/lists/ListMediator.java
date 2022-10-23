package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;

public interface ListMediator {

    ToDoList create(ToDoList toDoList);

    void delete(Long id);

    ToDoList findById(Long id);
}
