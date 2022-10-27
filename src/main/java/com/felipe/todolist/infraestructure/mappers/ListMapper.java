package com.felipe.todolist.infraestructure.mappers;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.infraestructure.model.ToDoListBasicVO;
import com.felipe.todolist.infraestructure.model.ToDoListVO;
import com.felipe.todolist.infraestructure.model.ToDoListWithDateVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public interface ListMapper {

    ToDoList todoListVoToToDoList(ToDoListWithDateVO toDoListWithDateVO);

    @Mapping(target = "date", expression = "java(LocalDate.now())")
    ToDoListWithDateVO todoListTotodoListVoWithDate(ToDoList toDoList);
    ToDoList todoListBasicToTodoList(ToDoListBasicVO toDoListBasic);

    ToDoListVO todoListToTodoListVo(ToDoList toDoList);
}
