package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;

import static com.felipe.todolist.domain.util.StringUtils.*;

public class ListValidator {

    private static final String NAME_IS_EMPTY = "Name is empty";
    private static final String USER_IS_EMPTY = "User is empty";
    private static final String INVALID_EMAIL_FORMAT = "The user does not have the email format";
    private static final int ZERO = 0;

    public void validateToCreate(ToDoList toDoList) {
        StringBuilder details = new StringBuilder();
        if(isNullOrBlank(toDoList.getName()))  details.append(NAME_IS_EMPTY).append(System.lineSeparator());
        if(isNullOrBlank(toDoList.getUser()))  details.append(USER_IS_EMPTY).append(System.lineSeparator());

        if(isNotNullOrBlank(toDoList.getUser()) && Boolean.TRUE.equals(isNotEmail(toDoList.getUser())))
            details.append(INVALID_EMAIL_FORMAT).append(System.lineSeparator());

        if(details.length() != ZERO){
            throw new IllegalArgumentException(details.toString());
        }
    }

    public void validateToUpdate(ToDoList toDoList){
        StringBuilder details = new StringBuilder();
        if(isNullOrBlank(toDoList.getName()))  details.append(NAME_IS_EMPTY).append(System.lineSeparator());

        if(details.length() != ZERO){
            throw new IllegalArgumentException(details.toString());
        }
    }
}
