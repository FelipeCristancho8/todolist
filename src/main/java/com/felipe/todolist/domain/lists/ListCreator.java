package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;

public interface ListCreator {

    public ToDoList create(ToDoList toDoList);
}
