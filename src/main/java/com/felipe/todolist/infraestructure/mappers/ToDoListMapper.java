package com.felipe.todolist.infraestructure.mappers;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.model.ToDoListBasicVO;
import com.felipe.todolist.infraestructure.model.ToDoListWithDateVO;

import java.time.LocalDate;

public class ToDoListMapper {

    private ToDoListMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ToDoList toDoList(ToDoListWithDateVO toDoListWithDateVO){
        ToDoList toDoList = new ToDoList();
        toDoList.setId(toDoListWithDateVO.getId());
        toDoList.setName(toDoListWithDateVO.getName());
        toDoList.setDescription(toDoListWithDateVO.getDescription());
        toDoList.setUser(toDoListWithDateVO.getUser());
        return toDoList;
    }

    public static ToDoListWithDateVO toDoListVoWithDate(ToDoList toDoList){
        ToDoListWithDateVO toDoListWithDateVO = new ToDoListWithDateVO();
        toDoListWithDateVO.setId(toDoList.getId());
        toDoListWithDateVO.setName(toDoList.getName());
        toDoListWithDateVO.setDescription(toDoList.getDescription());
        toDoListWithDateVO.setUser(toDoList.getUser());
        toDoListWithDateVO.setDate(LocalDate.now());
        return toDoListWithDateVO;
    }

    public static ToDoList toDoListBasictoDoList(ToDoListBasicVO toDoListBasicVO){
        ToDoList toDoList = new ToDoList();
        toDoList.setName(toDoListBasicVO.getName());
        toDoList.setDescription(toDoListBasicVO.getDescription());
        return toDoList;
    }

}
