package com.felipe.todolist.infraestructure.controllers;

import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.mappers.ToDoListMapper;
import com.felipe.todolist.infraestructure.model.ToDoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListController {

    @Autowired
    private ListMediator listMediator;

    @PostMapping("/lists")
    public ResponseEntity<?> create(@RequestBody ToDoListVO toDoListVO) {

        ToDoList toDoListToCreate = ToDoListMapper.toDoList(toDoListVO);
        ToDoList toDoListCreated = listMediator.create(toDoListToCreate);
        ToDoListVO toDoListVOCreated = ToDoListMapper.toDoListVo(toDoListCreated);
        return new ResponseEntity<>(toDoListVOCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/lists/{id}")
    public void delete(@PathVariable Long id){
        listMediator.delete(id);
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(listMediator.findById(id), HttpStatus.OK);
    }

}
