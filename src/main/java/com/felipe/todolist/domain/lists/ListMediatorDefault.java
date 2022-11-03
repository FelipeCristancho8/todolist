package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ItemRepository;
import com.felipe.todolist.domain.persistence.ListRepository;


import java.util.List;
import java.util.NoSuchElementException;

public class ListMediatorDefault implements ListMediator {
    private static final String ELEMENT_NOT_FOUND = "List not found";
    private final ListRepository listRepository;

    private final ItemRepository itemRepository;
    private final ListValidator listValidator;

    public ListMediatorDefault(ListRepository listRepository, ItemRepository repository, ListValidator listValidator) {
        this.listRepository = listRepository;
        this.itemRepository = repository;
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
    public ToDoList update(ToDoList toDoList) {
        listValidator.validateToUpdate(toDoList);
        ToDoList toUpdate = findById(toDoList.getId());
        ToDoList preparedToDoList = prepareToUpdate(toDoList, toUpdate);
        return this.listRepository.update(preparedToDoList);
    }

    @Override
    public ToDoList findById(Long id) {
        validateExistsListById(id);
        ToDoList foundTodoList = this.listRepository.findById(id);
        List<Item> itemsOfTodoList = this.itemRepository.findItemsByToDoListId(id);
        foundTodoList.addItems(itemsOfTodoList);

        return foundTodoList;
    }

    private ToDoList prepareToUpdate(ToDoList toDoListToUpdate, ToDoList toDoListSaved){
        ToDoList preparedToDoList = ToDoList.builder()
                .id(toDoListSaved.getId())
                .user(toDoListSaved.getUser())
                .description(toDoListSaved.getDescription()).build();
        preparedToDoList.setName(toDoListToUpdate.getName());

        if(toDoListToUpdate.getDescription() != null)
            preparedToDoList.setDescription(toDoListToUpdate.getDescription());
        return preparedToDoList;
    }

    public void validateExistsListById(Long id){
        StringBuilder details = new StringBuilder();
        boolean exists = this.listRepository.existsById(id);
        if(!exists){
            details.append(ELEMENT_NOT_FOUND);
            throw new NoSuchElementException(details.toString());
        }
    }
}
