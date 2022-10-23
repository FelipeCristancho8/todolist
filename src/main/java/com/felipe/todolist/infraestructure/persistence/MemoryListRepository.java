package com.felipe.todolist.infraestructure.persistence;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryListRepository implements ListRepository {

    private List<ToDoList> lists;

    public MemoryListRepository() {
        this.lists = new ArrayList();
    }

    @Override
    public ToDoList save(ToDoList toDoList) {
        lists.add(toDoList);

        toDoList.setId((long) lists.size());
        toDoList.setName(toDoList.getName());
        toDoList.setDescription(toDoList.getDescription());
        toDoList.setUser(toDoList.getUser());
        return toDoList;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public ToDoList update(ToDoList toDoList) {
        return null;
    }

    @Override
    public ToDoList findById(Long id) {
        return null;
    }
}
