package com.felipe.todolist.infraestructure.mappers;

import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.infraestructure.model.items.ItemBasicVO;
import com.felipe.todolist.infraestructure.model.items.ItemVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item itemBasicVOToItem(ItemBasicVO itemBasicVO);
    ItemVO itemToItemVO(Item item);
}
