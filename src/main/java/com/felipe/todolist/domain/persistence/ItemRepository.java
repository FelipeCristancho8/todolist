package com.felipe.todolist.domain.persistence;

import com.felipe.todolist.domain.model.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> findItemsByToDoListId(Long id);
    Item save(Item item, Long idList);
    void deleteById(Long id);
    boolean existsById();
    Item update(Item item);
    Item findById(Long id);
}
