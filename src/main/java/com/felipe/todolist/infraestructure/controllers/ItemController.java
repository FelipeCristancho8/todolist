package com.felipe.todolist.infraestructure.controllers;

import com.felipe.todolist.domain.items.ItemMediator;
import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.infraestructure.mappers.ItemMapper;
import com.felipe.todolist.infraestructure.model.items.ItemBasicVO;
import com.felipe.todolist.infraestructure.model.items.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {

    @Autowired
    private ItemMediator itemMediator;

    @Autowired
    private ItemMapper itemMapper;

    @PostMapping("/items")
    public ResponseEntity<ItemVO> create(@RequestBody ItemBasicVO item, @RequestParam Long idList){
        Item itemToCreate = itemMapper.itemBasicVOToItem(item);
        Item itemCreated = itemMediator.create(itemToCreate, idList);
        ItemVO itemResponse = itemMapper.itemToItemVO(itemCreated);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ItemVO> findById(@PathVariable Long id){
        Item foundItem = itemMediator.findById(id);
        ItemVO itemResponse = itemMapper.itemToItemVO(foundItem);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);

    }
}
