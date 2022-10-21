package com.felipe.todolist.infraestructure.endpoints;

import com.felipe.todolist.domain.lists.ListCreator;
import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.mappers.ToDoListMapper;
import com.felipe.todolist.infraestructure.model.ToDoListError;
import com.felipe.todolist.infraestructure.model.ToDoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListController {

    @Autowired
    private ListCreator listCreator;

    @PostMapping("/lists")
    public ResponseEntity<?> create(@RequestBody ToDoListVO toDoListVO) {

        ToDoList toDoListToCreate = ToDoListMapper.toDoList(toDoListVO);
        ToDoList toDoListCreated = listCreator.create(toDoListToCreate);
        ToDoListVO toDoListVOCreated = ToDoListMapper.toDoListVo(toDoListCreated);
        return new ResponseEntity<>(toDoListVOCreated, HttpStatus.CREATED);
    }
}
