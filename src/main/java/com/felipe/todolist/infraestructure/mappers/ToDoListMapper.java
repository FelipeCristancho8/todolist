package com.felipe.todolist.infraestructure.mappers;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.model.ToDoListBasicVO;
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

    public static ToDoListVO toDoListVoWithDate(ToDoList toDoList){
        ToDoListVO toDoListVO = new ToDoListVO();
        toDoListVO.setId(toDoList.getId());
        toDoListVO.setName(toDoList.getName());
        toDoListVO.setDescription(toDoList.getDescription());
        toDoListVO.setUser(toDoList.getUser());
        toDoListVO.setDate(LocalDate.now());
        return toDoListVO;
    }

    public static ToDoList toDoListBasictoDoList(ToDoListBasicVO toDoListBasicVO){
        ToDoList toDoList = new ToDoList();
        toDoList.setName(toDoListBasicVO.getName());
        toDoList.setDescription(toDoListBasicVO.getDescription());
        return toDoList;
    }

}
