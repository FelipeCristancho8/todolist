package com.felipe.todolist.domain.items;

import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;

public class ItemMediatorDefault implements ItemMediator{

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
        return null;
    }

    @Override
    public Item create(Item item, Long idList) {
        this.itemValidator.validateToCreate(item);
        this.listMediator.validateExistsListById(idList);

        return this.itemRepository.save(item, idList);
    }

    @Override
    public Item update(Item item) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
