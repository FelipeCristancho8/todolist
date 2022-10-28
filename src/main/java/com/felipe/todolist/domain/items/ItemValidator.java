package com.felipe.todolist.domain.items;

import com.felipe.todolist.domain.model.Item;

import static com.felipe.todolist.domain.util.StringUtils.isNullOrBlank;

public class ItemValidator {

    private static final String NAME_IS_EMPTY = "Name is empty";
    private static final int ZERO = 0;

    public void validateToCreate(Item item){
        StringBuilder details = new StringBuilder();
        if(isNullOrBlank(item.getName())) details.append(NAME_IS_EMPTY);

        if(details.length() != ZERO){
            throw new IllegalArgumentException(details.toString());
        }
    }
}
