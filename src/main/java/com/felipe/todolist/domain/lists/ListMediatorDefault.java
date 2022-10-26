package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;

import java.util.NoSuchElementException;

public class ListMediatorDefault implements ListMediator {
    private static final String ELEMENT_NOT_FOUND = "Element not found";
    private final ListRepository listRepository;
    private final ListValidator listValidator;

    public ListMediatorDefault(ListRepository listRepository, ListValidator listValidator) {
        this.listRepository = listRepository;
        this.listValidator = listValidator;
    }

    @Override
    public ToDoList  create(ToDoList toDoList){
        listValidator.validateToCreate(toDoList);
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
        //setear items
        return this.listRepository.findById(id);
    }

    @Override
    public ToDoList update(ToDoList toDoList) {
        listValidator.validateToUpdate(toDoList);
        ToDoList toUpdate = findById(toDoList.getId());
        ToDoList preparedToDoList = prepareToUpdate(toDoList, toUpdate);
        return this.listRepository.update(preparedToDoList);
    }

    private void validateExistsListById(Long id){
        StringBuilder details = new StringBuilder();
        boolean exists = this.listRepository.existsById(id);
        if(!exists){
            details.append(ELEMENT_NOT_FOUND);
            throw new NoSuchElementException(details.toString());
        }
    }

    private ToDoList prepareToUpdate(ToDoList toDoListToUpdate, ToDoList toDoListSaved){
        ToDoList preparedToDoList = ToDoList.builder()
                .id(toDoListSaved.getId())
                .user(toDoListSaved.getUser()).build();
        preparedToDoList.setName(toDoListToUpdate.getName());

        if(toDoListToUpdate.getDescription() != null)
            preparedToDoList.setDescription(toDoListToUpdate.getDescription());
        return preparedToDoList;
    }
}
