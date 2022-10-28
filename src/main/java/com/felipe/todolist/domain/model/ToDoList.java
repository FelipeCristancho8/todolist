package com.felipe.todolist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoList implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String user;
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    public void addItems(List<Item> items){
        this.items.addAll(items);
    }
}
