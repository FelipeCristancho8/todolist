package com.felipe.todolist.domain.items;

import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;

import java.util.NoSuchElementException;

public class ItemMediatorDefault implements ItemMediator{

    private static final String ITEM_NOT_FOUND = "Item not found";
    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;
    private final ListMediator listMediator;


    public ItemMediatorDefault(ItemRepository itemRepository, ItemValidator itemValidator, ListMediator listMediator) {
        this.itemRepository = itemRepository;
        this.itemValidator = itemValidator;
        this.listMediator = listMediator;
    }

    @Override
    public Item findById(Long id) {
        validateExistsItemById(id);
        return this.itemRepository.findById(id);
    }

    @Override
    public Item create(Item item, Long idList) {
        this.itemValidator.validateToCreate(item);
        this.listMediator.validateExistsListById(idList);

        return this.itemRepository.save(item, idList);
    }


    private void validateExistsItemById(Long id){
        StringBuilder details = new StringBuilder();
        boolean exists = this.itemRepository.existsById(id);
        if(!exists){
            details.append(ITEM_NOT_FOUND);
            throw new NoSuchElementException(details.toString());
        }
    }
}
