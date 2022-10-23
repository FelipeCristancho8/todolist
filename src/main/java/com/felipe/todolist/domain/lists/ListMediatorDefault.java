package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;

import java.util.NoSuchElementException;

import static com.felipe.todolist.domain.util.StringUtils.*;

public class ListMediatorDefault implements ListMediator {
    private final ListRepository listRepository;

    public ListMediatorDefault(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public ToDoList  create(ToDoList toDoList){
        validateToCreate(toDoList);
        return listRepository.save(toDoList);
    }

    @Override
    public void delete(Long id) {
        validateExistsListById(id);
        this.listRepository.delete(id);
    }

    @Override
    public ToDoList findById(Long id) {
        validateExistsListById(id);
        return this.listRepository.findById(id);
    }

    //Extraer este metodo y que esta clase lo use inyectandolo por medio de spring
    private void validateToCreate(ToDoList toDoList) {
        StringBuilder details = new StringBuilder();
        if(isNullOrBlank(toDoList.getName()))  details.append("Name is empty").append(System.lineSeparator());
        if(isNullOrBlank(toDoList.getUser()))  details.append("User is empty").append(System.lineSeparator());

        if(isNotNullOrBlank(toDoList.getUser()) && isNotEmail(toDoList.getUser()))
            details.append("The user does not have the email format").append(System.lineSeparator());

        if(details.length() != 0){
            throw new IllegalArgumentException(details.toString());
        }
    }

    private void validateToUpdate(ToDoList toDoList){
        StringBuilder details = new StringBuilder();
        if(isNullOrBlank(toDoList.getName()))  details.append("Name is empty").append(System.lineSeparator());

        if(details.length() != 0){
            throw new IllegalArgumentException(details.toString());
        }
    }

    private void validateExistsListById(Long id){
        StringBuilder details = new StringBuilder();
        boolean exists = this.listRepository.existsById(id);
        if(!exists){
            details.append("Elelement not found").append(System.lineSeparator());
            throw new NoSuchElementException(details.toString());
        }
    }


}
