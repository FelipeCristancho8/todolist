package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;

import static com.felipe.todolist.domain.util.StringUtils.isNotNullOrBlank;
import static com.felipe.todolist.domain.util.StringUtils.isNullOrBlank;
import static com.felipe.todolist.domain.util.StringUtils.isNotEmail;

public class ListCreatorDefault implements ListCreator{
    private ListRepository listRepository;

    public ListCreatorDefault(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public ToDoList  create(ToDoList toDoList){
        validate(toDoList);
        return listRepository.save(toDoList);
    }

    private void validate(ToDoList toDoList) {
        StringBuilder details = new StringBuilder();
        if(isNullOrBlank(toDoList.getName()))  details.append("Name is empty \n");
        if(isNullOrBlank(toDoList.getUser()))  details.append("User is empty \n");

        if(isNotNullOrBlank(toDoList.getUser()) && isNotEmail(toDoList.getUser()))
            details.append("The user does not have the email format \n");

        if(details.length() != 0){
            throw new IllegalArgumentException(details.toString());
        }
    }
}
