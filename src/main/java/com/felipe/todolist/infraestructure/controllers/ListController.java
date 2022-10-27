package com.felipe.todolist.infraestructure.controllers;

import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.mappers.ListMapper;
import com.felipe.todolist.infraestructure.model.ToDoListBasicVO;
import com.felipe.todolist.infraestructure.model.ToDoListVO;
import com.felipe.todolist.infraestructure.model.ToDoListWithDateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListController {

    @Autowired
    private ListMediator listMediator;

    @Autowired
    private ListMapper listMapper;

    @PostMapping("/lists")
    public ResponseEntity<ToDoListWithDateVO> create(@RequestBody ToDoListWithDateVO toDoListWithDateVO) {

        ToDoList toDoListToCreate = listMapper.todoListVoToToDoList(toDoListWithDateVO);
        ToDoList toDoListCreated = listMediator.create(toDoListToCreate);
        ToDoListWithDateVO toDoListWithDateVOCreated = listMapper.todoListTotodoListVoWithDate(toDoListCreated);
        return new ResponseEntity<>(toDoListWithDateVOCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/lists/{id}")
    public void delete(@PathVariable Long id){
        listMediator.delete(id);
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<ToDoListVO> findById(@PathVariable Long id){
        ToDoList foundTodoList = listMediator.findById(id);
        ToDoListVO foundTodoListVO = listMapper.todoListToTodoListVo(foundTodoList);
        return new ResponseEntity<>(foundTodoListVO, HttpStatus.OK);
    }

    @PatchMapping("/lists/{id}")
    public ResponseEntity<ToDoListWithDateVO> update(@RequestBody ToDoListBasicVO toDoListBasicVO, @PathVariable Long id){
        ToDoList toDoListToUpdate = listMapper.todoListBasicToTodoList(toDoListBasicVO);
        toDoListToUpdate.setId(id);
        ToDoList toDoListCreated = listMediator.update(toDoListToUpdate);
        ToDoListWithDateVO toDoListWithDateVOCreated = listMapper.todoListTotodoListVoWithDate(toDoListCreated);
        return new ResponseEntity<>(toDoListWithDateVOCreated, HttpStatus.OK);
    }
}
