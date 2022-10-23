package com.felipe.todolist.domain.persistence;


import com.felipe.todolist.domain.model.ToDoList;

public interface ListRepository {
    ToDoList save(ToDoList toDoList);

    void delete(Long id);

    boolean existsById(Long id);

    ToDoList update(ToDoList toDoList);

    ToDoList findById(Long id);
}
