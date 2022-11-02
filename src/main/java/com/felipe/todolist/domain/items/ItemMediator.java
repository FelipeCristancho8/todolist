package com.felipe.todolist.domain.items;

import com.felipe.todolist.domain.model.Item;

public interface ItemMediator {

    Item findById(Long id);
    Item create(Item item, Long idList);
    Item update(Item item);
    boolean finishItem(Long idItem);
}
