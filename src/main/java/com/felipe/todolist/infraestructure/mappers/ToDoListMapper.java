package com.felipe.todolist.infraestructure.mappers;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.model.ToDoListVO;

import java.time.LocalDate;

public class ToDoListMapper {

    public static ToDoList toDoList(ToDoListVO toDoListVO){
        ToDoList toDoList = new ToDoList();
        toDoList.setId(toDoListVO.getId());
        toDoList.setName(toDoListVO.getName());
        toDoList.setDescription(toDoListVO.getDescription());
        toDoList.setUser(toDoListVO.getUser());
        return toDoList;
    }

    public static ToDoListVO toDoListVo(ToDoList toDoList){
        ToDoListVO toDoListVO = new ToDoListVO();
        toDoListVO.setId(toDoList.getId());
        toDoListVO.setName(toDoList.getName());
        toDoListVO.setDescription(toDoList.getDescription());
        toDoListVO.setUser(toDoList.getUser());
        toDoListVO.setDate(LocalDate.now());
        return toDoListVO;
    }
}
