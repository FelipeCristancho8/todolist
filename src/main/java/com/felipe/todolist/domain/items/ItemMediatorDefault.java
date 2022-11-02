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
    public Item create(Item item, Long idList) {
        this.itemValidator.validateItem(item);
        this.listMediator.validateExistsListById(idList);

        return this.itemRepository.save(item, idList);
    }

    @Override
    public Item update(Item item) {
        this.itemValidator.validateItem(item);
        Item foundItem = this.findById(item.getId());
        Item preparedItem = this.prepareToUpdate(item, foundItem);
        return this.itemRepository.update(preparedItem);
    }

    @Override
    public Item findById(Long id) {
        validateExistsItemById(id);
        return this.itemRepository.findById(id);
    }

    private Item prepareToUpdate(Item itemToUpdate, Item itemSaved){
        Item preparedItem = Item.builder().id(itemSaved.getId())
                        .finished(itemSaved.isFinished())
                        .createdAt(itemSaved.getCreatedAt())
                        .build();
        preparedItem.setName(itemToUpdate.getName());

        return preparedItem;
    }


    private void validateExistsItemById(Long id){
        StringBuilder details = new StringBuilder();
        boolean exists = this.itemRepository.existsById(id);
        if(!exists){
            details.append(ITEM_NOT_FOUND);
            throw new NoSuchElementException(details.toString());
        }
    }

    @Override
    public boolean finishItem(Long idItem) {
        this.validateExistsItemById(idItem);
        return this.itemRepository.finishItem(idItem);
    }
}
